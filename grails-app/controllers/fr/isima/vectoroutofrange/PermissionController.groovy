package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class PermissionController {

    def springSecurityService

    /**
     * Display permission list view.
     * @return Permission list view.
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [permissions: Role.getAll().sort { it.requiredReputation },
                                      user       : springSecurityService.currentUser])
    }
}
