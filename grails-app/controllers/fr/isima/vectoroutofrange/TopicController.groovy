package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

class TopicController {

    TopicService topicService
    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST"]

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [topics: Topic.getAll()])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        render(view: 'view', model: [topic: Topic.get(params.id)])
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
            long id = user.id

            try {
                Topic topic = topicService.createNewTopic(id, title, text, tags)

                render(status: 201, text: topic.id)
            } catch (TopicServiceException e) {
                render(status: 403, text: 'ajax.failure.user.not.found')
            }
        } else {
            render(status: 403, text: 'ajax.failure.user.not.logged.in')
        }
    }

}
