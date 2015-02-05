package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class UserController {

    def userService

    static allowedMethods = [index : 'GET',
                             view  : 'GET',
                             edit  : 'GET',
                             create: 'GET',
                             save  : 'POST',
                             delete: 'POST']

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [users: User.list(params), userCount: User.count()])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        render(view: 'view', model: [user: User.get(Long.parseLong((String) params.id))])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def edit() {
        render(view: 'edit', model: [user: User.get(Long.parseLong((String) params.id))])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def create() {
        render(view: 'create')
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save() {
        try {
            if (params.id) {
                // Update user
                User user = userService.updateUser(Long.parseLong((String) params.id),
                        (String) params.firstname,
                        (String) params.lastname,
                        (String) params.nickname,
                        (String) params.website,
                        (String) params.location,
                        (String) params.about)
                render(status: 200, text: user.id)
            } else {
                // Create user
                User user = userService.createUser((String) params.username,
                        (String) params.password,
                        (String) params.firstname,
                        (String) params.lastname,
                        (String) params.username)
                render(status: 201, text: user.id)
            }
        } catch (UserServiceException e) {
            if (e.code == UserServiceExceptionCode.USER_NOT_FOUND) {
                render(status: 404, text: e.getCode())
            } else {
                render(status: 403, text: e.getCode())
            }
        }
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def delete() {
        // TODO: Add deleteUser to userService
        render(status: 404)
    }

}
