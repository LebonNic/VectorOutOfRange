package fr.isima.vectoroutofrange

import grails.test.spock.IntegrationSpec

class UserIntegrationSpec extends IntegrationSpec {

    UserService userService

    def setup() {
    }

    def cleanup() {
    }

    void "Test user creation"() {
        when:
        userService.createUser('foo', 'bar', 'baz', 'quux', 'meta')
        then:
        User.count == 1
    }

    void "Test user edition"() {
        when:
        def user = userService.createUser('foo', 'bar', 'baz', 'quux', 'meta')
        userService.updateUser(user.id, 'Baz', 'Quux', 'Meta', 'http://www.isima.fr', 'France', 'I\'m in an test')

        then:
        user.userInformation.firstName == 'Baz'
        user.userInformation.lastName == 'Quux'
        user.userInformation.nickname == 'Meta'
        user.userInformation.website == 'http://www.isima.fr'
        user.userInformation.location == 'France'
        user.userInformation.about == 'I\'m in an test'
    }
}
