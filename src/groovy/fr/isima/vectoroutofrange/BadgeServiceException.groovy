package fr.isima.vectoroutofrange

/**
 * Represents the codes associated to the exceptions that the BadgeService can throw.
 */
enum BadgeServiceExceptionCode{
    BADGE_NOT_FOUND
}

/**
 * Represents an exception potentially thrown by the BadgeService.
 */
class BadgeServiceException extends Exception{
    protected BadgeServiceExceptionCode code

    BadgeServiceException(BadgeServiceExceptionCode code, String message){
        super(message)
        this.code = code
    }

    public BadgeServiceExceptionCode getCode(){
        return this.code
    }
}
