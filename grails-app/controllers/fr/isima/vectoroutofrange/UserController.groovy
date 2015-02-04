package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class UserController {

    def userService

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [users: User.list(params), userCount: User.count()])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        render(view: 'view', model: [user: User.get(params.id)])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def edit() {
        render(view: 'edit', model: [user: User.get(params.id)])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def create() {
        render(view: 'create')
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save() {
        if (params.id) {
            // Update
            User user = User.get(params.id)
            user.userInformation.nickname = params.nickname
            user.userInformation.firstName = params.firstname
            user.userInformation.lastName = params.lastname
            user.userInformation.website = params.website
            user.userInformation.location = params.location
            user.userInformation.about = params.about

            user.save(flush: true, failOnError: true)

            render(status: 200)
        } else {
            try {
                // Create
                User user = userService.createUser(params.username, params.password, params.firstname, params.lastname, params.username)
                render(status: 201, text: user.id)
            } catch (Exception e) {
                render(status: 409, text: e.getMessage())
            }
        }
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def delete() {
        render(status: 404)
    }
}
