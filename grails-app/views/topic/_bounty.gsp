<div class="ribbon-warper" title="${message(code: 'voor.topic.bounty', args: [value])}">
    <g:if test="${value >= 0}">
        <div class="ribbon">+${value}</div>
    </g:if>
    <g:else>
        <div class="ribbon">${value}</div>
    </g:else>
</div>