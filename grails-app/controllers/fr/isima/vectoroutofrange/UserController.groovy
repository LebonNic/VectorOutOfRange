package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured

class UserController {

    def userService
    def springSecurityService

    static allowedMethods = [index : 'GET',
                             view  : 'GET',
                             edit  : 'GET',
                             create: 'GET',
                             save  : 'POST',
                             delete: 'POST']

    /**
     * Display list of user.
     * @return List of user view.
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [users: User.list(params), userCount: User.count()])
    }

    /**
     * Display a user profile.
     * @return User profile view.
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        render(view: 'view', model: [user: User.get(Long.parseLong((String) params.id))])
    }

    /**
     * Display a user profile edit view.
     * @return User profile edit view or 403 if not allowed.
     */
    @Secured(['IS_AUTHENTICATED_FULLY'])
    def edit() {
        User user = (User) springSecurityService.currentUser
        long id = Long.parseLong((String) params.id)

        // Allow access to user and user moderators
        if (user.id == id || SpringSecurityUtils.ifAllGranted('ROLE_MODERATE_USER')) {
            render(view: 'edit', model: [user: User.get(id)])
        } else {
            render(status: 403)
        }
    }

    /**
     * Display the creation view of a user.
     * @return The creation view of a user or 403 if user is authenticated
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def create() {
        // Only non-authenticated users can create profile
        if (springSecurityService.currentUser == null) {
            render(view: 'create')
        } else {
            render(403)
        }
    }

    /**
     * Update or create user with given parameters.
     * @return Create user id or 403 if not allowed and 404 if user does not exists.
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def save() {
        try {
            if (params.id) {
                // Update user
                User user = (User) springSecurityService.currentUser
                long id = Long.parseLong((String) params.id)

                // Allow access to user and user moderators
                if (user.id == id || SpringSecurityUtils.ifAllGranted('ROLE_MODERATE_USER')) {
                    userService.updateUser(id,
                            (String) params.firstname,
                            (String) params.lastname,
                            (String) params.nickname,
                            (String) params.website,
                            (String) params.location,
                            (String) params.about)
                    render(status: 200, text: id)
                } else {
                    render(403)
                }
            } else {
                // Create user
                if (springSecurityService.currentUser == null) {
                    User user = userService.createUser((String) params.username,
                            (String) params.password,
                            (String) params.firstname,
                            (String) params.lastname,
                            (String) params.username)
                    render(status: 201, text: user.id)
                } else {
                    render(status: 403)
                }
            }
        } catch (UserServiceException e) {
            if (e.code == UserServiceExceptionCode.USER_NOT_FOUND) {
                render(status: 404, text: e.getCode())
            } else {
                render(status: 403, text: e.getCode())
            }
        }
    }

    /**
     * Delete given user
     * @return
     */
    @Secured(['ROLE_MODERATE_USER'])
    def delete() {
        // TODO: Add deleteUser to userService
        render(status: 404)
    }

}
