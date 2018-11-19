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
    
    public static String getUser = ( ""
            + "Select userid,username,password,firstname,lastname,email,phone,u.locationid,lattitude,longitude "
            + "from user u join location l on u.locationid = l.locationid "
            + "where username=? and password=?"
            );
    
    public static String getRequest = ( ""
            + "SELECT RequestId,r.DisasterEventID,r.UserID,r.ProductID,QuantityRequested,"
            + "QuantityFulfilled,PriorityReferenceId,Expired,NeededByDate,"
            + "r.LocationID,p.type as productName,"
            + "d.type as disasterName,"
            + "l.zipcode as zipName "
            + "FROM Request r JOIN Product p on r.ProductId = p.ProductId "
            + "JOIN disasterevent d on r.DisasterEventId = d.DisasterEventId "
            + "JOIN location l on r.locationid = l.locationid "
            + "where Expired != 1"
            );
    
}
