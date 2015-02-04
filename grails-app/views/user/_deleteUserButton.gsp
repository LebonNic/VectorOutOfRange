<sec:ifLoggedIn>
    <g:if test="${user.id == sec.loggedInUserInfo(field: 'id').toLong()}">
        <a id="delete-user-button" class="action-link">
            <i class="fa fa-times" title="${message(code: 'voor.user.delete')}"></i>
        </a>
    </g:if>
</sec:ifLoggedIn>