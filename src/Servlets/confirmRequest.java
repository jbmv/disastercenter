/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DisasterCenter.*;

/**
 *
 * @author Tanner 
 */
public class confirmRequest extends HttpServlet {

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

		/* TODO still need to finish this servlet */
		// get next available donationID
        
		try {
			// connect to database, get next available ID for this new donation
			Class.forName("com.mysql.jdbc.Driver"); // MySQL database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false", "root",
					"password");
			//PreparedStatement pst = conn.prepareStatement(Queries.getNextDonationID);
			//ResultSet rs = pst.executeQuery();
			//rs.next(); // get the row
			//int donationID = rs.getInt("nextID"); // get next available donation ID

			// create Request object, populate with next avail ID
			
			Request newRequest = new Request();
            newRequest.setQuantityRequested(Integer.valueOf(request.getParameter("quantity")));
            newRequest.setExpired(false);
            // newRequest.setNeededByDate()  didn't see this input in jsp file
            newRequest.setUser((User) session.getAttribute("user"));
            newRequest.setProduct(new Product(request.getParameter("productId")));
            newRequest.setLocation(new Location());
            // how should i set other attribute/do I need to?

			//to do, update sql tables with new request

			session.setAttribute("newRequest", newRequest);
            // move to thank you for request
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/donationConfirmation.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println(e);
		}

	}


} 