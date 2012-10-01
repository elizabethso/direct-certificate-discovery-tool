<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html>
<jsp:include page="/include/headerbootstrap.jsp" flush="true">
	<jsp:param name="title"
		value="Welcome Page" />
	<jsp:param name="pgDesc"
		value="Direct Certificate Discovery Testing Tool - Welcome" />
	<jsp:param name="pgKey"
		value="Direct Certificate Discovery Testing Tool - Welcome Screen" />
	<jsp:param name="header" value="" />
</jsp:include>
 <script type="text/javascript">
 <script type="text/javascript" src="dropdown/jquery.js"></script>
 <script type="text/javascript" src="dropdown/bootstrap-dropdown.js"></script>




$(document).ready( function(){
    $("#home").removeClass("active");
 });

$(document).ready( function(){
    $("#discovery").removeClass("active");
 });


 $(document).ready( function(){
    $("#hosting").addClass("active");
 });





</script>
 
<!-- #BeginEditable "Bodytext" -->
<body>
  <div id="container">
 <h2>
 ${param.seldropDown} </br>
 </h2> 

<bean:define id="resultVal" name="CertLookUpActionForm" property="result" type="java.lang.String"/>
	<h3><bean:write name="CertLookUpActionForm" property="result" /></h3>
    <%   
      String val = "Fail: Certificate found at LDAP for dts557@onctest.org";  
    %>  

<logic:notEmpty name="CertLookUpActionForm" property="certResult">
    	<% if (resultVal.equalsIgnoreCase(val)) { %>   	
    	  <!-- Nothing -->
    	  <%} else { %>
	<h3>Verify that the discovered certificate is the intended certificate for the Direct address provided:</h3>
        <%} %>
	<br />
	
	 <textarea name="certResults" style ="width:600px" styleId="certResults" cols="200" rows="15"> 
	 	<bean:write name="CertLookUpActionForm" property="certResult" />
	 </textarea>
	</div>
</logic:notEmpty>
<a href="dns.jsp">Back to Hosting</a>
</body>

<!-- #EndEditable "Bodytext" -->
<jsp:include page="/include/footer.jsp" flush="true" />    
</html>

