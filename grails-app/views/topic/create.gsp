<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.layout.ask.question"/></h3>

<form id="post-question-form">
    <div class="row collapse prefix-radius">
        <div class="large-1 small-2 columns">
            <span class="prefix"><g:message code="voor.topic.title"/></span>
        </div>

        <div class="large-11 small-10 columns">
            <input id="question-title" type="text" placeholder="<g:message code="voor.topic.title"/>"/>
        </div>
    </div>

    <div class="row">
        <div class="large-12 small-12 columns">
            <textarea id="question-text" class="question-textarea"></textarea>
        </div>
    </div>

    <div class="row collapse">
        <div class="small-10 large-11 columns">
            <input id="tag-name-input" type="text" placeholder="<g:message code="voor.topic.tag.name"/>"/>
        </div>

        <div class="small-2 large-1 columns">
            <a class="button prefix" id="add-tag-button" disabled><g:message code="voor.topic.add.tag"/></a>
        </div>
    </div>

    <div class="row">
        <ul class="large-6 small-12 columns inline-list no-bullet" id="tag-container">

        </ul>

        <div class="large-6 small-12 columns">
            <button type="button" id="post-question-button" class="button small right" disabled><g:message
                    code="voor.topic.post.your.question"/></button>
        </div>
    </div>
</form>
<script>
    var questionTitle = $("#question-title");
    var questionText = $("#question-text");
    var tagNameInput = $("#tag-name-input");
    var addTagButton = $("#add-tag-button");
    var tagContainer = $("#tag-container");
    var postQuestionForm = $("#post-question-form");
    var postQuestionButton = $("#post-question-button");
    var tags = [];

    function tagExists(name) {
        for (var i = 0, length = tags.length; i < length; ++i) {
            if (tags[i] === name) {
                return true;
            }
        }
        return false;
    }

    function isQuestionValid() {
        return questionTitle.val() !== "" && questionText.val() !== "";
    }

    function updateSubmitState() {
        if (!isQuestionValid()) {
            postQuestionButton.attr("disabled", "disabled");
        } else {
            postQuestionButton.removeAttr("disabled");
        }
    }

    questionTitle.keyup(function () {
        updateSubmitState();
    });

    questionText.keyup(function () {
        updateSubmitState();
    });

    tagNameInput.keyup(function (event) {
        var tagName = tagNameInput.val();
        if (tagName === "" || tagExists(tagName)) {
            addTagButton.attr("disabled", "disabled");
        } else {
            addTagButton.removeAttr("disabled");
            if (event.which === 13) {
                addTagButton.click();
                addTagButton.attr("disabled", "disabled");
            }
        }
    });

    addTagButton.click(function () {
        var tagName = tagNameInput.val();
        if (tagName !== "" && !tagExists(tagName)) {
            tags.push(tagName);
            var tag = $("<li>")
                    .append($("<a>")
                            .append($("<span>", {class: "label radius"}).text(tagName)));

            // Remove tag on click
            tag.click(function () {
                for (var i = 0, length = tags.length; i < length; ++i) {
                    if (tags[i] === tagName) {
                        tags.splice(i, 1);
                    }
                }
                tag.remove();
            });

            tagNameInput.val("");
            tagContainer.append(tag);
        }
    });

    postQuestionForm.submit(function (event) {
        event.preventDefault();
    });

    postQuestionButton.click(function () {
        if (isQuestionValid()) {
            $.ajax({
                url: "${createLink(controller: 'topic', action: 'save')}",
                data: {
                    title: questionTitle.val(),
                    text: questionText.val(),
                    tags: tags
                },
                type: 'POST'
            }).success(function(data) {
                window.location.href = "${createLink(controller: 'topic', action: 'view')}/" + data;
            })
        }
    });
</script>
</body>
</html>
