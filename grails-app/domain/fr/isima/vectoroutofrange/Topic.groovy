package fr.isima.vectoroutofrange

class Topic {

    String title
    Post question
    int views
    Post bestAnswer

    static hasMany = [answers:Post, tags:Tag]

    static mappedBy = [question: "none", answers: "none", bestAnswer: "none"]

    static mapping = {
        sort 'question.content.date': 'desc'
    }

    static constraints = {
        answers nullable: true
        tags nullable: true
        bestAnswer nullable: true, validator: {val, obj ->
            def boolean isValid = false
            if(obj.bestAnswer) {
                if (val.type == PostType.ANSWER){
                    isValid = true
                }
            }
            else{
                isValid = true
            }

            return isValid
        }
    }
}
