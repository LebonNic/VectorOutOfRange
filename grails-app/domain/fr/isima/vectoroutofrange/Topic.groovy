package fr.isima.vectoroutofrange

class Topic {

    String title
    Post question
    int views
    Post bestAnswer

    static hasMany = [answers:Post, tags:Tag]

    static mappedBy = [question: "none", answers: "none", bestAnswer: "none"]

    static constraints = {
        answers nullable: true
        tags nullable: true
        bestAnswer nullable: true, validator: {val, obj ->
            if(obj.bestAnswer){
                if(valtype != PostType.QUESTION)
                    return false
                }
                else{
                    return true
                }
            }
    }
}
