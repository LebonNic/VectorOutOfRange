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
    void onNotify(Object entity, Object event) {

        if(event instanceof TopicEventCode)
        {
            def code = (TopicEventCode) event

            switch (code){
                case TopicEventCode.NEW_TOPIC_CREATED:
                    if(entity instanceof Topic){
                        def topic = (Topic)entity
                        Log.info("UserService is trying to update user ${topic.question.content.author.nickname} (potential award for a topic creation).")
                        //TODO Add the code to manage a user when he creates a new topic.
                    }
                    break

                case TopicEventCode.NEW_COMMENT_ON_POST:
                    if(entity instanceof Post){
                        def post = (Post) entity
                        Log.info("UserService is trying to update user ${post.content.author.nickname} (potential award for a comment).")
                        //TODO Add the code to manage a user when he creates a new post.
                    }
                    break

                case TopicEventCode.NEW_ANSWER_ON_TOPIC:
                    if(entity instanceof Post){
                        def post = (Post) entity
                        Log.info("UserService is trying to update user ${post.content.author.nickname} (potential award for an answer).")
                        //TODO Add the code to manage a user when he answers a question.
                    }
                    break

                case TopicEventCode.POST_CORRECTED:
                    if(entity instanceof Post){
                        def post = (Post) entity
                        Log.info("UserService is trying to update user ${post.content.author.nickname} (potential award for a post correction).")
                        //TODO Add the code to manage a user when he corrects a post.
                    }

                    break

                case TopicEventCode.POST_VOTED:
                    if(entity instanceof Post){
                        def post = (Post) entity
                        Log.info("UserService is trying to update user ${post.content.author.nickname} (potential award for a vote on a post).")
                        //TODO Add the code to manage a user when he votes on a post.
                    }
                    break
            }
        }
    }
}
