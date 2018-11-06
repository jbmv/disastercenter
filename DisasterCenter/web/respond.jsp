<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : respond
    Created on : Nov 5, 2018, 5:17:43 PM
    Author     : james
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1></h1>

    </body>
</html>

<%@ page import ="java.sql.*" %>
<%
   String reqId = request.getParameter("Select");
   out.print(reqId);
%>
