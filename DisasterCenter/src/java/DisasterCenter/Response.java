package DisasterCenter;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
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

public class Response {

    int responseID;
    int quantitySent;
    Request request;
    Date providedByDate;
    User user;

    public Response(String responseID) {
        this.responseID = responseID;
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

    public Date getProvidedByDate() {
        return productID;
    }

    public void setProvidedByDate(Date providedByDate) {
        this.providedByDate = providedByDate;
    }

    public int getRequest() {
        return requestID;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public boolean isRequestExpired() {
        return requestExpired;
    }

    public void setRequestExpired(boolean requestExpired) {
        this.requestExpired = requestExpired;
    }

    public int getUser() {
        return userID;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
