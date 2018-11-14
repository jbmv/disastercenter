package DisasterCenter;


import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mercury
 */
public class User {
    int userID;
    String userName;
    String password;
    String firstName;
    String lastName;
    int residency;
    String email;
    String phone;
    Date lastLogin;
    int failedLoginAttempts;



    public User(int userID) {
        this.userID = userID;
    }

    public User(String userName, String password, String firstName, String lastName, int residency, String email, String phone, Date lastLogin, int failedLoginAttempts) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.residency = residency;
        this.email = email;
        this.phone = phone;
        this.lastLogin = lastLogin;
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getResidency() {
        return residency;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public Date getLastLogin() {
        return lastLogin;
    }
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }
}

/*
* User x
* Request x
* Response x
* Donation
* StoredProduct
* Product x
* PriorityReference x
* Employee
* DisasterEvent x
* Call Center
*/