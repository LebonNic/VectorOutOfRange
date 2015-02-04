package fr.isima.vectoroutofrange

class UserInformation {

    String firstName
    String lastName
    String nickname
    int reputation

    String website
    String location
    String about

    static belongsTo = [user: User]

    static hasMany = [messages: Message, votes: Vote, badges: Badge]

    static constraints = {
        firstName nullable: true
        lastName nullable: true
        website nullable: true
        location nullable: true
        about nullable: true
        messages nullable: true
        votes nullable: true
        reputation min: 1
    }

    static mapping = {
        messages sort: 'date', order: 'desc'
    }

    def getBadges(BadgeType type) {
        return badges.findAll({
            it.type == type
        })
    }
}
