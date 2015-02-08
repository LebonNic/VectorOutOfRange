package fr.isima.vectoroutofrange

class TagTagLib {

    static namespace = 'voor'

    /**
     * Display a tag.
     * @param tag REQUIRED Tag to display.
     */
    def tag = { attrs ->
        out << render(template: '/tag/tag', model: [
                tag: (Tag) attrs['tag']
        ])
    }
}
