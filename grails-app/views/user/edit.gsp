<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
</head>

<body>
<h3>${user.userInformation.nickname}</h3>

<div class="row user-profile">
    <div class="large-2 small-4 columns text-center">
        <div class="user-avatar">
            <canvas id="${user.id}" class="avatar"></canvas>
        </div>
        <span class="label secondary"><g:message code="voor.user.reputation"/> ${user.userInformation.reputation}</span>
        <br/>
        <voor:medals type="platinum" user="${user}"/>
        <voor:medals type="gold" user="${user}"/>
        <voor:medals type="silver" user="${user}"/>
        <voor:medals type="bronze" user="${user}"/>
    </div>

    <div class="large-4 small-8 columns">
        <div class="row section">
            <div class="large-2 small-12 columns">
                <span class="section-name"><g:message code="voor.user.bio"/></span>
            </div>

            <div class="large-10 small-12 columns">
                <div class="row">
                    <div class="large-4 small-6 columns">
                        <span class="category"><g:message code="voor.user.nickname"/></span>
                    </div>

                    <div class="large-8 small-6 columns">
                        <input id="nickname-input" class="entry" value="${user.userInformation.nickname}"/>
                    </div>

                    <div class="large-12 small-12 columns">
                        <span id="invalid-nickname" class="label alert hide">
                            <g:message code="voor.error.nickname.already.exists"/>
                        </span>
                    </div>
                </div>

                <div class="row">
                    <div class="large-4 small-6 columns">
                        <span class="category"><g:message code="voor.user.website"/></span>
                    </div>

                    <div class="large-8 small-6 columns">
                        <input id="website-input" class="entry" value="${user.userInformation.website}"/>
                    </div>

                    <div class="large-12 small-12 columns">
                        <span id="invalid-website" class="label alert hide">
                            <g:message code="voor.error.website.invalid"/>
                        </span>
                    </div>
                </div>

                <div class="row">
                    <div class="large-4 small-6 columns">
                        <span class="category"><g:message code="voor.user.location"/></span>
                    </div>

                    <div class="large-8 small-6 columns">
                        <input id="location-input" class="entry" value="${user.userInformation.location}"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="large-2 small-12 columns">
                <span class="section-name"><g:message code="voor.user.private"/></span>
            </div>

            <div class="large-10 small-12 columns">
                <div class="row">
                    <div class="large-4 small-6 columns">
                        <span class="category"><g:message code="voor.user.username"/></span>
                    </div>

                    <div class="large-8 small-6 columns">
                        <span class="entry">${user.username}</span>
                    </div>
                </div>

                <div class="row">
                    <div class="large-4 small-6 columns">
                        <span class="category"><g:message code="voor.user.firstname"/></span>
                    </div>

                    <div class="large-8 small-6 columns">
                        <input id="firstname-input" class="entry" value="${user.userInformation.firstName}"/>
                    </div>
                </div>

                <div class="row">
                    <div class="large-4 small-6 columns">
                        <span class="category"><g:message code="voor.user.lastname"/></span>
                    </div>

                    <div class="large-8 small-6 columns">
                        <input id="lastname-input" class="entry" value="${user.userInformation.lastName}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="large-6 small-12 columns">
        <textarea id="about-input">${user.userInformation.about}</textarea>

        <button id="edit-profile-button" class="tiny button right"><g:message code="voor.user.edit"/></button>

    </div>
</div>

<script type="text/javascript">
    var editProfileButton = $("#edit-profile-button");
    var nicknameInput = $("#nickname-input");
    var websiteInput = $("#website-input");
    var locationInput = $("#location-input");
    var firstnameInput = $("#firstname-input");
    var lastnameInput = $("#lastname-input");
    var aboutInput = $("#about-input");

    var websiteInvalid = $("#invalid-website");
    var nicknameInvalid = $("#invalid-nickname");

    nicknameInput.keyup(function () {
        if (nicknameInput.val() !== "") {
            editProfileButton.removeAttr("disabled");
        } else {
            editProfileButton.attr("disabled", "disabled");
        }
    });

    nicknameInput.keyup();

    editProfileButton.click(function () {
        if (nicknameInput.val() !== "") {
            websiteInvalid.addClass("hide");
            nicknameInvalid.addClass("hide");

            $.ajax({
                url: "${createLink(controller: 'user', action: 'save', id: user.id)}",
                data: {
                    nickname: nicknameInput.val(),
                    website: websiteInput.val(),
                    location: locationInput.val(),
                    firstname: firstnameInput.val(),
                    lastname: lastnameInput.val(),
                    about: aboutInput.val()
                },
                type: "POST"
            }).success(function () {
                window.location.href = "${createLink(controller: 'user', action: 'view', id: user.id)}";
            }).error(function (data) {
                if (data.responseText === "USER_WEBSITE_INVALID") {
                    websiteInvalid.removeClass("hide");
                } else if (data.responseText === "USER_NICKNAME_INVALID") {
                    nicknameInvalid.removeClass("hide");
                }
            });
        }
    });
</script>

</body>
</html>
