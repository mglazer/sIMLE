
	  <table class="footer">
	    <tr>
	      <td><a href="<c:url value="/" />">Home</a></td>
	      <td align="right">
	      <% if (request.getUserPrincipal() != null) { %>
	      <a href="<c:url value="/static/j_spring_security_logout" />">Logout</a></td>
	      <% } %>
	    </tr>
	  </table>

	</div>
  </div>
</body>

</html>
