package fr.isima.vectoroutofrange

/**
 * Created by LebonNic on 30/01/2015.
 */

enum TopicServiceExceptionCode {
    AUTHOR_NOT_FOUND,
    POST_NOT_FOUND,
    TOPIC_NOT_FOUND,
    BUSINESS_LOGIC_ERROR
}

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
