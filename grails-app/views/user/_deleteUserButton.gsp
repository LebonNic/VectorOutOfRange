<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" %>
<sec:ifLoggedIn>
    <g:if test="${SpringSecurityUtils.ifAllGranted('ROLE_MODERATE_USER')}">
        <a id="delete-user-button" class="action-link">
            <i class="fa fa-times" title="${message(code: 'voor.user.delete')}"></i>
        </a>
    </g:if>
</sec:ifLoggedIn>