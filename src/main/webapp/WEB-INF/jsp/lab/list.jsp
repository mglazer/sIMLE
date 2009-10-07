<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />

<script type="text/javascript">
	$(document).ready(function() {

	});

	function mapInitialized()
	{
		var map = getFlexApp("NodeMap");
		<c:forEach items="${labs}" var="lab">
		map.addMarker("${lab.id}", ${lab.latitude}, ${lab.longitude});
		</c:forEach>
	}
		
</script>
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
			<th />
			<th />
		</thead>
		</tr>
		<c:forEach items="${labs}" var="lab">
			<tr>
				<td>${lab.id}</td>
				<td>${fn:substring(lab.name, 0, 10)}</td>
				<td>${fn:substring(lab.latitude, 0, 5)},${fn:substring(lab.longitude, 0, 5)}</td>
				<td><c:url value="/lab/${lab.id}" var="show_form_url" /> <c:url
					value="/static/images/show.png" var="show_image_url" /> 
					<form:form action="${show_form_url}" method="GET">
					<input alt="Show lab" src="${show_image_url}" title="Show lab"
						type="image" value="Show lab" />
					</form:form>
				</td>
				<td><c:url value="/lab/${lab.id}.xls" var="show_xls_url"/>
					<c:url value="/static/images/file_xls.png" var="show_xls_image_url"/>
					<form:form action="${show_xls_url}" method="GET">
					<input alt="Show XLS" src="${show_xls_image_url}" title="Show XLS" type="image" value="Show XLS"/>
					</form:form>
				</td>
				<td><c:url value="/lab/${lab.id}.iml" var="show_iml_url">
						<c:param name="pretty" value="true"/>
					</c:url>
					<c:url value="/static/images/iml.png" var="show_iml_image_url" />
					<form:form action="${show_iml_url}" method="GET">
					<input alt="Show IML" src="${show_iml_image_url}" title="Show IML" type="image" value="Show IML"/>
					</form:form>
				</td>
				<security:authorize ifAllGranted="ROLE_GROUP_ADMIN">
				<td>
					<c:url value="/lab/${lab.id}" var="update_form_url" />
					<c:url value="/static/images/update.png" var="update_image_url" /> 
					<form:form		action="${update_form_url}" method="GET">
						<input alt="Update lab" src="${update_image_url}"
							title="Update lab" type="image" value="Update lab" />
					</form:form>
				</td>
				</security:authorize>
				<%-- 
				<td>
					<c:url value="/lab/${lab.id}" var="delete_form_url" /> <c:url
					value="/static/images/delete.png" var="delete_image_url" /> 
					<form:form	action="${delete_form_url}" method="DELETE">
					<input alt="Delete lab" src="${delete_image_url}"
						title="Delete lab" type="image" value="Delete lab" />
					</form:form>
				</td>
				--%>
			</tr>
		</c:forEach>
	</table>
</c:if> <c:if test="${empty labs}">No labs found.</c:if></div>

<security:authorize ifAllGranted="ROLE_GROUP_ADMIN">
<div class="buttons">
	<c:url value="/lab/new" var="new_lab_url"/>
	<a href="${new_lab_url}" class="create">Create New Lab</a>
</div>
</security:authorize>

<jsp:directive.include file="/WEB-INF/jsp/embed_google_map.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />
