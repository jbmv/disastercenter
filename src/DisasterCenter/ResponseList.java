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
public class ResponseList {

    private Map<String, Response> instances = new HashMap<String, Response>();

    public Map<String, Response> getInstances() {
        return instances;
    }

    public void addInstance(Response response) {
        instances.put(String.valueOf(response.getResponseID()), response);
    }

    public void removeInstance(Response response) {
        instances.remove(String.valueOf(response.getResponseID()));
    }

}
