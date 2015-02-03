import fr.isima.vectoroutofrange.*

class BootStrap {

    TopicService topicService
    UserService userService

    def init = { servletContext ->

        // Create some badges
        def welcomeBadge = new Badge(name: "Welcome badge", description: "Congratulation you just joined the wonderful community of VectorOutOfRange !", type: BadgeType.BRONZE)
        def coolBadge = new Badge(name: "Cool Badge", description: "Congratulation, you've commented 10 times.", type: BadgeType.SILVER)
        def awesomeBadge = new Badge(name: "Welcome badge", description: "Congratulation, one of your answer has been upvoted 100 times", type: BadgeType.GOLD)
        def rockStarBadge = new Badge(name: "Rock Star", description: "You are a machine ! You answered to more than 10 000 000 questions on the site, congratulation !", type: BadgeType.PLATINUM)

        // Use a service to create new users
        def jeanNic = userService.createUser("jeannic", "pwd", "Jean", "Nic", "JeanNic")
        jeanNic.userInformation.addToBadges(welcomeBadge)
        jeanNic.save(flush: true, failOnError: true)

        def badAss = userService.createUser("badass", "pwd", "Bad", "Ass", "BadAss")
        badAss.userInformation.addToBadges(welcomeBadge)
        badAss.save(flush: true, failOnError: true)

        def goodGuy = userService.createUser("goodguy", "pwd", "Good", "Guy", "GoodGuy")
        goodGuy.userInformation.addToBadges(welcomeBadge)
        goodGuy.userInformation.addToBadges(coolBadge)
        goodGuy.userInformation.addToBadges(awesomeBadge)
        goodGuy.userInformation.addToBadges(rockStarBadge)
        goodGuy.save(flush: true, failOnError: true)

        // User a service to manage topics
        def tags = []
        tags << "GORM"
        tags << "Grails"

        topicService.createNewTopic(jeanNic.id, "L'héritage avec GORM", "L'héritage avec GORM est il full bug ?", tags)
        topicService.correctPost(1, jeanNic.id, "L'héritage avec GORM est il bogué ?")
        topicService.addComment(1, badAss.id, "Your question is damn shit mothafuka !")
        topicService.voteForPost(2, jeanNic.id, VoteType.DOWNVOTE)
        topicService.voteForPost(2, goodGuy.id, VoteType.DOWNVOTE)
        topicService.addAnswer(1, goodGuy.id, "Oui il existe des bugs non corrigés dans la gestion de l'héritage faite par GORM...")

        log.info("End of BootStrap ! =)")
    }

    def destroy = {
    }
}
