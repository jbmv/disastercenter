/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisasterCenter;

import java.util.*;

/**
 *
 * @author Tanner
 */
public class Product {

    int productId;
    String type;

    public Product(int productId) {
        this.productId = productId;
    }

    public void setProductType(String type) {
        this.type = type;
    }

    public int getProdId() {
        return this.productId;
    }

    public String getProdType() {
        return this.type;
    }
}
