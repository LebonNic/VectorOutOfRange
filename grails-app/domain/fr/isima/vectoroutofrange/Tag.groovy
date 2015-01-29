package fr.isima.vectoroutofrange

class Tag {

    String name
    String definition

    static belongsTo = Topic

    static hasMany = [topics:Topic]

    static constraints = {
    }
}
