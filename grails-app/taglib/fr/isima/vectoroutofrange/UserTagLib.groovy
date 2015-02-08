package fr.isima.vectoroutofrange

class UserTagLib {

    static namespace = 'voor'

    /**
     * Display a user badge describing user and possible action. If post is given, user displayed is post creator.
     * Otherwise, given user is displayed.
     * @param user User to display.
     * @param type If equals full, display user's medal, reputation and avatar.
     * @param post If specified add detail about user action on post.
     */
    def userBadge = { attrs ->
        String type = (String) attrs['type']
        Post post = (Post) attrs['post']
        User user = (User) attrs['user']

        if (post) {
            user = post.creator
        }
        if (type == 'full') {
            out << render(template: '/user/userBadgeFull', model: [
                    post: post,
                    user: user
            ])
        } else {
            out << render(template: '/user/userBadge', model: [
                    post: post,
                    user: user
            ])
        }
    }

    /**
     * Display a user profile.
     * @param user REQUIRED A user.
     */
    def userProfile = { attrs ->
        out << render(template: '/user/userProfile', model: [
                user: (User) attrs['user']
        ])
    }

    /**
     * Display a button to edit given user.
     * @param user REQUIRED User to edit.
     */
    def editUserButton = { attrs ->
        out << render(template: '/user/editUserButton', model: [
                user: (User) attrs['user']
        ])
    }

    /**
     * Display a button to delete given user.
     * @param user REQUIRED User to delete.
     */
    def deleteUserButton = { attrs ->
        out << render(template: '/user/deleteUserButton', model: [
                user: (User) attrs['user']
        ])
    }

    /**
     * Display user's medal count of given type.
     * @param user REQUIRED A user.
     * @param type REQUIRED Medal type (bronze, silver, gold, platinum)
     */
    def medals = { attrs ->
        String type = attrs['type']
        User user = (User) attrs['user']
        int count = 0;

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

    /**
     * Display a user timeline.
     * @param user REQUIRED A user.
     */
    def timeline = { attrs ->
        out << render(template: '/user/timeline', model: [
                user: (User) attrs['user']
        ])
    }

    /**
     * Display an event associated to a message on the timeline.
     * @param message REQUIRED A message.
     */
    def timelineEvent = { attrs ->
        out << render(template: '/user/timelineEvent', model: [
                message: (Message) attrs['message']
        ])
    }

    /**
     * Display a timeline showing user's votes.
     * @param user REQUIRED a user.
     */
    def voteTimeline = { attrs ->
        out << render(template: '/user/voteTimeline', model: [
                user: (User) attrs['user']
        ])
    }

    /**
     * Display a vote event on the timeline.
     * @param vote REQUIRED A vote.
     */
    def voteTimelineEvent = { attrs ->
        out << render(template: '/user/voteTimelineEvent', model: [
                vote: (Vote) attrs['vote']
        ])
    }
}
