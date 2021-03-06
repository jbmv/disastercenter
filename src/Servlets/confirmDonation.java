/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DisasterCenter.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

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
			return;
		}

		try {
			// connect to database, get next available ID for this new donation
			Class.forName("com.mysql.jdbc.Driver"); // MySQL database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false", "root",
					"password");
			PreparedStatement pst;// = conn.prepareStatement(Queries.getNextDonationID);
			//ResultSet rs = pst.executeQuery();
			//rs.next(); // get the row
			//int donationID = rs.getInt("nextID"); // get next available donation ID

			// create donation object, populate with next avail ID
			// populate product,amount,user fields of donation object
			Donation newDonation = new Donation();
			if (session.getAttribute("newDonation") != null) 
			{
				newDonation = (Donation) session.getAttribute("newDonation");
			}
			newDonation.setAmount(Integer.valueOf(request.getParameter("quantity")));

			int productId = Integer.valueOf(request.getParameter("productID"));
			if(productId == -1)
			{
				productId = CreateNewProduct(request.getParameter("other"), session, conn);
			}

			newDonation.setProductID(productId);
			newDonation.setUser((User) session.getAttribute("user"));
			System.out.println("getting product object for productID" + newDonation.getProductID());
			// need the product name for display on donationConfirmation.jsp
			ProductList productList = (ProductList) session.getAttribute("productList");
			newDonation.setProduct(productList.getProductByID(String.valueOf(newDonation.getProductID())));

			newDonation = CheckCurrentRequests(newDonation, session, conn);


			if(newDonation.getAmount() != 0)
			{
				pst = conn.prepareStatement(Queries.setDonation);
				pst.setString(1, String.valueOf(newDonation.getAmount()));
				pst.setString(2, String.valueOf(newDonation.getUser().getUserID()));
				pst.setString(3, String.valueOf(newDonation.getProductID()));
				pst.executeUpdate();
				
				pst = conn.prepareStatement(Queries.updateStoredProduct);
				pst.setString(1, String.valueOf(newDonation.getAmount()));
				pst.setString(2, String.valueOf(newDonation.getProductID()));

				pst.executeUpdate();
				
				session.setAttribute("newDonation", newDonation);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/thankYouMultiResponse.jsp");
				dispatcher.forward(request, response);
				return;
			}


			session.setAttribute("newDonation", newDonation);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/thankYouMultiResponse.jsp");
			dispatcher.forward(request, response);
			return;

		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println(e);
			e.printStackTrace();
		}

	}

	private int CreateNewProduct(String productType, HttpSession session, Connection conn) throws SQLException
	{
		// insert product into product table and stored product table
		PreparedStatement pst = conn.prepareStatement(Queries.createProduct);
		pst.setString(1, productType);
		pst.executeUpdate();

		pst = conn.prepareStatement(Queries.getNewProductId);
		ResultSet rs = pst.executeQuery();
		int newProductId = -1;
		while(rs.next())
		{
			newProductId = rs.getInt("productID");
		}
		ProductList productList = (ProductList) session.getAttribute("productList");
		Product newProduct = new Product(newProductId);
		newProduct.setProductType(productType);
		productList.addInstance(newProduct);
		session.setAttribute("productList", productList);

		return newProductId;
	}

	private Donation CheckCurrentRequests(Donation newDonation, HttpSession session, Connection conn) throws SQLException
	{
		ResponseList responseList = new ResponseList();
		RequestList requestList = (RequestList) session.getAttribute("requestList");
		Iterator<Request> requests = requestList.getInstances().values().iterator();
		while(requests.hasNext())
		{
			Request current = requests.next();
			if(current.getProduct().getProdId() == newDonation.getProductID())
			{
				int amountNeeded = current.getQuantityRequested() - current.getQuantityFulfilled();
				//Generate a response here
				Response newResponse = new Response();
				newResponse.setUser((User) session.getAttribute("user"));
				newResponse.setRequest(current);

				Calendar calendar = Calendar.getInstance();
				newResponse.setProvidedByDate(calendar.getTime());
				int currentAmt = newDonation.getAmount();
				if(currentAmt >= amountNeeded)
				{
					newDonation.setAmount(currentAmt - amountNeeded);
					newResponse.setQuantitySent(amountNeeded);
					current.setQuantityFulfilled(amountNeeded + current.getQuantityFulfilled());
				}
				else
				{
					newDonation.setAmount(0);
					newResponse.setQuantitySent(currentAmt);
					current.setQuantityFulfilled(currentAmt + current.getQuantityFulfilled());
				}
				// figure out how to get date newResponse.setProvidedByDate();
				
				// save response and update request	in sql
				DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
				PreparedStatement pst = conn.prepareStatement(Queries.createResponse);
				pst.setString(1, String.valueOf(newResponse.getQuantitySent()));
				pst.setString(2, String.valueOf(newResponse.getRequest().getRequestID()));
				pst.setString(3, String.valueOf(newResponse.getUser().getUserID()));
				pst.setString(4, df.format(newResponse.getProvidedByDate()));
				pst.executeUpdate();
				
				if (session.getAttribute("responseList") != null)
				{
					session.getAttribute("responseList");
				}
				responseList.addInstance(newResponse);
				
				if(current.getQuantityRequested() == current.getQuantityFulfilled())
				{
					pst = conn.prepareStatement(Queries.updateRequest);
					pst.setString(1, String.valueOf(current.getQuantityFulfilled()));
					pst.setString(2, String.valueOf(current.getRequestID()));
					pst.executeUpdate();
				}
				else
				{
					pst = conn.prepareStatement(Queries.updateRequest);
					pst.setString(1, String.valueOf(current.getQuantityFulfilled()));
					pst.setString(2, String.valueOf(current.getRequestID()));
					pst.executeUpdate();
				}
				
			}
			if(newDonation.getAmount() == 0)
			{
				break;
			}
		}
		session.setAttribute("responseList", responseList);
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
