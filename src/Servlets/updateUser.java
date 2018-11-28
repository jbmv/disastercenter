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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DisasterCenter.Location;
import DisasterCenter.Product;
import DisasterCenter.ProductList;
import DisasterCenter.Queries;
import DisasterCenter.User;

/**
 *
 * @author james
 */
public class updateUser extends HttpServlet {

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
			User user = (User) session.getAttribute("user");
			Location userLocation = (Location) session.getAttribute("userLocation");
		    String userName;
		    String password;
		    String firstName;
		    String lastName;
		    int locationID;
		    String email;
		    String phone;
		    Date lastLogin;
		    int failedLoginAttempts;
		    
		    userLocation.setCity(request.getParameter("city"));
		    userLocation.setLatitude(Integer.valueOf(request.getParameter("latitude")));
		    userLocation.setLongitude(Integer.valueOf(request.getParameter("longitude")));
		    userLocation.setStreet(request.getParameter("street"));
			user.setPassword(request.getParameter("password"));
			user.setFirstName(request.getParameter("fname"));
			user.setLastName(request.getParameter("lname"));
			user.setEmail(request.getParameter("email"));
			user.setPhone(request.getParameter("phone"));
			
			


			// then forward browser to userProfile.jsp view
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userProfile.jsp");
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
