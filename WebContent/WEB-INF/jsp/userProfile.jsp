<%-- 
    Document   : userProfile
    Created on : Nov 19, 2018, 3:30:39 AM
    Author     : james
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DisasterCenter.Location"%>
<%@page import="DisasterCenter.User"%>
<%
    //import java objects from HTTP session
    User user = (User) session.getAttribute("user");
    Location userLocation = (Location) session.getAttribute("userLocation");

%>


<!DOCTYPE html>
<html>
    <title>Disaster Center</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
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
        
        input[type=password], select {
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
                    <h1>User Profile</h1>
                </div>

                <div style="padding: 20px">
                    <form action="updateUser" method="POST">
                        <label>Username</label>
                        <input type="hidden" id="registering" name="registering" value="notregistering">
                        <input type="text" id="username" name="username" placeholder="<%= user.getUserName()%>" readonly>

                    <label>Password</label>
                    <input type="password" id="password" name="password" placeholder="password">

                    <label>email</label>
                    <input type="text" id="email" name="email" value="<%= user.getEmail()%>">

                    <label>First Name</label>
                    <input type="text" id="fname" name="fname" value="<%= user.getFirstName()%>">

                    <label>Last Name</label>
                    <input type="text" id="lname" name="lname" value="<%= user.getLastName()%>">

                    <label>Phone Number</label>
                    <input type="text" id="phone" name="phone" value="<%= user.getPhone()%>">

                    <label>Latitude</label>
                    <input type="text" id="latitude" name="latitude" value="<%= userLocation.getLatitude()%>">

                    <label>Longitude</label>
                    <input type="text" id="longitude" name="longitude" value="<%= userLocation.getLongitude()%>">

                    <label>City</label>
                    <input type="text" id="city" name="city" value="<%= userLocation.getCity()%>">
                    
                    <label>Street Number</label>
                    <input type="text" id="streetnum" name="streetnum" value="<%= userLocation.getStreetNumber()%>"> 
                    
                    <label>Street</label>
                    <input type="text" id="street" name="street" value="<%= userLocation.getStreet()%>">                    

                    <label>Zipcode</label>
                    <input type="text" id="zip" name="zip" value="<%= userLocation.getZipcode()%>">

                    <input type="submit" value="Update User Information">
                </form>
                
                
    </body>
</html>
