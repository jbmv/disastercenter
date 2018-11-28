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
public class Response {

    int responseID;
    int quantitySent;
    Request request;
    Date providedByDate;
    User user;

    public Response(int responseID) {
        this.responseID = responseID;
    }

    public Response() { }

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
        return providedByDate;
    }

    public void setProvidedByDate(Date providedByDate) {
        this.providedByDate = providedByDate;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
