<%@ page import="fr.isima.vectoroutofrange.PostType" %>
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
                        <span class="entry">${user.userInformation.nickname}</span>
                    </div>
                </div>

                <div class="row">
                    <div class="large-4 small-6 columns">
                        <span class="category"><g:message code="voor.user.website"/></span>
                    </div>

                    <div class="large-8 small-6 columns">
                        <span class="entry">
                            <g:if test="${user.userInformation.website}">
                                <a href="${user.userInformation.website}">${user.userInformation.website}</a>
                            </g:if>
                        </span>
                    </div>
                </div>

                <div class="row">
                    <div class="large-4 small-6 columns">
                        <span class="category"><g:message code="voor.user.location"/></span>
                    </div>

                    <div class="large-8 small-6 columns">
                        <g:if test="${user.userInformation.location}">
                            <span class="entry">${user.userInformation.location}</span>
                        </g:if>
                    </div>
                </div>
            </div>
        </div>

        <sec:ifLoggedIn>
            <g:if test="${user.id == sec.loggedInUserInfo(field: 'id').toLong()}">
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
                                <g:if test="${user.userInformation.firstName}">
                                    <span class="entry">${user.userInformation.firstName}</span>
                                </g:if>
                            </div>
                        </div>

                        <div class="row">
                            <div class="large-4 small-6 columns">
                                <span class="category"><g:message code="voor.user.lastname"/></span>
                            </div>

                            <div class="large-8 small-6 columns">
                                <g:if test="${user.userInformation.lastName}">
                                    <span class="entry">${user.userInformation.lastName}</span>
                                </g:if>
                            </div>
                        </div>
                    </div>
                </div>
            </g:if>
        </sec:ifLoggedIn>
    </div>

    <div class="large-6 small-12 columns">
        <blockquote>
            <g:if test="${user.userInformation.about}">
                ${user.userInformation.about}
            </g:if>
            <g:else>
                <g:message code="voor.user.about.me.empty"/>
            </g:else>
        </blockquote>

        <voor:editUserButton user="${user}"/>
        <voor:deleteUserButton user="${user}"/>

    </div>
</div>

<voor:badges user="${user}"/>

<voor:timeline user="${user}"/>

<script type="text/javascript">
    var deleteUserButton = $("#delete-user-button");
    deleteUserButton.click(function () {
        $.ajax({
            url: "${createLink(controller: 'user', action: 'delete', id: user.id)}",
            type: "POST"
        }).success(function () {
            window.location.href = "${createLink(controller: 'user', action: 'index')}";
        });
    });
</script>

<asset:javascript src="timeline.js"/>