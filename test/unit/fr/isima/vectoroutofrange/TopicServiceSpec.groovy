package fr.isima.vectoroutofrange

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TopicService)
class TopicServiceSpec extends Specification {

    def userService

    void "test that creating new topic creates new tags"() {
        when:
        def user = userService.createUser('user', 'pwd', 'John', 'Doe', 'JohnDoe')
        service.createNewTopic(user.id, 'Foo', 'Bar', ['Foo', 'Bar'])
        then:
        Tag.count() == 2
    }
}
