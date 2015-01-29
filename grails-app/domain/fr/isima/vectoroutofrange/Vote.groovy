package fr.isima.vectoroutofrange

enum VoteType{
    UPVOTE,
    DOWNVOTE
}

class Vote {

    Date date
    //User author
    VoteType type

    static belongsTo = [post:Post]

    static constraints = {
    }
}
