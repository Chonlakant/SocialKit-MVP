package co.aquario.mvp.handler;

import co.aquario.mvp.model.PostData;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by matthewlogan on 9/3/14.
 */
public interface ApiService {

    @GET("/api_movie/get_movie2.php?uid=1&cat=1")
     void getMovieAll(Callback<PostData> responseJson);
}
