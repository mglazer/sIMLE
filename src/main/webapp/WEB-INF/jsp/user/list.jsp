<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />

<div style="width: 100%" title="List all labs">
<c:if test="${not empty users}">
	<table width="300px">
		<tr>
		<thead>
			<th>Name</th>
			<th>Group</th>
			<th />
			<th />
			<th />
			<th />
			<th />
		</thead>
		</tr>
		<c:forEach items="${users}" var="user">
			<c:if test="${user.enabled}">
					<c:url value="/static/images/enabled.png" var="status_image_url"/>
					<c:set var="status" value="Enabled"/>
			</c:if>
			<c:if test="${not user.enabled}">
				<c:url value="/static/images/disabled.png" var="status_image_url"/>
				<c:set var="status" value="Disabled"/>
			</c:if>
			<tr>
				<td><img src="${status_image_url}" alt="${status}"/>${user.username}</td>
				<td>${user.group.groupName}</td>
				<td><c:url value="/user/${user.id}" var="show_form_url" /> <c:url
					value="/static/images/show.png" var="show_image_url" /> 
					<form:form action="${show_form_url}" method="GET">
					<input alt="Show User" src="${show_image_url}" title="Show Iser"
						type="image" value="Show User" />
					</form:form>
				</td>
				<security:authorize ifAllGranted="ROLE_GROUP_ADMIN">
				<td>
					<c:url value="/user/${user.id}/edit" var="update_form_url" />
					<c:url value="/static/images/update.png" var="update_image_url" /> 
					<form:form		action="${update_form_url}" method="GET">
						<input alt="Update user" src="${update_image_url}"
							title="Update user" type="image" value="Update user" />
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
</c:if> <c:if test="${empty users}">No Users found. This is a problem because if no users exist, then how do you?.</c:if></div>

<security:authorize ifAllGranted="ROLE_GROUP_ADMIN">
<div class="buttons">
	<c:url value="/user/new" var="new_user_url"/>
	<a href="${new_user_url}" class="create">Create New User</a>
</div>
</security:authorize>

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />
