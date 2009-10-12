<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />

<div style="width: 100%" title="Edit User"><c:url value="/user" var="form_url" /> 



<form:form	action="${form_url}" method="PUT" modelAttribute="user">
	<div id="user_name">
		<label for="_name">Name:</label> 
		<form:input id="_username" maxlength="30" path="username" size="0" />
		<form:errors path="username" cssClass="errors"/>
	</div>
	<div id="user_password">
		<label for="_password">Password:</label>
		<form:password id="_password" path="password" size="0"/>
		<form:errors path="password" cssClass="errors"/>
	</div>
	<div id="user_enabled">
		<label for="_enabled">Enabled:</label>
		<form:checkbox id="_enabled" checked="true" path="enabled"/>
		<form:errors path="enabled" cssClass="errors"/>
	</div>
	<form:hidden path="version"/>
	<div class="submit" id="user_submit">
		<input id="proceed" type="submit" value="Save" />
	</div>
</form:form>

</div>

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />
