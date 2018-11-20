package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DisasterCenter.Location;
import DisasterCenter.Queries;
import DisasterCenter.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class login extends HttpServlet {

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
			throws ServletException, IOException, SQLException, ClassNotFoundException {
		response.setContentType("text/html;charset=UTF-8");

		// get username and password from the html form fields
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			// get row from MySQL where username and password match
			Class.forName("com.mysql.jdbc.Driver"); // MySQL database connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false", "root",
					"password");
			PreparedStatement pst = conn.prepareStatement(Queries.getUser);
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();

			// check to see if a row was returned, if TRUE, username & password combo is
			// valid
			if (rs.next()) {
				// create User object, populate with columns returned from database
				User user = new User(rs.getInt("userid"));
				user.setUserName(rs.getString("username"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setPhone(rs.getString("phone"));
				user.setLocationID(rs.getInt("locationid"));

				// create Location object to store user location information
				Location userLocation = new Location(user.getLocationID());
				userLocation.setLatitude(rs.getInt("lattitude"));
				userLocation.setLongitude(rs.getInt("longitude"));

				// append User and Location objects to new HttpSessions so we can reference them
				// later
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("userLocation", userLocation);

				// connect to MySQL again and update last login and failed attempts columns
				PreparedStatement updateLastLogin = conn
						.prepareStatement("update user set lastlogin = ? where username = ?");
				PreparedStatement updateFailedAttempts = conn
						.prepareStatement("update user set failedloginattempts = 0 where username = ?");
				DateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
				Calendar calendar = Calendar.getInstance();
				String dfString = df.format(calendar.getTime());
				updateLastLogin.setString(1, dfString);
				updateLastLogin.setString(2, username);
				updateFailedAttempts.setString(1, username);
				updateLastLogin.executeUpdate();
				updateFailedAttempts.executeUpdate();

				// forward browser to the main page that shows current requests
				RequestDispatcher dispatcher = request.getRequestDispatcher("/requests");
				dispatcher.forward(request, response);

			} else {
				// if(rs.next()) returned FALSE = username & password combo did not exits in
				// MySQL
				// update database with failed login
				PreparedStatement updateLoginFailed = conn.prepareStatement(
						"update user set failedloginattempts = failedloginattempts + 1 where username = ?");
				updateLoginFailed.setString(1, username);
				updateLoginFailed.executeUpdate();

				// forward broswer to login page and display invalid login banner
				PrintWriter out = response.getWriter();
				out.print("<div class=\"w3-container w3-red\">" + "  <h1>Invalid Login!</h1>\n" + "</div>");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.html");
				dispatcher.include(request, response);

			}
		} catch (Exception e) {
			System.out.println("Something went wrong !! Please try again");
			System.out.println(e);
		}

	}

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
		} catch (SQLException ex) {
			Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
		} catch (SQLException ex) {
			Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
