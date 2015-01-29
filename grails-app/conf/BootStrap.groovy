import fr.isima.vectoroutofrange.Badge
import fr.isima.vectoroutofrange.Message
import fr.isima.vectoroutofrange.Post
import fr.isima.vectoroutofrange.PostType
import fr.isima.vectoroutofrange.Tag
import fr.isima.vectoroutofrange.Topic
import fr.isima.vectoroutofrange.User
import fr.isima.vectoroutofrange.UserInformation
import fr.isima.vectoroutofrange.Vote
import fr.isima.vectoroutofrange.VoteType
import jline.internal.Log

class BootStrap {

    def init = { servletContext ->

        //Create some badges
        def welcomeBadge = new Badge(name: "Welcome badge", description: "This badge is offered to all the new users of the site.")
        def rockStarBadge = new Badge(name: "Rock Star", description: "You are a machine ! You answered to more than 10 000 000 questions on the site, congratulation !")

        // Creation of a new Users
        def jeanNicInformaton = new UserInformation(firstName: "Jean", lastName: "Nic", nickname: "JeanNic", reputation: 0)
        def jeanNic = new User(username: "jeannic", password: "pwd", userInformation: jeanNicInformaton)
        jeanNic.userInformation.addToBadges(welcomeBadge)
        jeanNic.save(flush: true, failOnError: true)

        def badAssInformaton = new UserInformation(firstName: "Bad", lastName: "Ass", nickname: "BadAss", reputation: -10)
        def badAss = new User(username: "badass", password: "pwd", userInformation: badAssInformaton)
        badAss.userInformation.addToBadges(welcomeBadge)
        badAss.save(flush: true, failOnError: true)

        def goodGuyInformation = new UserInformation(firstName: "Good", lastName: "Guy", nickname: "GoodGuy", reputation: 150236)
        def goodGuy = new User(username: "goodguy", password: "pwd", userInformation: goodGuyInformation)
        goodGuy.userInformation.addToBadges(welcomeBadge)
        goodGuy.userInformation.addToBadges(rockStarBadge)
        goodGuy.save(flush: true, failOnError: true)

        // Creation of new tags
        def grailsTag = new Tag(name: "Grails", definition: "Grails is an Open Source, full stack, web application framework for the JVM.").save(flush: true, failOnError: true)
        def gormTag = new Tag(name: "GORM", definition: "The fantastic ORM library for Golang, aims to be developer friendly.")

        // Creation of a new topic
        def newMessage = new Message(text: "L'héritage avec GORM est il full bug ?", date: new Date(), author: jeanNic.userInformation)
        jeanNic.userInformation.addToMessages(newMessage)
        def newPost = new Post(content: newMessage, type: PostType.QUESTION)
        def newTopic = new Topic(title: "L'héritage avec GORM", question: newPost)
        newTopic.addToTags(grailsTag)
        newTopic.addToTags(gormTag)
        newTopic.save(flush: true, failOnError: true)

        // Correction of the post
        def postToCorrect = Post.get(1)

        if(postToCorrect){
            def correctedMessage = new Message(text: "L'héritage avec GORM est il bogué ?", date: new Date(), author: jeanNic.userInformation)
            jeanNic.userInformation.addToMessages(correctedMessage)
            postToCorrect.replaceCurrentContent(correctedMessage)
            postToCorrect.save(flush: true, failOnError: true)
        }
        else {
            Log.DEBUG("Impossible to fecth the post (correction routine)...")
        }

        // Add a comment on the post
        def postToComment = Post.get(1)

        if(postToComment){
            def message = new Message(text: "Your question is damn shit mothafuka !", date: new Date(), author: badAss.userInformation)
            badAss.userInformation.addToMessages(message)
            def post = new Post(topic: postToComment.topic, content: message, type: PostType.COMMENT)
            postToComment.addToComments(post)
            postToComment.save(flush: true, failOnError: true)
        }
        else {
            Log.DEBUG("Impossible to fecth the post (comment routine)...")
        }

        // Downvote the bad comment
        def badComment = postToComment.comments.first()

        if(badComment){
            def downVote = new Vote(type: VoteType.DOWNVOTE, date: new Date(), author: jeanNic.userInformation)
            def anOtherDownVote = new Vote(type: VoteType.DOWNVOTE, date: new Date(), author: goodGuy.userInformation)

            jeanNic.userInformation.addToVotes(downVote)
            goodGuy.userInformation.addToVotes(anOtherDownVote)

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
            def answerText = new Message(text: "Oui il existe des bugs non corrigés dans la gestion de l'héritage faite par GORM...", date: new Date(), author: goodGuy.userInformation)
            goodGuy.userInformation.addToMessages(answerText)
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
