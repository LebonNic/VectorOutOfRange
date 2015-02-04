<section id="cd-timeline" class="cd-container">
    <g:each var="message" in="${user.userInformation.messages}">
        <voor:timelineEvent message="${message}"/>
    </g:each>
</section>