/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisasterCenter;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author james
 */
public class DisasterList {

    private Map<String, DisasterEvent> instances = new HashMap<String, DisasterEvent>();

    public Map<String, DisasterEvent> getInstances() {
        return instances;
    }

    public void addInstance(DisasterEvent disaster) {
        instances.put(String.valueOf(disaster.getDisasterEventID()), disaster);
    }

    public void removeInstance(DisasterEvent disaster) {
        instances.remove(String.valueOf(disaster.getDisasterEventID()));
    }

}
