package fr.isima.vectoroutofrange

import grails.transaction.Transactional

@Transactional
class TopicService extends Subject{

    TagService tagService

    /**
     * Gets the user corresponding to the id passed as parameter.
     * @param userId The user's id to retrieve from the database.
     * @return The user corresponding to the id.
     */
    def getUser(long userId){
        def user = User.get(userId)
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
     * @return The post corresponding to the id.
     */
    def getPost(long postId){
        def post = Post.get(postId)
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
     * @return The topic corresponding to the id.
     */
    def getTopic(long topicId){
        def topic = Topic.get(topicId)
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
        def newTopic = new Topic(title: title, question: newPost)

        def collectedTags = this.getAssociatedTags(tagsName)

        for (tag in collectedTags) {
            newTopic.addToTags(tag)
        }

        newPost.save(flush: true, failOnError: true)
        newPost.topic = newTopic
        newTopic.save(flush: true, failOnError: true)
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
            postToComment.save(flush: true, failOnError: true)
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
        topicToAnswer.save(flush: true, failOnError: true)
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
        post.save(flush: true, failOnError: true)
        log.info("User ${author.userInformation.nickname} corrected a post on the topic ${post.topic.title}.")
        this.notifyObservers(new TopicServiceEvent(actor: author, post: post, topic: post.topic),  TopicServiceEventCode.POST_CORRECTED)

        return post
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
        def voter = this.getUser(voterId)
        def post = this.getPost(postId)

        def Vote vote = getUserVoteOnPost(post.id, voter.id)

        if(vote)
        {
            vote.type = type
            vote.save(flush: true, failOnError: true)
            log.info("User ${voter.userInformation.nickname} updated his vote for a post on the topic ${post.topic.title}.")
        }

        else
        {
            vote = new Vote(type: type, date: new Date(), author: voter.userInformation)
            voter.userInformation.addToVotes(vote)
            post.addToVotes(vote)
            post.save(flush: true, failOnError: true)
            log.info("User ${voter.userInformation.nickname} voted for a post on the topic ${post.topic.title}.")
        }

        if(type == VoteType.UPVOTE)
            this.notifyObservers(new TopicServiceEvent(actor: voter, post: post, topic: post.topic), TopicServiceEventCode.POST_UPVOTED)
        else
            this.notifyObservers(new TopicServiceEvent(actor: voter, post: post, topic: post.topic), TopicServiceEventCode.POST_DOWNVOTED)

        return vote
    }
}
