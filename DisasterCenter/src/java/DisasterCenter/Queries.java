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
    
    public static String getUser = "Select username,password from user where username=? and password=?";
    
    public static String correctUserLogin = "update user set lastlogin = ?, failedloginattempts = 0 where username = ?";

    public static String incorrectUserLogin = "update user set failedloginattempts = failedloginattempts + 1 where username = ?";
}
