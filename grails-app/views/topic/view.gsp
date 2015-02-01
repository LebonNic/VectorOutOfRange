<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<div class="panel">
    <div class="ribbon-warper">
        <div class="ribbon">+5</div>
    </div>

    <div class="row">
        <div class="large-1 small-12 columns">
            <div class="row">
                <div class="large-12 columns voter">
                    <a id="${topic.question.id}" class="upvote"></a>
                    <a id="${topic.question.id}" class="downvote"></a>
                    <span id="${topic.question.id}" class="vote-count">${topic.question.getScore()}</span>
                </div>
            </div>

            <div class="row">
                <div class="large-12 columns">
                    <span class="secondary label radius">0 <g:if test="${0 != 1}"><g:message
                            code="voor.topic.views"/></g:if><g:else><g:message code="voor.topic.view"/></g:else></span>
                </div>
            </div>
        </div>

        <div class="large-11 small-12 columns">
            <a href="${createLink(controller: 'topic', action: 'view', id: topic.id)}"><h5>${topic.title}</h5></a>

            <p class="text-justify">${topic.question.content.text}</p>

            <div class="row">
                <div class="large-9 small-12 columns">
                    <g:each var="tag" in="${topic.tags}">
                        <a href="${createLink(controller: 'tag', action: 'view', id: tag.id)}" title="${tag.definition}"><span
                                class="label radius">${tag.name}</span></a>
                    </g:each>
                </div>

                <div class="large-3 small-12 columns">
                    <div class="question-user-info">
                        <span><g:message code="voor.topic.asked" args="[topic.question.content.date]"/></span>
                        <br>

                        <div class="user-profile">
                            <div class="user-avatar">
                                <img src="${createLink(uri: "/images/avatar.png")}" alt="User avatar" width="32"
                                     height="32"/>
                            </div>

                            <div class="user-extra">
                                <a href="#">${topic.question.content.author.nickname}</a>
                                <br>
                                <span class="reputation">${topic.question.content.author.reputation}</span>
                                <span class="gold-medals">1</span>
                                <span class="silver-medals">11</span>
                                <span class="bronze-medals">41</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="large-12 small-12 columns">
                    <g:each var="comment" in="${topic.question.comments}">
                        <div class="question-comment">
                            <p class="text-justify">${comment.content.text}</p>
                            <span class="secondary label"><g:message code="voor.topic.by"/> <a
                                    href="#">${comment.content.author.nickname}</a> ${comment.content.date}
                            </span>
                        </div>
                    </g:each>
                    <div class="comment-editor">
                        <a class="add-comment" id="${topic.question.id}"><g:message
                                code="voor.topic.add.comment"/></a>

                        <form id="${topic.question.id}" class="add-comment-form hide">
                            <div class="row">
                                <div class="large-12 columns">
                                    <label><g:message code="voor.topic.your.comment"/>
                                        <textarea class="comment-text" id="${topic.question.id}"></textarea>
                                    </label>
                                    <button id="${topic.question.id}" type="button"
                                            class="add-comment-button tiny button right" disabled><g:message
                                            code="voor.topic.post.your.comment"/></button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<h5>${topic.answers.size()} <g:if test="${topic.answers.size() != 1}"><g:message
        code="voor.topic.answers"/></g:if><g:else><g:message code="voor.topic.answer"/></g:else></h5>

<g:each in="${topic.answers}" var="answer">
    <div class="panel">
        <div class="row">
            <div class="large-1 small-12 columns">
                <div class="row">
                    <div class="large-12 columns voter">
                        <a id="${answer.id}" class="upvote"></a>
                        <a id="${answer.id}" class="downvote"></a>
                        <span id="${answer.id}" class="vote-count">${answer.getScore()}</span>
                    </div>
                </div>
            </div>

            <div class="large-11 small-12 columns">
                <p class="text-justify">
                    ${answer.content.text}
                </p>

                <div class="row">
                    <div class="large-3 small-12 columns right">
                        <div class="question-user-info">
                            <span><g:message code="voor.topic.answered" args="[answer.content.date]"/></span>
                            <br>

                            <div class="user-profile">
                                <div class="user-avatar">
                                    <img src="${createLink(uri: "/images/avatar.png")}" alt="User avatar" width="32"
                                         height="32"/>
                                </div>

                                <div class="user-extra">
                                    <a href="#">${answer.content.author.nickname}</a>
                                    <br>
                                    <span class="reputation">${answer.content.author.reputation}</span>
                                    <span class="silver-medals">3</span>
                                    <span class="bronze-medals">21</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="large-12 small-12 columns">
                        <g:each var="comment" in="${answer.comments}">
                            <div class="question-comment">
                                <p class="text-justify">${comment.content.text}</p>
                                <span class="secondary label">by <a
                                        href="#">${comment.content.author.nickname}</a> ${comment.content.date}</span>
                            </div>
                        </g:each>
                        <div class="comment-editor">
                            <a class="add-comment" id="${answer.id}"><g:message
                                    code="voor.topic.add.comment"/></a>

                            <form class="add-comment-form hide" id="${answer.id}">
                                <div class="row">
                                    <div class="large-12 columns">
                                        <label><g:message code="voor.topic.your.comment"/>
                                            <textarea class="comment-text" id="${answer.id}"></textarea>
                                        </label>
                                        <button id="${answer.id}" type="button"
                                                class="add-comment-button tiny button right" disabled><g:message
                                                code="voor.topic.post.your.comment"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</g:each>

<form id="add-answer-form">
    <div class="row">
        <div class="large-12 columns">
            <label><g:message code="voor.topic.your.answer"/>
                <textarea id="answer-text" class="answer-textarea"></textarea>
            </label>
            <button id="add-answer-button" type="button" class="tiny button right" disabled><g:message
                    code="voor.topic.post.your.answer"/></button>
        </div>
    </div>
</form>

<!-- Comment handling -->
<script type="text/javascript">
    var addCommentReveal = $(".add-comment");
    var addCommentForm = $(".add-comment-form");
    var commentText = $(".comment-text");
    var addCommentButton = $(".add-comment-button");

    addCommentReveal.click(function () {
        var splitId = $(this).attr("id").split('-');
        var id = splitId[splitId.length - 1];
        $(this).addClass("hide");
        $("#" + id + ".add-comment-form").removeClass("hide");
    });

    commentText.keyup(function () {
        if ($(this).val() !== "") {
            $("#" + $(this).attr("id") + ".add-comment-button").removeAttr("disabled");
        } else {
            $("#" + $(this).attr("id") + ".add-comment-button").attr("disabled", "disabled");
        }
    });

    addCommentForm.submit(function (event) {
        event.preventDefault();
    });

    addCommentButton.click(function () {
        var id = $(this).attr("id");
        var commentText = $("#" + id + ".comment-text").val();

        if (commentText !== "") {
            $.ajax({
                url: "${createLink(controller: 'topic', action: 'comment')}/" + id,
                data: {
                    text: commentText
                },
                type: "POST"
            }).success(function () {
                window.location.href = "${createLink(controller: 'topic', action: 'view', id: topic.id)}";
            })
        }
    });
</script>

<!-- Votes handling -->
<script type="text/javascript">
    var upvote = $(".upvote");
    upvote.click(function () {
        var id = $(this).attr("id");
        $.ajax({
            url: "${createLink(controller: 'topic', action: 'upvote')}/" + id,
            type: "POST"
        }).success(function (data) {
            $("#" + id + ".vote-count").text(data);
            $("#" + id + ".upvote").addClass("active");
            $("#" + id + ".downvote").removeClass("active");
        })
    });

    var downvote = $(".downvote");
    downvote.click(function () {
        var id = $(this).attr("id");
        $.ajax({
            url: "${createLink(controller: 'topic', action: 'downvote')}/" + id,
            type: "POST"
        }).success(function (data) {
            $("#" + id + ".vote-count").text(data);
            $("#" + id + ".downvote").addClass("active");
            $("#" + id + ".upvote").removeClass("active");
        })
    });
</script>

<!-- Answer handling -->
<script type="text/javascript">
    var addAnswerForm = $("#add-answer-form");
    var addAnswerButton = $("#add-answer-button");
    var answerText = $("#answer-text");

    addAnswerButton.submit(function (event) {
        event.preventDefault();
    });

    answerText.keyup(function () {
        if (answerText.val() !== "") {
            addAnswerButton.removeAttr("disabled");
        } else {
            addAnswerButton.attr("disabled", "disabled");
        }
    });

    addAnswerButton.click(function () {
        if (answerText.val() !== "") {
            $.ajax({
                url: "${createLink(controller: 'topic', action: 'answer', id: topic.id)}",
                data: {
                    text: answerText.val()
                },
                type: 'POST'
            }).success(function () {
                window.location.href = "${createLink(controller: 'topic', action: 'view', id: topic.id)}";
            });
        }
    });
</script>

</body>
</html>
