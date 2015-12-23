package co.aquario.folkrice.model;

/**
 * Created by root1 on 11/14/15.
 */
public class HistoryDetail {

    double sub_total;
    double total_price;
    String state;
    String name;
    String product_id;

    public  HistoryDetail(){

    }

    public HistoryDetail(double sub_total, double total_price, String state, String name,String product_id) {
        this.sub_total = sub_total;
        this.total_price = total_price;
        this.state = state;
        this.name = name;
        this.product_id = product_id;
    }


    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
