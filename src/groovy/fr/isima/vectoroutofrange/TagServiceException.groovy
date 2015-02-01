package fr.isima.vectoroutofrange

/**
 * Created by LebonNic on 01/02/2015.
 */

enum TagServiceExceptionCode{
    TAG_NOT_FOUND
}

class TagServiceException extends Exception{
    protected TagServiceExceptionCode code;

    TagServiceException(TagServiceExceptionCode code, String message){
        super(message)
        this.code = code
    }

    public TagServiceExceptionCode getCode(){
        return this.code
    }
}
