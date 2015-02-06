package fr.isima.vectoroutofrange

class UserInformation {

    String firstName
    String lastName
    String nickname
    int reputation
    int editedPosts

    String website
    String location
    String about

    List<Badge> badges

    static belongsTo = [user: User]

    static hasMany = [messages: Message, votes: Vote, badges: Badge]

    static constraints = {
        nickname unique: true
        firstName nullable: true
        lastName nullable: true
        website nullable: true, url: true
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

    def getBadges(long id) {
        return badges.findAll({
            it.id == id
        })
    }
}
