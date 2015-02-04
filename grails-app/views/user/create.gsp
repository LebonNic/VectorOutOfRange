<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3><g:message code="voor.layout.sign.in"/></h3>

<form id="create-user-form">

    <div class="row collapse">
        <div class="small-3 large-2 columns">
            <span class="prefix"><g:message code="voor.user.username"/></span>
            <small id="invalid-username" class="error hide"><g:message code="voor.error.username.already.exists"/></small>
        </div>

        <div class="small-9 large-10 columns">
            <input id="username-input" type="text" placeholder="${message(code: 'voor.user.username')}"/>
        </div>
    </div>

    <div class="row collapse">
        <div class="small-3 large-2 columns">
            <span class="prefix"><g:message code="voor.user.password"/></span>
        </div>

        <div class="small-9 large-10 columns">
            <input id="password-input" type="password" placeholder="${message(code: 'voor.user.password')}"/>
        </div>
    </div>
    <div class="row collapse">
        <div class="small-3 large-2 columns">
            <span class="prefix"><g:message code="voor.confirm"/></span>
        </div>
        <div class="small-9 large-10 columns">
            <input id="password-confirm-input" type="password" placeholder="${message(code: 'voor.confirm')}"/>
        </div>
    </div>

    <div class="row collapse">
        <div class="small-3 large-2 columns">
            <span class="prefix"><g:message code="voor.user.firstname"/></span>
        </div>

        <div class="small-9 large-10 columns">
            <input id="firstname-input" type="text" placeholder="${message(code: 'voor.user.firstname')}"/>
        </div>
    </div>

    <div class="row collapse">
        <div class="small-3 large-2 columns">
            <span class="prefix"><g:message code="voor.user.lastname"/></span>
        </div>

        <div class="small-9 large-10 columns">
            <input id="lastname-input" type="text" placeholder="${message(code: 'voor.user.lastname')}"/>
        </div>
    </div>

    <button id="create-user-button" class="button small right"><g:message code="voor.layout.sign.in"/></button>
</form>

<script type="text/javascript">
    var createUserForm = $("#create-user-form");
    var createUserButton = $("#create-user-button");
    var usernameInput = $("#username-input");
    var passwordInput = $("#password-input");
    var passwordConfirmInput = $("#password-confirm-input");
    var firstnameInput = $("#firstname-input");
    var lastnameInput = $("#lastname-input");

    var invalidUsername = $("#invalid-username");

    function isFormValid() {
        return usernameInput.val() !== "" && passwordInput.val() !== "" &&
                        passwordInput.val() === passwordConfirmInput.val() &&
                        firstnameInput.val() !== "" &&
                        lastnameInput.val() !== ""
    }

    function updateForm() {
        if (isFormValid()) {
            createUserButton.removeAttr("disabled");
        } else {
            createUserButton.attr("disabled", "disabled");
        }
    }

    function resetForm() {
        invalidUsername.addClass("hide");
    }

    updateForm();

    createUserForm.submit(function(event) {
        event.preventDefault();
    });

    usernameInput.keyup(function() {
        updateForm();
    });

    passwordInput.keyup(function() {
        updateForm();
    });

    passwordConfirmInput.keyup(function() {
        updateForm();
    });

    firstnameInput.keyup(function() {
        updateForm();
    });

    lastnameInput.keyup(function() {
        updateForm();
    });

    lastnameInput.keyup(function() {
        updateForm();
    });

    createUserButton.click(function() {
        if (isFormValid()) {
            resetForm();
            $.ajax({
                url: "${createLink(controller: 'user', action: 'save')}",
                data: {
                    username: usernameInput.val(),
                    password: passwordInput.val(),
                    firstname: firstnameInput.val(),
                    lastname: lastnameInput.val()
                },
                type: "POST"
            }).success(function(data) {
                window.location.href = "${createLink(controller: 'login')}";
            }).error(function(data) {
                console.log("failed");
                invalidUsername.removeClass("hide");
            });
        }
    });
</script>
</body>
</html>
