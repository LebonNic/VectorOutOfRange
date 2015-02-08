<%@ page import="fr.isima.vectoroutofrange.PostType" %>

<div class="cd-timeline-block">
    <g:if test="${message.post.type == PostType.QUESTION}">
        <g:if test="${message.post.history[0] == message || message.post.history.empty}">
            <div class="cd-timeline-img">
                <i class="fa fa-question"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.asked.question"/></h2>

                <p>${message.text}</p>
                <a class="cd-read-more" href="${createLink(controller: 'topic', action: 'view', id: message.post.topic.id)}"><g:message code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${message.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:if>
        <g:else>
            <div class="cd-timeline-img">
                <i class="fa fa-pencil"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.edited.question"/></h2>

                <p>${message.text}</p>
                <a class="cd-read-more" href="${createLink(controller: 'topic', action: 'view', id: message.post.topic.id)}"><g:message code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${message.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:else>
    </g:if>
    <g:elseif test="${message.post.type == PostType.ANSWER}">
        <g:if test="${message.post.history[0] == message || message.post.history.empty}">
            <div class="cd-timeline-img">
                <i class="fa fa-reply"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.answered.question"/></h2>

                <p>${message.text}</p>
                <a class="cd-read-more" href="${createLink(controller: 'topic', action: 'view', id: message.post.topic.id)}"><g:message code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${message.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:if>
        <g:else>
            <div class="cd-timeline-img">
                <i class="fa fa-pencil"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.edited.answer"/></h2>

                <p>${message.text}</p>
                <a class="cd-read-more" href="${createLink(controller: 'topic', action: 'view', id: message.post.topic.id)}"><g:message code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${message.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:else>
    </g:elseif>
    <g:elseif test="${message.post.type == PostType.COMMENT}">
        <g:if test="${message.post.history[0] == message || message.post.history.empty}">
            <div class="cd-timeline-img">
                <i class="fa fa-comment"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.commented"/></h2>

                <p>${message.text}</p>
                <a class="cd-read-more" href="${createLink(controller: 'topic', action: 'view', id: message.post.topic.id)}"><g:message code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${message.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:if>
        <g:else>
            <div class="cd-timeline-img">
                <i class="fa fa-pencil"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.edited.comment"/></h2>

                <p>${message.text}</p>
                <a class="cd-read-more" href="${createLink(controller: 'topic', action: 'view', id: message.post.topic.id)}"><g:message code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${message.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:else>
    </g:elseif>
</div>