<%-- 
    Document   : newResponse
    Created on : Nov 19, 2018, 12:05:29 AM
    Author     : james
--%>

<%@page import="DisasterCenter.DisasterEvent" %>
<%@page import="DisasterCenter.DisasterList" %>
<%@page import="DisasterCenter.Product"%>
<%@page import="DisasterCenter.ProductList"%>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Map" %>

<%
    //import java objects from HTTP session
    ProductList productList = (ProductList) session.getAttribute("productList");
	DisasterList disasterList = (DisasterList) session.getAttribute("disasterList");

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
                    <h1>Create A New Request</h1>
                </div>

                <div style="padding: 20px">
                    <form action="confirmRequest" method="POST">


                        <label for="productID">Product Donating</label>
                        <select id="productID" name="productID">
                        <%                    // for every entry in requestList.instances, create one table row
                            Iterator it = productList.getInstances().entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                Product newProduct = productList.getInstances().get(pair.getKey());
                        %>
                        <option value="<%= newProduct.getProdId()%>"><% out.print(newProduct.getProdType()); %></option>
                        <% }%>
                    </select>
                    <input type="text" id="other" name="other" placeholder="Enter Other Product Type">

                    <label for="disaster">Disaster associated with this request</label>
                        <select id="disaster" name="disaster">
                        <%                    
                        // for every entry in disasterList.instances, create one table row
                            Iterator it2 = disasterList.getInstances().entrySet().iterator();
                            while (it2.hasNext()) {
                                Map.Entry pair = (Map.Entry) it2.next();
                                DisasterEvent disaster = disasterList.getInstances().get(pair.getKey());
                        %>
                        <option value="<%= disaster.getDisasterEventID()%>"><% out.print(disaster.getType() + " at zipcode " + disaster.getLocation().getZipcode()); %></option>
                        <% }%>
                    </select>

                    <label for="quantity">Quantity Requesting</label>
                    <input type="text" id="quantity" name="quantity" placeholder="Enter Quantity">

                    <label for="neededBy">Date needed by (YYYY-MM-DD)</label>
                    <input type="text" id="neededBy" name="neededBy" placeholder="Enter date needed by">


                    <input type="submit" value="Submit">
                </form>
            </div>
        </div>
    </body>
</html>
