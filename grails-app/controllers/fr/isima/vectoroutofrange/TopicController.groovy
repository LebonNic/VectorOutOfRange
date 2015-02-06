package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured

class TopicController {

    def topicService
    def springSecurityService

    static allowedMethods = [index       : 'GET',
                             view        : 'GET',
                             edit        : 'GET',
                             create      : 'GET',
                             save        : 'POST',
                             upvote      : 'POST',
                             downvote    : 'POST',
                             answer      : 'POST',
                             comment     : 'POST',
                             chooseAnswer: 'POST']

    /**
     * Display list of topics.
     * @return List of topics.
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [topics: Topic.list(params), topicCount: Topic.count()])
    }

    /**
     * Display given topic view.
     * @return Topic view or 404 if topic is unknown.
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        try {
            long id = Long.parseLong((String) params.id)
            topicService.incrementViewsCount(id)
            render(view: 'view', model: [topic: topicService.getTopic(id)])
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    /**
     * Display post editing view.
     * @return Post editing view.
     */
    @Secured(['ROLE_CREATE_POST'])
    def edit() {
        render(view: 'edit', model: [post: Post.get(Long.parseLong((String) params.id))])
    }

    /**
     * Display post creation view.
     * @return Post creation view.
     */
    @Secured(['ROLE_CREATE_POST'])
    def create() {
        render(view: 'create')
    }

    /**
     * Correct a post or create a new topic with given parameters.
     * @return Topic id or 404 if post is unknown.
     */
    @Secured(['ROLE_CREATE_POST'])
    def save() {
        String text = params.text
        User user = (User) springSecurityService.currentUser

        try {
            if (params.id) {
                Post post = (Post) topicService.correctPost(Long.parseLong((String) params.id), user.id, (String) params.text)
                render(status: 200, text: post.topic.id)
            } else {
                String title = params.title
                List<String> tags = params.list('tags[]')
                Topic topic = topicService.createNewTopic(user.id, title, text, tags)
                render(status: 201, text: topic.id)
            }
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    /**
     * Upvote given post.
     * @return Post score or 404 if post is unknown.
     */
    @Secured(['ROLE_VOTE_UP'])
    def upvote() {
        User user = (User) springSecurityService.currentUser

        try {
            long id = Long.parseLong((String) params.id)
            topicService.voteForPost(id, user.id, VoteType.UPVOTE)
            render(status: 201, text: topicService.getScoreForPost(id))
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    /**
     * Downvote given post.
     * @return Post score or 404 if post is unknown.
     */
    @Secured(['ROLE_VOTE_DOWN'])
    def downvote() {
        User user = (User) springSecurityService.currentUser

        try {
            long id = Long.parseLong((String) params.id)
            topicService.voteForPost(id, user.id, VoteType.DOWNVOTE)
            render(status: 201, text: topicService.getScoreForPost(id))
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    /**
     * Answer given question.
     * @return answer id or 404 if question is unknown.
     */
    @Secured(['ROLE_CREATE_POST'])
    def answer() {
        String text = params.text
        long id = Long.parseLong((String) params.id);
        User user = (User) springSecurityService.currentUser

        try {
            Post answer = topicService.addAnswer(id, user.id, text);
            render(status: 201, text: answer.id)
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    /**
     * Create a comment on given post.
     * @return Comment id, 403 if not allowed or 404 if post is unknown.
     */
    @Secured(['IS_AUTHENTICATED_FULLY'])
    def comment() {
        String text = params.text
        long id = Long.parseLong((String) params.id);
        Post post = Post.get(id);
        User user = (User) springSecurityService.currentUser

        if ((post.history.empty && post.content.author.user.id == user.id) ||
                post.history[0].author.user.id == user.id ||
                SpringSecurityUtils.ifAllGranted('ROLE_CREATE_COMMENT')) {
            try {
                Post comment = topicService.addComment(id, user.id, text);

                render(status: 201, text: comment.id)
            } catch (TopicServiceException e) {
                render(status: 404, text: e.getCode())
            }
        } else {
            render(403)
        }
    }

    /**
     * Delete given post.
     * @return 205 or 404 if post is unknown.
     */
    @Secured(['ROLE_CREATE_POST'])
    def delete() {
        try {
            topicService.deletePost(Long.parseLong((String) params.id))

            render(status: 205)
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    /**
     * Choose answer as best answer.
     * @return 200 if answer is marked, 403 if not allowed or 404 if post does not exist
     */
    @Secured(['ROLE_CREATE_POST'])
    def chooseAnswer() {
        try {
            User user = (User) springSecurityService.currentUser
            long id = Long.parseLong((String) params.id)
            Topic topic = Post.get(id).topic
            if ((topic.question.history.empty && topic.question.content.author.user.id == user.id) ||
                    (topic.question.history[0].author.id == user.id)) {
                topicService.tagPostAsBestAnswer(topic.id, id)
                render(status: 200)
            } else {
                render(status: 403)
            }
        } catch (TopicServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }
}
