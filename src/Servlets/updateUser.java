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
		System.out.println("starting updateUser servlet");
		//System.out.println(request.getParameter("registering"));
		String registeringNewUser = request.getParameter("registering");
		System.out.println(registeringNewUser);
		int oldUserID = 0;
		String oldUserName = "";
		
		HttpSession session = request.getSession(false);
		// create db connection
		Class.forName("com.mysql.jdbc.Driver"); // MySQL database connection
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false", "root",
				"password");
		
		if (registeringNewUser.equals("registering")) {
			System.out.println("registering == registering");
			session = request.getSession();
		} else {
			// REQURED TO BE LOGGED IN TO ACCESS THIS PAGE, if not logged in, redirect
			if (session == null) {
				// user not logged in, forward to login page
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.html");
				dispatcher.forward(request, response);
			} else {
				// get userid so we can preserve it for later
				User oldUser = (User) session.getAttribute("user");
				oldUserID = oldUser.getUserID();
				oldUserName = oldUser.getUserName();
			}

		}

		try (PrintWriter out = response.getWriter()) {
			System.out.println("try block, setting user object info");
			
			User user = new User();
			Location userLocation = new Location();
		    
		    userLocation.setCity(request.getParameter("city"));
		    userLocation.setLatitude(Float.valueOf(request.getParameter("latitude")));
		    userLocation.setLongitude(Float.valueOf(request.getParameter("longitude")));
		    userLocation.setStreet(request.getParameter("street"));
		    userLocation.setStreetNumber(Integer.valueOf(request.getParameter("streetnum")));
		    userLocation.setZipcodes(Integer.valueOf(request.getParameter("zip")));
		    
		    
			user.setPassword(request.getParameter("password"));
			user.setFirstName(request.getParameter("fname"));
			user.setLastName(request.getParameter("lname"));
			user.setEmail(request.getParameter("email"));
			user.setPhone(request.getParameter("phone"));
			
			if (registeringNewUser.equals("registering")) {
				System.out.println("setting username");
				
				// all code in this block executes if registering and then returns out of this servlet
				// test username chosen is unique in database, else redirect back with message
				user.setUserName(request.getParameter("username"));
				PreparedStatement pst = conn.prepareStatement(Queries.getNewUser);
				pst.setString(1, user.getUserName());
				ResultSet rs = pst.executeQuery();
				
				System.out.println("first sql query done");
				
				
				if (rs.next()) {
					// username already exists
					System.out.println("username already exists");
					out.print("<div class=\"w3-container w3-red\">" + "  <h1>That username already exists!</h1>\n" + "</div>");
					RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/register.jsp");
					dispatcher.include(request, response);
				}
				// location query: insert into location (lattitude,longitude,streetnum,street,city,zipcode)

				pst = conn.prepareStatement(Queries.createNewUserLocation);
				pst.setString(1, String.valueOf(userLocation.getLatitude()));
				pst.setString(2, String.valueOf(userLocation.getLongitude()));
				pst.setString(3, String.valueOf(userLocation.getStreetNumber()));
				pst.setString(4, userLocation.getStreet());
				pst.setString(5, userLocation.getCity());
				pst.setString(6, String.valueOf(userLocation.getZipcode()));
				pst.executeUpdate();
				
				System.out.println("sql update location done");

				// okay we made a new location, but we need the new locationID to create new user
				// query: where lattitude = ? and longitude = ? and streetnum = ?
				System.out.println("sql get location id we just created");
				pst = conn.prepareStatement(Queries.getNewUserLocation);
				pst.setString(1, String.valueOf(userLocation.getLatitude()));
				pst.setString(2, String.valueOf(userLocation.getLongitude()));
				pst.setString(3, String.valueOf(userLocation.getStreetNumber()));
				// this was not working, so doing something super wrong and getting last location id to be created!
				//pst = conn.prepareStatement("select locationid from location ORDER BY locationid DESC LIMIT 1");
				rs = pst.executeQuery();
				rs.next();
				System.out.println("rs.next() done");
				user.setLocationID(rs.getInt("locationid"));
				System.out.println("got location");
				
				
				//finally we can create the new user
				// querty: insert into user (username,password,firstname,lastname,email,phone,locationid) values = (?,?,?,?,?,?,?)
				pst = conn.prepareStatement(Queries.createNewUser);
				pst.setString(1, user.getUserName());
				pst.setString(2, user.getPassword());
				pst.setString(3, user.getFirstName());
				pst.setString(4, user.getLastName());
				pst.setString(5, user.getEmail());
				pst.setString(6, user.getPhone());
				pst.setInt(7, user.getLocationID());
				System.out.println("about to create user");
				pst.executeUpdate();

				
				
				// User created! redirect to login
				session.invalidate();
				System.out.println("created user, forward to login");
				out.print("<div class=\"w3-container w3-red\">" + "  <h1>User created, please login!</h1>\n" + "</div>");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.html");
				dispatcher.include(request, response);
				
				
			}

			// this code only executes if updating existing user
			// get old user id
			
			user.setUserID(oldUserID);
			user.setUserName(oldUserName);
			PreparedStatement pst = conn.prepareStatement(Queries.updateUser);
			pst.setString(1, user.getPassword());
			pst.setString(2, user.getFirstName());
			pst.setString(3, user.getLastName());
			pst.setString(4, user.getEmail());
			pst.setString(5, user.getPhone());
			pst.setInt(6, user.getUserID());
			System.out.println("about to update user");
			pst.executeUpdate();
			
			pst = conn.prepareStatement(Queries.updateUserLocation);
			pst.setFloat(1, userLocation.getLatitude());
			pst.setFloat(2, userLocation.getLongitude());
			pst.setInt(3, userLocation.getStreetNumber());
			pst.setString(4, userLocation.getStreet());
			pst.setString(5, userLocation.getCity());
			pst.setInt(6, userLocation.getZipcode());
			pst.setInt(7, userLocation.getLocationID());
			System.out.println("about to update user location");
			pst.executeUpdate();

			session.setAttribute("user", user);
			session.setAttribute("userLocation", userLocation);
			
			

			// then forward browser to userProfile.jsp view
			out.print("<div class=\"w3-container w3-red\">" + "  <h1>User Profile Updated!</h1>\n" + "</div>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userProfile.jsp");
			dispatcher.forward(request, response);

		}
		catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println(e);
			e.printStackTrace();
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
