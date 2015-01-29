package fr.isima.vectoroutofrange

class Badge {

    String name
    String description

    static belongsTo = UserInformation

    static hasMany = [users:UserInformation]

    static constraints = {
    }
}
