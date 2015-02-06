<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet href="timeline.css"/>
</head>

<body>
<h3><g:message code="voor.layout.permissions"/></h3>

<section id="cd-timeline" class="cd-container">
    <g:each var="permission" in="${permissions}">
        <div class="cd-timeline-block">
            <div class="cd-timeline-img">
                <sec:ifLoggedIn>
                    <sec:access expression="hasRole('${permission.authority}')">
                        <i class="fa fa-check"></i>
                    </sec:access>
                    <sec:noAccess expression="hasRole('${permission.authority}')">
                        <i class="fa fa-times"></i>
                    </sec:noAccess>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <i class="fa fa-times"></i>
                </sec:ifNotLoggedIn>
            </div>

            <div class="cd-timeline-content">
                <h2><g:message code="${permission.name}"/></h2>

                <p><g:message code="${permission.description}"/></p>
                <span class="cd-date"><g:formatNumber number="${permission.requiredReputation}"/></span>
            </div>
        </div>
    </g:each>
</section>

<asset:javascript src="timeline.js"/>
</body>
</html>
