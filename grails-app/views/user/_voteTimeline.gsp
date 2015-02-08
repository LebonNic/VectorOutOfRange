<section id="cd-timeline" class="cd-container">
    <g:each var="vote" in="${user.userInformation.votes}">
        <voor:voteTimelineEvent vote="${vote}"/>
    </g:each>
</section>