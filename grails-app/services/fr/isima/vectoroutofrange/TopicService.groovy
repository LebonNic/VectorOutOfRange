package fr.isima.vectoroutofrange

import grails.transaction.Transactional
import jline.internal.Log

@Transactional
class TopicService {

    def createNewTopic(long authorId, String title, String text, List<String> tagsName){
        def author = User.get(authorId)

        if(author){
            def collectedTags = []

            for(name in tagsName){
                def tag = Tag.findByName(name)
                if(tag){
                    collectedTags << tag
                }
                else{
                    def newTag = new Tag(name: name, definition: "").save(flush: true, failOnError: true)
                    collectedTags << newTag
                }
            }

            def questionText = new Message(text: text, date: new Date(), author: author.userInformation)
            author.userInformation.addToMessages(questionText)
            def newPost = new Post(content: questionText, type: PostType.QUESTION)
            def newTopic = new Topic(title: title, question: newPost)

            for(tag in collectedTags){
                newTopic.addToTags(tag)
            }

            newPost.save(flush: true, failOnError: true)
            newPost.topic = newTopic
            newTopic.save(flush: true, failOnError: true)
            Log.info("Creation of the topic ${title} by ${author.userInformation.nickname}.")

            return newTopic
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.createNewTopic doesn't exist.")
        }
    }

    def addComment(long postId, long authorId, String text){

        def author = User.get(authorId)

        if(author){
            def postToComment = Post.get(postId)

            if(postToComment){

                if(postToComment.type != PostType.COMMENT){
                    def commentText = new Message(text: text, date: new Date(), author: author.userInformation)
                    author.userInformation.addToMessages(commentText)
                    def commentPost = new Post(topic: postToComment.topic, content: commentText, type: PostType.COMMENT)
                    postToComment.addToComments(commentPost)
                    postToComment.save(flush: true, failOnError: true)
                    Log.info("User ${author.userInformation.nickname} posted a comment on a topic entitled ${postToComment.topic.title}.")

                    return postToComment
                }
                else{
                    throw new TopicServiceException(TopicServiceExceptionCode.BUSINESS_LOGIC_ERROR, "The post which is trying to be commented is already a comment.")
                }

            }
            else{
                throw new TopicServiceException(TopicServiceExceptionCode.POST_NOT_FOUND, "The post's id passed to the method TopicService.addComment doesn't exist.")
            }
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.addComment doesn't exist.")
        }
    }

    def addAnswer(long topicId, long authorId, String text){
        def author = User.get(authorId)

        if(author){
            def topicToAnswer = Topic.get(topicId)

            if(topicToAnswer){
                def answerText = new Message(text: text, date: new Date(), author: author.userInformation)
                author.userInformation.addToMessages(answerText)
                def answerPost = new Post(topic: topicToAnswer, content: answerText, type: PostType.ANSWER)
                topicToAnswer.addToAnswers(answerPost)
                topicToAnswer.save(flush: true, failOnError: true)
                Log.info("User ${author.userInformation.nickname} answered the question posted on the topic ${topicToAnswer.title}.")

                return topicToAnswer
            }
            else{
                throw new TopicServiceException(TopicServiceExceptionCode.TOPIC_NOT_FOUND, "The topic's id passed to the method TopicService.addAnswer doesn't exist.")
            }
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.addAnswer doesn't exist.")
        }
    }

    def correctPost(long postId, long authorId, String text){
        def author = User.get(authorId)

        if(author){
            def post = Post.get(postId)

            if(post){
                def correctedMessage = new Message(text: text, date: new Date(), author: author.userInformation)
                author.userInformation.addToMessages(correctedMessage)
                post.replaceCurrentContent(correctedMessage)
                post.save(flush: true, failOnError: true)
                Log.info("User ${author.userInformation.nickname} corrected a post on the topic ${post.topic.title}.")

                return post
            }
            else{
                throw new TopicServiceException(TopicServiceExceptionCode.POST_NOT_FOUND, "The post's id passed to the method TopicService.correctPost doesn't exist.")
            }
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.correctPost doesn't exist.")
        }
    }

    def voteForPost(long postId, long voterId, VoteType type){
        def voter = User.get(voterId)
        if(voter){
            def post = Post.get(postId)

            if(post){
                def vote = new Vote(type: type, date: new Date(), author: voter.userInformation)
                voter.userInformation.addToVotes(vote)
                post.addToVotes(vote)
                post.save(flush: true, failOnError: true)
                Log.info("User ${voter.userInformation.nickname} voted for a post on the topic ${post.topic.title}.")

                return post
            }
            else{
                throw new TopicServiceException(TopicServiceExceptionCode.POST_NOT_FOUND, "The post's id passed to the method TopicService.voteForPost doesn't exist.")
            }
        }
        else{
            throw new TopicServiceException(TopicServiceExceptionCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.voteForPost doesn't exist.")
        }
    }
}
