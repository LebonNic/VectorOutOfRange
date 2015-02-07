package fr.isima.vectoroutofrange

import grails.transaction.Transactional

import javax.annotation.PostConstruct

@Transactional
class BadgeService implements Observer {

    /**
     * The map where the badges are stored.
     */
    def badgesMap = [:]

    /**
     * Creates all the badges.
     */
    @PostConstruct
    void init(){
        this.badgesMap['newby'] = new Badge(name: "Newby", description: "Congratulation, you just joined the wonderful community of vector::out_of_range!", type: BadgeType.BRONZE).save(failOnError: true)

        this.badgesMap['niceQuestion'] = new Badge(name: "Nice Question", description: "Question score of 10 or more", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['goodQuestion'] = new Badge(name: "Good Question", description: "Question score of 25 or more", type: BadgeType.SILVER).save( failOnError: true)
        this.badgesMap['greatQuestion'] = new Badge(name: "Great Question", description: "Question score of 100 or more", type: BadgeType.GOLD).save( failOnError: true)
        this.badgesMap['ultimateBadge'] = new Badge(name: "The Ultimate Question of Life the Universe and Everything", description: "Question score of 500 or more", type: BadgeType.PLATINUM).save(failOnError: true)

        this.badgesMap['popularQuestion'] = new Badge(name: "Popular question", description: "Asked a question with 1,000 views", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['notableQuestion'] = new Badge(name: "Notable question", description: "Asked a question with 2,500 views", type: BadgeType.SILVER).save( failOnError: true)
        this.badgesMap['famousQuestion'] = new Badge(name: "Famous question", description: "Asked a question with 10,000 views", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['acclaimedQuestion'] = new Badge(name: "Acclaimed question", description: "Asked a question with 50,000 views", type: BadgeType.PLATINUM).save( failOnError: true)

        this.badgesMap['studentQuestion'] = new Badge(name: "Student", description: "Asked first question with score of 1 or more", type: BadgeType.BRONZE).save( failOnError: true)

        this.badgesMap['explainer'] = new Badge(name: "Explainer", description: "Edited and answered 1 question", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['refiner'] = new Badge(name: "Refiner", description: "Edited and answered 50 question", type: BadgeType.SILVER).save( failOnError: true)
        this.badgesMap['illuminator'] = new Badge(name: "Illuminator", description: "Edited and answered 500 question", type: BadgeType.GOLD).save( failOnError: true)

        this.badgesMap['guru'] = new Badge(name: "Guru", description: "Accepted answer and score of 40 or more", type: BadgeType.SILVER).save( failOnError: true)

        this.badgesMap['niceAnswer'] = new Badge(name: "Nice Answer", description: "Answer score of 10 or more", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['goodAnswer'] = new Badge(name: "Good Answer", description: "Answer score of 25 or more", type: BadgeType.SILVER).save( failOnError: true)
        this.badgesMap['greatAnswer'] = new Badge(name: "Great Answer", description: "Answer score of 100 or more", type: BadgeType.GOLD).save( failOnError: true)
        this.badgesMap['fortyTwo'] = new Badge(name: "42", description: "Answer score of 500 or more", type: BadgeType.PLATINUM).save( failOnError: true)

        this.badgesMap['populist'] = new Badge(name: "Populist", description: "Highest scoring answer that outscored an accepted answer with score of more than 10 by more than 2x", type: BadgeType.GOLD).save( failOnError: true)
        this.badgesMap['reversal'] = new Badge(name: "Reversal", description: "Provided answer of +20 score to a question of -5 score", type: BadgeType.GOLD).save( failOnError: true)
        this.badgesMap['selfLearner'] = new Badge(name: "Self-Learner", description: "Answered your own question with score of 3 or more", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['teacher'] = new Badge(name: "Teacher", description: "Answered first question with score of 1 or more", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['autobiographer'] = new Badge(name: "Autobiographer", description: "Completed &laquo; About Me &raquo; section of user profile", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['commentator'] = new Badge(name: "Commentator", description: "Left 10 comments", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['pundit'] = new Badge(name: "Pundit", description: "Left 10 comments with score of 5 or more", type: BadgeType.SILVER).save( failOnError: true)

        this.badgesMap['critic'] = new Badge(name: "Critic", description: "First down vote", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['supporter'] = new Badge(name: "Supporter", description: "First up vote", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['disciplined'] = new Badge(name: "Disciplined", description: "Deleted own post with score of 3 or higher", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['peerPressure'] = new Badge(name: "Peer Pressure", description: "Deleted own post with score of -3 or lower", type: BadgeType.BRONZE).save( failOnError: true)

        this.badgesMap['editor'] = new Badge(name: "Editor", description: "First edit", type: BadgeType.BRONZE).save( failOnError: true)
        this.badgesMap['strunkAndWhite'] = new Badge(name: "Strunk & White", description: "Edited 80 posts", type: BadgeType.SILVER).save( failOnError: true)
        this.badgesMap['copyEditor'] = new Badge(name: "Copy Editor", description: "Edited 500 posts", type: BadgeType.GOLD).save( failOnError: true)
    }

    /**
     * Gives a badge to a user.
     * @param badgeKey A string corresponding to a badge's key in the BadgeService's map.
     * @param user The user who receives the badges.
     * @return The user who receives the badges.
     */
    def private unlockBadge(String badgeKey, UserInformation user){
        def badge = this.badgesMap[badgeKey]

        if(badge){
            if(!isBadgeAlreadyUnlock(user, badge)){
                user.addToBadges(badge)
                log.info("User ${user.nickname} has just unlocked a new Badge : ${badge.name}.")
                user.save(failOnError: true)
            }
        }
        else{
            throw new BadgeServiceException(BadgeServiceExceptionCode.BADGE_NOT_FOUND, "The badge's key passed to the method BadgeService.unlockBadge doesn't exist.")
        }
    }

    def private isBadgeAlreadyUnlock(UserInformation user, Badge badge){
        def boolean isBadgeUnlock = false
        def badges = user.getBadges(badge.id)

        if(badges.size() > 0)
            isBadgeUnlock =  true

        return isBadgeUnlock
    }

    /**
     * Notifies the BadgeService that an event involving a user occurred. Then, the
     * BadgeService processes the notification and gives some badges to the user if needed.
     * @param event An object which contains some information about the event.
     * @param eventCode The code corresponding to the event.
     */
    @Override
    void update(Object event, Object eventCode) {

        // Event sent by the TopicService
        if(event instanceof TopicServiceEvent && eventCode instanceof TopicServiceEventCode) {
            def code = (TopicServiceEventCode) eventCode
            def topicEvent = (TopicServiceEvent) event

            switch (code){
                case TopicServiceEventCode.POST_UPVOTED:
                    def voter = topicEvent.actor.userInformation
                    def post = topicEvent.post
                    def author = topicEvent.post.content.author

                    this.unlockBadge('supporter', voter)

                    if(post.type == PostType.QUESTION){
                        this.checkForQuestionScoreReward(post, author)
                    }

                    else if(post.type == PostType.ANSWER){
                        this.checkForAnswerScoreReward(post, author)
                        this.checkForGuruBadge(post, author)
                    }

                    break

                case TopicServiceEventCode.POST_DOWNVOTED:
                    def voter = topicEvent.actor.userInformation
                    this.unlockBadge('critic', voter)
                    break

                case TopicServiceEventCode.VIEWS_COUNT_INCREMENTED:
                    def topic = topicEvent.topic
                    def author = topicEvent.topic.question.content.author
                    this.checkForQuestionViewsReward(topic.views, author)
                    break

                case TopicServiceEventCode.POST_TAGGED_AS_BEST_ANSWER:
                    def answer = topicEvent.post
                    def author = topicEvent.post.content.author
                    this.checkForGuruBadge(answer, author)
                    break

                case TopicServiceEventCode.NEW_COMMENT_ON_POST:
                    log.debug("New comment on post !")
                    def author = topicEvent.actor.userInformation
                    this.checkForCommentatorBadge(author)
                    break

                case TopicServiceEventCode.POST_CORRECTED:
                    def corrector = topicEvent.actor.userInformation
                    this.privateCheckForPostEditionReward(corrector)
                    break

            }
        }

        // Event sent by the UserService
        else if(event instanceof UserServiceEvent && eventCode instanceof UserServiceEventCode){
            def code = (UserServiceEventCode) eventCode
            def userEvent = (UserServiceEvent) event

            switch (code){
                case UserServiceEventCode.NEW_USER_CREATED:
                    this.unlockBadge('newby', userEvent.user.userInformation)
                    break

                case UserServiceEventCode.USER_UPDATED:
                    if(userEvent.user.userInformation.about){
                        this.unlockBadge('autobiographer', userEvent.user.userInformation)
                    }
                    break
            }

        }

    }

    /**
     * Gives a badge to the user when he edits (corrects) 10 posts.
     * @param corrector The user who could be rewarded.
     * @return The rewarded user or nothing.
     */
    def privateCheckForPostEditionReward(UserInformation corrector){
        //Bit of hack here...
        corrector.editedPosts += 1
        corrector.save( failOnError: true)

        log.debug("User ${corrector.nickname} has editied : ${corrector.editedPosts} posts.")

        if(corrector.editedPosts >= 1 && corrector.editedPosts < 80){
            this.unlockBadge('editor', corrector)
        }
        else if(corrector.editedPosts >= 80 && corrector.editedPosts < 500){
            this.unlockBadge('strunkAndWhite', corrector)
        }
        else if(corrector.editedPosts >= 500){
            this.unlockBadge('copyEditor', corrector)
        }
    }

    /**
     * Gives a badge to the user when he comments 10 posts.
     * @param author The user who could be rewarded.
     * @return The rewarded user or nothing.
     */
    def private checkForCommentatorBadge(UserInformation author){
        def Badge badge = this.badgesMap['commentator']

        if(!isBadgeAlreadyUnlock(author, badge)){
            def comments = []

            for(message in author.messages){
                log.debug("Message type : ${message.post.type}.")
                if(message.post.type == PostType.COMMENT)
                    comments.add(message.post)
            }

            log.debug("Number of comments : ${comments.size()}")

            if(comments.size() >= 10){
                this.unlockBadge('commentator', author)
            }
        }
    }

    /**
     * Gives a badge to the user when one of his answers is accepted with a score of
     * 40 or more.
     * @param answer The answer to check.
     * @param author The user who could be rewarded.
     * @return The rewarded user or nothing.
     */
    def private checkForGuruBadge(Post answer, UserInformation author){
        if(answer.topic.bestAnswer){
            if(answer.id == answer.topic.bestAnswer.id && answer.getScore() >= 40)
                this.unlockBadge('guru', author)
        }
    }

    /**
     * Gives a badge to the user one of his questions reaches a certain score.
     * @param question The question to check.
     * @param author The user who could be rewarded.
     * @return The rewarded user or nothing
     */
    def private checkForQuestionScoreReward(Post question, UserInformation author){
        def score = question.getScore()

        if(score > 0){
            this.unlockBadge('studentQuestion', author)
        }
        else if(score >= 10 && score < 25){
            this.unlockBadge('niceQuestion', author)
        }
        else if(score >= 25 && score < 100){
            this.unlockBadge('goodQuestion', author)
        }
        else if(score >= 100 && score < 500){
            this.unlockBadge('greatQuestion', author)
        }
        else if(score >= 500){
            this.unlockBadge('ultimateBadge', author)
        }
    }

    /**
     * Gives a badge to the user one of his questions reaches a certain number of views.
     * @param question The question to check.
     * @param author The user who could be rewarded.
     * @return The rewarded user or nothing
     */
    def private checkForQuestionViewsReward(int views, UserInformation author){

        if(views >= 1000 && views < 2500){
            this.unlockBadge('popularQuestion', author)
        }
        else if(views >= 2500 && views < 10000){
            this.unlockBadge('notableQuestion', author)
        }
        else if(views >= 10000 && views < 50000){
            this.unlockBadge('famousQuestion', author)
        }
        else if(views >= 50000){
            this.unlockBadge('acclaimedQuestion', author)
        }
    }

    /**
     * Gives a badge to the user one of his answers reaches a certain score.
     * @param question The answer to check.
     * @param author The user who could be rewarded.
     * @return The rewarded user or nothing
     */
    def private checkForAnswerScoreReward(Post answer, UserInformation author){
        def score = answer.getScore()

        if(score >= 1 && score < 10) {
            this.unlockBadge('teacher', author)
        }
        else if(score >= 10 && score < 25){
            this.unlockBadge('niceAnswer', author)
        }
        else if(score >= 25 && score < 100){
            this.unlockBadge('goodAnswer', author)
        }
        else if(score >= 100 && score < 500){
            this.unlockBadge('greatAnswer', author)
        }
        else if(score >= 500){
            this.unlockBadge('fortyTwo', author)
        }
    }
}
