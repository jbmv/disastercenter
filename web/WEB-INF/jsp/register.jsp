<%-- 
    Document   : userProfile
    Created on : Nov 19, 2018, 3:30:39 AM
    Author     : james
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>



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
                    <input type="hidden" id="registering" name="registering" value="registering">
                        <label>Username</label>
                        <input type="text" id="username" name="username" placeholder="Enter username">

                    <label>Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter password">

                    <label>email</label>
                    <input type="text" id="email" name="email" placeholder="Enter email">

                    <label>First Name</label>
                    <input type="text" id="fname" name="fname" placeholder="Enter first name ">

                    <label>Last Name</label>
                    <input type="text" id="lname" name="lname" placeholder="Enter last name">

                    <label>Phone Number</label>
                    <input type="text" id="phone" name="phone" placeholder="Enter phone number">

                    <label>Latitude</label>
                    <input type="text" id="latitude" name="latitude" placeholder="Enter latitude">

                    <label>Longitude</label>
                    <input type="text" id="longitude" name="longitude" placeholder="Enter longitude">

                    <label>City</label>
                    <input type="text" id="city" name="city" placeholder="Enter city">
                    
                    <label>Street Number</label>
                    <input type="text" id="streetnum" name="streetnum" placeholder="Enter street number"> 
                    
                    <label>Street</label>
                    <input type="text" id="street" name="street" placeholder="Enter Street">                    

                    <label>Zipcode</label>
                    <input type="text" id="zip" name="zip" placeholder="Enter Zipcode">

                    <input type="submit" value="Register New User">
                </form>
                
                
    </body>
</html>
