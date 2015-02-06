<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" %>
<!-- Display comments -->
<g:each var="comment" in="${post.comments.sort { a, b -> a.content.date <=> b.content.date }}">
    <voor:comment comment="${comment}"/>
</g:each>

<g:if test="${SpringSecurityUtils.ifAllGranted('ROLE_CREATE_COMMENT') ||
        (!post.history.empty && sec.loggedInUserInfo(field: 'id').asType(Long.class) == post.history[0].author.id) ||
        sec.loggedInUserInfo(field: 'id').asType(Long.class) == post.content.author.id}">
    <!-- Add comment editor -->
    <div class="comment-editor">
        <a id="${post.id}" class="add-comment">
            <g:message code="voor.topic.add.comment"/>
        </a>

        <form id="${post.id}" class="add-comment-form hide">
            <div class="row">
                <div class="large-12 columns">
                    <label><g:message code="voor.topic.your.comment"/>
                        <textarea id="${post.id}" class="comment-text"></textarea>
                    </label>
                    <button id="${post.id}" type="button" class="add-comment-button tiny button right" disabled>
                        <g:message code="voor.topic.post.your.comment"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
</g:if>