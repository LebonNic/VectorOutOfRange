package fr.isima.vectoroutofrange

import grails.transaction.Transactional

@Transactional
class UserService extends Subject implements Observer{

    /**
     * Retrieves a user from the database.
     * @param userId The id of the user to get.
     * @param lock Tells the method to lock the loaded object.
     * @return The user identified by the id.
     */
    def getUser(long userId, boolean lock = false){
        def user

        if(lock)
            user = User.lock(userId)
        else
            user = User.get(userId)

        if(user){
            return user
        }
        else {
            throw new UserServiceException(UserServiceExceptionCode.USER_NOT_FOUND, "The user's id passed to the method UserService.getUser doesn't exist.")
        }
    }

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
        def userInformation = new UserInformation(firstName: firstName, lastName: lastName, nickname: nickname, reputation: 1, editedPostsCount: 0)
        def user = new User(username: username, password: password, userInformation: userInformation)
        user.save(flush: true, failOnError: true)
        log.info("User ${nickname} has been created.")
        this.notifyObservers(new UserServiceEvent(user: user), UserServiceEventCode.NEW_USER_CREATED)
        return user
    }

    /**
     * Updates a user.
     * @param userId The user's id who has to be updated.
     * @param firstName The new user's first name.
     * @param lastName The new user's last name.
     * @param nickname The new user's nickname.
     * @param website The new user's website.
     * @param location The new user's location.
     * @param about The new user's description.
     * @return The updated user.
     */
    def updateUser(long userId, String firstName, String lastName, String nickname, String website, String location, String about){
        def user = this.getUser(userId)

        try {
            user.userInformation.firstName = firstName
            user.userInformation.lastName = lastName
            user.userInformation.nickname = nickname
            user.userInformation.website = website
            user.userInformation.location = location
            user.userInformation.about = about
            user.save(flush: true, failOnError: true)
            this.notifyObservers(new UserServiceEvent(user: user), UserServiceEventCode.USER_UPDATED)
            log.info("User ${user.userInformation.nickname} (username : ${user.username}) has been updated.")
        } catch (Exception e) {
            user.errors.each {
                if (it.fieldError.field == "userInformation.website") {
                    throw new UserServiceException(UserServiceExceptionCode.USER_WEBSITE_INVALID, "User website is not a valid url")
                } else if (it.fieldError.field == "userInformation.nickname") {
                    throw new UserServiceException(UserServiceExceptionCode.USER_NICKNAME_INVALID, "User nickname is already used")
                }
            }
        }

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
