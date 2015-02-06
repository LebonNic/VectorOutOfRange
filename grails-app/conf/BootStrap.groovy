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
        topicService.correctPost(1, jeanNic.id, "L'héritage avec GORM est il bogué ?\n\n    function foo() {\n        doFoo();\n    }")
        topicService.addComment(1, badAss.id, "Your question is damn shit mothafuka !")
        topicService.correctPost(2, badAss.id, "Your question is damn shit mothafuka bitch !")
        topicService.voteForPost(2, jeanNic.id, VoteType.DOWNVOTE)
        topicService.voteForPost(2, goodGuy.id, VoteType.DOWNVOTE)
        topicService.addAnswer(1, goodGuy.id, "Oui il existe des bugs non corrigés dans la gestion de l'héritage faite par GORM...")
        topicService.addComment(3, badAss.id, "Your answer is damn shit mothafuka !")
        topicService.addComment(3, badAss.id, "You noob !")

        topicService.tagPostAsBestAnswer(1, 3)

        def createPostPermission = new Role(authority: 'ROLE_CREATE_POST', name: 'Create posts', description: 'Ask a question or contribute an answer', requiredReputation: 1)
        def voteUpPermission = new Role(authority: 'ROLE_VOTE_UP', name: 'Vote up', description: 'Indicate when questions and answers are useful', requiredReputation: 15)
        def createCommentPermission = new Role(authority: 'ROLE_CREATE_COMMENT', name: 'Create comments', description: 'Add comments on questions and answers', requiredReputation: 15)
        def voteDownPermission = new Role(authority: 'ROLE_VOTE_DOWN', name: 'Vote down', description: 'Indicate when questions and answers are not useful', requiredReputation: 125)
        def moderateTagPermission = new Role(authority: 'ROLE_MODERATE_TAG', name: 'Moderate tag', description: 'Edit and delete tags', requiredReputation: 1500)
        def moderatePostPermission = new Role(authority: 'ROLE_MODERATE_POST', name: 'Moderate posts', description: 'Edit and delete posts', requiredReputation: 10000)
        def moderateUserPermission = new Role(authority: 'ROLE_MODERATE_USER', name: 'Moderate users', description: 'Edit and delete users', requiredReputation: 20000)

        createPostPermission.save(flush: true, failOnError: true)
        voteUpPermission.save(flush: true, failOnError: true)
        createCommentPermission.save(flush: true, failOnError: true)
        voteDownPermission.save(flush: true, failOnError: true)
        moderateTagPermission.save(flush: true, failOnError: true)
        moderatePostPermission.save(flush: true, failOnError: true)
        moderateUserPermission.save(flush: true, failOnError: true)

        def admin = userService.createUser('admin', 'Süp3rÜb3rC0mp73xP4s5w0Rd', 'Ad', 'Min', 'Your God')
        admin.save(flush: true, failOnError: true)

        UserRole.create(admin, createPostPermission, true)
        UserRole.create(admin, voteUpPermission, true)
        UserRole.create(admin, createCommentPermission, true)
        UserRole.create(admin, voteDownPermission, true)
        UserRole.create(admin, moderateTagPermission, true)
        UserRole.create(admin, moderatePostPermission, true)
        UserRole.create(admin, moderateUserPermission, true)

        UserRole.create(jeanNic, createPostPermission, true)

        log.info("End of BootStrap ! =)")
    }

    def destroy = {
    }
}
