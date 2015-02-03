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
    <a class="action-link" href="${createLink(controller: 'tag', action: 'edit', id: tag.id)}" title="${message(code: 'voor.tag.edit')}">
        <i class="fa fa-pencil"></i>
    </a>
    <a id="delete-tag" class="action-link" title="${message(code: 'voor.tag.delete')}">
        <i class="fa fa-times"></i>
    </a>
</div>

<h3><g:message code="voor.topic.questions"/></h3>

<g:each var="topic" in="${tag.topics}">
    <div class="panel">
        <div class="ribbon-warper">
            <div class="ribbon">+5</div>
        </div>

        <div class="row">
            <div class="large-1 small-3 columns">
                <div class="row">
                    <div class="large-12 columns">
                        <span class="secondary label radius">${topic.question.votes.size()}
                        <g:if test="${topic.question.votes.size() != 1}"><g:message
                                code="voor.topic.votes"/>
                        </g:if>
                        <g:else>
                            <g:message code="voor.topic.vote"/>
                        </g:else>
                        </span>
                    </div>
                </div>

                <div class="row">
                    <div class="large-12 columns">
                        <span class="secondary label radius">${topic.answers.size()}
                        <g:if test="${topic.answers.size() != 1}">
                            <g:message code="voor.topic.answers"/>
                        </g:if>
                        <g:else>
                            <g:message code="voor.topic.answer"/>
                        </g:else>
                        </span>
                    </div>
                </div>

                <div class="row">
                    <div class="large-12 columns">
                        <span class="secondary label radius">0 <g:if test="${0 != 1}"><g:message
                                code="voor.topic.views"/></g:if><g:else><g:message
                                code="voor.topic.view"/></g:else></span>
                    </div>
                </div>
            </div>

            <div class="large-11 small-9 columns">
                <a href="${createLink(controller: 'topic', action: 'view', id: topic.id)}"><h5>${topic.title}</h5></a>

                <div class="row">
                    <div class="large-8 small-12 columns">
                        <g:each var="tag" in="${topic.tags}">
                            <a href="${createLink(controller: 'tag', action: 'view', id: tag.id)}"
                               title="${tag.definition}"><span class="label radius">${tag.name}</span></a>
                        </g:each>
                    </div>

                    <div class="large-4 small-12 columns">
                        <span class="secondary label"><g:message code="voor.topic.asked.by"
                                                                 args="[topic.question.content.date]"/> <a
                                href="${createLink(controller: 'user', action: 'view', id: topic.question.content.author.id)}">${topic.question.content.author.nickname}</a>
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
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

</body>
</html>
