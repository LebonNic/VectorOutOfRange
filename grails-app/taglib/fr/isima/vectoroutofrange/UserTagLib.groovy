package fr.isima.vectoroutofrange

class UserTagLib {
    static namespace = 'voor'

    def userBadge = { attrs ->
        def type = attrs['type']
        Post post = attrs['post']

        if (type == 'full') {
            out << render(template: '/user/userBadgeFull', model: ['post': post])
        } else {
            out << render(template: '/user/userBadge', model: ['post': post])
        }
    }
}
