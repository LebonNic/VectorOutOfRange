<h5>${answers.size()} <g:if test="${answers.size() != 1}">
    <g:message code="voor.topic.answers"/>
</g:if>
<g:else>
    <g:message code="voor.topic.answer"/>
</g:else>
</h5>

<g:each var="answer" in="${answers}">
    <voor:answer answer="${answer}"/>
</g:each>

<sec:ifLoggedIn>
    <sec:ifAllGranted roles="ROLE_CREATE_POST">
        <form id="add-answer-form">
            <div class="row">
                <div class="large-12 columns">
                    <label><g:message code="voor.topic.your.answer"/>
                        <textarea id="answer-text" class="answer-textarea"></textarea>
                    </label>
                    <button id="add-answer-button" type="button" class="tiny button right" disabled>
                        <g:message code="voor.topic.post.your.answer"/>
                    </button>
                </div>
            </div>
        </form>
    </sec:ifAllGranted>
</sec:ifLoggedIn>