package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class BadgeController {

    def springSecurityService

    static allowedMethods = [index: 'GET']

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [bronzeBadges: Badge.findAll {
            type == BadgeType.BRONZE
        }, silverBadges: Badge.findAll {
            type == BadgeType.SILVER
        }, goldBadges: Badge.findAll {
            type == BadgeType.GOLD
        }, platinumBadges: Badge.findAll {
            type == BadgeType.PLATINUM
        }, user: (User) springSecurityService.currentUser])
    }
}
