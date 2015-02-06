package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class PermissionController {

    def springSecurityService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [permissions: Role.getAll().sort { it.requiredReputation },
                                      user       : springSecurityService.currentUser])
    }
}
