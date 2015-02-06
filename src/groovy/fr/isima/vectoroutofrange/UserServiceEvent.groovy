package fr.isima.vectoroutofrange

/**
 * Represents the events' codes triggered by the UserService.
 */
enum UserServiceEventCode{
    NEW_USER_CREATED,
    USER_UPDATED
}

/**
 * Represents an event triggered by the UserService.
 */
class UserServiceEvent {
    /**
     * The user processed by the service before the event is triggered.
     */
    def User user
}
