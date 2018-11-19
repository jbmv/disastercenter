<%@page import="DisasterCenter.Request"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : respond
    Created on : Nov 5, 2018, 5:17:43 PM
    Author     : james
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import ="java.sql.*" %>
<%@ page import ="DisasterCenter.Request" %>
<%@ page import ="DisasterCenter.Response" %>
<%
    String reqId = request.getParameter("Select");
    Request req = new Request(reqId);
    try {
        Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false", "root", "password");
        PreparedStatement pst = conn.prepareStatement("SELECT RequestId,QuantityRequested,QuantityFulfilled,Expired,User_UserID,Product_ProductID,PriorityReference_PriorityReferenceId,DisasterEvent_DisasterEventId,p.type as ProdName,d.type as DisasterType,d.location as location FROM Request r JOIN Product p on r.Product_ProductId = p.ProductId JOIN disasterevent d on r.DisasterEvent_DisasterEventId = DisasterEventId where requestid = " + reqId);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            req.setQuantityRequested(Integer.parseInt(rs.getString("QuantityRequested")));
            req.setQuantityFulfilled(Integer.parseInt(rs.getString("QuantityFulfilled")));
            req.setExpired(Boolean.parseBoolean(rs.getString("Expired")));
            req.setProductID(Integer.parseInt(rs.getString("Product_ProductId")));
            req.setProductName(rs.getString("ProdName"));
            req.setPriorityReference(Integer.parseInt(rs.getString("PriorityReference_PriorityReferenceId")));
            req.setDisasterEventID(Integer.parseInt(rs.getString("DisasterEvent_DisasterEventId")));
            req.setDisasterType(rs.getString("DisasterType"));
            req.setUserID(Integer.parseInt(rs.getString("User_UserId")));
            req.setLocation(Integer.parseInt(rs.getString("Location")));
        }

    } catch (Exception e) {
        out.print(e);
    }

    int nextRespID = 1;
    Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?&useSSL=false", "root", "password");
    PreparedStatement pst = conn.prepareStatement("SELECT max(responseid) as max from response");
    ResultSet rs = pst.executeQuery();
    if (rs.next() && rs.getString("max") != null) {
        nextRespID = 1 + Integer.parseInt(rs.getString("max"));
    }

    Response newResponse = new Response(Integer.toString(nextRespID));
    newResponse.setProductID(req.getProductID());
    newResponse.setProductName(req.getProductName());
    newResponse.setQuantitySent(req.getQuantityRequested());
    newResponse.setRequestID(req.getRequestID());
    newResponse.setUserID(req.getUserID()); // this is wrong!!!!!!!
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1></h1>
        <form action="confirmation.jsp" method="POST">
            <input type="hidden" name="resid" value="<%= newResponse.getResponseID()%>"/>
            <input type="hidden" name="reqid" value="<%= newResponse.getRequestID()%>"/>
            <input type="hidden" name="prodid" value="<%= newResponse.getProductID()%>"/>
            <input type="hidden" name="userid" value="<%= newResponse.getUserID()%>"/>
            <table border="1">
                <thead>
                    <tr>
                        <th>Location</th>
                        <th>Disaster</th>
                        <th>Product</th>
                        <th>Quantity Sending</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><% out.print(req.getLocation()); %>
                        <td><% out.print(req.getDisasterType()); %>
                        <td><% out.print(req.getProductName());%>
                        <td><input type="text" name="quantity" value="<%= req.getQuantityRequested()%>" /></td>
                    </tr>
                </tbody>
            </table>
            <input type="submit" name="submit" value="Confirm Donation" />
        </form>

        <% out.print(Request.instances);%>

    </body>
</html>


