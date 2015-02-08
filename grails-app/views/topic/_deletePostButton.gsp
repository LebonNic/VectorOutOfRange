<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" %>

<sec:ifLoggedIn>
    <g:if test="${SpringSecurityUtils.ifAllGranted('ROLE_MODERATE_POST') ||
            (sec.loggedInUserInfo(field: 'id').toLong() == post.creator.id)}">
        <a class="action-link" data-reveal-id="delete-post-modal-${post.id}">
            <i class="fa fa-times" title="<g:message code="voor.topic.delete.post"/>"></i>
        </a>

        <div id="delete-post-modal-${post.id}" class="reveal-modal" data-reveal>
            <h3><g:message code="voor.topic.delete.confirmation"/></h3>

            <p><g:message code="voor.topic.delete.confirmation.message"/></p>
            <a class="close-reveal-modal">&#215;</a>

            <a id="${post.id}" class="delete-post-button small button right">
                <g:message code="voor.confirm"/>
            </a>
        </div>
    </g:if>
</sec:ifLoggedIn>