<g:if test="${post.topic.bestAnswer == null}">
    <sec:ifLoggedIn>
        <g:if test="${sec.loggedInUserInfo(field: 'id').toLong() == post.topic.question.creator.id}">
            <a id="${post.id}" class="choose-best-answer action-link">
                <i class="fa fa-check" title="<g:message code="voor.topic.choose.as.best.answer"/>"></i>
            </a>
        </g:if>
    </sec:ifLoggedIn>
</g:if>
<g:else>
    <g:if test="${post.topic.bestAnswer.id == post.id}">
        <i class="fa fa-check best-answer" title="<g:message code="voor.topic.chose.as.best.answer"/>"></i>
    </g:if>
</g:else>