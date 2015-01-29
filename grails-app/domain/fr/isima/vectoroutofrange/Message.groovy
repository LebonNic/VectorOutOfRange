package fr.isima.vectoroutofrange

class Message {

    //Date date
    //User author
    String text

    static belongsTo = [post: Post]

    static constraints = {
    }
}
