<%@ page import="fr.isima.vectoroutofrange.PostType" %>
<span class="secondary label">
    <g:if test="${post}">
        <g:if test="${post.type == PostType.QUESTION}">
            <g:message code="voor.topic.asked" args="[post.content.date]"/> <a
                href="${createLink(controller: 'user', action: 'view', id: post.content.author.user.id)}">
            ${post.content.author.nickname}
        </a>
        </g:if>
        <g:elseif test="${post.type == PostType.ANSWER}">
            <g:message code="voor.topic.answered" args="[post.content.date]"/> <a
                href="${createLink(controller: 'user', action: 'view', id: post.content.author.user.id)}">
            ${post.content.author.nickname}
        </a>
        </g:elseif>
        <g:elseif test="${post.type == PostType.COMMENT}">
            <g:message code="voor.topic.by"/> <a
                href="${createLink(controller: 'user', action: 'view', id: post.content.author.user.id)}">
            ${post.content.author.nickname}
        </a> ${post.content.date}
        </g:elseif>
    </g:if>
</span>