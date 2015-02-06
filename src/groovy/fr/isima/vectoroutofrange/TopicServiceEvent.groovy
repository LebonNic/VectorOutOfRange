package fr.isima.vectoroutofrange

/**
 * Represents the events' codes triggered by the TopicService.
 */
enum TopicServiceEventCode {
    NEW_TOPIC_CREATED,
    NEW_COMMENT_ON_POST,
    NEW_ANSWER_ON_TOPIC,
    POST_CORRECTED,
    POST_DELETED,
    POST_UPVOTED,
    POST_DOWNVOTED,
    VIEWS_COUNT_INCREMENTED,
    POST_TAGGED_AS_BEST_ANSWER
}

/**
 * Represents an event triggered by the TopicService.
 */
class TopicServiceEvent {

    /**
     * The user who initiates the event.
     */
    def User actor

    /**
     * The post associated to the event.
     */
    def Post post

    /**
     * The topic associated to the event
     */
    def Topic topic
}
