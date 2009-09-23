<!--WARNING: This file is maintained by ROO! IT WILL BE OVERWRITTEN unless you specify null	@RooWebScaffold(automaticallyMaintainView = false) in the governing controller-->
<jsp:directive.include file="/WEB-INF/jsp/includes.jsp"/>
<jsp:directive.include file="/WEB-INF/jsp/header.jsp"/>
<script type="text/javascript">dojo.require("dijit.TitlePane");dojo.require("dijit.form.FilteringSelect");dojo.require("dijit.form.MultiSelect");</script>
<div dojoType="dijit.TitlePane" style="width: 100%" title="Update Host">
<c:url value="/host/${host.id}" var="form_url"/>
<form:form action="${form_url}" method="PUT" modelAttribute="host">
<div id="roo_host_lab">
<c:if test="${not empty labs}">
<label for="_lab">Lab:</label>
<form:select cssStyle="width:250px" id="_lab" path="lab">
<form:options itemValue="id" items="${labs}"/>
</form:select>
<script type="text/javascript">Spring.addDecoration(new Spring.ElementDecoration({elementId : '_lab', widgetType: 'dijit.form.FilteringSelect', widgetAttrs : {hasDownArrow : true}})); </script>
</c:if>
</div>
<br/>
<div id="roo_host_applications">
<c:if test="${not empty applications}">
<label for="_applications">Applications:</label>
<form:select cssStyle="width:250px" id="_applications" path="applications">
<form:options itemValue="id" items="${applications}"/>
</form:select>
<script type="text/javascript">Spring.addDecoration(new Spring.ElementDecoration({elementId : '_applications', widgetType: 'dijit.form.MultiSelect', widgetAttrs: {}})); </script>
</c:if>
</div>
<br/>
<div id="roo_host_name">
<label for="_name">Name:</label>
<form:input cssStyle="width:250px" id="_name" maxlength="30" path="name" size="0"/>
<br/>
<form:errors cssClass="errors" id="_name" path="name"/>
<script type="text/javascript">Spring.addDecoration(new Spring.ElementDecoration({elementId : "_name", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {promptMessage: "Enter Name", invalidMessage: "", required : false}})); </script>
</div>
<br/>
<div id="roo_host_dnsNames">
<label for="_dnsNames">Dns Names:</label>
<form:input cssStyle="width:250px" id="_dnsNames" maxlength="30" path="dnsNames" size="0"/>
<br/>
<form:errors cssClass="errors" id="_dnsNames" path="dnsNames"/>
<script type="text/javascript">Spring.addDecoration(new Spring.ElementDecoration({elementId : "_dnsNames", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {promptMessage: "Enter Dns Names", invalidMessage: "", required : false}})); </script>
</div>
<br/>
<div id="roo_host_addressIP">
<label for="_addressIP">Address I P:</label>
<form:input cssStyle="width:250px" id="_addressIP" maxlength="30" path="addressIP" size="0"/>
<br/>
<form:errors cssClass="errors" id="_addressIP" path="addressIP"/>
<script type="text/javascript">Spring.addDecoration(new Spring.ElementDecoration({elementId : "_addressIP", widgetType : "dijit.form.ValidationTextBox", widgetAttrs : {promptMessage: "Enter Address I P (required)", invalidMessage: "", required : true}})); </script>
</div>
<br/>
<div class="submit" id="roo_host_submit">
<script type="text/javascript">Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:'proceed', event:'onclick'}));</script>
<input id="proceed" type="submit" value="Update"/>
</div>
<form:hidden id="_id" path="id"/>
<form:hidden id="_version" path="version"/>
</form:form>
</div>
<jsp:directive.include file="/WEB-INF/jsp/footer.jsp"/>
