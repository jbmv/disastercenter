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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
			newRequest.setQuantityFulfilled(0);
			newRequest.setExpired(false);
			
			String date = request.getParameter("neededBy");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date neededBy = dateFormat.parse(date);
			newRequest.setNeededByDate(neededBy);

            newRequest.setUser((User) session.getAttribute("user"));
			
			int productId = Integer.valueOf(request.getParameter("productID"));
			if(productId == -1)
			{
				productId = CreateNewProduct(request.getParameter("other"), session, conn);
			}
			
			ProductList pList = (ProductList) session.getAttribute("productList");
			newRequest.setProduct(pList.getProductByID(String.valueOf(productId)));

            int disID = Integer.valueOf(request.getParameter("disaster"));
			newRequest.setDisasterEventID(disID);
			DisasterList dList = (DisasterList) session.getAttribute("disasterList");
			Map<String, DisasterEvent> disInst = dList.getInstances();
			DisasterEvent dEvent = disInst.get(String.valueOf(disID));
			newRequest.setLocation(dEvent.getLocation());
			
			//to do, update sql tables with new request
			PreparedStatement pst = conn.prepareStatement(Queries.insNewRequest);
			pst.setString(1, String.valueOf(newRequest.getQuantityRequested()));
			pst.setString(2, String.valueOf(newRequest.getUser().getUserID()));
			pst.setString(3, String.valueOf(productId));
			pst.setString(4, String.valueOf(disID));
			pst.setString(5, dateFormat.format(neededBy));
			pst.setString(6, String.valueOf(newRequest.getLocation().getLocationID()));
			pst.executeUpdate();

			// check stored product for the requested item
			StoredProduct storedProduct = new StoredProduct();
			pst = conn.prepareStatement(Queries.getStoredProduct);
			pst.setString(1, String.valueOf(newRequest.getProduct().getProdId()));
			ResultSet rs = pst.executeQuery();

			while(rs.next())
			{
				storedProduct = new StoredProduct(rs.getInt("storedProductId"));
				storedProduct.setProductId(rs.getInt("productId"));
				storedProduct.setAmount(rs.getInt("quantity"));
			}

			int storedAmt = storedProduct.getAmount();
			if(storedAmt > 0)
			{
				if( storedAmt >= newRequest.getQuantityRequested())
				{
					pst = conn.prepareStatement(Queries.createResponse);
					pst.setString(1, String.valueOf(newRequest.getQuantityRequested()));
					pst.setString(2, String.valueOf(newRequest.getRequestID()));
					pst.setString(3, String.valueOf(1));
					DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
					Calendar calendar = Calendar.getInstance();
					String dfString = df.format(calendar.getTime());
					pst.setString(4, dfString);
					pst.executeUpdate();

					pst = conn.prepareStatement(Queries.updateRequest);
					pst.setString(1, String.valueOf(newRequest.getQuantityRequested()));
					pst.setString(2, String.valueOf(newRequest.getRequestID()));
					pst.executeUpdate();

					pst = conn.prepareStatement(Queries.updateStoredProduct);
					pst.setString(1, String.valueOf(-1*newRequest.getQuantityRequested()));
					pst.setString(2, String.valueOf(storedProduct.getProductId()));
					pst.executeUpdate();
				}
				else
				{
					pst = conn.prepareStatement(Queries.createResponse);
					pst.setString(1, String.valueOf(storedAmt));
					pst.setString(2, String.valueOf(newRequest.getRequestID()));
					pst.setString(3, String.valueOf(1));
					DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
					Calendar calendar = Calendar.getInstance();
					String dfString = df.format(calendar.getTime());
					pst.setString(4, dfString);
					pst.executeUpdate();

					pst = conn.prepareStatement(Queries.updateRequest);
					pst.setString(1, String.valueOf(newRequest.getQuantityRequested()));
					pst.setString(2, String.valueOf(newRequest.getRequestID()));
					pst.executeUpdate();

					pst = conn.prepareStatement(Queries.updateStoredProduct);
					pst.setString(1, String.valueOf(0));
					pst.setString(2, String.valueOf(storedProduct.getProductId()));
					pst.executeUpdate();
				}
			}
			session.setAttribute("newRequest", newRequest);
            // move to thank you for request
			RequestDispatcher dispatcher = request.getRequestDispatcher("/requests");
			dispatcher.forward(request, response);

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

