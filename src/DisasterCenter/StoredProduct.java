/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DisasterCenter;

/**
 *
 * @author Tanner
 */
public class StoredProduct {

    int storedProductId;
    int amount;
    int productId;

    public StoredProduct(int storedProductId) 
    {
        this.storedProductId = storedProductId;
    }

    public StoredProduct() {

    }

	public int getStoredProductId() {
        return this.storedProductId;
    }

    public void setAmount(int amt) {
        this.amount = amt;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setProductId(int prodId) {
        this.productId = prodId;
    }

    public int getProductId() {
        return this.productId;
    }
}