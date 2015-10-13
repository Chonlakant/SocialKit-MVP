package co.aquario.mvp.model;

/**
 * Created by root1 on 9/19/15.
 */
public class AddAddress {

    String name;
    String pass;
    String contry;
    String district;
    String postal;
    String home;

    public AddAddress(String name, String pass, String contry, String district, String postal, String home) {
        this.name = name;
        this.pass = pass;
        this.contry = contry;
        this.district = district;
        this.postal = postal;
        this.home = home;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }
}
