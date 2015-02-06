<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" %>
<sec:ifLoggedIn>
    <g:if test="${SpringSecurityUtils.ifAllGranted('ROLE_MODERATE_POST') ||
            (!post.history.empty && sec.loggedInUserInfo(field: 'id').asType(Long.class) == post.history[0].author.user.id) ||
            (sec.loggedInUserInfo(field: 'id').asType(Long.class) == post.content.author.user.id)}">
        <a class="action-link" href="${createLink(controller: 'topic', action: 'edit', id: post.id)}">
            <i class="fa fa-pencil" title="<g:message code="voor.topic.edit.post"/>"></i>
        </a>
    </g:if>
</sec:ifLoggedIn>