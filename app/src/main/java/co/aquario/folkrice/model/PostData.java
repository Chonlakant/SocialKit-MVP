package co.aquario.folkrice.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by root1 on 10/5/15.
 */
public class PostData {


    /**
     * status : 1
     * posts : [{"id":"1356","name":"Nightcrawler เหยี่ยวข่าวคลั่ง ล่าข่าวโหด","image":"http://movie.mthai.com/wp-content/uploads/2015/01/url12-384x600.jpg","url":"https://r13---sn-ab5l6nel.c.docs.google.com/videoplayback?requiressl=yes&id=7a3d74c13aa241e3&itag=18&source=webdrive&app=docs&ip=2607:5300:60:7f0d::&ipbits=0&expire=1441638205&sparams=requiressl,id,itag,source,ip,ipbits,expire&signature=43B44D702014FC00D72B2498600221A22AC9E610.73BF1BAB5E39E9957C84B0F8E2EA712021582997&key=ck2","description":"ไม่มี"}]
     */

    @SerializedName("status")
    private int status;
    @SerializedName("posts")
    private List<PostsEntity> posts;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPosts(List<PostsEntity> posts) {
        this.posts = posts;
    }

    public int getStatus() {
        return status;
    }

    public List<PostsEntity> getPosts() {
        return posts;
    }

    public static class PostsEntity {
        /**
         * id : 1356
         * name : Nightcrawler เหยี่ยวข่าวคลั่ง ล่าข่าวโหด
         * image : http://movie.mthai.com/wp-content/uploads/2015/01/url12-384x600.jpg
         * url : https://r13---sn-ab5l6nel.c.docs.google.com/videoplayback?requiressl=yes&id=7a3d74c13aa241e3&itag=18&source=webdrive&app=docs&ip=2607:5300:60:7f0d::&ipbits=0&expire=1441638205&sparams=requiressl,id,itag,source,ip,ipbits,expire&signature=43B44D702014FC00D72B2498600221A22AC9E610.73BF1BAB5E39E9957C84B0F8E2EA712021582997&key=ck2
         * description : ไม่มี
         */

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

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }

        public String getUrl() {
            return url;
        }

        public String getDescription() {
            return description;
        }
    }
}
