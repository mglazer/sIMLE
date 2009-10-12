<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<h3>Welcome to sIMLE <security:authentication property="principal.username"/></h3>

<c:url value="/labs" var="list_lab_url"/>
<c:url value="/applications" var="list_application_url"/>
<p>
	You can view your <a href="${list_lab_url}">labs</a> or list of available <a href="${list_application_url}">applications</a>.
</p>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
