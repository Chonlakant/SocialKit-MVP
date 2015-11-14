package co.aquario.folkrice.model;

/**
 * Created by root1 on 10/15/15.
 */
public class Status {

    String status ;
    String error;

    public Status(){

    }

    public Status(String status, String error) {
        this.status = status;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
