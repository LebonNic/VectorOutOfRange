<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.tag.edit"/> <span class="label radius">${tag.name}</span></h3>

<form id="edit-tag-form">
    <div class="row">
        <div class="large-12 small-12 columns">
            <label>
                <textarea id="tag-text" class="tag-textarea">${tag.definition}</textarea>
            </label>
        </div>
        <button id="edit-tag-button" type="button" class="button tiny right"><g:message code="voor.tag.edit"/></button>
    </div>
</form>
<script type="text/javascript">
    var editTagForm = $("#edit-tag-form");
    var tagText = $("#tag-text");
    var editTagButton = $("#edit-tag-button");

    editTagForm.submit(function (event) {
        event.preventDefault();
    });

    tagText.keyup(function () {
        if (tagText.val() !== "") {
            editTagButton.removeAttr("disabled");
        } else {
            editTagButton.attr("disabled", "disabled");
        }
    });

    editTagButton.click(function () {
        if (tagText.val() !== "") {
            $.ajax({
                url: "${createLink(controller: 'tag', action: 'save', id: tag.id)}",
                data: {
                    name: "${tag.name}",
                    definition: tagText.val()
                },
                type: "POST"
            }).success(function () {
                window.location.href = "${createLink(controller: 'tag', action: 'view', id: tag.id)}";
            });
        }
    });

    // Enable button if text is not empty
    tagText.keyup();
</script>
</body>
</html>
