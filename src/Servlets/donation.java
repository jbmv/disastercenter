/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DisasterCenter.Product;
import DisasterCenter.ProductList;
import DisasterCenter.Queries;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author james
 */
public class donation extends HttpServlet {

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

			try {
				// get all products from database, append to product list
				// this is so we can populate the product dropdown when making a custom request
				// NOTE: we should switch this to stored proceedure
				Class.forName("com.mysql.jdbc.Driver"); // MySQL database connection
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false",
						"root", "password");
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

				// append productList to HTTP session for use in the view,
				// then forward browser to createDonation.jsp view
				session.setAttribute("productList", productList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/createDonation.jsp");
				dispatcher.forward(request, response);

			} catch (Exception e) {
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
