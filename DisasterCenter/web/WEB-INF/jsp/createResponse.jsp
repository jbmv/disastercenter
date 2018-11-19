<%-- 
    Document   : newResponse
    Created on : Nov 19, 2018, 12:05:29 AM
    Author     : james
--%>

<%@page import="DisasterCenter.Request"%>
<%@page import="DisasterCenter.Response"%>
<%@page import="DisasterCenter.RequestList"%>
<%@page import="DisasterCenter.Location"%>
<%@page import="DisasterCenter.User"%>
<%
    //import java objects from HTTP session
    User user = (User) session.getAttribute("user");
    Location userLocation = (Location) session.getAttribute("userLocation");
    RequestList requestList = (RequestList) session.getAttribute("requestList");
    int requestID = Integer.parseInt(session.getAttribute("requestID").toString());
    Request currentRequest = (Request) requestList.getInstances().get(String.valueOf(requestID));
    Response newResponse = new Response(requestID);
    newResponse.setUser(user);

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Disaster Center</title>
    </head>
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

        div {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
        }
    </style>
    <body>

        <h3>Create New Response</h3>

        <div>
            <form action="confirmResponse">
                <label for="fname">Disaster</label>
                <input type="text" id="disaster" name="disaster" placeholder="<%= currentRequest.getDisasterName()%>" readonly>

                <label for="lname">Location</label>
                <input type="text" id="lname" name="lastname" placeholder="<%= currentRequest.getZipName()%>" readonly>

                <label for="lname">Product</label>
                <input type="text" id="lname" name="lastname" placeholder="<%= currentRequest.getProductName()%>" readonly>
                
                <label for="lname">Deliver By</label>
                <input type="text" id="lname" name="lastname" placeholder="<%= currentRequest.getNeededByDate()%>" readonly>
                
                <label for="lname">Quantity</label>
                <input type="text" id="lname" name="lastname" value="<%= currentRequest.getQuantityRequested() %>">

  

                <input type="submit" value="Submit">
            </form>
        </div>

    </body>
</html>
