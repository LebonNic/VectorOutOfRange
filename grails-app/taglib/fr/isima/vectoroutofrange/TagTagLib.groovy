package fr.isima.vectoroutofrange

class TagTagLib {
    static namespace = 'voor'

    def tag = { attrs ->
        out << render(template: '/tag/tag', model: ['tag': attrs['tag']])
    }
}
