<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />
<div style="width: 100%" title="Create New Lab"><c:url value="/lab" var="form_url" /> 

<form:form	action="${form_url}" method="POST" modelAttribute="lab">
	<div id="lab_name">
		<label for="_name">Name:</label> 
		<form:input id="_name" maxlength="30" path="name" size="0" />
	</div>
	<div id="lab_location">
		<label for="_location">Location:</label>
		<form:input id="_location" maxlength="30" path="location" size="0" />
	</div>
	<div class="submit" id="lab_submit">
		<input id="proceed" type="submit" value="Save" />
	</div>
</form:form>

</div>

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />
