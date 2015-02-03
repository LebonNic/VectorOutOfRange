<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.layout.users"/></h3>

<ul class="small-block-grid-1 medium-block-grid-3 large-block-grid-6">
    <g:each var="user" in="${users}">
        <li>
            <div class="user-badge">
                <div class="user-profile">
                    <div class="user-avatar">
                        <img src="${createLink(uri: "/images/avatar.png")}" alt="User avatar" width="32" height="32"/>
                    </div>

                    <div class="user-extra">
                        <a href="${createLink(controller: 'user', action: 'view', id: user.id)}">${user.userInformation.nickname}</a>
                        <br>
                        <span class="reputation">${user.userInformation.reputation}</span>
                        <span class="gold-medals">1</span>
                        <span class="silver-medals">11</span>
                        <span class="bronze-medals">41</span>
                    </div>
                </div>
            </div>
        </li>
    </g:each>
</ul>

<div class="row">
    <g:paginate controller="user" action="index" total="${userCount}"/>
</div>
</body>
</html>
