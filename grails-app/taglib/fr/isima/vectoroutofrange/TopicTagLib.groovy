package fr.isima.vectoroutofrange

class TopicTagLib {

    static namespace = 'voor'

    def topicService
    def springSecurityService

    /**
     * Display a topic.
     * @param topic REQUIRED Topic to display.
     * @param type If equals full, display topic with question, answers, etc. Otherwise, display a topic summary.
     */
    def topic = { attrs ->
        if (attrs['type'] == 'full') {
            out << render(template: '/topic/topicFull', model: [
                    topic: (Topic) attrs['topic']
            ])
        } else {
            out << render(template: '/topic/topic', model: [
                    topic: (Topic) attrs['topic']
            ])
        }
    }

    /**
     * Display post's comments and comment editor.
     * @param post REQUIRED Post from which comments are retrieved.
     */
    def comments = { attrs ->
        Post post = (Post) attrs['post']
        def comments = post.comments

        comments.sort { a, b -> a.content.date <=> b.content.date }

        out << render(template: '/topic/comments', model: [
                post    : post,
                comments: comments
        ])
    }

    /**
     * Display a comment.
     * @param comment REQUIRED Comment to display.
     */
    def comment = { attrs ->
        out << render(template: '/topic/comment', model: [
                comment: (Post) attrs['comment']
        ])
    }

    /**
     * Display answers to a topic.
     * @param topic REQUIRED Topic from which answers are retrieved.
     */
    def answers = { attrs ->
        Topic topic = (Topic) attrs['topic']
        def answers = topic.answers

        answers.sort { -it.score }
        out << render(template: '/topic/answers', model: [
                answers: answers
        ])
    }

    /**
     * Display an answer.
     * @param answer REQUIRED Answer to display.
     */
    def answer = { attrs ->
        out << render(template: '/topic/answer', model: [
                answer: (Post) attrs['answer']
        ])
    }

    /**
     * Display a bounty ribbon.
     * @param value REQUIRED Bounty value.
     */
    def bounty = { attrs ->
        out << render(template: '/topic/bounty', model: [
                value: Integer.parseInt((String) attrs['value'])
        ])
    }

    /**
     * Display a vote count.
     * @param value REQUIRED Vote count.
     */
    def voteCount = { attrs ->
        out << render(template: '/topic/voteCount', model: [
                value: attrs['value']
        ])
    }

    /**
     * Display an answer count.
     * @param value REQUIRED Answer count.
     */
    def answerCount = { attrs ->
        out << render(template: '/topic/answerCount', model: [
                value: attrs['value']
        ])
    }

    /**
     * Display a view count.
     * @param value REQUIRED View count.
     */
    def viewCount = { attrs ->
        out << render(template: '/topic/viewCount', model: [
                value: attrs['value']
        ])
    }

    /**
     * Display the up/down voter associated to given post. If a user is logged in, display his former vote.
     * @param post REQUIRED Post where up/down voter is added.
     */
    def voter = { attrs ->
        Post post = (Post) attrs['post']
        User user = (User) springSecurityService.currentUser
        Vote vote = null
        if (user) {
            vote = topicService.getUserVoteOnPost(post.id, user.id)
        }

        out << render(template: '/topic/voter', model: [
                post: post,
                vote: vote
        ])
    }

    /**
     * Display an edit button to edit given post.
     * @param post REQUIRED Post to edit.
     */
    def editPostButton = { attrs ->
        out << render(template: '/topic/editPostButton', model: [
                post: (Post) attrs['post']
        ])
    }

    /**
     * Display a delete button to delete given post.
     * @param post REQUIRED Post to delete.
     */
    def deletePostButton = { attrs ->
        out << render(template: '/topic/deletePostButton', model: [
                post: (Post) attrs['post']
        ])
    }

    /**
     * Display a button to choose given answer as best answer.
     * If best answer is already chosen, display nothing on not chosen answers and a chosen mark on chosen answer.
     */
    def chooseBestAnswer = { attrs ->
        out << render(template: '/topic/chooseBestAnswer', model: [
                post: (Post) attrs['post']
        ])
    }
}
