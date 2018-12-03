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
			+ "Select userid,username,password,firstname,lastname,email,phone,u.locationid,lattitude,longitude,streetnum,street,city,zipcode "
			+ "from user u join location l on u.locationid = l.locationid " + "where username=? and password=?");

	public static String getRequest =
			// gets all non-expired requests, calculates distance from user based on lat/lon
			// algorithm source:
			// http://www.plumislandmedia.net/mysql/haversine-mysql-nearest-loc/
			("" + "SELECT RequestId,r.DisasterEventID,r.UserID,r.ProductID,QuantityRequested,"
					+ "QuantityFulfilled,Expired,NeededByDate,l.lattitude,l.longitude, "
					+ "r.LocationID,streetnum,street,city,p.type as productName," + "d.type as disasterName," + "l.zipcode as zipName, "
					+ "111.045* DEGREES(ACOS(COS(RADIANS(userLat))" + "                 * COS(RADIANS(l.lattitude))"
					+ "                 * COS(RADIANS(userLon) - RADIANS(l.longitude))"
					+ "                 + SIN(RADIANS(userLat))"
					+ "                 * SIN(RADIANS(l.lattitude)))) AS distance "
					+ "FROM Request r JOIN Product p on r.ProductId = p.ProductId "
					+ "JOIN disasterevent d on r.DisasterEventId = d.DisasterEventId "
					+ "JOIN location l on r.locationid = l.locationid " + " JOIN ("
					+ "     SELECT  ?  AS userLat, ? AS userLon" + "   ) AS p ON 1=1 " + "HAVING expired = 0 AND quantityfulfilled < quantityrequested "
					+ "ORDER BY distance");

	public static String getProducts = "select * from product"; 

	public static String correctUserLogin = "update user set lastlogin = ?, failedloginattempts = 0 where username = ?";

	public static String incorrectUserLogin = "update user set failedloginattempts = failedloginattempts + 1 where username = ?";

	public static String getNextDonationID = "SELECT IFNULL(MAX(donationid) + 1, 1) as nextID FROM donation";
																												
	public static String setDonation = "insert into Donation (Amount, UserID, ProductId) values (?, ?, ?)";																											// ID +
																												
	public static String updateStoredProduct = "update StoredProduct set Quantity = Quantity + ? where StoredProductId = ?";																																		

	public static String updateRequest = "update Request set QuantityFulfilled = ? where RequestID = ?";																										
	
	public static String createResponse = "insert into Response (QuantitySent, RequestId, UserId, ProvidedByDate) values (?,?,?,?)";	
	
	public static String updateFulfilledRequestAmount = "update Request set quantityFulfilled = ? where requestId = ?";																											// null

	public static String getDisasters = "select disastereventid,type,location,startdate,lattitude,longitude,zipcode from DisasterEvent d join location l on d.location = l.locationid";

	public static String getAddressToSendResponse = "";

	public static String expireOldRequests = "update request set expired = 1 where neededbydate < now()";

	public static String getNewUser = "select username from user where username = ?";

	public static String createNewUserLocation = "insert into location (lattitude,longitude,streetnum,street,city,zipcode) values (?,?,?,?,?,?)";
	
	public static String getNewUserLocation = "select locationid from location where lattitude = ? and longitude = ? and streetnum = ?";
	
	public static String createNewUser = "insert into user (username,password,firstname,lastname,email,phone,locationid,failedloginattempts) values (?,?,?,?,?,?,?,0)";
	public static String createNewUserSP = ""

	public static String updateUser = "update user set password = ?, firstname = ?, lastname = ?, email = ?, phone = ? where userid = ?";

	public static String updateUserLocation = "update location set lattitude = ?, longitude = ?, streetnum = ?, street = ?, city = ?, zipcode = ? where locationid = ?";

	public static String insNewRequest = "insert into Request (QuantityRequested, QuantityFulfilled, Expired, UserId, ProductId, DisasterEventId, NeededByDate, LocationID) values (?, 0, 0, ?, ?, ?, ?, ?)";

}
