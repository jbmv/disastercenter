package DisasterCenter;

import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author james
 */
public class Response {
    
    public static Map<String,Response> instances = new HashMap<String,Response>();
    
    int responseID;
    int quantitySent;
    int requestID;
    LocalDate providedByDate;
    int userID;

    public Response(String responseID) {
        instances.put(responseID,this);
        this.responseID = Integer.parseInt(responseID);
    }

    public int getResponseID() {
        return responseID;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public int getQuantitySent() {
        return quantitySent;
    }

    public void setQuantitySent(int quantitySent) {
        this.quantitySent = quantitySent;
    }

    public LocalDate getProvidedByDate() {
        return productID;
    }

    public void setProvidedByDate(LocalDate providedByDate) {
        this.providedByDate = providedByDate;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public boolean isRequestExpired() {
        return requestExpired;
    }

    public void setRequestExpired(boolean requestExpired) {
        this.requestExpired = requestExpired;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
