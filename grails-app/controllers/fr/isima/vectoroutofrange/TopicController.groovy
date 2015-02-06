package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class TopicController {

    def topicService
    def springSecurityService

    static allowedMethods = [index       : 'GET',
                             view        : 'GET',
                             edit        : 'GET',
                             create      : 'GET',
                             save        : 'POST',
                             upvote      : 'POST',
                             downvote    : 'POST',
                             answer      : 'POST',
                             comment     : 'POST',
                             chooseAnswer: 'POST']

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [topics: Topic.list(params), topicCount: Topic.count()])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        try {
            long id = Long.parseLong((String) params.id)
            topicService.incrementViewsCount(id)
            render(view: 'view', model: [topic: topicService.getTopic(id)])
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    @Secured(['ROLE_CREATE_POST'])
    def edit() {
        render(view: 'edit', model: [post: Post.get(Long.parseLong((String) params.id))])
    }

    @Secured(['ROLE_CREATE_POST'])
    def create() {
        render(view: 'create')
    }

    @Secured(['ROLE_CREATE_POST'])
    def save() {
        String title = params.title
        String text = params.text
        List<String> tags = params.list('tags[]')
        User user = (User) springSecurityService.currentUser

        if (user != null) {
            try {
                if (params.id) {
                    Post post = (Post) topicService.correctPost(Long.parseLong((String) params.id), user.id, (String) params.text)
                    render(status: 200, text: post.topic.id)
                } else {
                    Topic topic = topicService.createNewTopic(user.id, title, text, tags)
                    render(status: 201, text: topic.id)
                }
            } catch (TopicServiceException e) {
                render(status: 404, text: e.getCode())
            }
        } else {
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    @Secured(['ROLE_VOTE_UP'])
    def upvote() {
        User user = (User) springSecurityService.currentUser
        if (user != null) {
            try {
                def id = Long.parseLong((String) params.id)
                topicService.voteForPost(id, user.id, VoteType.UPVOTE)
                render(status: 201, text: topicService.getScoreForPost(id))
            } catch (TopicServiceException e) {
                render(status: 404, text: e.getCode())
            }
        } else {
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    @Secured(['ROLE_VOTE_DOWN'])
    def downvote() {
        User user = (User) springSecurityService.currentUser
        if (user != null) {
            try {
                def id = Long.parseLong((String) params.id)
                topicService.voteForPost(id, user.id, VoteType.DOWNVOTE)
                render(status: 201, text: topicService.getScoreForPost(id))
            } catch (TopicServiceException e) {
                render(status: 404, text: e.getCode())
            }
        } else {
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    @Secured(['ROLE_CREATE_POST'])
    def answer() {
        String text = params.text
        long id = Long.parseLong((String) params.id);
        User user = (User) springSecurityService.currentUser

        if (user != null) {
            try {
                Post answer = topicService.addAnswer(id, user.id, text);

                render(status: 201, text: answer.id)
            } catch (TopicServiceException e) {
                render(status: 404, text: e.getCode())
            }
        } else {
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    @Secured(['ROLE_CREATE_COMMENT'])
    def comment() {
        String text = params.text
        long id = Long.parseLong((String) params.id);
        User user = (User) springSecurityService.currentUser

        if (user != null) {
            try {
                Post comment = topicService.addComment(id, user.id, text);

                render(status: 201, text: comment.id)
            } catch (TopicServiceException e) {
                render(status: 404, text: e.getCode())
            }
        } else {
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    @Secured(['ROLE_CREATE_POST'])
    def delete() {
        try {
            topicService.deletePost(Long.parseLong((String) params.id))

            render(status: 205)
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    @Secured(['ROLE_CREATE_POST'])
    def chooseAnswer() {
        try {
            // TODO: remove when service is modified
            // TODO: check if current user is question author
            def id = Long.parseLong((String) params.id)
            def topicId = Post.get(id).topic.id
            topicService.tagPostAsBestAnswer(topicId, id)
            render(status: 200)
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }
}
