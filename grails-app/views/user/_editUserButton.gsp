<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" %>

<sec:ifLoggedIn>
    <g:if test="${user.id == sec.loggedInUserInfo(field: 'id').toLong() ||
            SpringSecurityUtils.ifAllGranted('ROLE_MODERATE_USER')}">
        <a class="action-link" href="${createLink(controller: 'user', action: 'edit', id: user.id)}">
            <i class="fa fa-pencil" title="${message(code: 'voor.user.edit')}"></i>
        </a>
    </g:if>
</sec:ifLoggedIn>