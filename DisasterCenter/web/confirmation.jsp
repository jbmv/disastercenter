<%-- 
    Document   : confirmation
    Created on : Nov 6, 2018, 9:32:26 AM
    Author     : james
--%>

<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String resid = request.getParameter("resid");
String reqid = request.getParameter("reqid");
String userid = request.getParameter("userid");
String prodid = request.getParameter("prodid");
String quantity = request.getParameter("quantity");

try {
            Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?&useSSL=false", "root", "password");
        PreparedStatement pst = conn.prepareStatement("insert into response values (?,?,?,?,?,?)");
        pst.setString(1, resid);
        pst.setString(2, quantity);
        pst.setString(3, prodid);
        pst.setString(4, reqid);
        pst.setString(5, "0");
        pst.setString(6, userid);
        pst.executeUpdate();
}
catch(Exception e){       
       out.println("Something went wrong !! Please try again"); 
       out.println(e);
   }      



%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Submission Complete</h1>
    </body>
</html>
