<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.layout.tags"/></h3>

<g:each var="tag" in="${tags}">
    <div class="row tag-container">
        <a href="${createLink(controller: 'tag', action: 'view', id: tag.id)}"><span class="label">${tag.name}</span>
        </a>

        <p class="text-justify">
            <g:if test="${tag.definition}">${tag.definition}</g:if>
            <g:else><g:message code="voor.tag.has.no.definition" args="[tag.name]"/>.</g:else>
        </p>
    </div>
</g:each>

</body>
</html>
