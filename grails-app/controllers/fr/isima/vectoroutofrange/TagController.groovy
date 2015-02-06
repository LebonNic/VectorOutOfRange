package fr.isima.vectoroutofrange

import grails.plugin.springsecurity.annotation.Secured

class TagController {

    TagService tagService

    static allowedMethods = [index : 'GET',
                             view  : 'GET',
                             edit  : 'GET',
                             save  : 'POST',
                             delete: 'POST']

    /**
     * Display tags list view.
     * @return Tags list view.
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def index() {
        render(view: 'index', model: [tags: Tag.list(params), tagCount: Tag.count()])
    }

    /**
     * Display tag wiki view.
     * @return Tag wiki view.
     */
    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def view() {
        render(view: 'view', model: [tag: Tag.get((String) params.id)])
    }

    /**
     * Display tag edition view.
     * @return Tag edition view.
     */
    @Secured(['ROLE_MODERATE_TAG'])
    def edit() {
        render(view: 'edit', model: [tag: Tag.get((String) params.id)])
    }

    /**
     * Update given tag wiki.
     * @return Tag id or 404 if tag is unknown.
     */
    @Secured(['ROLE_MODERATE_TAG'])
    def save() {
        try {
            Tag tag = (Tag) tagService.updateTag(Long.parseLong((String) params.id),
                    (String) params.name,
                    (String) params.definition)
            render(status: 200, text: tag.id)
        } catch (TagServiceException e) {
            render(status: 404, text: e.getCode())
        }
    }

    /**
     * Delete given tag.
     * @return 205 or 404 if tag is unknown.
     */
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
