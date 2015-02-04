<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.layout.questions"/></h3>

<g:each var="topic" in="${topics}">
    <g:render template="topic" model="[topic: topic]"/>
</g:each>

<voor:paginate controller="topic" action="index" total="${topicCount}"/>
</body>
</html>
