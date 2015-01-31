import fr.isima.vectoroutofrange.Badge
import fr.isima.vectoroutofrange.BadgeType
import fr.isima.vectoroutofrange.TopicService
import fr.isima.vectoroutofrange.UserService
import fr.isima.vectoroutofrange.VoteType
import jline.internal.Log

class BootStrap {

    TopicService topicService
    UserService userService

    def init = { servletContext ->

        // Create some badges
        def welcomeBadge = new Badge(name: "Welcome badge", description: "Congratulation you just joined the wonderful community of VectorOutOfRange !", type: BadgeType.BRONZE)
        def rockStarBadge = new Badge(name: "Rock Star", description: "You are a machine ! You answered to more than 10 000 000 questions on the site, congratulation !", type: BadgeType.PLATINIUM)

        // Use a service to create new users
        def jeanNic = userService.createUser("jeannic", "pwd", "Jean", "Nic", "JeanNic")
        jeanNic.userInformation.addToBadges(welcomeBadge)
        jeanNic.save(flush: true, failOnError: true)

        def badAss = userService.createUser("badass", "pwd", "Bad", "Ass", "BadAss")
        badAss.userInformation.addToBadges(welcomeBadge)
        badAss.save(flush: true, failOnError: true)

        def goodGuy = userService.createUser("goodguy", "pwd", "Good", "Guy", "GoodGuy")
        goodGuy.userInformation.addToBadges(welcomeBadge)
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

        Log.info("End of BootStrap ! =)")
    }

    def destroy = {
    }
}
