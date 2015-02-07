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
    void init() {
        this.badgesMap['newby'] = new Badge(name: "voor.badge.newby.name", description: "voor.badge.newby.description", type: BadgeType.BRONZE).save(failOnError: true)

        this.badgesMap['niceQuestion'] = new Badge(name: "voor.badge.niceQuestion.name", description: "voor.badge.niceQuestion.name", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['goodQuestion'] = new Badge(name: "voor.badge.goodQuestion.name", description: "voor.badge.goodQuestion.name", type: BadgeType.SILVER).save(failOnError: true)
        this.badgesMap['greatQuestion'] = new Badge(name: "voor.badge.greatQuestion.name", description: "voor.badge.greatQuestion.name", type: BadgeType.GOLD).save(failOnError: true)
        this.badgesMap['ultimateBadge'] = new Badge(name: "voor.badge.ultimateQuestion.name", description: "voor.badge.ultimateQuestion.name", type: BadgeType.PLATINUM).save(failOnError: true)

        this.badgesMap['popularQuestion'] = new Badge(name: "voor.badge.popularQuestion.name", description: "voor.badge.popularQuestion.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['notableQuestion'] = new Badge(name: "voor.badge.notableQuestion.name", description: "voor.badge.notableQuestion.description", type: BadgeType.SILVER).save(failOnError: true)
        this.badgesMap['famousQuestion'] = new Badge(name: "voor.badge.famousQuestion.name", description: "voor.badge.famousQuestion.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['acclaimedQuestion'] = new Badge(name: "voor.badge.acclaimedQuestion.name", description: "voor.badge.acclaimedQuestion.description", type: BadgeType.PLATINUM).save(failOnError: true)

        this.badgesMap['studentQuestion'] = new Badge(name: "voor.badge.student.name", description: "voor.badge.student.description", type: BadgeType.BRONZE).save(failOnError: true)

        this.badgesMap['explainer'] = new Badge(name: "voor.badge.explainer.name", description: "voor.badge.explainer.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['refiner'] = new Badge(name: "voor.badge.refiner.name", description: "voor.badge.refiner.description", type: BadgeType.SILVER).save(failOnError: true)
        this.badgesMap['illuminator'] = new Badge(name: "voor.badge.illuminator.name", description: "voor.badge.illuminator.description", type: BadgeType.GOLD).save(failOnError: true)

        this.badgesMap['guru'] = new Badge(name: "voor.badge.guru.name", description: "voor.badge.guru.description", type: BadgeType.SILVER).save(failOnError: true)

        this.badgesMap['niceAnswer'] = new Badge(name: "voor.badge.niceAnswer.name", description: "voor.badge.niceAnswer.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['goodAnswer'] = new Badge(name: "voor.badge.goodAnswer.name", description: "voor.badge.goodAnswer.description", type: BadgeType.SILVER).save(failOnError: true)
        this.badgesMap['greatAnswer'] = new Badge(name: "voor.badge.greatAnswer.name", description: "voor.badge.greatAnswer.description", type: BadgeType.GOLD).save(failOnError: true)
        this.badgesMap['fortyTwo'] = new Badge(name: "voor.badge.fortyTwo.name", description: "voor.badge.fortyTwo.description", type: BadgeType.PLATINUM).save(failOnError: true)

        this.badgesMap['populist'] = new Badge(name: "voor.badge.populist.name", description: "voor.badge.populist.description", type: BadgeType.GOLD).save(failOnError: true)
        this.badgesMap['reversal'] = new Badge(name: "voor.badge.reversal.name", description: "voor.badge.reversal.description", type: BadgeType.GOLD).save(failOnError: true)
        this.badgesMap['selfLearner'] = new Badge(name: "voor.badge.selfLearner.name", description: "voor.badge.selfLearner.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['teacher'] = new Badge(name: "voor.badge.teacher.name", description: "voor.badge.teacher.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['autobiographer'] = new Badge(name: "voor.badge.autobiographer.name", description: "voor.badge.autobiographer.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['commentator'] = new Badge(name: "voor.badge.commentator.name", description: "voor.badge.commentator.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['pundit'] = new Badge(name: "voor.badge.pundit.name", description: "voor.badge.pundit.description", type: BadgeType.SILVER).save(failOnError: true)

        this.badgesMap['critic'] = new Badge(name: "voor.badge.critic.name", description: "voor.badge.critic.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['supporter'] = new Badge(name: "voor.badge.supporter.name", description: "voor.badge.supporter.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['disciplined'] = new Badge(name: "voor.badge.disciplined.name", description: "voor.badge.disciplined.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['peerPressure'] = new Badge(name: "voor.badge.peerPressure.name", description: "voor.badge.peerPressure.description", type: BadgeType.BRONZE).save(failOnError: true)

        this.badgesMap['editor'] = new Badge(name: "voor.badge.editor.name", description: "voor.badge.editor.description", type: BadgeType.BRONZE).save(failOnError: true)
        this.badgesMap['strunkAndWhite'] = new Badge(name: "voor.badge.strunkAndWhite.name", description: "voor.badge.strunkAndWhite.description", type: BadgeType.SILVER).save(failOnError: true)
        this.badgesMap['copyEditor'] = new Badge(name: "voor.badge.copyEditor.name", description: "voor.badge.copyEditor.description", type: BadgeType.GOLD).save(failOnError: true)
    }

    /**
     * Gives a badge to a user.
     * @param badgeKey A string corresponding to a badge's key in the BadgeService's map.
     * @param user The user who receives the badges.
     * @return The user who receives the badges.
     */
    def private unlockBadge(String badgeKey, UserInformation user) {
        Badge badge = (Badge) this.badgesMap[badgeKey]

        if (badge) {
            if (!isBadgeAlreadyUnlock(user, badge)) {
                user.addToBadges(badge)
                log.info("User ${user.nickname} has just unlocked a new Badge : ${badge.name}.")
                user.save(failOnError: true)
            }
        } else {
            throw new BadgeServiceException(BadgeServiceExceptionCode.BADGE_NOT_FOUND, "The badge's key passed to the method BadgeService.unlockBadge doesn't exist.")
        }
    }

    def private static isBadgeAlreadyUnlock(UserInformation user, Badge badge) {
        def boolean isBadgeUnlock = false
        def badges = user.getBadges(badge.id)

        if (badges.size() > 0)
            isBadgeUnlock = true

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
        if (event instanceof TopicServiceEvent && eventCode instanceof TopicServiceEventCode) {
            def code = (TopicServiceEventCode) eventCode
            def topicEvent = (TopicServiceEvent) event

            switch (code) {
                case TopicServiceEventCode.POST_UPVOTED:
                    def voter = topicEvent.actor.userInformation
                    def post = topicEvent.post
                    def author = topicEvent.post.content.author

                    this.unlockBadge('supporter', voter)

                    if (post.type == PostType.QUESTION) {
                        this.checkForQuestionScoreReward(post, author)
                    } else if (post.type == PostType.ANSWER) {
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
        else if (event instanceof UserServiceEvent && eventCode instanceof UserServiceEventCode) {
            def code = (UserServiceEventCode) eventCode
            def userEvent = (UserServiceEvent) event

            switch (code) {
                case UserServiceEventCode.NEW_USER_CREATED:
                    this.unlockBadge('newby', userEvent.user.userInformation)
                    break

                case UserServiceEventCode.USER_UPDATED:
                    if (userEvent.user.userInformation.about) {
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
    def privateCheckForPostEditionReward(UserInformation corrector) {
        //Bit of hack here...
        corrector.editedPosts += 1
        corrector.save(failOnError: true)

        log.debug("User ${corrector.nickname} has editied : ${corrector.editedPosts} posts.")

        if (corrector.editedPosts >= 1 && corrector.editedPosts < 80) {
            this.unlockBadge('editor', corrector)
        } else if (corrector.editedPosts >= 80 && corrector.editedPosts < 500) {
            this.unlockBadge('strunkAndWhite', corrector)
        } else if (corrector.editedPosts >= 500) {
            this.unlockBadge('copyEditor', corrector)
        }
    }

    /**
     * Gives a badge to the user when he comments 10 posts.
     * @param author The user who could be rewarded.
     * @return The rewarded user or nothing.
     */
    def private checkForCommentatorBadge(UserInformation author) {
        Badge badge = (Badge) this.badgesMap['commentator']

        if (!isBadgeAlreadyUnlock(author, badge)) {
            def comments = []

            for (message in author.messages) {
                log.debug("Message type : ${message.post.type}.")
                if (message.post.type == PostType.COMMENT)
                    comments.add(message.post)
            }

            log.debug("Number of comments : ${comments.size()}")

            if (comments.size() >= 10) {
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
    def private checkForGuruBadge(Post answer, UserInformation author) {
        if (answer.topic.bestAnswer) {
            if (answer.id == answer.topic.bestAnswer.id && answer.getScore() >= 40)
                this.unlockBadge('guru', author)
        }
    }

    /**
     * Gives a badge to the user one of his questions reaches a certain score.
     * @param question The question to check.
     * @param author The user who could be rewarded.
     * @return The rewarded user or nothing
     */
    def private checkForQuestionScoreReward(Post question, UserInformation author) {
        def score = question.getScore()

        if (score > 0) {
            this.unlockBadge('studentQuestion', author)
        } else if (score >= 10 && score < 25) {
            this.unlockBadge('niceQuestion', author)
        } else if (score >= 25 && score < 100) {
            this.unlockBadge('goodQuestion', author)
        } else if (score >= 100 && score < 500) {
            this.unlockBadge('greatQuestion', author)
        } else if (score >= 500) {
            this.unlockBadge('ultimateBadge', author)
        }
    }

    /**
     * Gives a badge to the user one of his questions reaches a certain number of views.
     * @param question The question to check.
     * @param author The user who could be rewarded.
     * @return The rewarded user or nothing
     */
    def private checkForQuestionViewsReward(int views, UserInformation author) {

        if (views >= 1000 && views < 2500) {
            this.unlockBadge('popularQuestion', author)
        } else if (views >= 2500 && views < 10000) {
            this.unlockBadge('notableQuestion', author)
        } else if (views >= 10000 && views < 50000) {
            this.unlockBadge('famousQuestion', author)
        } else if (views >= 50000) {
            this.unlockBadge('acclaimedQuestion', author)
        }
    }

    /**
     * Gives a badge to the user one of his answers reaches a certain score.
     * @param question The answer to check.
     * @param author The user who could be rewarded.
     * @return The rewarded user or nothing
     */
    def private checkForAnswerScoreReward(Post answer, UserInformation author) {
        def score = answer.getScore()

        if (score >= 1 && score < 10) {
            this.unlockBadge('teacher', author)
        } else if (score >= 10 && score < 25) {
            this.unlockBadge('niceAnswer', author)
        } else if (score >= 25 && score < 100) {
            this.unlockBadge('goodAnswer', author)
        } else if (score >= 100 && score < 500) {
            this.unlockBadge('greatAnswer', author)
        } else if (score >= 500) {
            this.unlockBadge('fortyTwo', author)
        }
    }
}
