<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.tag.about"/> <span class="label radius">${tag.name}</span></h3>
<div class="row panel">
    <p class="text-justify">
        <g:if test="${tag.definition}">${tag.definition}</g:if>
        <g:else><g:message code="voor.tag.has.no.definition" args="[tag.name]"/>.</g:else>
    </p>
    <a class="edit-tag" href="${createLink(controller: 'tag', action: 'edit', id: tag.id)}"><g:message code="voor.tag.edit"/></a>
</div>
</body>
</html>
