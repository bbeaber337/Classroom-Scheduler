	<%@ page import="com.scheduler.services.*" %>
	<%@ page import="com.scheduler.valueObjects.*" %>
	<%@ page import="com.scheduler.jsp.*" %>
	<%@ page import="java.util.*" %>
	<%@ page import="java.sql.*" %>
	<% 
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		HTMLServices hs = new HTMLServices(session, request, response, out); 
		adminServices as = new adminServices(session, request, response, out);
	%>
<%-- Begin Validation --%>
	<%System.out.print("Checking Login Status (classlist.jspf)\n");
	//Always going to redirct unless current session key equals the adminKey
	//Even if this is set to the userKey the page will NOT be displayed
	if(as.invalidAdmin() ){
		System.out.print(" Invalid User\n");
	} else {%>
	
	<%System.out.print("Ensuring a Semester was selected (classlist.jspf)\n");
	//Always going to redirct unless current session key equals the adminKey
	//Even if this is set to the userKey the page will NOT be displayed
	if(!as.validateSemester()){
		System.out.print(" Need to select a semester \n");
	} else {%>
<%-- End Validation --%>
	<% 	
	hs.buildClassesJSON();%>
	<% }} %>