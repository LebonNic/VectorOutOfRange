import fr.isima.vectoroutofrange.*

class BootStrap {

    TopicService topicService
    UserService userService

    def init = { servletContext ->

        // Create some badges
        def newby = new Badge(name: "Newby", description: "Congratulation, you just joined the wonderful community of vector::out_of_range!", type: BadgeType.BRONZE)

        def niceQuestion = new Badge(name: "Nice Question", description: "Question score of 10 or more", type: BadgeType.BRONZE)
        def goodQuestion = new Badge(name: "Good Question", description: "Question score of 25 or more", type: BadgeType.SILVER)
        def greatQuestion = new Badge(name: "Great Question", description: "Question score of 100 or more", type: BadgeType.GOLD)
        def ultimateQuestion = new Badge(name: "The Ultimate Question of Life the Universe and Everything", description: "Question score of 500 or more", type: BadgeType.PLATINUM)

        def popularQuestion = new Badge(name: "Popular question", description: "Asked a question with 1,000 views", type: BadgeType.BRONZE)
        def notableQuestion = new Badge(name: "Notable question", description: "Asked a question with 2,500 views", type: BadgeType.SILVER)
        def famousQuestion = new Badge(name: "Famous question", description: "Asked a question with 10,000 views", type: BadgeType.BRONZE)
        def acclaimedQuestion = new Badge(name: "Acclaimed question", description: "Asked a question with 50,000 views", type: BadgeType.PLATINUM)

        def studentQuestion = new Badge(name: "Student", description: "Asked first question with score of 1 or more", type: BadgeType.BRONZE)

        def explainer = new Badge(name: "Explainer", description: "Edited and answered 1 question", type: BadgeType.BRONZE)
        def refiner = new Badge(name: "Refiner", description: "Edited and answered 50 question", type: BadgeType.SILVER)
        def illuminator = new Badge(name: "Illuminator", description: "Edited and answered 500 question", type: BadgeType.GOLD)

        def guru = new Badge(name: "Guru", description: "Accepted answer and score of 40 or more", type: BadgeType.SILVER)

        def niceAnswer = new Badge(name: "Nice Answer", description: "Answer score of 10 or more", type: BadgeType.BRONZE)
        def goodAnswer = new Badge(name: "Good Answer", description: "Answer score of 25 or more", type: BadgeType.SILVER)
        def greatAnswer = new Badge(name: "Great Answer", description: "Answer score of 100 or more", type: BadgeType.GOLD)
        def fortyTwo = new Badge(name: "42", description: "Answer score of 500 or more", type: BadgeType.PLATINUM)

        def populist = new Badge(name: "Populist", description: "Highest scoring answer that outscored an accepted answer with score of more than 10 by more than 2x", type: BadgeType.GOLD)
        def reversal = new Badge(name: "Reversal", description: "Provided answer of +20 score to a question of -5 score", type: BadgeType.GOLD)
        def selfLearner = new Badge(name: "Self-Learner", description: "Answered your own question with score of 3 or more", type: BadgeType.BRONZE)
        def teacher = new Badge(name: "Teacher", description: "Answered first question with score of 1 or more", type: BadgeType.BRONZE)
        def autobiographer = new Badge(name: "Autobiographer", description: "Completed &laquo; About Me &raquo; section of user profile", type: BadgeType.BRONZE)
        def commentator = new Badge(name: "Commentator", description: "Left 10 comments", type: BadgeType.BRONZE)
        def pundit = new Badge(name: "Pundit", description: "Left 10 comments with score of 5 or more", type: BadgeType.SILVER)

        def critic = new Badge(name: "Critic", description: "First down vote", type: BadgeType.BRONZE)
        def supporter = new Badge(name: "Supporter", description: "First up vote", type: BadgeType.BRONZE)
        def disciplined = new Badge(name: "Disciplined", description: "Deleted own post with score of 3 or higher", type: BadgeType.BRONZE)
        def peerPressure = new Badge(name: "Peer Pressure", description: "Deleted own post with score of -3 or lower", type: BadgeType.BRONZE)

        def editor = new Badge(name: "Editor", description: "First edit", type: BadgeType.BRONZE)
        def strunkAndWhite = new Badge(name: "Strunk & White", description: "Edited 80 posts", type: BadgeType.SILVER)
        def copyEditor = new Badge(name: "Copy Editor", description: "Edited 500 posts", type: BadgeType.GOLD)

        // Use a service to create new users
        def jeanNic = userService.createUser("jeannic", "pwd", "Jean", "Nic", "JeanNic")
        jeanNic.userInformation.addToBadges(newby)
        jeanNic.save(flush: true, failOnError: true)
        userService.updateUser(1, "Jean", "Nic", "JeanNic", "www.jeannic.com", "France", "This is JeanNic, can you beat this ?")

        def badAss = userService.createUser("badass", "pwd", "Bad", "Ass", "BadAss")
        badAss.userInformation.addToBadges(newby)
        badAss.save(flush: true, failOnError: true)

        def goodGuy = userService.createUser("goodguy", "pwd", "Good", "Guy", "GoodGuy")
        goodGuy.userInformation.addToBadges(newby)
        goodGuy.userInformation.addToBadges(niceQuestion)
        goodGuy.userInformation.addToBadges(niceQuestion)
        goodGuy.userInformation.addToBadges(niceQuestion)
        goodGuy.userInformation.addToBadges(niceQuestion)
        goodGuy.userInformation.addToBadges(goodQuestion)
        goodGuy.userInformation.addToBadges(goodQuestion)
        goodGuy.userInformation.addToBadges(goodQuestion)
        goodGuy.userInformation.addToBadges(greatQuestion)
        goodGuy.userInformation.addToBadges(greatQuestion)
        goodGuy.userInformation.addToBadges(ultimateQuestion)
        goodGuy.userInformation.addToBadges(popularQuestion)
        goodGuy.userInformation.addToBadges(popularQuestion)
        goodGuy.userInformation.addToBadges(popularQuestion)
        goodGuy.userInformation.addToBadges(popularQuestion)
        goodGuy.userInformation.addToBadges(popularQuestion)
        goodGuy.userInformation.addToBadges(notableQuestion)
        goodGuy.userInformation.addToBadges(notableQuestion)
        goodGuy.userInformation.addToBadges(famousQuestion)
        goodGuy.userInformation.addToBadges(famousQuestion)
        goodGuy.userInformation.addToBadges(acclaimedQuestion)

        goodGuy.userInformation.addToBadges(studentQuestion)
        goodGuy.userInformation.addToBadges(explainer)
        goodGuy.userInformation.addToBadges(refiner)
        goodGuy.userInformation.addToBadges(illuminator)
        goodGuy.userInformation.addToBadges(guru)
        goodGuy.userInformation.addToBadges(niceAnswer)
        goodGuy.userInformation.addToBadges(goodAnswer)
        goodGuy.userInformation.addToBadges(greatAnswer)
        goodGuy.userInformation.addToBadges(fortyTwo)

        goodGuy.userInformation.addToBadges(populist)
        goodGuy.userInformation.addToBadges(reversal)
        goodGuy.userInformation.addToBadges(selfLearner)
        goodGuy.userInformation.addToBadges(teacher)
        goodGuy.userInformation.addToBadges(autobiographer)
        goodGuy.userInformation.addToBadges(commentator)
        goodGuy.userInformation.addToBadges(pundit)

        goodGuy.userInformation.addToBadges(critic)
        goodGuy.userInformation.addToBadges(supporter)
        goodGuy.userInformation.addToBadges(disciplined)
        goodGuy.userInformation.addToBadges(peerPressure)

        goodGuy.userInformation.addToBadges(editor)
        goodGuy.userInformation.addToBadges(strunkAndWhite)
        goodGuy.userInformation.addToBadges(copyEditor)

        goodGuy.save(flush: true, failOnError: true)

        // User a service to manage topics
        def tags = []
        tags << "GORM"
        tags << "Grails"

        topicService.createNewTopic(jeanNic.id, "L'héritage avec GORM", "L'héritage avec GORM est il full bug ?", tags)
        topicService.correctPost(1, jeanNic.id, "L'héritage avec GORM est il bogué ?")
        topicService.addComment(1, badAss.id, "Your question is damn shit mothafuka !")
        topicService.correctPost(2, badAss.id, "Your question is damn shit mothafuka bitch !")
        topicService.voteForPost(2, jeanNic.id, VoteType.DOWNVOTE)
        topicService.voteForPost(2, goodGuy.id, VoteType.DOWNVOTE)
        topicService.addAnswer(1, goodGuy.id, "Oui il existe des bugs non corrigés dans la gestion de l'héritage faite par GORM...")
        topicService.addComment(3, badAss.id, "Your answer is damn shit mothafuka !")
        topicService.addComment(3, badAss.id, "You noob !")

        topicService.tagPostAsBestAnswer(1,3)

        log.info("End of BootStrap ! =)")
    }

    def destroy = {
    }
}
