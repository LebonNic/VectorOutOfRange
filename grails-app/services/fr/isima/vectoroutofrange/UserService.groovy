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
                    this.increaseAuthorReputation(event)
                    break

                case TopicServiceEventCode.POST_DOWNVOTED:
                    this.decreaseVoterReputation(event)
                    this.decreaseAuthorReputation(event)
                    break
            }
        }
    }

    /**
     * Decreases the reputation of a user for a downvote on an answer.
     * @param event The event containing information about the vote.
     * @return The voter.
     */
    def private decreaseVoterReputation(TopicServiceEvent event){
        if(event.post.type == PostType.ANSWER){
            def voter = event.actor
            voter.userInformation.reputation -= 1
            log.info("User ${voter.userInformation.nickname} loses 1 points of reputation because he downvoted an answer.")

            // The reputation can't be lower than 1
            if(voter.userInformation.reputation < 1)
                voter.userInformation.reputation = 1

            voter.save(flush: true, failOnError: true)
        }
    }

    /**
     * Decreases the reputation of a user because one of his posts has been downvoted.
     * @param event The event containing information about the vote.
     * @return The author of the post which has been downvoted.
     */
    def private decreaseAuthorReputation(TopicServiceEvent event){
        def author = event.post.content.author

        if(event.post.type == PostType.QUESTION){
            author.reputation -= 2
            log.info("User ${author.nickname} loses 2 points of reputation for a downvote on his question.")
        }
        else if(event.post.type == PostType.ANSWER){
            author.reputation -= 2
            log.info("User ${author.nickname} loses 2 points of reputation for a downvote on his answer.")
        }

        // The reputation can't be lower than 1
        if(author.reputation < 1)
            author.reputation = 1

        author.save(flush: true, failOnError: true)
    }

    /**
     * Increases the reputation of a user because one of his posts has been upvoted.
     * @param event The event containing information about the vote.
     * @return The author of the post which has been upvoted
     */
    def private increaseAuthorReputation(TopicServiceEvent event){
        def author = event.post.content.author

        if(event.post.type == PostType.QUESTION){
            author.reputation += 5
            log.info("User ${author.nickname} gains 5 points of reputation for an upvote on his question.")
        }
        else if(event.post.type == PostType.ANSWER){
            author.reputation += 10
            log.info("User ${author.nickname} gains 10 points of reputation for an upvote on his answer.")
        }

        author.save(flush: true, failOnError: true)
    }
}
