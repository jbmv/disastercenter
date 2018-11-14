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

import java.time.LocalDate;

public class Request {
    
    int requestID;
    int quantityRequested;
    int quantityFulfilled;
    boolean expired;
    Date neededByDate;
    User user;
    Product product;
    PriorityReference priorityReference;
    DisasterEvent disasterEvent;
    Location location;
    
    public Request(int requestID) {
        this.requestID = requestID;
    }

    public Request(int location, Product product, int requestID, int quantityRequested, int quantityFulfilled, boolean expired, User user, PriorityReference priorityReference, DisasterEvent disasterEvent, LocalDate neededByDate) {
        this.requestID = requestID;
        this.quantityRequested = quantityRequested;
        this.quantityFulfilled = quantityFulfilled;
        this.expired = expired;
        this.user = user;
        this.product = product;
        this.priorityReference = priorityReference;
        this.disasterEvent = disasterEvent;
        this.location = location;
        this.neededByDate = neededByDate;
    }

    public Location getLocation() {
        return locationId;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDisasterEvent() {
        return disasterType;
    }

    public void setDisasterEvent(DisasterEvent disasterEvent) {
        this.disasterEvent = disasterEvent;
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

    public Date getNeededByDate() {
        return quantityRequested;
    }

    public void setNeededByDate(Date neededByDate) {
        this.neededByDate = neededByDate;
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

    public int getUser() {
        return user;
    }

    public void setUserID(User user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPriorityReference() {
        return priorityReference;
    }

    public void setPriorityReference(PriorityReference priorityReference) {
        this.priorityReference = priorityReference;
    }
}
