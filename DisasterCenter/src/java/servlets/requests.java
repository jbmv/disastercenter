/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import DisasterCenter.Location;
import DisasterCenter.Queries;
import DisasterCenter.Request;
import DisasterCenter.RequestList;
import DisasterCenter.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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
public class requests extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        //This code is not working, supposed to check that we have a session before displaying
        HttpSession session = request.getSession(false);
        if (session == null) {
            PrintWriter out = response.getWriter();
            out.print("<div class=\"w3-container w3-red\">"
                    + "  <h1>not logged in</h1>\n"
                    + "</div>");
            RequestDispatcher dispatcher = request.getRequestDispatcher(
                    "WEB-INF/login.html");
            dispatcher.forward(request, response);
        }

        Location userLocation = (Location) session.getAttribute("userLocation");

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            try {
                // get all requests from database
                // NOTE: we should switch this to stored proceedure
                Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false", "root", "password");
                PreparedStatement pst = conn.prepareStatement(Queries.getRequest);
                pst.setString(1, String.valueOf(userLocation.getLatitude()));
                pst.setString(2, String.valueOf(userLocation.getLongitude()));
                ResultSet rs = pst.executeQuery();

                // create RequestList object, get HTTP session to append requestList
                RequestList requestList = new RequestList();
//                HttpSession session = request.getSession(false);

                while (rs.next()) {
                    Request newRequest = new Request(rs.getInt(1));
                    newRequest.setQuantityRequested(rs.getInt("QuantityRequested"));
                    newRequest.setQuantityFulfilled(rs.getInt("QuantityFulfilled"));
                    newRequest.setExpired(rs.getBoolean("Expired"));
                    newRequest.setZipName(rs.getString("zipName"));
                    newRequest.setDisasterName(rs.getString("disasterName"));
                    newRequest.setProductName(rs.getString("productName"));
                    newRequest.setDistance(rs.getInt("distance"));

                    // append each request to requestList object
                    requestList.addInstance(newRequest);
                }

                // append requestList to HTTP session, forward to jsp view
                session.setAttribute("requestList", requestList);
                RequestDispatcher dispatcher = request.getRequestDispatcher(
                        "/WEB-INF/jsp/currentRequests.jsp");
                dispatcher.forward(request, response);

            } catch (Exception e) {
                out.print(e);
            }

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
