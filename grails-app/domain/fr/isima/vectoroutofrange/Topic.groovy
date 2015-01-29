package fr.isima.vectoroutofrange

class Topic {

    String title
    Post question

    static hasMany = [answers:Post]

    static mappedBy = [question: "topic", answers: "none"]

    static constraints = {
        answers nullable: true
    }
}
