package fr.isima.vectoroutofrange

enum PostType{
    QUESTION,
    ANSWER,
    COMMENT
}

class Post {

    Message content
    PostType type
    Post parentPost

    static hasMany = [history: Message, comments: Post, votes:Vote]

    static mappedBy = [content: "none", history: "none", comments: "parentPost"]

    static mapping = {
        content lazy: false
    }

    static belongsTo = [topic: Topic]

    static constraints = {
        history nullable: true
        votes nullable: true
        topic nullable: true
        parentPost nullable: true
        content nullable: true
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

    /**
     * Get post creator.
     * @return User who created this post.
     */
    def getCreator() {
        if (history.empty) {
            return content.author.user
        } else {
            return history.getAt(0).author.user
        }
    }
}
