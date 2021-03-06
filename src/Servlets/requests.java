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
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author james
 */
public class requests extends HttpServlet {

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
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		response.setContentType("text/html;charset=UTF-8");

		// REQURED TO BE LOGGED IN TO ACCESS THIS PAGE, if not logged in, redirect
		HttpSession session = request.getSession(false);
		if (session == null) {
			PrintWriter out = response.getWriter();
			out.print("<div class=\"w3-container w3-red\">" + "  <h1>not logged in</h1>\n" + "</div>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/login.html");
			dispatcher.forward(request, response);
		}

		// get userLocation object from HttpSession
		Location userLocation = (Location) session.getAttribute("userLocation");

		try (PrintWriter out = response.getWriter()) {

			try {

				// get all non-expired requests from database
				Class.forName("com.mysql.jdbc.Driver"); // MySQL database connection
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false",
						"root", "password");
				
				// first, expire any old requests before displaying requests
				PreparedStatement pst = conn.prepareStatement(Queries.expireOldRequests);
				pst.executeUpdate();
				
				pst = conn.prepareStatement(Queries.getRequest);
				pst.setString(1, String.valueOf(userLocation.getLatitude()));
				pst.setString(2, String.valueOf(userLocation.getLongitude()));
				
				//query returns: RequestId,r.DisasterEventID,r.UserID,r.ProductID,QuantityRequested,
				// QuantityFulfilled,PriorityReferenceId,Expired,NeededByDate,l.lattitude,l.longitude,
				//r.LocationID,streetnum,street,city,p.type as productName," + "d.type as disasterName," + "l.zipcode as zipName, 
				ResultSet rs = pst.executeQuery();

				// create RequestList object, add all current requests, append requestList to
				// HttpSession
				RequestList requestList = new RequestList();

				while (rs.next()) {

					Request newRequest = new Request(rs.getInt("RequestID"));
					
					Location location = new Location(rs.getInt("LocationID"));
					location.setStreetNumber(rs.getInt("streetnum"));
					location.setStreet(rs.getString("street"));
					location.setCity(rs.getString("city"));
					location.setZipcodes(rs.getInt("zipName"));
					newRequest.setLocation(location);
					
					Product product = new Product(rs.getInt("ProductID"));
					product.setProductType(rs.getString("productName"));
					newRequest.setProduct(product);
					
					newRequest.setQuantityRequested(rs.getInt("QuantityRequested"));
					newRequest.setQuantityFulfilled(rs.getInt("QuantityFulfilled"));
					newRequest.setExpired(rs.getBoolean("Expired"));
					newRequest.setZipName(rs.getString("zipName"));
					newRequest.setDisasterName(rs.getString("disasterName"));
					newRequest.setProductName(rs.getString("productName"));
					newRequest.setDistance(rs.getFloat("distance"));
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
					Date date = df.parse(rs.getString("NeededByDate").substring(0, 10));
					String stringDate = df.format(date);
					System.out.println(rs.getString("NeededByDate").substring(0, 10));
					System.out.println(df.format(date));
					newRequest.setNeededByDate(date);

					// append each request to requestList object
					requestList.addInstance(newRequest);
				}

				// append requestList to HTTP session, send browser to currentRequests.jsp view
				session.setAttribute("requestList", requestList);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/currentRequests.jsp");
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
		try {
			processRequest(request, response);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(requests.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(requests.class.getName()).log(Level.SEVERE, null, ex);
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
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(requests.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(requests.class.getName()).log(Level.SEVERE, null, ex);
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
