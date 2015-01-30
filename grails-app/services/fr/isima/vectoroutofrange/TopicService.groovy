package fr.isima.vectoroutofrange

import grails.transaction.Transactional

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
        }
        else{
            throw new TopicException(TopicExceptionErrorCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.createNewTopic doesn't exist.")
        }
    }

    def addComment(long authorId, long postId, String text){
        def author = User.get(authorId)

        if(author){
            def postToComment = Post.get(postId)

            if(postToComment){
                def commentText = new Message(text: text, date: new Date(), author: author.userInformation)
                author.userInformation.addToMessages(commentText)
                def commentPost = new Post(topic: postToComment.topic, content: commentText, type: PostType.COMMENT)
                postToComment.addToComments(commentPost)
                postToComment.save(flush: true, failOnError: true)
            }
            else{
                throw new TopicException(TopicExceptionErrorCode.POST_NOT_FOUND, "The post's id passed to the method TopicService.addComment doesn't exist.")
            }
        }
        else{
            throw new TopicException(TopicExceptionErrorCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.addComment doesn't exist.")
        }
    }

    def addAnswer(long authorId, long topicId, String text){
        def author = User.get(authorId)

        if(author){
            def topicToAnswer = Topic.get(topicId)

            if(topicToAnswer){
                def answerText = new Message(text: text, date: new Date(), author: author.userInformation)
                author.userInformation.addToMessages(answerText)
                def answerPost = new Post(topic: topicToAnswer, content: answerText, type: PostType.ANSWER)
                topicToAnswer.addToAnswers(answerPost)
                topicToAnswer.save(flush: true, failOnError: true)
            }
            else{
                throw new TopicException(TopicExceptionErrorCode.TOPIC_NOT_FOUND, "The topic's id passed to the method TopicService.addAnswer doesn't exist.")
            }
        }
        else{
            throw new TopicException(TopicExceptionErrorCode.AUTHOR_NOT_FOUND, "The author's id passed to the method TopicService.addAnswer doesn't exist.")
        }
    }
}
