package co.aquario.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root1 on 10/6/15.
 */
public class PostDataNew {

    @SerializedName("productId")
    private int productId;
    @SerializedName("lineProductId")
    private int lineProductId;
    @SerializedName("farmerId")
    private int farmerId;
    @SerializedName("catId")
    private int catId;
    @SerializedName("name")
    private String name;
    @SerializedName("name2")
    private String name2;
    @SerializedName("name_th")
    private String nameTh;
    @SerializedName("desc")
    private String desc;
    @SerializedName("price")
    private double price;
    @SerializedName("howtouse")
    private String howtouse;
    @SerializedName("nutrition")
    private String nutrition;
    @SerializedName("stock")
    private int stock;
    @SerializedName("size")
    private double size;
    @SerializedName("image")
    private String image;
    @SerializedName("thumb")
    private String thumb;

    public PostDataNew(){

    }

    public PostDataNew(int productId, int lineProductId, int farmerId, int catId, String name, String name2, String nameTh, String desc, double price, String howtouse, String nutrition, int stock, double size, String image, String thumb) {
        this.productId = productId;
        this.lineProductId = lineProductId;
        this.farmerId = farmerId;
        this.catId = catId;
        this.name = name;
        this.name2 = name2;
        this.nameTh = nameTh;
        this.desc = desc;
        this.price = price;
        this.howtouse = howtouse;
        this.nutrition = nutrition;
        this.stock = stock;
        this.size = size;
        this.image = image;
        this.thumb = thumb;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getLineProductId() {
        return lineProductId;
    }

    public void setLineProductId(int lineProductId) {
        this.lineProductId = lineProductId;
    }

    public int getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(int farmerId) {
        this.farmerId = farmerId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHowtouse() {
        return howtouse;
    }

    public void setHowtouse(String howtouse) {
        this.howtouse = howtouse;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
