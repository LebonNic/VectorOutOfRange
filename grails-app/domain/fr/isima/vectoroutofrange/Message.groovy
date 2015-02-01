package fr.isima.vectoroutofrange

class Message {

    Date date
    UserInformation author
    String text

    static belongsTo = [post: Post]

    static constraints = {
        post nullable: true
    }
}
