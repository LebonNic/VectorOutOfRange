<div class="panel">
    <voor:bounty value="5"/>

    <div class="row">
        <div class="large-1 small-12 columns">
            <!-- Up/Down voter -->
            <voor:voter post="${topic.question}"/>
            <!-- View count on topic -->
            <voor:viewCount value="${topic.views}"/>
            <!-- Edit and delete buttons -->
            <voor:editPostButton post="${topic.question}"/>
            <voor:deletePostButton post="${topic.question}"/>
        </div>

        <div class="large-11 small-12 columns">
            <a href="${createLink(controller: 'topic', action: 'view', id: topic.id)}"><h5>${topic.title}</h5></a>

            <p class="text-justify">${topic.question.content.text}</p>

            <div class="row">
                <!-- Topic's tags -->
                <div class="large-9 small-12 columns">
                    <g:each var="tag" in="${topic.tags}">
                        <voor:tag tag="${tag}"/>
                    </g:each>
                </div>

                <!-- User badge -->
                <div class="large-3 small-12 columns">
                    <voor:userBadge type="full" post="${topic.question}"/>
                </div>
            </div>

            <!-- Question's comments -->
            <voor:comments post="${topic.question}"/>
        </div>
    </div>
</div>

<!-- Topic's answers -->
<voor:answers topic="${topic}"/>

<!-- Topic comments handling -->
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

<!-- Topic answer handling -->
<script type="text/javascript">
    var addAnswerForm = $("#add-answer-form");
    var addAnswerButton = $("#add-answer-button");
    var answerText = $("#answer-text");

    addAnswerForm.submit(function (event) {
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


<!-- Question delete handling -->
<script type="text/javascript">
    var deletePostButton = $(".delete-post-button");

    deletePostButton.click(function () {
        var id = $(this).attr("id");
        console.log(id);

        $.ajax({
            url: "${createLink(controller: 'topic', action: 'delete')}/" + id,
            type: "POST"
        }).success(function() {
            if (id == ${topic.question.id}) {
                window.location.href = "${createLink(controller: 'topic', action: 'index')}";
            } else {
                window.location.href = "${createLink(controller: 'topic', action: 'view', id: topic.id)}";
            }
        });
    });
</script>