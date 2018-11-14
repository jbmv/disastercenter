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

    public Location getLocation() {
        return location;
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
        return neededByDate;
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

    public User getUser() {
        return user;
    }

    public void setUserID(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PriorityReference getPriorityReference() {
        return priorityReference;
    }

    public void setPriorityReference(PriorityReference priorityReference) {
        this.priorityReference = priorityReference;
    }
}
