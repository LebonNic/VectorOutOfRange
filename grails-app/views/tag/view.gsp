<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet href="highlight.js"/>
</head>

<body>
<h3><g:message code="voor.tag.about"/> <span class="label radius">${tag.name}</span></h3>

<div class="row panel">
    <markdown:renderHtml><g:if test="${tag.definition}">${tag.definition}</g:if><g:else><g:message code="voor.tag.has.no.definition" args="[tag.name]"/>.</g:else>
    </markdown:renderHtml>
    <a class="action-link" href="${createLink(controller: 'tag', action: 'edit', id: tag.id)}" title="${message(code: 'voor.tag.edit')}">
        <i class="fa fa-pencil"></i>
    </a>
    <a id="delete-tag" class="action-link" title="${message(code: 'voor.tag.delete')}">
        <i class="fa fa-times"></i>
    </a>
</div>

<h3><g:message code="voor.topic.questions"/></h3>

<g:each var="topic" in="${tag.topics}">
    <g:render template="/topic/topic" model="[topic: topic]"/>
</g:each>

<script type="text/javascript">
    var deleteTag = $("#delete-tag");
    deleteTag.click(function () {
        $.ajax({
            url: "${createLink(controller: 'tag', action: 'delete', id: tag.id)}",
            type: "POST"
        }).success(function () {
            window.location.href = "${createLink(controller: 'tag', action: 'index')}";
        });
    });
</script>
<asset:javascript src="highlight.js"/>
<script>hljs.initHighlightingOnLoad();</script>
</body>
</html>
