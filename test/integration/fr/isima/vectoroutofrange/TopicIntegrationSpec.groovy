package fr.isima.vectoroutofrange

import grails.test.spock.IntegrationSpec

class TopicIntegrationSpec extends IntegrationSpec {

    TopicService topicService
    UserService userService
    TagService tagService

    User user

    void setup() {
        user = userService.createUser("user", "password", "John", "Smith", "JohnSmith")
    }

    void cleanup() {

    }

    void "Test that service creates topic only if author is known"() {
        when:
        topicService.createNewTopic(user.id, 'A title', 'A text', ['A tag', 'Another tag'])
        topicService.createNewTopic(0, 'I will probably not be created', 'So this message is useless', [])

        then:
        thrown(TopicServiceException)
        Topic.count == 1
    }

    void "Test that service create tag only when necessary"() {
        when:
        topicService.createNewTopic(user.id, 'An even better question', 'About something', ['With a tag', 'And a tag'])

        then:
        Tag.count == 2
    }

    void "Test that answers can be added to a topic"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A random title', 'A random text', ['A random tag'])
        def answer = topicService.addAnswer(topic.id, user.id, 'Random answer')

        then:
        topic.answers[0] == answer
        answer.topic == topic
    }

    void "Test that comments can be added to questions"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A random title', 'A random text', ['A random tag'])
        def comment = topicService.addComment(topic.question.id, user.id, 'Random comment')

        then:
        topic.question.comments[0] == comment
        comment.parentPost == topic.question
    }

    void "Test that comments can be added to answers"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A random title', 'A random text', ['A random tag'])
        def answer = topicService.addAnswer(topic.id, user.id, 'Random answer')
        def comment = topicService.addComment(answer.id, user.id, 'Random comment')

        then:
        answer.comments[0] == comment
        comment.parentPost == answer
    }

    void "Test that questions can be corrected"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A random title', 'A randm text', ['A random tag'])
        topicService.correctPost(topic.question.id, user.id, 'A random text')

        then:
        topic.question.content.text == 'A random text'
        topic.question.history[0].text == 'A randm text'
    }

    void "Test that answers can be corrected"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A random title', 'A random text', ['A random tag'])
        def answer = topicService.addAnswer(topic.id, user.id, 'Random anwser')
        topicService.correctPost(answer.id, user.id, 'Random answer')

        then:
        answer.content.text == 'Random answer'
        answer.history[0].text == 'Random anwser'
    }

    void "Test that question comments can be corrected"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A random title', 'A random text', ['A random tag'])
        def comment = topicService.addComment(topic.question.id, user.id, 'Random coment')
        topicService.correctPost(comment.id, user.id, 'Random comment')

        then:
        comment.content.text == 'Random comment'
        comment.history[0].text == 'Random coment'
    }

    void "Test that answer comments can be corrected"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A random title', 'A random text', ['A random tag'])
        def answer = topicService.addAnswer(topic.id, user.id, 'Random answer')
        def comment = topicService.addComment(answer.id, user.id, 'Random coment')
        topicService.correctPost(comment.id, user.id, 'Random comment')

        then:
        comment.content.text == 'Random comment'
        comment.history[0].text == 'Random coment'
    }

    void "Test that questions can be deleted"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A title', 'A text', ['A tag', 'Another tag'])
        def topicCount = Topic.count()
        topicService.addAnswer(topic.id, user.id, 'Random answer')
        topicService.addComment(topic.question.id, user.id, 'Random comment')
        topicService.deletePost(topic.question.id)

        then:
        Topic.count() == topicCount - 1
    }

    void "Test that answers can be deleted"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A title', 'A text', ['A tag', 'Another tag'])
        def answer = topicService.addAnswer(topic.id, user.id, 'Random answer')
        topicService.addComment(answer.id, user.id, 'Random comment')
        topicService.tagPostAsBestAnswer(answer.id)
        def answerCount = topic.answers.size()
        topicService.deletePost(answer.id)

        then:
        topic.answers.size() == answerCount - 1
    }

    void "Test that comments can be deleted"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A title', 'A text', ['A tag', 'Another tag'])
        def comment = topicService.addComment(topic.question.id, user.id, 'Random comment')
        def commentCount = topic.question.comments.size()
        topicService.deletePost(comment.id)

        then:
        topic.question.comments.size() == commentCount - 1
    }

    void "Test that questions can be voted"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A random title', 'A random text', ['A random tag'])
        topicService.voteForPost(topic.question.id, user.id, VoteType.UPVOTE)
        topicService.voteForPost(topic.question.id, user.id, VoteType.DOWNVOTE)

        then:
        topic.question.votes[0].type == VoteType.DOWNVOTE
    }

    void "Test that answers can be voted"() {
        when:
        def topic = topicService.createNewTopic(user.id, 'A random title', 'A random text', ['A random tag'])
        def answer = topicService.addAnswer(topic.id, user.id, 'Random answer')
        topicService.voteForPost(answer.id, user.id, VoteType.DOWNVOTE)
        topicService.voteForPost(answer.id, user.id, VoteType.UPVOTE)

        then:
        answer.votes[0].type == VoteType.UPVOTE
    }
}
