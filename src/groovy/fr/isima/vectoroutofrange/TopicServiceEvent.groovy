package fr.isima.vectoroutofrange

/**
 * Created by LebonNic on 01/02/2015.
 */

enum TopicServiceEventCode {
    NEW_TOPIC_CREATED,
    NEW_COMMENT_ON_POST,
    NEW_ANSWER_ON_TOPIC,
    POST_CORRECTED,
    POST_VOTED
}

class TopicServiceEvent {
    def User actor
    def Post post
    def Topic topic
}
