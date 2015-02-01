package fr.isima.vectoroutofrange

import grails.transaction.Transactional

@Transactional
class TagService {

    def getTag(long tagId){
        def tag = Tag.get(tagId)

        if(tag){
            return tag
        }
        else {
            throw new TagServiceException(TagServiceExceptionCode.TAG_NOT_FOUND, "The tag's id passed to the method TagService.getTag doesn't exist")
        }
    }

    def createTag(String name, String definition = ""){
        def tag = new Tag(name: name, definition: definition).save(flush: true, failOnError: true)
        return tag
    }

    def updateTag(long tagId, String newName, String newDefinition = ""){
        def tag = this.getTag(tagId)

        tag.name = newName
        tag.definition = newDefinition
        tag.save(flush: true, failOnError: true)

        return tag
    }

    def deleteTag(long tagId){
        def tag = this.getTag(tagId)
        tag.delete()
    }
}
