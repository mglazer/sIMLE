<!--WARNING: This file is maintained by ROO! IT WILL BE OVERWRITTEN unless you specify null	@RooWebScaffold(automaticallyMaintainView = false) in the governing controller-->
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />
<div style="width: 100%" title="List all labs">
<c:if test="${not empty labs}">
	<table width="300px">
		<tr>
		<thead>
			<th>Id</th>
			<th>Name</th>
			<th>Location</th>
			<th />
			<th />
			<th />
		</thead>
		</tr>
		<c:forEach items="${labs}" var="lab">
			<tr>
				<td>${lab.id}</td>
				<td>${fn:substring(lab.name, 0, 10)}</td>
				<td>${fn:substring(lab.location, 0, 10)}</td>
				<td><c:url value="/lab/${lab.id}" var="show_form_url" /> <c:url
					value="/static/images/show.png" var="show_image_url" /> 
					<form:form action="${show_form_url}" method="GET">
					<input alt="Show lab" src="${show_image_url}" title="Show lab"
						type="image" value="Show lab" />
					</form:form>
				</td>
				<td>
					<c:url value="/lab/${lab.id}/edit" var="update_form_url" />
					<c:url value="/static/images/update.png" var="update_image_url" /> 
					<form:form		action="${update_form_url}" method="GET">
						<input alt="Update lab" src="${update_image_url}"
							title="Update lab" type="image" value="Update lab" />
					</form:form>
				</td>
				<td>
					<c:url value="/lab/${lab.id}" var="delete_form_url" /> <c:url
					value="/static/images/delete.png" var="delete_image_url" /> 
					<form:form	action="${delete_form_url}" method="DELETE">
					<input alt="Delete lab" src="${delete_image_url}"
						title="Delete lab" type="image" value="Delete lab" />
					</form:form>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if> <c:if test="${empty labs}">No labs found.</c:if></div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />
