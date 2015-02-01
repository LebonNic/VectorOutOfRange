package fr.isima.vectoroutofrange

import grails.transaction.Transactional
import jline.internal.Log

@Transactional
class TopicService extends Subject{

    def getUser(long userId){
        def user = User.get(userId)
        if(user){
            return user
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.getUser doesn't exist.")
        }
    }

    def getPost(long postId){
        def post = Post.get(postId)
        if(post){
            return post
        }
        else {
            throw new TopicServiceException(TopicServiceExceptionCode.POST_NOT_FOUND, "The post's id passed to the method TopicService.getPost doesn't exist.")
        }
    }

    def getTopic(long topicId){
        def topic = Topic.get(topicId)
        if(topic){
            return topic
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.TOPIC_NOT_FOUND, "The topic's id passed to the method TopicService.getTopic doesn't exist.")
        }
    }

    private def getAssociatedTags(List<String> tagsName){
        def collectedTags = []

        for (name in tagsName) {
            def tag = Tag.findByName(name)
            if (tag) {
                collectedTags << tag
            } else {
                def newTag = new Tag(name: name, definition: "").save(flush: true, failOnError: true)
                collectedTags << newTag
            }
        }

        return collectedTags
    }

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
        Log.info("Creation of the topic ${title} by ${author.userInformation.nickname}.")
        this.notifyObservers(new TopicServiceEvent(actor: author, post: newPost, topic: newTopic), TopicServiceEventCode.NEW_TOPIC_CREATED)

        return newTopic
    }

    def addComment(long postId, long authorId, String text){
        def author = this.getUser(authorId)
        def postToComment = this.getPost(postId)

        if(postToComment.type != PostType.COMMENT){
            def commentText = new Message(text: text, date: new Date(), author: author.userInformation)
            author.userInformation.addToMessages(commentText)
            def commentPost = new Post(topic: postToComment.topic, content: commentText, type: PostType.COMMENT)
            postToComment.addToComments(commentPost)
            postToComment.save(flush: true, failOnError: true)
            Log.info("User ${author.userInformation.nickname} posted a comment on a topic entitled ${postToComment.topic.title}.")
            this.notifyObservers(new TopicServiceEvent(actor: author, post: postToComment, topic: postToComment.topic),  TopicServiceEventCode.NEW_COMMENT_ON_POST)

            return commentPost
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.BUSINESS_LOGIC_ERROR, "The post which is trying to be commented is already a comment.")
        }
    }

    def addAnswer(long topicId, long authorId, String text){
        def author = this.getUser(authorId)
        def topicToAnswer = this.getTopic(topicId)

        def answerText = new Message(text: text, date: new Date(), author: author.userInformation)
        author.userInformation.addToMessages(answerText)
        def answerPost = new Post(topic: topicToAnswer, content: answerText, type: PostType.ANSWER)
        topicToAnswer.addToAnswers(answerPost)
        topicToAnswer.save(flush: true, failOnError: true)
        Log.info("User ${author.userInformation.nickname} answered the question posted on the topic ${topicToAnswer.title}.")
        this.notifyObservers(new TopicServiceEvent(actor: author, post: answerPost, topic: topicToAnswer),  TopicServiceEventCode.NEW_ANSWER_ON_TOPIC)

        return answerPost
    }

    def correctPost(long postId, long authorId, String text){
        def author = this.getUser(authorId)
        def post = this.getPost(postId)

        def correctedMessage = new Message(text: text, date: new Date(), author: author.userInformation)
        author.userInformation.addToMessages(correctedMessage)
        post.replaceCurrentContent(correctedMessage)
        post.save(flush: true, failOnError: true)
        Log.info("User ${author.userInformation.nickname} corrected a post on the topic ${post.topic.title}.")
        this.notifyObservers(new TopicServiceEvent(actor: author, post: post, topic: post.topic),  TopicServiceEventCode.POST_CORRECTED)

        return post
    }

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

    def getScoreForPost(long postId){
        def post = this.getPost(postId)
        return post.getScore()
    }

    def voteForPost(long postId, long voterId, VoteType type){
        def voter = this.getUser(voterId)
        def post = this.getPost(postId)

        def Vote vote = getUserVoteOnPost(post.id, voter.id)

        if(vote)
        {
            vote.type = type
            vote.save(flush: true, failOnError: true)
            Log.info("User ${voter.userInformation.nickname} updated his vote for a post on the topic ${post.topic.title}.")
        }

        else
        {
            vote = new Vote(type: type, date: new Date(), author: voter.userInformation)
            voter.userInformation.addToVotes(vote)
            post.addToVotes(vote)
            post.save(flush: true, failOnError: true)
            Log.info("User ${voter.userInformation.nickname} voted for a post on the topic ${post.topic.title}.")
        }

        this.notifyObservers(new TopicServiceEvent(actor: voter, post: post, topic: post.topic), TopicServiceEventCode.POST_VOTED)

        return vote
    }
}
