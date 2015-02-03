<div class="panel">
    <voor:bounty value="5"/>

    <div class="row">
        <!-- Topic's statistics -->
        <div class="large-1 small-3 columns">
            <voor:voteCount value="${topic.question.votes.size()}"/>
            <br/>
            <voor:answerCount value="${topic.answers.size()}"/>
            <br/>
            <voor:viewCount value="${topic.views}"/>
        </div>

        <div class="large-11 small-9 columns">
            <!-- Topic's title -->
            <a href="${createLink(controller: 'topic', action: 'view', id: topic.id)}"><h5>${topic.title}</h5></a>

            <div class="row">
                <!-- Topic's tags -->
                <div class="large-8 small-12 columns">
                    <g:each var="tag" in="${topic.tags}">
                        <voor:tag tag="${tag}"/>
                    </g:each>
                </div>

                <!-- User's badge -->
                <div class="large-4 small-12 columns">
                    <voor:userBadge post="${topic.question}"/>
                </div>
            </div>
        </div>
    </div>
</div>