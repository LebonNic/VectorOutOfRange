package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class BadgeController {

    def springSecurityService

    static allowedMethods = [index: 'GET']

    /**
     * Display badge list view.
     * @return Badge list view.
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [
                user: (User) springSecurityService.currentUser
        ])
    }
}
