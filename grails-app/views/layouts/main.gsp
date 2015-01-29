<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="vector::out_of_range"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
    <asset:stylesheet src="application.css"/>
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
                <a href="index.html"><h1>vector::out_of_range</h1></a>
            </section>
        </nav>

        <aside class="left-off-canvas-menu">
            <ul class="off-canvas-list">
                <li><label>Menu</label></li>
                <li class="has-submenu"><a href="#">Questions</a>
                    <ul class="left-submenu">
                        <li class="back"><a href="#">Back</a></li>
                        <li><a href="question_create_view.html">Ask Question</a></li>
                        <li><a href="${createLink(controller: 'topic')}">Browse Questions</a></li>
                    </ul>
                </li>
                <li><a href="tag_list_view.html">Tags</a></li>
                <li><a href="user_list_view.html">Users</a></li>
                <li><a href="badge_list_view.html">Badges</a></li>
                <li><label>Profile</label></li>
                <li><a href="user_view.html">Dramloc</a></li>
                <li><a href="#">Disconnect</a></li>
            </ul>
        </aside>
        <section class="container">
            <g:layoutBody/>
        </section>
        <a class="exit-off-canvas"></a>
    </div>
</div>

<asset:javascript src="application.js"/>
</body>
</html>
