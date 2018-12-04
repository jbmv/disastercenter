<%-- 
    Document   : newResponse
    Created on : Nov 19, 2018, 12:05:29 AM
    Author     : james
--%>

<%@page import="DisasterCenter.Product"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="DisasterCenter.ProductList"%>
<%@page import="DisasterCenter.Request"%>
<%@page import="DisasterCenter.Response"%>
<%@page import="DisasterCenter.RequestList"%>
<%@page import="DisasterCenter.Location"%>
<%@page import="DisasterCenter.User"%>
<%
    //import java objects from HTTP session
    ProductList productList = (ProductList) session.getAttribute("productList");

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
                    <h1>Create Custom Donation</h1>
                </div>

                <div style="padding: 20px">
                    <form action="confirmDonation" method="POST">


                        <label for="productID">Product Donating</label>
                        <select id="productID" name="productID">
                        <%                    // for every entry in requestList.instances, create one table row
                            Iterator it = productList.getInstances().entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                Product newProduct = (Product) productList.getInstances().get(pair.getKey());
                            %>
                            <option value="<%= newProduct.getProdId()%>"><% out.print(newProduct.getProdType()); %></option>
                            <% }%>
                        </select>
                        <input type="text" id="other" name="other" placeholder="Enter Other Product Type">


                        <label for="quantity">Quantity Donating</label>
                        <input type="text" id="quantity" name="quantity" placeholder="Enter Quantity">



                        <input type="submit" value="Submit">
                    </form>
                </div>
            </div>
    </body>
</html>
