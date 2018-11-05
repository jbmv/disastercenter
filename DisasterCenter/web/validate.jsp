
<%@page import="java.util.Calendar"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%-- 
    Document   : validate
    Created on : 28 Feb, 2015, 8:50:26 AM
    Author     : Lahaul Seth
--%>
 
<%@ page import ="java.sql.*" %>
<%
    try{
        String username = request.getParameter("username");   
        String password = request.getParameter("password");
        Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?&useSSL=false", "root", "password" );    
        PreparedStatement pst = conn.prepareStatement("Select username,password from user where username=? and password=?");
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery(); 
        /*
        ResultSetMetaData rsmd = rs.getMetaData();
        int colnums = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= colnums; i++) {
                if (i > 1) out.print(",  ");
                String columnValue = rs.getString(i);
                out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            out.println("");
        }
        */
        if(rs.next()) {          
           out.println("Valid login credentials");
           PreparedStatement update = conn.prepareStatement("update user set lastlogin = ? where username = ?");
           PreparedStatement update2 = conn.prepareStatement("update user set failedloginattempts = 0 where username = ?");
           DateFormat df = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
           Calendar calendar = Calendar.getInstance();
           String dfString = df.format(calendar.getTime());
           out.println(dfString);
           update.setString(1, dfString);
           update.setString(2, username);
           update2.setString(1, username);
           update.executeUpdate();
           update2.executeUpdate();
           %>
           <jsp:forward page="landing.jsp" />
           <%
        }
        else {
           PreparedStatement update = conn.prepareStatement("update user set failedloginattempts = failedloginattempts + 1 where username = ?");
           update.setString(1, username);
update.executeUpdate();
        out.println("Invalid login credentials"); 
        }
   }
   catch(Exception e){       
       out.println("Something went wrong !! Please try again"); 
       out.println(e);
   }      
%>