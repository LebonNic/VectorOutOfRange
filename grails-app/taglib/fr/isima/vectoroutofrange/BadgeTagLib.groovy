package fr.isima.vectoroutofrange

class BadgeTagLib {

    static namespace = 'voor'

    /**
     * Display available badges.
     * @param user If specified, display user's badge.
     */
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

    /**
     * Display a badge.
     * @param badge REQUIRED Badge to display.
     * @param user If specified, display the amount of badge of this kind received by the user.
     */
    def badge = { attrs ->
        out << render(template: '/badge/badge', model: [
                user : (User) attrs['user'],
                badge: (Badge) attrs['badge']
        ])
    }
}
