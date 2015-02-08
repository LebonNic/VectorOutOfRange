<span data-tooltip class="has-tip" title="${message(code: badge.name)} - ${message(code: badge.description)}">
    <div class="medal-group">
        <g:if test="${user}">
            <g:each in="${user.userInformation.getBadges(badge.id)}" var="userBadge">
                <div class="medal ${badge.type.toString().toLowerCase()}"></div>
            </g:each>
        </g:if>
        <div class="sample medal ${badge.type.toString().toLowerCase()}"></div>
        <g:if test="${user}">
            <span class="secondary label">${user.userInformation.getBadges(badge.id).size()}</span>
        </g:if>
    </div>
</span>