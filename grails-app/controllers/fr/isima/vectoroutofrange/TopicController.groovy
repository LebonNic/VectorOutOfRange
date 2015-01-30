package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured
import jline.internal.Log

class TopicController {

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
        Log.debug(params.title)
        Log.debug(params.text)
        Log.debug(params.tags)
    }

}
