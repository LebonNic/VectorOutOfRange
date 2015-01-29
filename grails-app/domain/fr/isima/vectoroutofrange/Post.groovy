package fr.isima.vectoroutofrange

enum PostType{
    QUESTION,
    ANSWER,
    COMMENT
}

class Post {

    Message content
    PostType type

    static hasMany = [history: Message, comments: Post, votes:Vote]

    static mappedBy = [content: "post", history: "none"]

    static belongsTo = [topic: Topic]

    static constraints = {
        history nullable: true
        votes nullable: true
    }
}
