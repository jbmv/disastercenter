<%-- 
    Document   : newResponse
    Created on : Nov 19, 2018, 12:05:29 AM
    Author     : james
--%>


<%@page import="DisasterCenter.Donation"%>
<%
    //import java objects from HTTP session
    Donation newDonation = (Donation) session.getAttribute("newDonation");

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <title>Disaster Center</title>
<body>
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
        <!-- Sidebar -->
        <jsp:include page="sidebar.jsp"></jsp:include>

            <!-- Page Content -->
            <div style="margin-left:15%">
                <div class="w3-container w3-teal">
                    <h1>Thank you for your donation!</h1>
                </div>
                
                              <div class="w3-container w3-aqua">
                              
                              <h2>
                              Currently, there are no requests for these items
                              <br>
                              Your donation of <% out.print(newDonation.getAmount()); %> <% out.print(newDonation.getProduct().getProdType()); %> has been placed on call.
                              <br>
                              You will be notified when a new request has been matched.</h2>





            </div>

</body>
</html>