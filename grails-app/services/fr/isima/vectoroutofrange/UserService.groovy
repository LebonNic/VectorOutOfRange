package fr.isima.vectoroutofrange

import grails.transaction.Transactional

@Transactional
class UserService extends Subject implements Observer{

    def rolesMap = [:]

    /**
     * Initialises a map containing all the roles available on the site.
     */
    void init() {
        this.rolesMap['createPostPermission'] = new Role(authority: 'ROLE_CREATE_POST', name: 'Create posts', description: 'Ask a question or contribute an answer', requiredReputation: 1).save(flush: true, failOnError: true)
        this.rolesMap['voteUpPermission'] = new Role(authority: 'ROLE_VOTE_UP', name: 'Vote up', description: 'Indicate when questions and answers are useful', requiredReputation: 15).save(flush: true, failOnError: true)
        this.rolesMap['createCommentPermission'] = new Role(authority: 'ROLE_CREATE_COMMENT', name: 'Create comments', description: 'Add comments on questions and answers', requiredReputation: 15).save(flush: true, failOnError: true)
        this.rolesMap['voteDownPermission'] = new Role(authority: 'ROLE_VOTE_DOWN', name: 'Vote down', description: 'Indicate when questions and answers are not useful', requiredReputation: 125).save(flush: true, failOnError: true)
        this.rolesMap['moderateTagPermission'] = new Role(authority: 'ROLE_MODERATE_TAG', name: 'Moderate tag', description: 'Edit and delete tags', requiredReputation: 1500).save(flush: true, failOnError: true)
        this.rolesMap['moderatePostPermission'] = new Role(authority: 'ROLE_MODERATE_POST', name: 'Moderate posts', description: 'Edit and delete posts', requiredReputation: 10000).save(flush: true, failOnError: true)
        this.rolesMap['moderateUserPermission'] = new Role(authority: 'ROLE_MODERATE_USER', name: 'Moderate users', description: 'Edit and delete users', requiredReputation: 20000).save(flush: true, failOnError: true)
    }

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
        log.info("Creation of user ${nickname}.")
        def userInformation = new UserInformation(firstName: firstName, lastName: lastName, nickname: nickname, reputation: 1, editedPostsCount: 0, isAdmin: false)
        def user = new User(username: username, password: password, userInformation: userInformation)
        user.save(flush: true, failOnError: true)
        this.grantRoleToUser(user, 'createPostPermission')
        this.notifyObservers(new UserServiceEvent(user: user), UserServiceEventCode.NEW_USER_CREATED)
        return user
    }

    /**
     * Transforms a lambda user in god.
     * @param userId The user's id who is going to be transformed.
     * @return A new god !
     */
    def grantUserAdminRole(long userId){
        def admin = this.getUser(userId)
        this.grantRoleToUser(admin, 'createPostPermission')
        this.grantRoleToUser(admin, 'voteUpPermission')
        this.grantRoleToUser(admin, 'createCommentPermission')
        this.grantRoleToUser(admin, 'voteDownPermission')
        this.grantRoleToUser(admin, 'moderateTagPermission')
        this.grantRoleToUser(admin, 'moderatePostPermission')
        this.grantRoleToUser(admin, 'moderateUserPermission')
        admin.userInformation.isAdmin = true
        admin.save(flush: true, failOnError: true)
        log.info("User ${admin.userInformation.nickname} became an admin.")
        return admin
    }

    /**
     * Gives a role to the user passed as parameter.
     * @param user The user who should get a new role.
     * @param roleKey A string corresponding to a role's key in the UserService's map
     * @return ???
     */
    def grantRoleToUser(User user, String roleKey){
        def Role role = (Role) this.rolesMap[roleKey]

        if(role){
            if(!UserRole.exists(user.id, role.id)){
                UserRole.create(user, role, true)
                log.info("User ${user.userInformation.nickname} has been given a new privilege : ${role.name}.")
            }
            else {
                log.debug("User ${user.userInformation.nickname} has already the role : ${role.name}.")
            }
        }
        else {
            throw new UserServiceException(UserServiceExceptionCode.USER_ROLE_NOT_FOUND, "The role's key \" ${roleKey}\" passed to the method UserService.grantRoleToUser doesn't exist")
        }
    }

    /**
     * Removes a role to the user passed as parameter.
     * @param user The user who should lose his role.
     * @param roleKey A string corresponding to a role's key in the UserService's map
     * @return ???
     */
    def private removeRoleToUser(User user, String roleKey){
        def role = (Role) this.rolesMap[roleKey]

        if(role){
            if(UserRole.exists(user.id, role.id)){
                UserRole.remove(user, role)
                log.info("User ${user.userInformation.nickname} has lost privilege : ${role.name}.")
            }
            else{
                log.debug("Can't remove \"${role.name}\" privilege to user ${user.userInformation.nickname} because he has not this role yet.")
            }
        }
        else{
            throw new UserServiceException(UserServiceExceptionCode.USER_ROLE_NOT_FOUND, "The role's key \" ${roleKey}\" passed to the method UserService.removeRoleToUser doesn't exist")
        }
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
        def User user = this.getUser(userId)

        try {
            user.userInformation.firstName = firstName
            user.userInformation.lastName = lastName
            user.userInformation.nickname = nickname
            user.userInformation.website = website
            user.userInformation.location = location
            user.userInformation.about = about
            user.save( failOnError: true)
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
     * Checks if the user passed as parameter has earned enough reputation to get a new role.
     * @param user The user who has to be checked.
     * @return ???
     */
    def private checkRolesToGrantForUser(User user){

        if(!user.userInformation.isAdmin){
            def reputation = user.userInformation.reputation
            if(reputation >= 15 && reputation < 125){
                this.grantRoleToUser(user, 'voteUpPermission')
                this.grantRoleToUser(user, 'createCommentPermission')
            }
            else if(reputation >= 125 && reputation < 1500){
                this.grantRoleToUser(user, 'voteDownPermission')
            }
            else if(reputation >= 1500 && reputation < 10000){
                this.grantRoleToUser(user, 'moderateTagPermission')
            }
            else if(reputation >= 10000 && reputation < 20000){
                this.grantRoleToUser(user, 'moderatePostPermission')
            }
            else if(reputation >= 20000){
                this.grantRoleToUser(user, 'moderateUserPermission')
            }
        }
        else {
            log.debug("No privileges to grant. User \"${user.userInformation.nickname}\" is a sort of Chuck Norris and has already every privileges.")
        }
    }

    /**
     * Checks if the user passed as parameter should lose his roles.
     * @param user The user who has to be checked.
     * @return ???
     */
    def private checkRolesToRemoveForUser(User user){
        if(!user.userInformation.isAdmin){
            def reputation = user.userInformation.reputation

            if(reputation < 15){
                this.removeRoleToUser(user, 'voteUpPermission')
                this.removeRoleToUser(user, 'createCommentPermission')
            }
            else if(reputation >= 15 && reputation < 125){
                this.removeRoleToUser(user, 'voteDownPermission')
            }
            else if(reputation >= 125 && reputation < 1500){
                this.removeRoleToUser(user, 'moderateTagPermission')
            }
            else if (reputation >= 1500 && reputation < 10000){
                this.removeRoleToUser(user, 'moderatePostPermission')
            }
            else if(reputation >= 10000 && reputation < 20000){
                this.removeRoleToUser(user, 'moderateUserPermission')
            }
            else {
                log.debug("Ahah user \"${user.userInformation.nickname}\" is god and can't lose his privileges !")
            }
        }
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
                case TopicServiceEventCode.POST_TAGGED_AS_BEST_ANSWER:
                    def questionAuthor = topicEvent.actor
                    def answerAuthor = topicEvent.post.content.author.user
                    this.rewardUsersForBestAnswer(questionAuthor, answerAuthor)
                    this.checkRolesToGrantForUser(questionAuthor)
                    this.checkRolesToGrantForUser(answerAuthor)
                    break

                case TopicServiceEventCode.POST_UPVOTED:
                    this.increaseAuthorReputation(topicEvent)
                    this.checkRolesToGrantForUser(topicEvent.post.content.author.user)
                    break

                case TopicServiceEventCode.POST_DOWNVOTED:
                    this.decreaseVoterReputation(topicEvent)
                    this.decreaseAuthorReputation(topicEvent)
                    this.checkRolesToRemoveForUser(topicEvent.actor)
                    this.checkRolesToRemoveForUser(topicEvent.post.content.author.user)
                    break
            }
        }
    }

    /**
     * Gives reputation points to the author of an post because this one has been accepted as best answer on a topic. It
     * also gives points to the user who accepted the answer and who is the topic's question author.
     * @param questionAuthor The user corresponding to the author of a topic's question.
     * @param answerAuthor The user corresponding to the author of the post tagged as best answer.
     * @return ???
     */
    def private rewardUsersForBestAnswer(User questionAuthor, User answerAuthor){
        questionAuthor.userInformation.reputation += 2
        questionAuthor.save(failOnError: true)
        answerAuthor.userInformation.reputation += 15
        questionAuthor.save(failOnError: true)
        log.info("User \"${questionAuthor.userInformation.nickname}\" gains 2 points of reputation for accepting an answer on one of his topics.")
        log.info("User \"${answerAuthor.userInformation.nickname}\" gains 15 points of reputation because one of his answers has been accepted.")
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

            voter.save( failOnError: true)
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

        author.save( failOnError: true)
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

        author.save( failOnError: true)
    }
}
