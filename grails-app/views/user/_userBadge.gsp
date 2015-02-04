<%@ page import="fr.isima.vectoroutofrange.PostType" %>
<span class="secondary label">
    <g:if test="${post}">
        <g:if test="${post.type == PostType.QUESTION}">
            <g:message code="voor.topic.asked.by" args="[formatDate(date: post.content.date, type: 'datetime', style: 'SHORT')]"/> <a
                href="${createLink(controller: 'user', action: 'view', id: user.id)}">
            ${user.userInformation.nickname}
        </a>
        </g:if>
        <g:elseif test="${post.type == PostType.ANSWER}">
            <g:message code="voor.topic.answered" args="[formatDate(date: post.content.date, type: 'datetime', style: 'SHORT')]"/> <a
                href="${createLink(controller: 'user', action: 'view', id: user.id)}">
            ${user.userInformation.nickname}
        </a>
        </g:elseif>
        <g:elseif test="${post.type == PostType.COMMENT}">
            <g:message code="voor.topic.by"/> <a
                href="${createLink(controller: 'user', action: 'view', id: user.id)}">
            ${user.userInformation.nickname}
        </a> <g:formatDate date="${post.content.date}" type="datetime" style="SHORT"/>
        </g:elseif>
    </g:if>
</span>