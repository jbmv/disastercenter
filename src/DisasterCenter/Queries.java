/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisasterCenter;

/**
 *
 * @author james
 */
public class Queries {

    public static String getUser = (""
            + "Select userid,username,password,firstname,lastname,email,phone,u.locationid,lattitude,longitude "
            + "from user u join location l on u.locationid = l.locationid "
            + "where username=? and password=?");

    public static String getRequest = (""
            + "SELECT RequestId,r.DisasterEventID,r.UserID,r.ProductID,QuantityRequested,"
            + "QuantityFulfilled,PriorityReferenceId,Expired,NeededByDate,l.lattitude,l.longitude, "
            + "r.LocationID,p.type as productName,"
            + "d.type as disasterName,"
            + "l.zipcode as zipName, "
            + "111.045* DEGREES(ACOS(COS(RADIANS(userLat))"
            + "                 * COS(RADIANS(l.lattitude))"
            + "                 * COS(RADIANS(userLon) - RADIANS(l.longitude))"
            + "                 + SIN(RADIANS(userLat))"
            + "                 * SIN(RADIANS(l.lattitude)))) AS distance "
            + "FROM Request r JOIN Product p on r.ProductId = p.ProductId "
            + "JOIN disasterevent d on r.DisasterEventId = d.DisasterEventId "
            + "JOIN location l on r.locationid = l.locationid "
            + " JOIN ("
            + "     SELECT  ?  AS userLat, ? AS userLon"
            + "   ) AS p ON 1=1"
            + " "
            + "ORDER BY distance" // http://www.plumislandmedia.net/mysql/haversine-mysql-nearest-loc/
            );

    public static String getProducts = "select * from product"; // should probably be a stored proceedure

    public static String correctUserLogin = "update user set lastlogin = ?, failedloginattempts = 0 where username = ?";

    public static String incorrectUserLogin = "update user set failedloginattempts = failedloginattempts + 1 where username = ?";

	public static String getNextDonationID = "SELECT IFNULL(MAX(donationid) + 1, 1) as nextID FROM donation"; // get last ID + 1, return 1 if null
}
