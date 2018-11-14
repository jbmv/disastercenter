/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisasterCenter;

import java.time.LocalDate;

/**
 *
 * @author james
 */
public class DisasterEvent {
    
    int disasterEventID;
    String type;
    Location location;
    LocalDate startDate;

    public DisasterEvent(int disasterEventID) {
        this.disasterEventID = disasterEventID;
    }

    public int getDisasterEventID() {
        return disasterEventID;
    }

    public void setDisasterEventID(int disasterEventID) {
        this.disasterEventID = disasterEventID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    
    
}