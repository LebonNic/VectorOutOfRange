package fr.isima.vectoroutofrange

/**
 * Represents the events' codes triggered by the TopicService.
 */
enum TopicServiceEventCode {
    NEW_TOPIC_CREATED,
    NEW_COMMENT_ON_POST,
    NEW_ANSWER_ON_TOPIC,
    POST_CORRECTED,
    POST_UPVOTED,
    POST_DOWNVOTED
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
