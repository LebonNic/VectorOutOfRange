<div class="panel">
    <div class="row">
        <div class="large-1 small-12 columns">
            <voor:voter post="${answer}"/>
            <voor:editPostButton post="${answer}"/>
            <voor:deletePostButton post="${answer}"/>
            <voor:chooseBestAnswer post="${answer}"/>
        </div>

        <div class="large-11 small-12 columns">
            <markdown:renderHtml>${answer.content.text}</markdown:renderHtml>

            <div class="row">
                <div class="large-3 small-12 columns right">
                    <voor:userBadge post="${answer}" type="full"/>
                </div>
            </div>

            <voor:comments post="${answer}"/>
        </div>
    </div>
</div>