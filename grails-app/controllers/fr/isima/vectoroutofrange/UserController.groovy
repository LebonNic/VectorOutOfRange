package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class UserController {

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [users: User.list(params), userCount: User.count()])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        render(view: 'view', model: [user: User.get(params.id)])
    }
}
