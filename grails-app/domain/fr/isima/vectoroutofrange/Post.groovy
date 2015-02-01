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

    static transients = ['getScore']

    /**
     * Replaces the current content of a post by a new one (to correct if for example).
     * The old content is archived in the post's history.
     * @param newContent The new message to replace the old content.
     * @return The new message.
     */
    def replaceCurrentContent(Message newContent){
        assert(newContent != null) : "The new content of a post can't be set to null."
        this.addToHistory(this.content)
        this.content = newContent
    }

    /**
     * Computes the score of a post. The score is the sum of the upvotes minus the
     * sum of the downvotes.
     * @return The score of a post.
     */
    def int getScore(){
        def score = 0

        for(vote in this.votes){
            if(vote.type == VoteType.UPVOTE)
                score += 1
            else
                score -=1
        }

        return score
    }
}
