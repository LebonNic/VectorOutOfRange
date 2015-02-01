package fr.isima.vectoroutofrange

import grails.transaction.Transactional

@Transactional
class UserService implements Observer{

    /**
     * Creates and saves a new user in the database.
     * @param username The name of the user.
     * @param password The password of the user.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param nickname The nickname of the user.
     * @return The new created user.
     */
    def createUser(String username, String password, String firstName, String lastName, String nickname){
        def userInformation = new UserInformation(firstName: firstName, lastName: lastName, nickname: nickname, reputation: 1)
        def user = new User(username: username, password: password, userInformation: userInformation)
        user.save(flush: true, failOnError: true)
        log.info("User ${nickname} has been created.")
        return user
    }

    /**
     * Notifies the UserService that an event involving a user occurred. Then, the
     * UserService processes the notification and updates the user's profile.
     * @param event An object which contains some information about the event.
     * @param eventCode The code corresponding to the event.
     */
    @Override
    void update(Object event, Object eventCode) {
        if(event instanceof TopicServiceEvent && eventCode instanceof TopicServiceEventCode)
        {
            def code = (TopicServiceEventCode) eventCode
            def topicEvent = (TopicServiceEvent) event

            switch (code){
                case TopicServiceEventCode.NEW_TOPIC_CREATED:
                    log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for a topic creation).")
                    //TODO Add the code to manage a user when he creates a new topic.
                    break

                case TopicServiceEventCode.NEW_COMMENT_ON_POST:
                    log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for a comment).")
                    //TODO Add the code to manage a user when he creates a new post.
                    break

                case TopicServiceEventCode.NEW_ANSWER_ON_TOPIC:
                    log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for an answer).")
                    //TODO Add the code to manage a user when he answers a question.
                    break

                case TopicServiceEventCode.POST_CORRECTED:
                    log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for a post correction).")
                    //TODO Add the code to manage a user when he corrects a post.
                    break

                case TopicServiceEventCode.POST_UPVOTED:
                    log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for a vote on a post).")
                    def postAuthor = topicEvent.post.content.author
                    postAuthor.reputation += 5
                    postAuthor.save(flush: true, failOnError: true)
                    log.info("User ${postAuthor.nickname} gains 5 points of reputation.")
                    break

                case TopicServiceEventCode.POST_DOWNVOTED:
                    log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for a vote on a post).")
                    //TODO Add the code to manage a user when he votes on a post.
                    def postAuthor = topicEvent.post.content.author
                    postAuthor.reputation -= 5
                    postAuthor.save(flush: true, failOnError: true)
                    log.info("User ${postAuthor.nickname} loses 5 points of reputation.")
                    break
            }
        }
    }
}
