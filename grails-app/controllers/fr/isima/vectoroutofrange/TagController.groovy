package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class TagController {

    TagService tagService

    static allowedMethods = [index : 'GET',
                             view  : 'GET',
                             edit  : 'GET',
                             save  : 'POST',
                             delete: 'POST']

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [tags: Tag.list(params), tagCount: Tag.count()])
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        render(view: 'view', model: [tag: Tag.get((String) params.id)])
    }

    @Secured(['ROLE_MODERATE_TAG'])
    def edit() {
        render(view: 'edit', model: [tag: Tag.get((String) params.id)])
    }

    @Secured(['ROLE_MODERATE_TAG'])
    def save() {
        try {
            Tag tag = tagService.updateTag(Long.parseLong((String) params.id),
                    (String) params.name,
                    (String) params.definition)
            render(status: 200, text: tag.id)
        } catch (TagServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    @Secured(['ROLE_MODERATE_TAG'])
    def delete() {
        try {
            tagService.deleteTag(Long.parseLong((String) params.id))
            render(status: 205)
        } catch (TagServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }
}
