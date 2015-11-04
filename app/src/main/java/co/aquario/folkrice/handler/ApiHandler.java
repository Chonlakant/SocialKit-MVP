package co.aquario.folkrice.handler;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import co.aquario.folkrice.model.PostData;
import co.aquario.folkrice.event.FailedEvent;
import co.aquario.folkrice.event.PostsEvent;
import co.aquario.folkrice.event.SuccessPostEvent;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by matthewlogan on 9/3/14.
 */
public class ApiHandler  {

    private Context context;
    private ApiService api;
    private ApiBus apiBus;

    public ApiHandler(Context context, ApiService api,
                      ApiBus apiBus) {

        this.context = context;
        this.api = api;
        this.apiBus = apiBus;
    }

    public void registerForEvents() {
        apiBus.register(this);
    }

    @Subscribe
    public void onPostEvent(PostsEvent event) {
            api.getMovieAll(new Callback<PostData>() {
                @Override
                public void success(PostData postData, Response response) {
                    apiBus.post(new SuccessPostEvent(postData));
                    Log.e("5656676", postData.getPosts().size() + "");
                }

                @Override
                public void failure(RetrofitError error) {
                    apiBus.post(new FailedEvent());
                }
            });

    }

}
