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
public class PriorityReference {

    int priorityReferenceID;
    String level;

    public PriorityReference(int priorityReferenceID) {
        this.priorityReferenceID = priorityReferenceID;
    }

    public PriorityReference() { }

    public int getPriorityReferenceID() {
        return priorityReferenceID;
    }

    public void setPriorityReferenceID(int priorityReferenceID) {
        this.priorityReferenceID = priorityReferenceID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
