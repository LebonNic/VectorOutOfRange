package fr.isima.vectoroutofrange

class Role {
    String authority
    String name
    String description
    int requiredReputation

    static mapping = {
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }
}
