<%@ page import="fr.isima.vectoroutofrange.VoteType" %>
<div class="voter">
    <a id="${post.id}" class="upvote${vote?.type == VoteType.UPVOTE ? ' active' : ''}">
        <i class="fa fa-arrow-up"></i>
    </a>
    <span id="${post.id}" class="vote-count">${post.score}</span>
    <a id="${post.id}" class="downvote${vote?.type == VoteType.DOWNVOTE ? ' active' : ''}">
        <i class="fa fa-arrow-down"></i>
    </a>
</div>