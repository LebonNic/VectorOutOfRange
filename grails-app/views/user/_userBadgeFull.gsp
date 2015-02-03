<%@ page import="fr.isima.vectoroutofrange.PostType; fr.isima.vectoroutofrange.BadgeType" %>
<div class="user-badge">
    <span>
        <g:if test="${post}">
            <g:if test="${post.type == PostType.QUESTION}">
                <g:message code="voor.topic.asked" args="[post.content.date]"/>
            </g:if>
            <g:elseif test="${post.type == PostType.ANSWER}">
                <g:message code="voor.topic.answered" args="[post.content.date]"/>
            </g:elseif>
            <g:elseif test="${post.type == PostType.COMMENT}">
                <g:message code="voor.topic.by"/>
            </g:elseif>
        </g:if>
    </span>

    <br/>

    <div class="user-profile">

        <div class="user-avatar">
            <img src="${createLink(uri: '/images/avatar.png')}" alt="User avatar" width="32" height="32"/>
        </div>

        <div class="user-extra">
            <a href="${createLink(controller: 'user', action: 'view', id: post.content.author.user.id)}">
                ${post.content.author.nickname}
            </a>
            <br/>
            <span class="reputation">${post.content.author.reputation}</span>

            <g:if test="${!post.content.author.getBadges(BadgeType.PLATINUM).empty}">
                <span class="platinum-medals">${post.content.author.getBadges(BadgeType.PLATINUM).size()}</span>
            </g:if>
            <g:if test="${!post.content.author.getBadges(BadgeType.GOLD).empty}">
                <span class="gold-medals">${post.content.author.getBadges(BadgeType.GOLD).size()}</span>
            </g:if>
            <g:if test="${!post.content.author.getBadges(BadgeType.SILVER).empty}">
                <span class="silver-medals">${post.content.author.getBadges(BadgeType.SILVER).size()}</span>
            </g:if>
            <g:if test="${!post.content.author.getBadges(BadgeType.BRONZE).empty}">
                <span class="bronze-medals">${post.content.author.getBadges(BadgeType.BRONZE).size()}</span>
            </g:if>
        </div>
    </div>
</div>