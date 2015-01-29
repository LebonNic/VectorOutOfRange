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
        topic nullable: true
    }

    def replaceCurrentContent(Message newContent){
        assert(newContent != null) : "The new content of a post can't be set to null."
        this.addToHistory(this.content)
        this.content = newContent
    }
}
