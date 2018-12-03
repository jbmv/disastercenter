/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisasterCenter;

import java.util.*;

/**
 *
 * @author james
 */
public class ResponseList {

    private List<Response> instances = new ArrayList<Response>(10);

    public List<Response> getInstances() {
        return instances;
    }

    public void addInstance(Response response) {
        instances.add(response);
    }

    public void removeInstance(Response response) {
        instances.remove(response);
    }

}
