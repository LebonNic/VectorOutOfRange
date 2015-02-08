<div class="medal-container">
    <g:each var="badge" in="${bronzeBadges}">
        <voor:badge user="${user}" badge="${badge}"/>
    </g:each>
    <g:each var="badge" in="${silverBadges}">
        <voor:badge user="${user}" badge="${badge}"/>
    </g:each>
    <g:each var="badge" in="${goldBadges}">
        <voor:badge user="${user}" badge="${badge}"/>
    </g:each>
    <g:each var="badge" in="${platinumBadges}">
        <voor:badge user="${user}" badge="${badge}"/>
    </g:each>
</div>