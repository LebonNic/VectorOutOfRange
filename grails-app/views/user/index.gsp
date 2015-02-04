<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.layout.users"/></h3>

<ul class="small-block-grid-1 large-block-grid-5">
    <g:each var="user" in="${users}">
        <li><voor:userBadge type="full" user="${user}"/></li>
    </g:each>
</ul>

<div class="row">
    <voor:paginate controller="user" action="index" total="${userCount}" max="20"/>
</div>
</body>
</html>
