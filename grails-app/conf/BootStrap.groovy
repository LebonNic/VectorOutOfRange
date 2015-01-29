import fr.isima.vectoroutofrange.Message
import fr.isima.vectoroutofrange.Post
import fr.isima.vectoroutofrange.PostType
import fr.isima.vectoroutofrange.Tag
import fr.isima.vectoroutofrange.Topic
import fr.isima.vectoroutofrange.Vote
import fr.isima.vectoroutofrange.VoteType
import jline.internal.Log

class BootStrap {

    def init = { servletContext ->

        // Creation of a new topic
        def grailsTag = new Tag(name: "Grails", definition: "Grails is an Open Source, full stack, web application framework for the JVM.").save(flush: true, failOnError: true)
        def gormTag = new Tag(name: "GORM", definition: "The fantastic ORM library for Golang, aims to be developer friendly.")
        def newMessage = new Message(text: "L'héritage avec GORM est il full bug ?", date: new Date())
        def newPost = new Post(content: newMessage, type: PostType.QUESTION)
        def newTopic = new Topic(title: "L'héritage avec GORM", question: newPost)
        newTopic.addToTags(grailsTag)
        newTopic.addToTags(gormTag)
        newTopic.save(flush: true, failOnError: true)

        // Correction of the post
        def postToCorrect = Post.get(1)

        if(postToCorrect){
            def correctedMessage = new Message(text: "L'héritage avec GORM est il bogué ?", date: new Date())
            postToCorrect.replaceCurrentContent(correctedMessage)
            postToCorrect.save(flush: true, failOnError: true)
        }
        else {
            Log.DEBUG("Impossible to fecth the post (correction routine)...")
        }

        // Add a comment on the post
        def postToComment = Post.get(1)

        if(postToComment){
            def message = new Message(text: "Your question is damn shit mothafuka !", date: new Date())
            def post = new Post(topic: postToComment.topic, content: message, type: PostType.COMMENT)
            postToComment.addToComments(post)
            postToComment.save(flush: true, failOnError: true)
        }
        else {
            Log.DEBUG("Impossible to fecth the post (comment routine)...")
        }

        // Down the bad comment
        def badComment = postToComment.comments.first()

        if(badComment){
            def downVote = new Vote(type: VoteType.DOWNVOTE, date: new Date())
            def anOtherDownVote = new Vote(type: VoteType.DOWNVOTE, date: new Date())
            badComment.addToVotes(downVote)
            badComment.addToVotes(anOtherDownVote)
            badComment.save(flush: true, failOnError: true)
        }
        else {
            Log.DEBUG("Can't fetch the bad comment...")
        }

        // Add an answer to the topic
        def topicToAnswer = Topic.get(1)
        if(topicToAnswer){
            def answerText = new Message(text: "Oui il existe des bugs non corrigés dans la gestion de l'héritage faite par GORM...", date: new Date())
            def answerPost = new Post(topic: topicToAnswer, content: answerText, type: PostType.ANSWER)
            topicToAnswer.addToAnswers(answerPost)
            topicToAnswer.save(flush: true, failOnError: true)
        }
        else {
            Log.DEBUG("Can't fetch the topic...")
        }

        Log.info("End of BootStrap ! =)")

    }
    def destroy = {
    }
}
