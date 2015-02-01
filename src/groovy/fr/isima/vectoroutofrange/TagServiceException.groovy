package fr.isima.vectoroutofrange

/**
 * Represents the codes associated to the exceptions that the TagService can throw.
 */
enum TagServiceExceptionCode{
    TAG_NOT_FOUND
}

/**
 * Represents an exception potentially thrown by the TagService.
 */
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
