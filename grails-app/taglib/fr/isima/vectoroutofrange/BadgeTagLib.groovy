package fr.isima.vectoroutofrange

class BadgeTagLib {

    static namespace = 'voor'

    def badges = { attrs ->
        User user = (User) attrs['user']
        out << render(template: '/badge/badges', model: [
                user          : user,
                bronzeBadges  : Badge.findAll({ type == BadgeType.BRONZE }),
                silverBadges  : Badge.findAll({ type == BadgeType.SILVER }),
                goldBadges    : Badge.findAll({ type == BadgeType.GOLD }),
                platinumBadges: Badge.findAll({ type == BadgeType.PLATINUM })
        ])
    }

    def badge = { attrs ->
        out << render(template: '/badge/badge', model: [
                user : (User) attrs['user'],
                badge: (Badge) attrs['badge'],
                type : (String) attrs['type']
        ])
    }
}
