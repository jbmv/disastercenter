<%-- 
    Document   : newResponse
    Created on : Nov 19, 2018, 12:05:29 AM
    Author     : james
--%>


<%@page import="DisasterCenter.Response"%>
<%@page import="DisasterCenter.ResponseList"%>
<%@page import="DisasterCenter.RequestList"%>
<%@page import="DisasterCenter.Request"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%
	//import java objects from HTTP session
/* 	Request currentRequest = (Request) session.getAttribute("currentRequest");
	Response newResponse = (Response) session.getAttribute("newResponse");
	String address = "Disaster Center\n" + currentRequest.getLocation().getStreetNumber() + " "
			+ currentRequest.getLocation().getStreet() + "\n"
			+ currentRequest.getLocation().getCity() + " " 
			+ currentRequest.getLocation().getZipcode();  */
	ResponseList responseList = (ResponseList) session.getAttribute("responseList");
	RequestList requestList = (RequestList) session.getAttribute("requestList");
	System.out.println(requestList);
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <style>
        textarea[type=text], select {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            resize: none;
        }
     </style>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<title>Disaster Center</title>

<body>

	<!-- Sidebar -->
	<jsp:include page="sidebar.jsp"></jsp:include>

	<!-- Page Content -->
	<div style="margin-left: 15%">
		<div class="w3-container w3-teal">
			<h1>Thank you for your donation</h1>
			</div>
		<%                    // for every entry in requestList.instances, create one table row
                                Iterator it = responseList.getInstances().iterator();
                                while (it.hasNext()) {
                                    Response newResponse = (Response) it.next();
                                    System.out.println(newResponse.getRequest().getRequestID());
                                    System.out.println(requestList.getInstances().get(newResponse.getRequest().getRequestID()));
                                	Request currentRequest = requestList.getInstances().get(String.valueOf(newResponse.getRequest().getRequestID()));
                                	String address = "Disaster Center\n" + currentRequest.getLocation().getStreetNumber() + " "
                                			+ currentRequest.getLocation().getStreet() + "\n"
                                			+ currentRequest.getLocation().getCity() + " " 
                                			+ currentRequest.getLocation().getZipcode(); 
         
         %>
			<div class="w3-container w3-deep-orange">
			<h2>Please send the donation of <% out.print(newResponse.getQuantitySent()); %> <% out.print(currentRequest.getProductName()); %> to...</h2>
			<textarea label="address" type="text"cols="40" rows="5" readonly><% out.print(address); %></textarea>
			<h2><% out.print(currentRequest.getDateString() != null ? "...by " + currentRequest.getDateString() : "... as soon as possible."); %></h2>
			</div>
<<<<<<< HEAD
			<% } } %>
			
			<% if (newDonation != null) {
				%>
				
			<div class="w3-container w3-aqua">
			<h2>Your quantity donated exceeded the quantity for this request. 
			A new donation with the excess quantity has been created.
			When a request for this product is created, you will be notified.</h2>
			

			<input type="hidden" id="productID" name="productID" value="<%= newDonation.getProductID() %>">
			<label for="productID">Product</label>
			<input type="text" id="productName" name="productName" value="<%= newDonation.getProduct().getProdType() %>" readonly>
			<label for="quantity">Quantity Donating</label>
			<input type="text" id="quantity" name="quantity" value="<%= newDonation.getAmount() %>" readonly>



	</div>
				
				<% } %>
			
=======
			<% } %>
>>>>>>> parent of 7357e0c... Merge branch 'master' of https://github.com/jbmv/disastercenter

	</div>
</body>
</html>