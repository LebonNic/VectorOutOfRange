import fr.isima.vectoroutofrange.Message
import fr.isima.vectoroutofrange.Post
import fr.isima.vectoroutofrange.PostType
import fr.isima.vectoroutofrange.Topic
import fr.isima.vectoroutofrange.Vote
import fr.isima.vectoroutofrange.VoteType
import jline.internal.Log

class BootStrap {

    def init = { servletContext ->

        // Creation of a new topic
        def newMessage = new Message(text: "L'héritage avec GORM est il full bug ?")
        def newPost = new Post(content: newMessage, type: PostType.QUESTION)
        def newTopic = new Topic(title: "L'héritage avec GORM", question: newPost)
        newTopic.save(flush: true, failOnError: true)

        // Correction of the post
        def postToCorrect = Post.get(1)

        if(postToCorrect){
            def correctedMessage = new Message(text: "L'héritage avec GORM est il bogué ?")
            postToCorrect.addToHistory(postToCorrect.content)
            postToCorrect.content = correctedMessage
            postToCorrect.save(flush: true, failOnError: true)
        }
        else {
            Log.DEBUG("Impossible to fecth the post (correction routine)...")
        }

        // Add a comment on the post
        def postToComment = Post.get(1)

        if(postToComment){
            def message = new Message(text: "Your question is damn shit mothafuka !")
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
            def downVote = new Vote(type: VoteType.DOWNVOTE)
            def anOtherDownVote = new Vote(type: VoteType.DOWNVOTE)
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
            def answerText = new Message(text: "Oui il existe des bugs non corrigés dans la gestion de l'héritage faite par GORM...")
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
