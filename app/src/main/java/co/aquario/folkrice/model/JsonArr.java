package co.aquario.folkrice.model;

/**
 * Created by root1 on 10/12/15.
 */
public class JsonArr {
    int product_id ;
    int quantity;

    public JsonArr(){

    }

    public JsonArr(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
