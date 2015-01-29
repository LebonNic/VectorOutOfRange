package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class TopicController {

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index',
                model: [topics: Topic.getAll()])
    }

}
