<%@ page import="fr.isima.vectoroutofrange.PostType; fr.isima.vectoroutofrange.BadgeType" %>
<div class="user-badge">
    <g:if test="${post}">
        <span>
            <g:if test="${post.type == PostType.QUESTION}">
                <g:message code="voor.topic.asked" args="[formatDate(date: post.content.date, type: 'datetime', style: 'SHORT')]"/>
            </g:if>
            <g:elseif test="${post.type == PostType.ANSWER}">
                <g:message code="voor.topic.answered" args="[formatDate(date: post.content.date, type: 'datetime', style: 'SHORT')]"/>
            </g:elseif>
            <g:elseif test="${post.type == PostType.COMMENT}">
                <g:message code="voor.topic.by"/>
            </g:elseif>
            <br/>
        </span>
    </g:if>

    <div class="user-profile">

        <div class="user-avatar">
            <canvas id="${user.id}" class="avatar"></canvas>
        </div>

        <div class="user-extra">
            <a href="${createLink(controller: 'user', action: 'view', id: user.id)}">
                ${user.userInformation.nickname}
            </a>
            <br/>
            <span class="reputation">${user.userInformation.reputation}</span>

            <voor:medals type="platinum" user="${user}"/>
            <voor:medals type="gold" user="${user}"/>
            <voor:medals type="silver" user="${user}"/>
            <voor:medals type="bronze" user="${user}"/>
        </div>
    </div>
</div>