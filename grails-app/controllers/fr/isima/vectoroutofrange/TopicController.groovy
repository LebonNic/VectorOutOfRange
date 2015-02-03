package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

import javax.annotation.PostConstruct

class TopicController {

    TopicService topicService
    UserService userService
    SpringSecurityService springSecurityService

    @PostConstruct
    void init() {
        log.info("Post construction of the TopicController.")
        topicService.addObserver(userService)
    }

    static allowedMethods = [index   : 'GET',
                             view    : 'GET',
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

    // @Secured(['CAN_CREATE_QUESTION'])
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def create() {
        render(view: 'create')
    }

    // @Secured(['CAN_CREATE_QUESTION'])
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save() {
        String title = params.title
        String text = params.text
        List<String> tags = params.list('tags[]')
        User user = (User) springSecurityService.currentUser

        if (user != null) {
            try {
                if (params.id) {
                    Post post = topicService.correctPost(Long.parseLong(params.id), user.id, params.text)
                    render(status: 200, text: post.topic.id)
                } else {
                    Topic topic = topicService.createNewTopic(user.id, title, text, tags)
                    render(status: 201, text: topic.id)
                }
            } catch (TopicServiceException e) {
                render(status: 401, text: e.getCode())
            }
        } else {
            // TODO: remove when security is added
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    // @Secured(['CAN_UPVOTE'])
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def upvote() {
        User user = (User) springSecurityService.currentUser
        if (user != null) {
            long id = Long.parseLong(params.id);

            try {
                topicService.voteForPost(id, user.id, VoteType.UPVOTE)
                render(status: 200, text: topicService.getScoreForPost(id))
            } catch (TopicServiceException e) {
                render(status: 401, text: e.getCode())
            }
        } else {
            // TODO: remove when security is added
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    // @Secured(['CAN_DOWNVOTE'])
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def downvote() {
        User user = (User) springSecurityService.currentUser
        if (user != null) {
            long id = Long.parseLong(params.id);

            try {
                topicService.voteForPost(id, user.id, VoteType.DOWNVOTE)
                render(status: 200, text: topicService.getScoreForPost(id))
            } catch (TopicServiceException e) {
                render(status: 401, text: e.getCode())
            }
        } else {
            // TODO: remove when security is added
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    // @Secured(['CAN_CREATE_ANSWER'])
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def answer() {
        String text = params.text
        long id = Long.parseLong(params.id);
        User user = (User) springSecurityService.currentUser

        if (user != null) {
            try {
                Post answer = topicService.addAnswer(id, user.id, text);

                render(status: 201, text: answer.id)
            } catch (TopicServiceException e) {
                render(status: 401, text: e.getCode())
            }
        } else {
            // TODO: remove when security is added
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    // @Secured(['CAN_CREATE_COMMENT'])
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def comment() {
        String text = params.text
        long id = Long.parseLong(params.id);
        User user = (User) springSecurityService.currentUser

        if (user != null) {
            try {
                Post comment = topicService.addComment(id, user.id, text);

                render(status: 201, text: comment.id)
            } catch (TopicServiceException e) {
                render(status: 401, text: e.getCode())
            }
        } else {
            // TODO: remove when security is added
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def edit() {
        render(view: 'edit', model: [post: Post.get(params.id)])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def delete() {
        try {
            Post post = Post.get(params.id)

            if (post.type == PostType.QUESTION) {
                log.info 'Deleting topic'
                post.topic.delete()
            } else {
                log.info 'Deleting comment or post'
                post.delete()
            }
            render(status: 205)
        } catch (TopicServiceException e) {
            render(status: 401, text: e.getCode())
        }
    }
}
