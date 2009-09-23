<!--WARNING: This file is maintained by ROO! IT WILL BE OVERWRITTEN unless you specify null	@RooWebScaffold(automaticallyMaintainView = false) in the governing controller-->
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">dojo.require("dijit.TitlePane");</script>
<div dojoType="dijit.TitlePane" style="width: 100%" title="Find Hosts By Lab">
<c:url value="/host/find/ByLab" var="form_url"/>
<form:form action="${form_url}" method="GET">
<div id="roo_host_lab">
<c:if test="${not empty labs}">
<label for="_lab">Lab:</label>
<select name="lab" style="width:250px">
<c:forEach items="${labs}" var="lab">
<option value="${lab.id}">${lab}</option>
</c:forEach>
</select>
</c:if>
</div>
<br/>
<div class="submit" id="roo_host_submit">
<script type="text/javascript">Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:'proceed', event:'onclick'}));</script>
<input id="proceed" type="submit" value="Find"/>
</div>
</form:form>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
