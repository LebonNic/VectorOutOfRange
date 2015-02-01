package fr.isima.vectoroutofrange

/**
 * Represents the codes associated to the exceptions that the TopicService can throw.
 */
enum TopicServiceExceptionCode {
    AUTHOR_NOT_FOUND,
    POST_NOT_FOUND,
    TOPIC_NOT_FOUND,
    BUSINESS_LOGIC_ERROR
}

/**
 * Represents an exception potentially thrown by the TopicService.
 */
class TopicServiceException extends Exception{

    protected TopicServiceExceptionCode code;

    TopicServiceException(TopicServiceExceptionCode code, String message){
        super(message)
        this.code = code
    }

    public TopicServiceExceptionCode getCode(){
        return this.code
    }
}
