package fr.isima.vectoroutofrange

class UserTagLib {
    static namespace = 'voor'

    def userBadge = { attrs ->
        def type = attrs['type']
        Post post = attrs['post']
        User user = attrs['user']

        if (post) {
            if (post.history.empty) {
                user = post.content.author.user
            } else {
                user = post.history[0].author.user
            }
        }
        if (type == 'full') {
            out << render(template: '/user/userBadgeFull', model: ['post': post, 'user': user])
        } else {
            out << render(template: '/user/userBadge', model: ['post': post, 'user': user])
        }
    }

    def userProfile = { attrs ->
        out << render(template: '/user/userProfile', model: [user: attrs['user']])
    }

    def editUserButton = { attrs ->
        out << render(template: '/user/editUserButton', model: ['user': attrs['user']])
    }

    def deleteUserButton = { attrs ->
        out << render(template: '/user/deleteUserButton', model: ['user': attrs['user']])
    }

    def medals = { attrs ->
        def type = attrs['type']
        User user = (User) attrs['user']
        def count = 0;
        switch (type) {
            case 'bronze':
                count = user.userInformation.getBadges(BadgeType.BRONZE).size()
                break;
            case 'silver':
                count = user.userInformation.getBadges(BadgeType.SILVER).size()
                break;
            case 'gold':
                count = user.userInformation.getBadges(BadgeType.GOLD).size()
                break;
            case 'platinum':
                count = user.userInformation.getBadges(BadgeType.PLATINUM).size()
                break;
        }

        out << render(template: '/user/medal', model: [type: type, count: count])
    }

    def timeline = { attrs ->
        out << render(template: '/user/timeline', model: ['user': attrs['user']])
    }

    def timelineEvent = { attrs ->
        out << render(template: '/user/timelineEvent', model: ['message': attrs['message']])
    }
}
