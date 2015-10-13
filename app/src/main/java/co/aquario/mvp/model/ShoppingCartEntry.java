package co.aquario.mvp.model;


public class ShoppingCartEntry {

    private PostDataNew mProduct;
    private int mQuantity;

    public ShoppingCartEntry(PostDataNew product, int quantity) {
        mProduct = product;
        mQuantity = quantity;
    }

    public PostDataNew getProduct() {
        return mProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

}