<ul id="main_menu">
	<li>
	<c:url value="/labs" var="list_lab_url"/>
	<c:url value="/static/images/labs.png" var="list_lab_image_url"/>
	<a href="${list_lab_url}"><img src="${list_lab_image_url}" alt="List Labs"/><span>Labs</span></a>
	</li>
	<li>
	<c:url value="/applications" var="list_application_url"/>
	<c:url value="/static/images/applications.png" var="list_application_image_url"/>
	<a href="${list_application_url}"><img src="${list_application_image_url}" alt="List Applications"/><span>Applications</span></a>
	</li>
	<security:authorize ifAllGranted="ROLE_GROUP_ADMIN">
	<li>
	<c:url value="/users" var="list_users_url"/>
	<c:url value="/static/images/user.png" var="list_users_image_url"/>
	<a href="${list_users_url}"><img src="${list_users_image_url}" alt="List Users"/><span>Users</span></a>
	</li>
</security:authorize>
</ul>
