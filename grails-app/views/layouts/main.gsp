<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="${message(code: "voor.application.name")}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
    <g:layoutHead/>
</head>

<body>
<div class="off-canvas-wrap" data-offcanvas>
    <div class="inner-wrap">
        <nav class="tab-bar">
            <section class="left-small">
                <a class="left-off-canvas-toggle menu-icon" href="#"><span></span></a>
            </section>
            <section class="right tab-bar-section">
                <a href="${createLink(uri: '/')}"><h1><g:message code="voor.application.name"/></h1></a>
            </section>
        </nav>

        <aside class="left-off-canvas-menu">
            <ul class="off-canvas-list">
                <li><label><g:message code="voor.layout.menu"/></label></li>
                <sec:access expression="hasRole('ROLE_CREATE_POST')">
                <li class="has-submenu"><a href="#"><g:message code="voor.layout.questions"/></a>
                    <ul class="left-submenu">
                        <li class="back"><a href="#"><g:message code="voor.layout.back"/></a></li>
                        <li><a href="${createLink(controller: 'topic', action: 'create')}"><g:message code="voor.layout.ask.question"/></a></li>
                        <li><a href="${createLink(controller: 'topic')}"><g:message code="voor.layout.browse.questions"/></a></li>
                    </ul>
                </li>
                </sec:access>
                <sec:noAccess expression="hasRole('ROLE_CREATE_POST')">
                    <li><a href="${createLink(controller: 'topic')}"><g:message code="voor.layout.browse.questions"/></a></li>
                </sec:noAccess>
                <li><a href="${createLink(controller: 'tag', action: 'index')}"><g:message code="voor.layout.tags"/></a></li>
                <li><a href="${createLink(controller: 'user', action: 'index')}"><g:message code="voor.layout.users"/></a></li>
                <li><a href="${createLink(controller: 'badge', action: 'index')}"><g:message code="voor.layout.badges"/></a></li>
                <li><a href="${createLink(controller: 'permission', action: 'index')}"><g:message code="voor.layout.permissions"/></a></li>
                <li><label><g:message code="voor.layout.profile"/></label></li>
                <sec:ifLoggedIn>
                    <li><a href="${createLink(controller: 'user', action: 'view', id: sec.loggedInUserInfo(field: 'id'))}"><sec:loggedInUserInfo field="username"/></a></li>
                    <li><a id="logout-button"><g:message code="voor.layout.log.out"/></a></li>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <li><a href="${createLink(controller: 'login')}"><g:message code="voor.layout.log.in"/></a></li>
                    <li><a href="${createLink(controller: 'user', action: 'create')}"><g:message code="voor.layout.sign.in"/></a></li>
                </sec:ifNotLoggedIn>
            </ul>
        </aside>
        <section class="container">
            <div class="row">
                <g:layoutBody/>
            </div>
        </section>
        <a class="exit-off-canvas"></a>
    </div>
</div>
<script>
    $(document).foundation();
    $(".inner-wrap").css("minHeight", $("body").height());
    $("#logout-button").click(function() {
        $.ajax({
            url: "${createLink(controller: 'logout')}",
            type: 'POST'
        }).success(function() {
            window.location.href = "${createLink(uri: '/')}";
        })
    });
</script>
</body>
</html>
