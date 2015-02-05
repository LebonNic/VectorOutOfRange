package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.SpringSecurityService

class TopicTagLib {
    static namespace = 'voor'

    TopicService topicService
    SpringSecurityService springSecurityService

    def topic = { attrs ->
        if (attrs['type'] == 'full') {
            out << render(template: '/topic/topicFull', model: ['topic': attrs['topic']])
        } else {
            out << render(template: '/topic/topic', model: ['topic': attrs['topic']])
        }
    }

    def comments = { attrs ->
        out << render(template: '/topic/comments', model: ['post': attrs['post']])
    }

    def comment = { attrs ->
        out << render(template: '/topic/comment', model: ['comment': attrs['comment']])
    }

    def answers = { attrs ->
        out << render(template: '/topic/answers', model: ['topic': attrs['topic']])
    }

    def answer = { attrs ->
        out << render(template: '/topic/answer', model: ['answer': attrs['answer']])
    }

    def bounty = { attrs ->
        out << render(template: '/topic/bounty', model: ['value': attrs['value']])
    }

    def voteCount = { attrs ->
        out << render(template: '/topic/voteCount', model: ['value': attrs['value']])
    }

    def answerCount = { attrs ->
        out << render(template: '/topic/answerCount', model: ['value': attrs['value']])
    }

    def viewCount = { attrs ->
        out << render(template: '/topic/viewCount', model: ['value': attrs['value']])
    }

    def voter = { attrs ->
        Post post = attrs['post']
        User user = (User) springSecurityService.currentUser
        Vote vote = null
        if (user) {
            vote = topicService.getUserVoteOnPost(post.id, user.id)
        }

        out << render(template: '/topic/voter', model: ['post': attrs['post'], 'vote': vote])
    }

    def editPostButton = { attrs ->
        out << render(template: '/topic/editPostButton', model: ['post': attrs['post']])
    }

    def deletePostButton = { attrs ->
        out << render(template: '/topic/deletePostButton', model: ['post': attrs['post']])
    }

    def chooseBestAnswer = { attrs ->
        out << render(template: '/topic/chooseBestAnswer', model: ['post': attrs['post']])
    }
}
