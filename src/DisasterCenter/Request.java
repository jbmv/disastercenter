package DisasterCenter;

import java.util.*;

public class Request {

    int requestID;
    int quantityRequested;
    int quantityFulfilled;
    boolean expired;
    Date neededByDate;
    User user;
    Product product;
    PriorityReference priorityReference;
    int disasterEventID;
    Location location;

    // Below are needed to construct web tables and are not part of DB Request table
    String zipName;
    String productName;
    String disasterName;
    String dateString;
    
    
    public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public void setUser(User user) {
		this.user = user;
	}

	float distance;

    public Request(int requestID) {
        this.requestID = requestID;
    }

    public Request() { }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getZipName() {
        return zipName;
    }

    public void setZipName(String zipName) {
        this.zipName = zipName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDisasterName() {
        return disasterName;
    }

    public void setDisasterName(String disasterName) {
        this.disasterName = disasterName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getDisasterEventID() {
        return disasterEventID;
    }

    public void setDisasterEventID(int disasterEvent) {
        this.disasterEventID = disasterEvent;
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

    public boolean getExpired() {
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
