import fr.isima.vectoroutofrange.Badge
import fr.isima.vectoroutofrange.BadgeType
import fr.isima.vectoroutofrange.TopicService
import fr.isima.vectoroutofrange.User
import fr.isima.vectoroutofrange.UserInformation
import jline.internal.Log

class BootStrap {

    TopicService topicService

    def init = { servletContext ->

        //Create some badges
        def welcomeBadge = new Badge(name: "Welcome badge", description: "This badge is offered to all the new users of the site.", type: BadgeType.BRONZE)
        def rockStarBadge = new Badge(name: "Rock Star", description: "You are a machine ! You answered to more than 10 000 000 questions on the site, congratulation !", type: BadgeType.PLATINIUM)

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

        def tags = []
        tags << "GORM"
        tags << "Grails"

        topicService.createNewTopic(jeanNic.id, "L'héritage avec GORM", "L'héritage avec GORM est il full bug ?", tags)
        topicService.addComment(badAss.id, 1, "Your question is damn shit mothafuka !")
        topicService.addAnswer(goodGuy.id, 1, "Oui il existe des bugs non corrigés dans la gestion de l'héritage faite par GORM...")

        Log.info("End of BootStrap ! =)")
    }
    def destroy = {
    }
}
