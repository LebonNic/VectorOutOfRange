class BootStrap {

    def userService
    def badgeService
    def topicService

    def init = { servletContext ->

        userService.init()
        topicService.addObserver(userService)
        topicService.addObserver(badgeService)
        userService.addObserver(badgeService)

        // Populate the site
        def jeanNic = userService.createUser("jeannic", "pwd", "Jean", "Nic", "JeanNic")
        userService.updateUser(1, "Jean", "Nic", "JeanNic", "http://www.jeannic.com", "France", "This is JeanNic, can you beat this ?")

        def badAss = userService.createUser("badass", "pwd", "Bad", "Ass", "BadAss")
        def goodGuy = userService.createUser("goodguy", "pwd", "Good", "Guy", "GoodGuy")
        def admin = userService.createUser('admin', 'Süp3rÜb3rC0mp73xP4s5w0Rd', 'Ad', 'Min', 'Your God')
        userService.grantUserAdminRole(admin.id)

        def tags = []
        tags << "GORM"
        tags << "Grails"

        topicService.createNewTopic(jeanNic.id, "L'héritage avec GORM", "L'héritage avec GORM est il full bug ?", tags)
        topicService.correctPost(1, jeanNic.id, "L'héritage avec GORM est il bogué ?\n\n    function foo() {\n        doFoo();\n    }")
        topicService.addComment(1, badAss.id, "Your question is damn shit mothafuka !")
        topicService.correctPost(2, badAss.id, "Your question is damn shit mothafuka bitch !")
        topicService.addAnswer(1, goodGuy.id, "Oui il existe des bugs non corrigés dans la gestion de l'héritage faite par GORM...")
        topicService.addComment(3, badAss.id, "Your answer is damn shit mothafuka !")
        topicService.addComment(3, badAss.id, "You noob !")

        log.info("End of BootStrap ! =)")
    }

    def destroy = {
    }
}
