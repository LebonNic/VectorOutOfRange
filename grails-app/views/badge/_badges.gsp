<div class="medal-container">
    <g:each var="badge" in="${bronzeBadges}">
        <voor:badge user="${user}" type="bronze" badge="${badge}"/>
    </g:each>
    <g:each var="badge" in="${silverBadges}">
        <voor:badge user="${user}" type="silver" badge="${badge}"/>
    </g:each>
    <g:each var="badge" in="${goldBadges}">
        <voor:badge user="${user}" type="gold" badge="${badge}"/>
    </g:each>
    <g:each var="badge" in="${platinumBadges}">
        <voor:badge user="${user}" type="platinum" badge="${badge}"/>
    </g:each>
</div>