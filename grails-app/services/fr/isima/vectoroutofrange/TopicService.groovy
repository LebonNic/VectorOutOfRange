package fr.isima.vectoroutofrange

import grails.transaction.Transactional

@Transactional
class TopicService extends Subject{

    TagService tagService

    /**
     * Gets the user corresponding to the id passed as parameter.
     * @param userId The user's id to retrieve from the database.
     * @param lock Tells the method to lock the loaded object.
     * @return The user corresponding to the id.
     */
    def getUser(long userId, boolean lock = false){
        def user

        if (lock)
            user = User.lock(userId)
        else
            user = User.get(userId)

        if(user){
            return user
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.getUser doesn't exist.")
        }
    }

    /**
     * Gets the post corresponding to the id passed as parameter.
     * @param postId The post's id to retrieve from the database.
     * @param lock Tells the method to lock the loaded object.
     * @return The post corresponding to the id.
     */
    def getPost(long postId, boolean lock = false){
        def post

        if(lock)
            post = Post.lock(postId)
        else
            post = Post.get(postId)

        if(post){
            return post
        }
        else {
            throw new TopicServiceException(TopicServiceExceptionCode.POST_NOT_FOUND, "The post's id passed to the method TopicService.getPost doesn't exist.")
        }
    }

    /**
     * Gets the topic corresponding to the id passed as parameter.
     * @param topicId The topic's id to retrieve from the database.
     * @param lock Tells the method to lock the loaded object.
     * @return The topic corresponding to the id.
     */
    def getTopic(long topicId, boolean lock = false){
        def topic

        if(lock)
            topic = Topic.lock(topicId)
        else
            topic = Topic.get(topicId)

        if(topic){
            return topic
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.TOPIC_NOT_FOUND, "The topic's id passed to the method TopicService.getTopic doesn't exist.")
        }
    }

    /**
     * Retrieves or creates the tags corresponding to the names passed as
     * parameter. If a name matches an existing tag in the database, this tag is
     * retrieved, otherwise a new one is created.
     * @param tagsNames A list of strings corresponding to a list of tags' names.
     * @return A collection of tags.
     */
    private def getAssociatedTags(List<String> tagsNames){
        def collectedTags = []

        for (name in tagsNames) {
            def tag = Tag.findByName(name)
            if (tag) {
                collectedTags << tag
            } else {
                def newTag = tagService.createTag(name)
                collectedTags << newTag
            }
        }

        return collectedTags
    }

    /**
     * Creates a new topic and saves it in the database.
     * @param authorId Id of the user who creates the topic.
     * @param title Title of the topic.
     * @param text Content of the topic.
     * @param tagsName List of tags associated with the topic.
     * @return The new topic.
     */
    def createNewTopic(long authorId, String title, String text, List<String> tagsName) {
        def author = this.getUser(authorId)

        def questionText = new Message(text: text, date: new Date(), author: author.userInformation)
        author.userInformation.addToMessages(questionText)
        def newPost = new Post(content: questionText, type: PostType.QUESTION)
        def newTopic = new Topic(title: title, question: newPost, views: 0)

        def collectedTags = this.getAssociatedTags(tagsName)

        for (tag in collectedTags) {
            newTopic.addToTags(tag)
        }

        newPost.save( failOnError: true)
        newPost.topic = newTopic
        newTopic.save( failOnError: true)

        questionText.post = newPost
        questionText.save( failOnError: true)

        log.info("Creation of the topic ${title} by ${author.userInformation.nickname}.")
        this.notifyObservers(new TopicServiceEvent(actor: author, post: newPost, topic: newTopic), TopicServiceEventCode.NEW_TOPIC_CREATED)

        return newTopic
    }

    /**
     * Adds a comment on a post.
     * @param postId The id of the post to comment.
     * @param authorId The id of the user who comments the post.
     * @param text The content of the author's comment.
     * @return The new comment.
     */
    def addComment(long postId, long authorId, String text){
        def author = this.getUser(authorId)
        def postToComment = this.getPost(postId)

        if(postToComment.type != PostType.COMMENT){
            def commentText = new Message(text: text, date: new Date(), author: author.userInformation)
            author.userInformation.addToMessages(commentText)
            def commentPost = new Post(topic: postToComment.topic, content: commentText, type: PostType.COMMENT)
            postToComment.addToComments(commentPost)
            postToComment.save( failOnError: true)

            commentText.post = commentPost
            commentText.save( failOnError: true)

            log.info("User ${author.userInformation.nickname} posted a comment on a topic entitled ${postToComment.topic.title}.")
            this.notifyObservers(new TopicServiceEvent(actor: author, post: postToComment, topic: postToComment.topic),  TopicServiceEventCode.NEW_COMMENT_ON_POST)

            return commentPost
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.BUSINESS_LOGIC_ERROR, "The post which is trying to be commented is already a comment.")
        }
    }

    /**
     * Adds an answer to a topic.
     * @param topicId The id of the topic to give an answer.
     * @param authorId The id of the user who gives the answers.
     * @param text The content of the answer.
     * @return The new post corresponding to the answer.
     */
    def addAnswer(long topicId, long authorId, String text){
        def author = this.getUser(authorId)
        def topicToAnswer = this.getTopic(topicId)

        def answerText = new Message(text: text, date: new Date(), author: author.userInformation)
        author.userInformation.addToMessages(answerText)
        def answerPost = new Post(topic: topicToAnswer, content: answerText, type: PostType.ANSWER)
        topicToAnswer.addToAnswers(answerPost)
        topicToAnswer.save( failOnError: true)

        answerText.post = answerPost
        answerText.save( failOnError: true)

        log.info("User ${author.userInformation.nickname} answered the question posted on the topic ${topicToAnswer.title}.")
        this.notifyObservers(new TopicServiceEvent(actor: author, post: answerPost, topic: topicToAnswer),  TopicServiceEventCode.NEW_ANSWER_ON_TOPIC)

        return answerPost
    }

    /**
     * Allows someone to correct a post.
     * @param postId The id of the post to correct.
     * @param authorId The id of the user who made the correction.
     * @param text The content of the correction.
     * @return The corrected post.
     */
    def correctPost(long postId, long authorId, String text){
        def author = this.getUser(authorId)
        def post = this.getPost(postId)

        def correctedMessage = new Message(text: text, date: new Date(), author: author.userInformation)
        author.userInformation.addToMessages(correctedMessage)
        post.replaceCurrentContent(correctedMessage)
        post.save( failOnError: true)

        correctedMessage.post = post
        correctedMessage.save( failOnError: true)

        log.info("User ${author.userInformation.nickname} corrected a post on the topic ${post.topic.title}.")
        this.notifyObservers(new TopicServiceEvent(actor: author, post: post, topic: post.topic),  TopicServiceEventCode.POST_CORRECTED)

        return post
    }

    /**
     * Deletes a post. If the post is a question, the associated topic is also suppressed
     * with all its content (comments, answer, votes, etc...) .
     * @param postId The id of the post to suppress.
     * @return ???
     */
    def deletePost(long postId){
        def postToDelete = this.getPost(postId)

        if(postToDelete.type == PostType.COMMENT){
            log.info("Deletes a comment from ${postToDelete.content.author.nickname}.")
            postToDelete.parentPost.removeFromComments(postToDelete)
            this.deleteContentAssociateToPost(postToDelete)
        }

        else if(postToDelete.type == PostType.ANSWER){
            log.info("Deletes a answer from ${postToDelete.content.author.nickname}.")
            if(postToDelete.topic.bestAnswer){
                if(postToDelete.id == postToDelete.topic.bestAnswer.id){
                    log.debug("Deletes the best answer.")
                    postToDelete.topic.bestAnswer = null
                }
            }
            postToDelete.topic.removeFromAnswers(postToDelete)
            this.deleteContentAssociateToPost(postToDelete)
        }

        else if(postToDelete.type == PostType.QUESTION){
            log.info("Deletes a question from ${postToDelete.content.author.nickname}.")
            def topic = postToDelete.topic
            topic.answers.each { answer ->
                this.deleteContentAssociateToPost(answer)
                answer.delete()
            }
            this.deleteContentAssociateToPost(postToDelete)
            topic.delete()
        }

        log.debug("call .delete().")
        postToDelete.delete()
    }

    /**
     * Removes associations between the classes Message, Vote and Post in order to allow the post's suppression.
     * @param post The post to be processed.
     */
    def private deleteContentAssociateToPost(Post post){

        // Delete the content
        log.debug("Deletes the content of the post.")
        post.content.author.removeFromMessages(post.content)
        post.content.delete()

        // Delete the history
        for(message in post.history){
            log.debug("Deletes an history message from ${message.author.nickname}.")
            message.author.removeFromMessages(message)
            //message.delete()
        }

        // Delete the votes
        for(vote in post.votes){
            log.debug("Deletes a vote from ${vote.author.nickname}.")
            vote.author.removeFromVotes(vote)
        }

        // Delete the comments
        for(comment in post.comments){
            log.debug("Deletes a comment from ${comment.content.author.nickname}.")
            deleteContentAssociateToPost(comment)
            comment.delete()
        }
    }

    /**
     * Retrieves the vote of a user on a certain post.
     * @param postId The id of the post.
     * @param userId The id of the user.
     * @return The user's vote if this one has already voted on the post or null
     * if he hasn't done it.
     */
    def getUserVoteOnPost(long postId, long userId){
        def user = this.getUser(userId)
        def post = this.getPost(postId)
        def userVote = null

        for(vote in post.votes){
            if(vote.author.id == user.userInformation.id)
            {
                userVote = vote
                break
            }
        }

        return userVote
    }

    /**
     * Returns the score of a post.
     * @param postId The id of the post.
     * @return The post's score.
     */
    def getScoreForPost(long postId){
        def post = this.getPost(postId)
        return post.getScore()
    }

    /**
     * Allows a user to vote for a post (upvote or downvote).
     * @param postId The id of the voted post.
     * @param voterId The id of the user who votes.
     * @param type The type of the vote (upvote or downvote).
     * @return The user's vote.
     */
    def voteForPost(long postId, long voterId, VoteType type){
        def voter = this.getUser(voterId, true)
        def post = this.getPost(postId)

        def Vote vote = getUserVoteOnPost(post.id, voter.id)
        def boolean voteTypeHasChanged = true

        if(vote)
        {
            if(vote.type == type)
                voteTypeHasChanged = false
            else
                vote.type = type

            vote.save( failOnError: true)
            log.info("User ${voter.userInformation.nickname} updated his vote for a post on the topic ${post.topic.title}.")
        }

        else
        {
            vote = new Vote(type: type, date: new Date(), author: voter.userInformation)
            voter.userInformation.addToVotes(vote)
            post.addToVotes(vote)
            post.save( failOnError: true)
            log.info("User ${voter.userInformation.nickname} voted for a post on the topic ${post.topic.title}.")
        }

        if(voteTypeHasChanged){
            if(type == VoteType.UPVOTE)
                this.notifyObservers(new TopicServiceEvent(actor: voter, post: post, topic: post.topic), TopicServiceEventCode.POST_UPVOTED)
            else
                this.notifyObservers(new TopicServiceEvent(actor: voter, post: post, topic: post.topic), TopicServiceEventCode.POST_DOWNVOTED)
        }

        return vote
    }

    /**
     * Allows to tag a post as the best answer on a topic.
     * @param topicId The topic's id where the answer is.
     * @param postId The post's id corresponding to the answer.
     * @return The topic where the answer is tagged.
     */
    def tagPostAsBestAnswer(long topicId, long postId){
        def topic = this.getTopic(topicId)
        def post = this.getPost(postId)

        if(post.type == PostType.ANSWER){
            topic.bestAnswer = post
            topic.save( failOnError: true)
            log.info("${post.content.author.nickname}'s post has been tagged as best answer on the topic ${topic.title}.")
            this.notifyObservers(new TopicServiceEvent(actor: null, post: post, topic: topic), TopicServiceEventCode.POST_TAGGED_AS_BEST_ANSWER)
            return topic
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.BUSINESS_LOGIC_ERROR, "Only answers can be tagged as best answer.")
        }
    }

    /**
     * Allows to increment the counter of views on a topic.
     * @param topicId The topic's id where the counter needs to be incremented.
     * @return The topic where the counter is incremented.
     */
    def incrementViewsCount(long topicId){
        def topic = this.getTopic(topicId, true)
        topic.views += 1
        topic.save( failOnError: true)
        this.notifyObservers(new TopicServiceEvent(actor: null, post: topic.question, topic: topic), TopicServiceEventCode.VIEWS_COUNT_INCREMENTED)

        return topic
    }
}
