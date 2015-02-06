import fr.isima.vectoroutofrange.*

class BootStrap {

    TopicService topicService
    UserService userService
    BadgeService badgeService

    def init = { servletContext ->

        topicService.addObserver(userService)
        topicService.addObserver(badgeService)
        userService.addObserver(badgeService)

        // Use a service to create new users
        def jeanNic = userService.createUser("jeannic", "pwd", "Jean", "Nic", "JeanNic")
        userService.updateUser(1, "Jean", "Nic", "JeanNic", "http://www.jeannic.com", "France", "This is JeanNic, can you beat this ?")

        def badAss = userService.createUser("badass", "pwd", "Bad", "Ass", "BadAss")

        def goodGuy = userService.createUser("goodguy", "pwd", "Good", "Guy", "GoodGuy")

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
