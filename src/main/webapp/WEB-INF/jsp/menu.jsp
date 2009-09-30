<ul id="main_menu">
	<li>
	<c:url value="/labs" var="list_lab_url"/>
	<c:url value="/static/images/labs.png" var="list_lab_image_url"/>
	<a href="${list_lab_url}"><img src="${list_lab_image_url}" alt="List Labs"/></a>
	</li>
	<li>
	<c:url value="/applications" var="list_application_url"/>
	<c:url value="/static/images/applications.png" var="list_application_image_url"/>
	<a href="${list_application_url}"><img src="${list_application_image_url}" alt="List Applications"/></a>
	</li>
</ul>
