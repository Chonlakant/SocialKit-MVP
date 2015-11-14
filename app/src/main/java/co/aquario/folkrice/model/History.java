package co.aquario.folkrice.model;

/**
 * Created by root1 on 11/13/15.
 */
public class History {

    String name;
    double price;
    String state;
    int producrId;

    public History(){

    }

    public History(String name, double price, String state, int producrId) {
        this.name = name;
        this.price = price;
        this.state = state;
        this.producrId = producrId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getProducrId() {
        return producrId;
    }

    public void setProducrId(int producrId) {
        this.producrId = producrId;
    }
}
