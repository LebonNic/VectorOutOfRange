<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" %>

<sec:ifLoggedIn>
    <g:if test="${SpringSecurityUtils.ifAllGranted('ROLE_MODERATE_POST') ||
            (sec.loggedInUserInfo(field: 'id').toLong() == post.creator.id)}">
        <a class="action-link" href="${createLink(controller: 'topic', action: 'edit', id: post.id)}">
            <i class="fa fa-pencil" title="<g:message code="voor.topic.edit.post"/>"></i>
        </a>
    </g:if>
</sec:ifLoggedIn>