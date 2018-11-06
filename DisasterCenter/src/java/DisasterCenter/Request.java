package DisasterCenter;


import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author james
 */
public class Request {
    
    public static Map<String,Request> instances = new HashMap<String,Request>();
    
    
    
    int requestID;
    int quantityRequested;
    int quantityFulfilled;
    boolean expired;
    int userID;
    int productID;
    int priorityReference;
    int disasterEventID;
    int location;
    String productName;
    String disasterType;
    
    public Request(String requestID) {
        instances.put(requestID,this);
    }

    public Request(int location, String disasterType, String productName, int requestID, int quantityRequested, int quantityFulfilled, boolean expired, int userID, int productID, int priorityReference, int disasterEventID) {
        this.requestID = requestID;
        this.quantityRequested = quantityRequested;
        this.quantityFulfilled = quantityFulfilled;
        this.expired = expired;
        this.userID = userID;
        this.productID = productID;
        this.priorityReference = priorityReference;
        this.disasterEventID = disasterEventID;
        this.productName = productName;
        this.disasterType = disasterType;
        this.location = location;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(String disasterType) {
        this.disasterType = disasterType;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getQuantityRequested() {
        return quantityRequested;
    }

    public void setQuantityRequested(int quantityRequested) {
        this.quantityRequested = quantityRequested;
    }

    public int getQuantityFulfilled() {
        return quantityFulfilled;
    }

    public void setQuantityFulfilled(int quantityFulfilled) {
        this.quantityFulfilled = quantityFulfilled;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getPriorityReference() {
        return priorityReference;
    }

    public void setPriorityReference(int priorityReference) {
        this.priorityReference = priorityReference;
    }

    public int getDisasterEventID() {
        return disasterEventID;
    }

    public void setDisasterEventID(int disasterEventID) {
        this.disasterEventID = disasterEventID;
    }
    
    
    
}
