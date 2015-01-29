package fr.isima.vectoroutofrange

enum BadgeType{
    PLATINIUM,
    GOLD,
    SILVER,
    BRONZE
}

class Badge {

    String name
    String description
    BadgeType type

    static belongsTo = UserInformation

    static hasMany = [users:UserInformation]

    static constraints = {
    }
}
