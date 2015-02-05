<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.layout.badges"/></h3>

<div class="medal-container">
    <g:each var="badge" in="${bronzeBadges}">
        <span data-tooltip class="has-tip" title="${message(code: badge.name)} - ${message(code: badge.description)}">
            <div class="medal-group">
                <sec:ifLoggedIn>
                    <g:each in="${user.userInformation.getBadges(badge.id)}" var="userBadge">
                        <div class="medal bronze"></div>
                    </g:each>
                </sec:ifLoggedIn>
                <div class="medal bronze sample"></div>
                <sec:ifLoggedIn>
                    <span class="secondary label">${user.userInformation.getBadges(badge.id).size()}</span>
                </sec:ifLoggedIn>
            </div>
        </span>
    </g:each>
    <g:each var="badge" in="${silverBadges}">
        <span data-tooltip class="has-tip" title="${message(code: badge.name)} - ${message(code: badge.description)}">
            <div class="medal-group">
                <sec:ifLoggedIn>
                    <g:each in="${user.userInformation.getBadges(badge.id)}" var="userBadge">
                        <div class="medal silver"></div>
                    </g:each>
                </sec:ifLoggedIn>
                <div class="medal silver sample"></div>
                <sec:ifLoggedIn>
                    <span class="secondary label">${user.userInformation.getBadges(badge.id).size()}</span>
                </sec:ifLoggedIn>
            </div>
        </span>
    </g:each>
    <g:each var="badge" in="${goldBadges}">
        <span data-tooltip class="has-tip" title="${message(code: badge.name)} - ${message(code: badge.description)}">
            <div class="medal-group">
                <sec:ifLoggedIn>
                    <g:each in="${user.userInformation.getBadges(badge.id)}" var="userBadge">
                        <div class="medal gold"></div>
                    </g:each>
                </sec:ifLoggedIn>
                <div class="medal gold sample"></div>
                <sec:ifLoggedIn>
                    <span class="secondary label">${user.userInformation.getBadges(badge.id).size()}</span>
                </sec:ifLoggedIn>
            </div>
        </span>
    </g:each>
    <g:each var="badge" in="${platinumBadges}">
        <span data-tooltip class="has-tip" title="${message(code: badge.name)} - ${message(code: badge.description)}">
            <div class="medal-group">
                <sec:ifLoggedIn>
                    <g:each in="${user.userInformation.getBadges(badge.id)}" var="userBadge">
                        <div class="medal platinum"></div>
                    </g:each>
                </sec:ifLoggedIn>
                <div class="medal platinum sample"></div>
                <sec:ifLoggedIn>
                    <span class="secondary label">${user.userInformation.getBadges(badge.id).size()}</span>
                </sec:ifLoggedIn>
            </div>
        </span>
    </g:each>
</div>
</body>
</html>
