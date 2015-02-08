<%@ page import="fr.isima.vectoroutofrange.VoteType; fr.isima.vectoroutofrange.PostType" %>

<div class="cd-timeline-block">
    <g:if test="${vote.post.type == PostType.QUESTION}">
        <g:if test="${vote.type == VoteType.UPVOTE}">
            <div class="cd-timeline-img">
                <i class="fa fa-arrow-up"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.voted.up.question"/></h2>
                <a class="cd-read-more"
                   href="${createLink(controller: 'topic', action: 'view', id: vote.post.topic.id)}"><g:message
                        code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${vote.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:if>
        <g:else>
            <div class="cd-timeline-img">
                <i class="fa fa-arrow-down"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.voted.down.question"/></h2>

                <a class="cd-read-more"
                   href="${createLink(controller: 'topic', action: 'view', id: vote.post.topic.id)}"><g:message
                        code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${vote.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:else>
    </g:if>
    <g:elseif test="${vote.post.type == PostType.ANSWER}">
        <g:if test="${vote.type == VoteType.UPVOTE}">
            <div class="cd-timeline-img">
                <i class="fa fa-arrow-up"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.voted.up.answer"/></h2>

                <a class="cd-read-more"
                   href="${createLink(controller: 'topic', action: 'view', id: vote.post.topic.id)}"><g:message
                        code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${vote.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:if>
        <g:else>
            <div class="cd-timeline-img">
                <i class="fa fa-arrow-down"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.voted.down.answer"/></h2>

                <a class="cd-read-more"
                   href="${createLink(controller: 'topic', action: 'view', id: vote.post.topic.id)}"><g:message
                        code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${vote.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:else>
    </g:elseif>
    <g:elseif test="${vote.post.type == PostType.COMMENT}">
        <g:if test="${vote.type == VoteType.UPVOTE}">
            <div class="cd-timeline-img">
                <i class="fa fa-arrow-up"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.voted.up.comment"/></h2>

                <a class="cd-read-more"
                   href="${createLink(controller: 'topic', action: 'view', id: vote.post.topic.id)}"><g:message
                        code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${vote.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:if>
        <g:else>
            <div class="cd-timeline-img">
                <i class="fa fa-arrow-down"></i>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="voor.topic.voted.down.comment"/></h2>

                <a class="cd-read-more"
                   href="${createLink(controller: 'topic', action: 'view', id: vote.post.topic.id)}"><g:message
                        code="voor.topic.go.to.question"/></a>
                <span class="cd-date"><g:formatDate date="${vote.date}" type="datetime"
                                                    style="MEDIUM"/></span>
            </div>
        </g:else>
    </g:elseif>
</div>