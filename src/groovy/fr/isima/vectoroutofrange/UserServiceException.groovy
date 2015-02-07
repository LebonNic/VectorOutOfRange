package fr.isima.vectoroutofrange

/**
 * Represents the codes associated to the exceptions that the UserService can throw
 */
enum UserServiceExceptionCode{
    USER_NOT_FOUND,
    USER_WEBSITE_INVALID,
    USER_NICKNAME_INVALID,
    USER_ROLE_NOT_FOUND
}

/**
 * Represents an exception potentially thrown by the UserService.
 */
class UserServiceException extends Exception{

    protected UserServiceExceptionCode code

    UserServiceException(UserServiceExceptionCode code, String message){
        super(message)
        this.code = code
    }

    public UserServiceExceptionCode getCode(){
        return  this.code
    }

}
