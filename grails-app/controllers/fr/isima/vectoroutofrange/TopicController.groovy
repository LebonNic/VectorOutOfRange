package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

import javax.annotation.PostConstruct

class TopicController {

    def topicService
    def userService
    def springSecurityService

    @PostConstruct
    void init() {
        log.info("Post construction of the TopicController.")
        topicService.addObserver(userService)
    }

    static allowedMethods = [index   : 'GET',
                             view    : 'GET',
                             edit    : 'GET',
                             create  : 'GET',
                             save    : 'POST',
                             upvote  : 'POST',
                             downvote: 'POST',
                             answer  : 'POST',
                             comment : 'POST']

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [topics: Topic.list(params), topicCount: Topic.count()])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        Topic topic = Topic.get(params.id)
        topic.views++
        topic.save(flush: true, failOnError: true)

        render(view: 'view', model: [topic: topic])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def edit() {
        render(view: 'edit', model: [post: Post.get(Long.parseLong((String) params.id))])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def create() {
        render(view: 'create')
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save() {
        String title = params.title
        String text = params.text
        List<String> tags = params.list('tags[]')
        User user = (User) springSecurityService.currentUser

        if (user != null) {
            try {
                if (params.id) {
                    Post post = topicService.correctPost(Long.parseLong((String) params.id), user.id, (String) params.text)
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

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def delete() {
        try {
            topicService.deletePost(Long.parseLong((String) params.id))

            render(status: 205)
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }
}
