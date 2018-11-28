/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DisasterCenter.Donation;
import DisasterCenter.Location;
import DisasterCenter.Queries;
import DisasterCenter.Request;
import DisasterCenter.RequestList;
import DisasterCenter.Response;
import DisasterCenter.User;

/**
 *
 * @author james
 */
public class confirmResponse extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// REQURED TO BE LOGGED IN TO ACCESS THIS PAGE, if not logged in, redirect
		HttpSession session = request.getSession(false);
		if (session == null) {
			// user not logged in, forward to login page
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.html");
			dispatcher.forward(request, response);
		}

		try (PrintWriter out = response.getWriter()) {

			// import HTTP session attributes
		    User user = (User) session.getAttribute("user");
		    RequestList requestList = (RequestList) session.getAttribute("requestList");
		    Request currentRequest = (Request) session.getAttribute("currentRequest");
		    
		    // create response object and get form data for quantity
			Response newResponse = new Response();
			newResponse.setRequest(currentRequest);
			newResponse.setUser(user);
			newResponse.setQuantitySent(Integer.valueOf(request.getParameter("quantity")));
			newResponse.setProvidedByDate(currentRequest.getNeededByDate());
			session.setAttribute("newResponse", newResponse);
			

			
			// database operations 

			try {
				Class.forName("com.mysql.jdbc.Driver"); // MySQL database connection
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false",
						"root", "password");
				PreparedStatement createResponse = conn.prepareStatement(Queries.createResponse);
				//updateRequest = "update Request set QuantityFulfilled = ?, Expired = ? where RequestID = ?"
				PreparedStatement updateRequest = conn.prepareStatement(Queries.updateRequest);
				updateRequest.setString(2, String.valueOf(currentRequest.getRequestID()));
				
				// need to check if the Quantity select exceeds the request
				if (newResponse.getQuantitySent() == (currentRequest.getQuantityRequested() - currentRequest.getQuantityFulfilled())) {
					updateRequest.setString(1, String.valueOf(currentRequest.getQuantityRequested()));
					session.setAttribute("splitResponse", "False");
				}
				else if (newResponse.getQuantitySent() < (currentRequest.getQuantityRequested() - currentRequest.getQuantityFulfilled())){
					updateRequest.setString(1, String.valueOf(currentRequest.getQuantityFulfilled() + newResponse.getQuantitySent()));
					session.setAttribute("splitResponse", "False");
				}
				else if (newResponse.getQuantitySent() > (currentRequest.getQuantityRequested() - currentRequest.getQuantityFulfilled())) {
					updateRequest.setString(1, String.valueOf(currentRequest.getQuantityRequested()));
					updateRequest.setString(2,  "1");
					Donation newDonation = new Donation();
					newDonation.setAmount(newResponse.getQuantitySent() - (currentRequest.getQuantityRequested() - currentRequest.getQuantityFulfilled()));
					newDonation.setProductID(currentRequest.getProduct().getProdId());
					// quantity exceeds request so....
					
					// we need to create a new donation for the overflow
					// query: insert into Donation (Amount, UserID, ProductId) values (?, ?, ?)
					PreparedStatement createNewDonation = conn.prepareStatement(Queries.setDonation);
					createNewDonation.setString(1, String.valueOf(newDonation.getAmount()));
					createNewDonation.setString(2, String.valueOf(user.getUserID()));
					createNewDonation.setString(3, String.valueOf(newDonation.getProductID()));
					createNewDonation.executeUpdate();
					session.setAttribute("splitResponse", "True");
					session.setAttribute("donationOverflow", createNewDonation);
				}
				
				createResponse.setString(1, String.valueOf(newResponse.getQuantitySent()));
				createResponse.setString(2, String.valueOf(currentRequest.getRequestID()));
				createResponse.setString(3, String.valueOf((user.getUserID())));
				DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
				if (newResponse.getProvidedByDate() != null) {
					String dfString = df.format(newResponse.getProvidedByDate());
					createResponse.setString(4, dfString);
				}
				else {
					// currently in the database, providedbydate cannot be null
					// so i set it to the current time
					Calendar calendar = Calendar.getInstance();
					String dfString = df.format(calendar.getTime());
					createResponse.setString(4, dfString);
				}
				createResponse.executeUpdate();
				updateRequest.executeUpdate();
								
				
				// forward to webpage with address to send response to
				// separate webpages depending on if a new donation for overflow created
				if (session.getAttribute("splitResponse") == "True") {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/thankYouSplitResponse.jsp");
					dispatcher.forward(request, response);
				}
				else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/thankYouResponse.jsp");
					dispatcher.forward(request, response);
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				out.print(e);
				e.printStackTrace();
			} 
			
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
