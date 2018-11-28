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
 * @author james
 */
public class confirmDonation extends HttpServlet {

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
			PreparedStatement pst = conn.prepareStatement(Queries.getNextDonationID);
			ResultSet rs = pst.executeQuery();
			rs.next(); // get the row
			int donationID = rs.getInt("nextID"); // get next available donation ID

			// create donation object, populate with next avail ID
			// populate product,amount,user fields of donation object
			Donation newDonation = new Donation(donationID);
			newDonation.setAmount(Integer.valueOf(request.getParameter("quantity")));
			newDonation.setProductID(Integer.valueOf(request.getParameter("productID")));
			newDonation.setUser((User) session.getAttribute("user"));

			newDonation = CheckCurrentRequests(newDonation, session);

			if(newDonation.getAmount() != 0)
			{
				PreparedStatement pst = conn.prepareStatement(Queries.setDonation);
				pst.setString(1, String.valueOf(newDonation.getAmount()));
				pst.setString(2, String.valueOf(newDonation.getProductID()));
				pst.setString(3, String.valueof(newDonation.getUser().getUserId()));
				
				pst = conn.prepareStatement(Queries.updateStoredProduct);
				pst.setString(1, String.valueOf(newDonation.getAmount()));
				pst.setString(2, String.valueOf(newDonation.getProdcutID()));
			}

			// TODO add db update code here
			// don't forget to recheck database to see if that new donation id was used?
			// ... concurrency

			session.setAttribute("newDonation", newDonation);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/donationConfirmation.jsp");
			dispatcher.forward(request, response);

		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println(e);
		}

	}

	private Donation CheckCurrentRequests(Donation newDonation, HttpSession session)
	{
		RequestList requestList = session.getAttribute("requestList");
		Iterator<Request> requests = requestList.getInstances().values().iterator();
		while(requests.hasNext())
		{
			Request current = requests.next();
			if(current.getProduct().getProdId() == newDonation.getProductID())
			{
				int amountNeeded = current.getQuantityRequested() - current.getQuantityFulfilled();
				//Generate a response here
				Response newResponse = new Response();
				newResponse.setUser(session.getAttribute("user"));
				newResponse.setRequest(current);
				int currentAmt = newDonation.getAmount();
				if(currentAmt >= amountNeeded)
				{
					newDonation.setAmount(currentAmt - amountNeeded);
					newResponse.setQuantitySent(amountNeeded);
				}
				else
				{
					newDonation.setAmount(0);
					newResponse.setQuantitySent(amountNeeded - currentAmt);
				}
				newResponse.setProvidedByDate();
				// save response and update request	in sql
			}
			if(newDonation.getAmount() == 0)
			{
				break;
			}
		}

		return newDonation;
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
