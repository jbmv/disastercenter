<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : landing
    Created on : Nov 5, 2018, 2:49:31 PM
    Author     : james
--%>
<%@ page import ="java.sql.*" %>
<%@ page import ="java.lang.*" %>
<%@ page import ="DisasterCenter.Request" %>
<%
    Request.instances.clear();
    try {
        Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?&useSSL=false", "root", "password");
        PreparedStatement pst = conn.prepareStatement("SELECT RequestId,QuantityRequested,QuantityFulfilled,Expired,User_UserID,Product_ProductID,PriorityReference_PriorityReferenceId,DisasterEvent_DisasterEventId,p.type as ProdName,d.type as DisasterType,d.location as location FROM Request r JOIN Product p on r.Product_ProductId = p.ProductId JOIN disasterevent d on r.DisasterEvent_DisasterEventId = DisasterEventId");
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Request reqObj = new Request(rs.getString("RequestId"));
            // reqObj.setRequestID(Integer.parseInt(rs.getString("RequestId")));
            reqObj.setQuantityRequested(Integer.parseInt(rs.getString("QuantityRequested")));
            reqObj.setQuantityFulfilled(Integer.parseInt(rs.getString("QuantityFulfilled")));
            reqObj.setExpired(Boolean.parseBoolean(rs.getString("Expired")));
            reqObj.setProductID(Integer.parseInt(rs.getString("Product_ProductId")));
            reqObj.setProductName(rs.getString("ProdName"));
            reqObj.setPriorityReference(Integer.parseInt(rs.getString("PriorityReference_PriorityReferenceId")));
            reqObj.setDisasterEventID(Integer.parseInt(rs.getString("DisasterEvent_DisasterEventId")));
            reqObj.setDisasterType(rs.getString("DisasterType"));
            reqObj.setUserID(Integer.parseInt(rs.getString("User_UserId")));
            reqObj.setLocation(Integer.parseInt(rs.getString("Location")));
        }

    } catch (Exception e) {
        out.print(e);
    }


%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Current Requests</h1>
        <form action="respond.jsp" method="get">
        <table border="1">
            <thead>
                <tr>
                    <th>Location</th>
                    <th>Product</th>
                    <th>Quantity Needed</th>
                    <th>Disaster</th>
                    <th>Select</th>
                </tr>
            </thead>
            <tbody>
                <%                    
                    Iterator it = Request.instances.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        Request item = Request.instances.get(pair.getKey());
                %>
                <tr>
                    <td><% out.print(item.getLocation()); %></td>
                    <td><% out.print(item.getProductName()); %></td>
                    <td><% out.print(item.getQuantityRequested()); %></td>
                    <td><% out.print(item.getDisasterType()); %></td>
                    <td>
                        <input type="radio" name="Select" value="<%= item.getRequestID() %>" /></td>
                        <% }%>
            </tbody>
        </table>
        
                            <input type="submit" value="Respond to selected" />
    </form>


</html>
