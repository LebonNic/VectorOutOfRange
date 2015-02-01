package fr.isima.vectoroutofrange

import grails.transaction.Transactional
import jline.internal.Log

@Transactional
class UserService implements Observer{

    def createUser(String username, String password, String firstName, String lastName, String nickname){
        def userInformation = new UserInformation(firstName: firstName, lastName: lastName, nickname: nickname, reputation: 0)
        def user = new User(username: username, password: password, userInformation: userInformation)
        user.save(flush: true, failOnError: true)
        Log.info("User ${nickname} has been created.")
        return user
    }

    @Override
    void update(Object event, Object eventCode) {
        if(event instanceof TopicServiceEvent && eventCode instanceof TopicServiceEventCode)
        {
            def code = (TopicServiceEventCode) eventCode
            def topicEvent = (TopicServiceEvent) event

            switch (code){
                case TopicServiceEventCode.NEW_TOPIC_CREATED:
                    Log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for a topic creation).")
                    //TODO Add the code to manage a user when he creates a new topic.
                    break

                case TopicServiceEventCode.NEW_COMMENT_ON_POST:
                    Log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for a comment).")
                    //TODO Add the code to manage a user when he creates a new post.
                    break

                case TopicServiceEventCode.NEW_ANSWER_ON_TOPIC:
                    Log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for an answer).")
                    //TODO Add the code to manage a user when he answers a question.
                    break

                case TopicServiceEventCode.POST_CORRECTED:
                    Log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for a post correction).")
                    //TODO Add the code to manage a user when he corrects a post.
                    break

                case TopicServiceEventCode.POST_VOTED:
                    Log.info("UserService is trying to update user ${topicEvent.actor.userInformation.nickname} (potential reward for a vote on a post).")
                    //TODO Add the code to manage a user when he votes on a post.
                    break
            }
        }
    }
}
