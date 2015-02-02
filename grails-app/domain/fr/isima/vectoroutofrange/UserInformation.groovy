package fr.isima.vectoroutofrange

class UserInformation {

    String firstName
    String lastName
    String nickname
    int reputation

    static belongsTo = [user:User]

    static hasMany = [messages:Message, votes:Vote, badges:Badge]

    static constraints = {
        messages nullable: true
        votes nullable: true
        reputation min: 1
    }
}
