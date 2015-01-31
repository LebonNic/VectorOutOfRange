package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import jline.internal.Log

import javax.annotation.PostConstruct

class TopicController {

    TopicService topicService
    UserService userService
    SpringSecurityService springSecurityService

    @PostConstruct
    void init() {
        Log.info("Post construction of the TopicController.")
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
        render(view: 'index', model: [topics: Topic.getAll()])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        render(view: 'view', model: [topic: Topic.get(params.id)])
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
                Topic topic = topicService.createNewTopic(user.id, title, text, tags)

                render(status: 201, text: topic.id)
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
}
