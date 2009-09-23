<ul id="roo_menu">
	<li id="web_mvc_jsp_host_category">
	<h2>Lab</h2>
	<ul>
		<li id="web_mvc_jsp_create_lab_menu_item"><c:url value="/lab/new"
			var="web_mvc_jsp_create_lab_menu_item_url" /> <a
			href="${web_mvc_jsp_create_lab_menu_item_url}">Create new Lab</a></li>
		<li id="web_mvc_jsp_list_lab_menu_item"><c:url value="/labs"
			var="web_mvc_jsp_list_lab_menu_item_url" /> <a
			href="${web_mvc_jsp_list_lab_menu_item_url}">List all Labs</a></li>
	</ul>
	</li>
	<h2>Application</h2>
	<ul>
		<li id="create_application_menu_item">
			<c:url value="/application/new" var="create_application_url"/>
			<a href="${create_application_url}">Create new Application</a>
		</li>
		<li id="list_applications_menu_item">
			<c:url value="/applications" var="list_applications_url"/>
			<a href="${list_applications_url}">List all Applications</a>
		</li>
	</ul>
</ul>
