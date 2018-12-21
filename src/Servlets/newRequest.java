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

/**
 *
 * @author james
 */
public class newRequest extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException       if a servlet-specific error occurs
	 * @throws IOException            if an I/O error occurs
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		response.setContentType("text/html;charset=UTF-8");

		// REQURED TO BE LOGGED IN TO ACCESS THIS PAGE, if not logged in, redirect
		HttpSession session = request.getSession(false);
		if (session == null) {
			// user not logged in, forward to login page
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.html");
			dispatcher.forward(request, response);
		}

		try (PrintWriter out = response.getWriter()) {


			// get all products from database, append to product list
			// this is so we can populate the product dropdown when making a custom request

			Class.forName("com.mysql.jdbc.Driver"); // MySQL database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false", "root",
					"password");
			PreparedStatement pst = conn.prepareStatement(Queries.getProducts);
			ResultSet rs = pst.executeQuery();

			// create ProductList object to append all products to
			ProductList productList = new ProductList();

			while (rs.next()) {
				// iterate through all rows in product table, append to productList
				Product newProduct = new Product(rs.getInt("productID"));
				newProduct.setProductType(rs.getString("type"));

				// append each request to requestList object
				productList.addInstance(newProduct);
			}
			// other option
			Product other = new Product(-1);
			other.setProductType("Other");
			productList.addInstance(other);

			// get all disasters from DisasterEvents table and append to disastersList
			// this is to populate the select disaster associated with a new request
			pst = conn.prepareStatement(Queries.getDisasters);
			rs = pst.executeQuery();
			DisasterList disasterList = new DisasterList();
			
			while (rs.next()) {
				// iterate through all rows in DisasterEvent table, append to disasterList
				// query returns: disastereventid,type,location,startdate,lattitude,longitude,zipcode
				DisasterEvent disaster = new DisasterEvent();
				Location location = new Location();
				location.setLatitude(rs.getLong("lattitude"));
				location.setLongitude(rs.getLong("longitude"));
				location.setZipcodes(rs.getInt("zipcode"));
				location.setlocationID(rs.getInt("location"));
				disaster.setDisasterEventID(rs.getInt("disastereventid"));
				disaster.setLocation(location);
				disaster.setType(rs.getString("type"));
				disaster.setStartDate(rs.getDate("startdate"));
				
				//append each disaster to disasterList
				disasterList.addInstance(disaster);
			}
			
			
			// append productList and disasterList to HTTP session for use in the view,
			// then forward browser to createDonation.jsp view
			session.setAttribute("productList", productList);
			session.setAttribute("disasterList", disasterList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/createNewRequest.jsp");
			dispatcher.forward(request, response);

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
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
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
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
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
