package fr.isima.vectoroutofrange

/**
 * Created by LebonNic on 30/01/2015.
 */

enum TopicExceptionErrorCode{
    AUTHOR_NOT_FOUND,
    POST_NOT_FOUND,
    TOPIC_NOT_FOUND
}

class TopicException extends Exception{

    protected TopicExceptionErrorCode code;

    TopicException(TopicExceptionErrorCode code, String message){
        super(message)
        this.code = code
    }

    public TopicExceptionErrorCode getCode(){
        return this.code
    }
}
