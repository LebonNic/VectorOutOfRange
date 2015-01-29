package fr.isima.vectoroutofrange

class Topic {

    String title
    Post question

    static hasMany = [answers:Post, tags:Tag]

    static mappedBy = [question: "none", answers: "none"]

    static constraints = {
        answers nullable: true
        tags nullable: true
    }
}
