<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.topic.edit.post"/></h3>

<form id="edit-post-form">
    <div class="row">
        <div class="large-12 small-12 columns">
            <textarea id="post-text" class="question-textarea">${post.content.text}</textarea>
        </div>
    </div>

    <div class="row">
        <button type="button" id="edit-post-button" class="button small right" disabled><g:message
                code="voor.topic.edit.post"/></button>
    </div>
</form>
<script>
    var editPostForm = $("#edit-post-form");
    var editPostButton = $("#edit-post-button");
    var postText = $("#post-text");

    editPostForm.submit(function(event) {
        event.preventDefault();
    });

    postText.keyup(function () {
        if (postText.val() !== "") {
            editPostButton.removeAttr("disabled");
        } else {
            editPostButton.attr("disabled", "disabled");
        }
    });

    postText.keyup();

    editPostButton.click(function () {
        if (postText.val() !== "") {
            $.ajax({
                url: "${createLink(controller: 'topic', action: 'save')}/" + ${post.id},
                data: {
                    text: postText.val()
                },
                type: "POST"
            }).success(function (data) {
                window.location.href = "${createLink(controller: 'topic', action: 'view')}/" + data;
            })
        }
    });
</script>
</body>
</html>
