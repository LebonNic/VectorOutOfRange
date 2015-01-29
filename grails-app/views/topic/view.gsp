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
                    <a class="upvote" href="#"></a>
                    <a class="downvote" href="#"></a>
                    <span class="vote-count">${topic.question.votes.size()}</span>
                </div>
            </div>

            <div class="row">
                <div class="large-12 columns">
                    <span class="secondary label radius">0 views</span>
                </div>
            </div>
        </div>

        <div class="large-11 small-12 columns">
            <a href="${createLink(controller: 'topic', action: 'view', id: topic.id)}"><h5>${topic.title}</h5></a>

            <p class="text-justify">${topic.question.content.text}</p>

            <div class="row">
                <div class="large-9 small-12 columns">
                    <g:each var="tag" in="${topic.tags}">
                        <a href="${createLink(controller: 'tag', action: 'view', id: tag.id)}"><span
                                class="label radius">${tag.name}</span></a>
                    </g:each>
                </div>

                <div class="large-3 small-12 columns">
                    <div class="question-user-info">
                        <span>Asked ${topic.question.content.date}</span>
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
                    <g:each var="comment" in="${topic.question.content}">
                        <div class="question-comment">
                            <p class="text-justify">${comment.text}</p>
                            <span class="secondary label">by <a href="#">${comment.author.nickname}</a> ${comment.date}
                            </span>
                        </div>
                    </g:each>
                    <div class="comment-editor">
                        <a class="add-comment" id="add-comment-${topic.question.id}">Add a comment</a>

                        <form id="add-comment-form-${topic.question.id}" class="hide">
                            <div class="row">
                                <div class="large-12 columns">
                                    <label>Your comment
                                        <textarea></textarea>
                                    </label>
                                    <button type="submit" class="tiny button right">Post your comment</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<h5>${topic.answers.size()} answers</h5>

<div class="panel">
    <g:each in="${topic.answers}" var="answer">
        <div class="row">
            <div class="large-1 small-12 columns">
                <div class="row">
                    <div class="large-12 columns voter">
                        <a class="upvote" href="#"></a>
                        <a class="downvote" href="#"></a>
                        <span class="vote-count">${answer.votes.size()}</span>
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
                            <span>Answered ${answer.content.date}</span>
                            <br>

                            <div class="user-profile">
                                <div class="user-avatar">
                                    <img src="${createLink(uri: "/images/avatar.png")}" alt="User avatar" width="32" height="32"/>
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
                            <a class="add-comment" id="add-comment-${answer.id}">Add a comment</a>

                            <form id="add-comment-form-${answer.id}" class="hide">
                                <div class="row">
                                    <div class="large-12 columns">
                                        <label>Your comment
                                            <textarea></textarea>
                                        </label>
                                        <button type="submit" class="tiny button right">Post your comment</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </g:each>
</div>

<form id="add-answer">
    <div class="row">
        <div class="large-12 columns">
            <label>Your answer
                <textarea class="answer-textarea"></textarea>
            </label>
            <button type="submit" class="tiny button right">Post your answer</button>
        </div>
    </div>
</form>

<script>
    $(".add-comment").on("click", function () {
        var splitId = $(this).attr("id").split('-');
        var id = splitId[splitId.length - 1];
        $(this).addClass("hide");
        $("#add-comment-form-" + id).removeClass("hide");
    });
</script>

</body>
</html>
