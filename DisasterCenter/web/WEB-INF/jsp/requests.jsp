<%-- 
    Document   : home
    Created on : Nov 18, 2018, 3:14:31 PM
    Author     : james
--%>

<%@page import="DisasterCenter.RequestList"%>
<%@page import="java.util.Map"%>
<%@page import="DisasterCenter.Request"%>
<%@page import="java.util.Iterator"%>
<%@ page import="DisasterCenter.User" %>
<%@ page import="DisasterCenter.Location" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%
    //import java objects from HTTP session
    User user = (User) session.getAttribute("user");
    Location userLocation = (Location) session.getAttribute("userLocation");
    RequestList requestList = (RequestList) session.getAttribute("requestList");
    
%>
<html>
<title>Disaster Center</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
table {
    border-spacing: 0;
    width: 100%;
    border: 1px solid #ddd;
}

th {
    cursor: pointer;
}

th, td {
    text-align: left;
    padding: 16px;
}

tr:nth-child(even) {
    background-color: #f2f2f2
}
</style>
<body>

<!-- Sidebar -->
<jsp:include page="sidebar.jsp">

<!-- Page Content -->
<div style="margin-left:15%">

<div class="w3-container w3-teal">
  <h1>Current Requests</h1>
</div>

<div class="w3-container">

<h2>Click on a request to create a response</h2>

<div class="w3-responsive">
        <form action="respond.jsp" method="get">
<table class="w3-table-all">
            <thead>
                <tr>
            <th onclick="sortTable(0)"> Location</th>
                    <th onclick="sortTable(1)">Product</th>
                    <th onclick="sortTable(2)">Quantity Needed</th>
                    <th onclick="sortTable(3)">Disaster</th>
                    <th>Select</th>
                </tr>
            </thead>
            <tbody>
                <%             
                    // for every entry in requestList.instances, create one table row
                    Iterator it = requestList.getInstances().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        Request newRequest = (Request) requestList.getInstances().get(pair.getKey());
                %>
                <tr>
                    <td><% out.print(newRequest.getZipName()); %></td>
                    <td><% out.print(newRequest.getProductName()); %></td>
                    <td><% out.print(newRequest.getQuantityRequested()); %></td>
                    <td><% out.print(newRequest.getDisasterName()); %></td>
                    <td>
                        <input type="radio" name="Select" value="<%= newRequest.getRequestID() %>" /></td>
                        <% }%>
            </tbody>
        </table>
        
                            <input type="submit" value="Respond to selected" />
    </form>
</table>
</div>

</div>

</body>
</html>


