package fr.isima.vectoroutofrange

import grails.transaction.Transactional
import jline.internal.Log

@Transactional
class UserService {

    def createUser(String username, String password, String firstName, String lastName, String nickname){
        def userInformaton = new UserInformation(firstName: firstName, lastName: lastName, nickname: nickname, reputation: 0)
        def user = new User(username: username, password: password, userInformation: userInformaton)
        user.save(flush: true, failOnError: true)
        Log.info("User ${nickname} has been created.")
        return user
    }

}
