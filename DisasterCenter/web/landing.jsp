<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : landing
    Created on : Nov 5, 2018, 2:49:31 PM
    Author     : james
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <sql:query var="result" dataSource="jdbc/mydb">
            SELECT d.location,d.type as disaster,p.type as product,quantityrequested FROM request r join product p on r.product_productid = p.productid join disasterevent d on r.disasterevent_disastereventid = d.disastereventid
        </sql:query>
            
        <table border="1">
            <!-- column headers -->
            <tr>
                <c:forEach var="columnName" items="${result.columnNames}">
                    <th><c:out value="${columnName}"/></th>
                    </c:forEach>
                    <th>Respond<th>
            </tr>
            <!-- column data -->
            <c:forEach var="row" items="${result.rowsByIndex}">
                <tr>
                    <c:forEach var="column" items="${row}">
                        <td><c:out value="${column}"/></td>
                    </c:forEach>
                        <td><input type="radio" name="respond" value="" /></td>
                </tr>
            </c:forEach>
        </table>
            <form action="respond.jsp">
                <input type="submit" value="Respond to selected" />
            </form>
            <form action="donate">
                            <input type="submit" value="Donate for future" />
            </form>
    </body>
</html>
