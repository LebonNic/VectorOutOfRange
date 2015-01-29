package fr.isima.vectoroutofrange

class TopicController {

    def index() {
        render(view: 'index',
                model: [topics: Topic.getAll()])
    }
}
