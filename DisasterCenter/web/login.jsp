<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : login
    Created on : Nov 1, 2018, 11:59:46 AM
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
        <h1><sql:query var="names" dataSource="jdbc/mydb">
                SELECT * FROM mydb.User
            </sql:query>
                
            <table border="1">
                <!-- column headers -->
                <tr>
                <c:forEach var="columnName" items="${names.columnNames}">
                    <th><c:out value="${columnName}"/></th>
                </c:forEach>
                </tr>
                <!-- column data -->
                <c:forEach var="row" items="${names.rowsByIndex}">
                    <tr>
                    <c:forEach var="column" items="${row}">
                        <td><c:out value="${column}"/></td>
                    </c:forEach>
                    </tr>
                </c:forEach>
            </table></h1>
    </body>
</html>
