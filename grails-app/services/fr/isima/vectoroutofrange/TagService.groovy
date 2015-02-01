package fr.isima.vectoroutofrange

import grails.transaction.Transactional

@Transactional
class TagService {

    /**
     * Retrieves the tag corresponding to the id passed as parameter.
     * @param tagId The id of the tag.
     * @return The retrieved tag.
     */
    def getTag(long tagId){
        def tag = Tag.get(tagId)

        if(tag){
            return tag
        }
        else {
            throw new TagServiceException(TagServiceExceptionCode.TAG_NOT_FOUND, "The tag's id passed to the method TagService.getTag doesn't exist")
        }
    }

    /**
     * Creates and saves a new tag in the database.
     * @param name The name of the tag.
     * @param definition A definition associated to the tag.
     * @return The new tag.
     */
    def createTag(String name, String definition = ""){
        def tag = new Tag(name: name, definition: definition).save(flush: true, failOnError: true)
        return tag
    }

    /**
     * Updates a tag and saves it to the database.
     * @param tagId The id of the tag to update.
     * @param newName The new name of the tag.
     * @param newDefinition The new definition of the tag.
     * @return The updated tag.
     */
    def updateTag(long tagId, String newName, String newDefinition = ""){
        def tag = this.getTag(tagId)

        tag.name = newName
        tag.definition = newDefinition
        tag.save(flush: true, failOnError: true)

        return tag
    }

    /**
     * Delete a tag from the database.
     * @param tagId The id of the tag to delete.
     * @return ??
     */
    def deleteTag(long tagId){
        def tag = this.getTag(tagId)

        for(topic in tag.topics){
            topic.tags.remove(tag)
            topic.save(flush: true, failOnError: true)
        }

        tag.delete()
    }
}
