<%-- 
    Document   : home
    Created on : Nov 18, 2018, 3:14:31 PM
    Author     : james
--%>

<%@page import="DisasterCenter.RequestList"%>
<%@page import="java.util.Map"%>
<%@page import="DisasterCenter.Request"%>
<%@page import="java.util.Iterator"%>
<%@ page import="DisasterCenter.User" %>
<%@ page import="DisasterCenter.Location" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%
    //import java objects from HTTP session
    User user = (User) session.getAttribute("user");
    Location userLocation = (Location) session.getAttribute("userLocation");
    RequestList requestList = (RequestList) session.getAttribute("requestList");

%>
<html>
    <title>Disaster Center</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <style>
        table {
            border-spacing: 0;
            width: 100%;
            border: 1px solid #ddd;
        }

        th,td {
            cursor: pointer;
        }

        th, td {
            text-align: left;
            padding: 16px;
        }

        tr:hover {
            background-color:lightblue;
        }

    </style>
    <body>

        <!-- Sidebar -->
        <jsp:include page="sidebar.jsp"></jsp:include>

            <!-- Page Content -->
            <div style="margin-left:15%">

                <div class="w3-container w3-teal">
                    <h1>Current Requests</h1>
                </div>

                <div class="w3-container">

                    <h2>Click on a request to create a response</h2>

                    <div class="w3-responsive">
                        <table id="myTable" class="w3-table-all">
                            <thead>
                                <tr>
                                    <th onclick="sortOnDistance()"> Distance</th>
                                    <th onclick="sortTable(1)"> Location</th>
                                    <th onclick="sortTable(2)">Product</th>
                                    <th onclick="sortTable(3)">Quantity Needed</th>
                                    <th onclick="sortTable(4)">Needed By</th>
                                    <th onclick="sortTable(5)">Disaster</th>
                                </tr>
                            </thead>
                            <tbody>
                            <%                    // for every entry in requestList.instances, create one table row
                                Iterator it = requestList.getInstances().entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry pair = (Map.Entry) it.next();
                                    Request newRequest = (Request) requestList.getInstances().get(pair.getKey());
                            %>
                            <tr onclick="document.location = 'respond?requestID=<% out.print(newRequest.getRequestID()); %>'">
                                <td><% out.print(newRequest.getDistance()); %></td>
                                <td><% out.print(newRequest.getZipName()); %></td>
                                <td><% out.print(newRequest.getProductName()); %></td>
                                <td><% out.print(newRequest.getQuantityRequested() - newRequest.getQuantityFulfilled()); %></td>
                                <td><% out.print(newRequest.getDateString()); %></td>
                                <td><% out.print(newRequest.getDisasterName()); %></td>
                                <% }%>
                        </tbody>
                    </table>

                </div>

            </div>
            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    sortOnDistance();
                }, false);
            </script>

            <script>
                function sortOnDistance() {
                    var table, rows, switching, i, x, y, shouldSwitch;
                    table = document.getElementById("myTable");
                    switching = true;
                    /*Make a loop that will continue until
                     no switching has been done:*/
                    while (switching) {
                        //start by saying: no switching is done:
                        switching = false;
                        rows = table.rows;
                        /*Loop through all table rows (except the
                         first, which contains table headers):*/
                        for (i = 1; i < (rows.length - 1); i++) {
                            //start by saying there should be no switching:
                            shouldSwitch = false;
                            /*Get the two elements you want to compare,
                             one from current row and one from the next:*/
                            x = rows[i].getElementsByTagName("TD")[0];
                            y = rows[i + 1].getElementsByTagName("TD")[0];
                            //check if the two rows should switch place:
                            if (Number(x.innerHTML) > Number(y.innerHTML)) {
                                //if so, mark as a switch and break the loop:
                                shouldSwitch = true;
                                break;
                            }
                        }
                        if (shouldSwitch) {
                            /*If a switch has been marked, make the switch
                             and mark that a switch has been done:*/
                            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                            switching = true;
                        }
                    }
                }
            </script>



            <script>
                function sortTable(n) {
                    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
                    table = document.getElementById("myTable");
                    switching = true;
                    //Set the sorting direction to ascending:
                    dir = "asc";
                    /*Make a loop that will continue until
                     no switching has been done:*/
                    while (switching) {
                        //start by saying: no switching is done:
                        switching = false;
                        rows = table.rows;
                        /*Loop through all table rows (except the
                         first, which contains table headers):*/
                        for (i = 1; i < (rows.length - 1); i++) {
                            //start by saying there should be no switching:
                            shouldSwitch = false;
                            /*Get the two elements you want to compare,
                             one from current row and one from the next:*/
                            x = rows[i].getElementsByTagName("TD")[n];
                            y = rows[i + 1].getElementsByTagName("TD")[n];
                            /*check if the two rows should switch place,
                             based on the direction, asc or desc:*/
                            if (dir == "asc") {
                                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                                    //if so, mark as a switch and break the loop:
                                    shouldSwitch = true;
                                    break;
                                }
                            } else if (dir == "desc") {
                                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                                    //if so, mark as a switch and break the loop:
                                    shouldSwitch = true;
                                    break;
                                }
                            }
                        }
                        if (shouldSwitch) {
                            /*If a switch has been marked, make the switch
                             and mark that a switch has been done:*/
                            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                            switching = true;
                            //Each time a switch is done, increase this count by 1:
                            switchcount++;
                        } else {
                            /*If no switching has been done AND the direction is "asc",
                             set the direction to "desc" and run the while loop again.*/
                            if (switchcount == 0 && dir == "asc") {
                                dir = "desc";
                                switching = true;
                            }
                        }
                    }
                }
            </script>

    </body>
</html>


