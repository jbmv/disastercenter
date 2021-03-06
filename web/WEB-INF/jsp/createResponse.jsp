<%-- 
    Document   : newResponse
    Created on : Nov 19, 2018, 12:05:29 AM
    Author     : james
--%>

<%@page import="DisasterCenter.Location"%>
<%@page import="DisasterCenter.Request" %>
<%@page import="DisasterCenter.User"%>
<%
    //import java objects from HTTP session
    User user = (User) session.getAttribute("user");
    Location userLocation = (Location) session.getAttribute("userLocation");
	Request currentRequest = (Request) session.getAttribute("currentRequest");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <title>Disaster Center</title>
    <style>
        input[type=text], select {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type=submit] {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type=submit]:hover {
            background-color: #45a049;
        }

    </style>
    <body>
        <!-- Sidebar -->
        <jsp:include page="sidebar.jsp"></jsp:include>

            <!-- Page Content -->
            <div style="margin-left:15%">
                <div class="w3-container w3-teal">
                    <h1>Create New Response</h1>
                </div>

                <div style="padding: 20px">
                    <form action="confirmResponse">
                        <label>Disaster</label>
                        <input type="text" id="disaster" name="disaster" placeholder="<%= currentRequest.getDisasterName()%>" readonly>

                    <label>Location</label>
                    <input type="text" id="location" name="location" placeholder="<%= currentRequest.getZipName()%>" readonly>

                    <label>Product Requested</label>
                    <input type="text" id="product" name="product" placeholder="<%= currentRequest.getProductName()%>" readonly>

                    <label>Deliver By</label>
                    <input type="text" id="deliverby" name="deliverby" placeholder="<%= currentRequest.getDateString() != null ? currentRequest.getDateString() : "not specified"%>" readonly>

                    <label style="color: red;">Quantity</label>
                    <input type="text" id="quantity" name="quantity" value="<%= currentRequest.getQuantityRequested() - currentRequest.getQuantityFulfilled()%>">



                    <input type="submit" value="Submit">
                </form>
            </div>
        </div>
    </body>
</html>
