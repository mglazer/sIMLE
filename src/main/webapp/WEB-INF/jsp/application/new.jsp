<jsp:directive.include file="/WEB-INF/jsp/includes.jsp" />
<jsp:directive.include file="/WEB-INF/jsp/header.jsp" />
<div style="width: 100%" title="Create New Application"><c:url value="/application" var="form_url" /> 

<form:form	action="${form_url}" method="POST" modelAttribute="application">
	<form:errors path="*" cssClass="errorBox"/>
	<div id="application_name">
		<label for="_name">Name:</label> 
		<form:input id="_name" maxlength="30" path="name" size="0" />
		<form:errors path="name" cssClass="errors"/>
	</div>
	<div id="application_notes">
		<label for="_notes">Notes:</label>
		<form:textarea id="_notes" path="notes" cols="30" rows="6" />
		<form:errors path="name" cssClass="errors"/>
	</div>
	<div class="submit" id="application_submit">
		<input id="proceed" type="submit" value="Save" />
	</div>
</form:form>

</div>

<jsp:directive.include file="/WEB-INF/jsp/footer.jsp" />