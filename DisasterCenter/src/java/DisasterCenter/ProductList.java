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
public class ProductList {
    
        private Map<String, Object> instances = new HashMap<String, Object>();

    public Map<String, Object> getInstances() {
        return instances;
    }
    
    
    public void addInstance(Product product) {
        instances.put(String.valueOf(product.getProdId()), product);
    }
    
    public void removeInstance(Product product) {
        instances.remove(String.valueOf(product.getProdId()));
    }
    
}
