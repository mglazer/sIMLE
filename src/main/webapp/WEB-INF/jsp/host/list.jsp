<!--WARNING: This file is maintained by ROO! IT WILL BE OVERWRITTEN unless you specify null	@RooWebScaffold(automaticallyMaintainView = false) in the governing controller-->
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">dojo.require("dijit.TitlePane");</script>
<div dojoType="dijit.TitlePane" style="width: 100%" title="List all Hosts">
<c:if test="${not empty hosts}">
<table width="300px">
<tr>
<thead>
<th>Id</th>
<th>Lab</th>
<th>Applications</th>
<th>Name</th>
<th>Dns Names</th>
<th>Address I P</th>
<th/>
<th/>
<th/>
</thead>
</tr>
<c:forEach items="${hosts}" var="host">
<tr>
<td>${host.id}</td>
<td>${fn:substring(host.lab, 0, 10)}</td>
<td>${fn:length(host.applications)}</td>
<td>${fn:substring(host.name, 0, 10)}</td>
<td>${fn:substring(host.dnsNames, 0, 10)}</td>
<td>${fn:substring(host.addressIP, 0, 10)}</td>
<td>
<c:url value="/host/${host.id}" var="show_form_url"/>
<c:url value="/static/images/show.png" var="show_image_url"/>
<form:form action="${show_form_url}" method="GET">
<input alt="Show host" src="${show_image_url}" title="Show host" type="image" value="Show host"/>
</form:form>
</td>
<td>
<c:url value="/host/${host.id}/form" var="update_form_url"/>
<c:url value="/static/images/update.png" var="update_image_url"/>
<form:form action="${update_form_url}" method="GET">
<input alt="Update host" src="${update_image_url}" title="Update host" type="image" value="Update host"/>
</form:form>
</td>
<td>
<c:url value="/host/${host.id}" var="delete_form_url"/>
<c:url value="/static/images/delete.png" var="delete_image_url"/>
<form:form action="${delete_form_url}" method="DELETE">
<input alt="Delete host" src="${delete_image_url}" title="Delete host" type="image" value="Delete host"/>
</form:form>
</td>
</tr>
</c:forEach>
</table>
</c:if>
<c:if test="${empty hosts}">No Hosts found.</c:if>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
