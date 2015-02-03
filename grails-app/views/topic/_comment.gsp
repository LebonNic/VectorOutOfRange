<div class="row question-comment">
    <div class="large-1 columns">
        <voor:voter post="${comment}"/>
        <voor:editPostButton post="${comment}"/>
        <voor:deletePostButton post="${comment}"/>
    </div>

    <div class="large-11 columns">
        <p class="text-justify">${comment.content.text}</p>
        <voor:userBadge post="${comment}"/>
    </div>
</div>