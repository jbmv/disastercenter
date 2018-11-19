package servlets;

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
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project?&useSSL=false", "root", "password");
            PreparedStatement pst = conn.prepareStatement(Queries.getUser);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setFirstName(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setPhone(rs.getString(7));
                user.setLocationID(rs.getInt(8));
                Location userLocation = new Location(user.getLocationID());
                userLocation.setLatitude(rs.getInt(9));
                userLocation.setLongitude(rs.getInt(10));

                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userLocation", userLocation);

                PreparedStatement updateLastLogin = conn.prepareStatement("update user set lastlogin = ? where username = ?");
                PreparedStatement updateFailedAttempts = conn.prepareStatement("update user set failedloginattempts = 0 where username = ?");
                DateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                Calendar calendar = Calendar.getInstance();
                String dfString = df.format(calendar.getTime());
                updateLastLogin.setString(1, dfString);
                updateLastLogin.setString(2, username);
                updateFailedAttempts.setString(1, username);
                updateLastLogin.executeUpdate();
                updateFailedAttempts.executeUpdate();

                RequestDispatcher dispatcher = request.getRequestDispatcher(
                        "/requests");
                dispatcher.forward(request, response);

            } else {
                PreparedStatement updateLoginFailed = conn.prepareStatement("update user set failedloginattempts = failedloginattempts + 1 where username = ?");
                updateLoginFailed.setString(1, username);
                updateLoginFailed.executeUpdate();
                PrintWriter out = response.getWriter();
                out.print("<div class=\"w3-container w3-red\">"
                        + "  <h1>Invalid Login!</h1>\n"
                        + "</div>");
                RequestDispatcher dispatcher = request.getRequestDispatcher(
                        "/index.html");
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
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
