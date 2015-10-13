package co.aquario.mvp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ShoppingCartHelper  {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<PostDataNew> catalog = new Vector<>();
    private static Map<PostDataNew, ShoppingCartEntry> cartMap = new HashMap<PostDataNew, ShoppingCartEntry>();

    public static List<PostDataNew> getCatalog(){
        return catalog;
    }

    public static void setQuantity(PostDataNew product, int quantity) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products
        if(quantity <= 0) {
            if(curEntry != null)
                removeProduct(product);
            return;
        }

        // If a current cart entry doesn't exist, create one
        if(curEntry == null) {
            curEntry = new ShoppingCartEntry(product, quantity);
            cartMap.put(product, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(PostDataNew product) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        if(curEntry != null)
            return curEntry.getQuantity();

        return 0;
    }

    public static void removeProduct(PostDataNew product) {
        cartMap.remove(product);
    }

    public static void removeListProduct(List<PostDataNew> product) {
        cartMap.remove(product);
    }

    public static List<PostDataNew> getCartList() {
        List<PostDataNew> cartList = new Vector<PostDataNew>(cartMap.keySet().size());
        for(PostDataNew p : cartMap.keySet()) {
            cartList.add(p);
        }

        return cartList;
    }




}