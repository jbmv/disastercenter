<%-- 
    Document   : newResponse
    Created on : Nov 19, 2018, 12:05:29 AM
    Author     : james
--%>


<%@page import="DisasterCenter.Response"%>
<%
	//import java objects from HTTP session
	Response newResponse = (Response) session.getAttribute("newResponse");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<title>Disaster Center</title>
<body>

	<!-- Sidebar -->
	<jsp:include page="sidebar.jsp"></jsp:include>

	<!-- Page Content -->
	<div style="margin-left: 15%">
		<div class="w3-container w3-teal">
			<h1>You donated more than was requested</h1>
			</div>
			<h1>Please send the requested quantity to:</h1>
			<h1>The remaining quantity has been added as a new custom donation</h1>

	</div>
</body>
</html>