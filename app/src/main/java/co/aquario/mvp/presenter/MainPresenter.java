package co.aquario.mvp.presenter;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.aquario.mvp.data.TopMovieListView;
import co.aquario.mvp.model.PostDataNew;
import cz.msebera.android.httpclient.Header;

public class MainPresenter implements Presenter<TopMovieListView> {
    private TopMovieListView mMainView;

    List<PostDataNew> list = new ArrayList<>();

    @Override
    public void attachView(TopMovieListView view) {
        this.mMainView = view;
    }

//    public void loadData() {
//        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
//        asyncHttpClient.get("http://ihdmovie.xyz/root/api_movie/get_movie2.php?uid=1&cat=1", new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//                try {
////                    PostDataNew mainModel = new PostDataNew();
////                    JSONArray ja = response.getJSONArray("posts");
////                    for (int i = 0; i < ja.length(); i++) {
////                        JSONObject job = ja.getJSONObject(i);
////                        mainModel.setId(job.optString("id"));
////                        mainModel.setName(job.optString("name"));
////                        mainModel.setImage(job.optString("image"));
////                        mainModel.setUrl(job.optString("url"));
////                        mainModel.setDescription(job.optString("description"));
////                        list.add(mainModel);
////                        mMainView.setArticles(list);
////                    }
//
//                    if (response != null) {
//                        JSONArray ja = response.getJSONArray("posts");
//                        for (int i = 0; i < ja.length(); i++) {
//                            JSONObject obj = ja.getJSONObject(i);
//                            String id = obj.optString("id");
//                            String name = obj.getString("name");
//                            String image = obj.getString("image");
//                            String url = obj.optString("url");
//                            String description = obj.optString("description");
//
//                            PostDataNew mainModel = new PostDataNew();
//                            mainModel.setId(id);
//                            mainModel.setName(name);
//                            mainModel.setImage(image);
//                            mainModel.setUrl(url);
//                            mainModel.setDescription(description);
//
//                            list.add(mainModel);
//                            mMainView.setArticles(list);
//                        }
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//        });
//    }

    public void loadData() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get("http://103.253.145.83/api/v1/products", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
//                    PostDataNew mainModel = new PostDataNew();
//                    JSONArray ja = response.getJSONArray("posts");
//                    for (int i = 0; i < ja.length(); i++) {
//                        JSONObject job = ja.getJSONObject(i);
//                        mainModel.setId(job.optString("id"));
//                        mainModel.setName(job.optString("name"));
//                        mainModel.setImage(job.optString("image"));
//                        mainModel.setUrl(job.optString("url"));
//                        mainModel.setDescription(job.optString("description"));
//                        list.add(mainModel);
//                        mMainView.setArticles(list);
//                    }

                    if (response != null) {
                        JSONArray ja = response.getJSONArray("products");
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject obj = ja.getJSONObject(i);
                            String name = obj.getString("name_th");
                            String image = obj.getString("image");
                            String url = obj.optString("thumb");
                            String description = obj.optString("desc");

                            PostDataNew mainModel = new PostDataNew();
                            mainModel.setName(name);
                            mainModel.setImage(image);
                            mainModel.setUrl(url);
                            mainModel.setDescription(description);

                            list.add(mainModel);
                            mMainView.setArticles(list);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
    @Override
    public void detachView() {
        this.mMainView = null;
    }
}
