<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3>Questions</h3>

<g:each var="topic" in="${topics}">
    <div class="panel">
        <div class="ribbon-warper">
            <div class="ribbon">+5</div>
        </div>

        <div class="row">
            <div class="large-1 small-3 columns">
                <div class="row">
                    <div class="large-12 columns">
                        <span class="secondary label radius">${topic.question.votes.size()} votes</span>
                    </div>
                </div>

                <div class="row">
                    <div class="large-12 columns">
                        <span class="secondary label radius">${topic.answers.size()} answers</span>
                    </div>
                </div>

                <div class="row">
                    <div class="large-12 columns">
                        <span class="secondary label radius">0 views</span>
                    </div>
                </div>
            </div>

            <div class="large-11 small-9 columns">
                <a href="${createLink(controller: 'topic', action: 'view', id: topic.id)}"><h5>${topic.title}</h5></a>

                <div class="row">
                    <div class="large-8 small-12 columns">
                        <g:each var="tag" in="${topic.tags}">
                            <a href="${createLink(controller: 'tag', action: 'view', id: tag.id)}"><span class="label radius">${tag.name}</span></a>
                        </g:each>
                    </div>

                    <div class="large-4 small-12 columns">
                        <span class="secondary label">Asked ${topic.question.content.date} by <a href="#">${topic.question.content.author.nickname}</a></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</g:each>

</body>
</html>
