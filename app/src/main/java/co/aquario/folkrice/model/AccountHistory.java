package co.aquario.folkrice.model;

/**
 * Created by root1 on 11/13/15.
 */
public class AccountHistory {
    int account_id;
    String state;
    String created_at;
    int order_id;

    public AccountHistory(){

    }

    public AccountHistory(int account_id, String state, String created_at, int order_id) {
        this.account_id = account_id;
        this.state = state;
        this.created_at = created_at;
        this.order_id = order_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
