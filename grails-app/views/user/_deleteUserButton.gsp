<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" %>

<sec:ifLoggedIn>
    <g:if test="${SpringSecurityUtils.ifAllGranted('ROLE_MODERATE_USER')}">
        <a class="action-link" data-reveal-id="delete-user-modal">
            <i class="fa fa-times" title="<g:message code="voor.user.delete"/>"></i>
        </a>

        <div id="delete-user-modal" class="reveal-modal" data-reveal>
            <h3><g:message code="voor.user.delete.confirmation"/></h3>

            <p><g:message code="voor.user.delete.confirmation.message"/></p>
            <a class="close-reveal-modal">&#215;</a>

            <a id="delete-user-buttton" class="small button right">
                <g:message code="voor.confirm"/>
            </a>
        </div>
    </g:if>
</sec:ifLoggedIn>