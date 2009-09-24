<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />
<div style="width: 100%" title="List all applications">
<c:if test="${not empty applications}">
	<table width="300px">
		<tr>
		<thead>
			<th>Id</th>
			<th>Name</th>
			<th>Added By</th>
			<th>Notes</th>
			<th />
			<th />
			<th />
		</thead>
		</tr>
		<c:forEach items="${applications}" var="application">
			<tr>
				<td>${application.id}</td>
				<td>${fn:substring(application.name, 0, 10)}</td>
				<td>${fn:substring(application.addedByUsername, 0, 10)}</td>
				<td>${fn:substring(application.notes, 0, 10)}</td>
				<td><c:url value="/application/${application.id}" var="show_form_url" /> <c:url
					value="/static/images/show.png" var="show_image_url" /> 
					<form:form action="${show_form_url}" method="GET">
					<input alt="Show application" src="${show_image_url}" title="Show application"
						type="image" value="Show application" />
					</form:form>
				</td>
				<td>
					<c:url value="/application/${application.id}/edit" var="update_form_url" />
					<c:url value="/static/images/update.png" var="update_image_url" /> 
					<form:form		action="${update_form_url}" method="GET">
						<input alt="Update application" src="${update_image_url}"
							title="Update application" type="image" value="Update application" />
					</form:form>
				</td>
				<td>
					<c:url value="/application/${application.id}" var="delete_form_url" /> <c:url
					value="/static/images/delete.png" var="delete_image_url" /> 
					<form:form	action="${delete_form_url}" method="DELETE">
					<input alt="Delete application" src="${delete_image_url}"
						title="Delete application" type="image" value="Delete application" />
					</form:form>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if> 

<c:if test="${empty applications}">No applications found.</c:if></div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />