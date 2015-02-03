<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3>${user.userInformation.nickname}</h3>

<span class="label">Nickname</span><span class="label secondary">${user.userInformation.nickname}</span>
<br>
<g:if test="${user.id == sec.loggedInUserInfo(field: 'id').toLong()}">
    <span class="label secondary radius">C'est ta page :)</span>
</g:if>

</body>
</html>
