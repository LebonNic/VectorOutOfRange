<%@ page import="fr.isima.vectoroutofrange.VoteType" %>
<div class="voter">
    <sec:ifAllGranted roles="ROLE_VOTE_UP">
        <a id="${post.id}" class="upvote${vote?.type == VoteType.UPVOTE ? ' active' : ''}">
            <i class="fa fa-arrow-up"></i>
        </a>
    </sec:ifAllGranted>
    <span id="${post.id}" class="vote-count">${post.score}</span>
    <sec:ifAllGranted roles="ROLE_VOTE_DOWN">
        <a id="${post.id}" class="downvote${vote?.type == VoteType.DOWNVOTE ? ' active' : ''}">
            <i class="fa fa-arrow-down"></i>
        </a>
    </sec:ifAllGranted>
</div>