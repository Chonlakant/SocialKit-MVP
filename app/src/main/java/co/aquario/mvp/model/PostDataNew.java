package co.aquario.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by root1 on 10/6/15.
 */
public class PostDataNew {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("url")
    private String url;
    @SerializedName("description")
    private String description;

    public PostDataNew() {

    }

    public PostDataNew(String id, String name, String image, String url, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.url = url;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
