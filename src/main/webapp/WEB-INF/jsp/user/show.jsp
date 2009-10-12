<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>


<div style="width: 100%" title="Show User">

<c:if test="${not empty user}">
	<h2>${user.username}</h2>
	
	<dl>
		<dt>Belongs to group:</dt><dd>${user.group.groupName}</dd>
		<dt>Enabled:</dt><dd>${user.enabled}</dd>
	</dl>
	
</c:if>
<c:if test="${empty user}">No user found with this id.</c:if>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
