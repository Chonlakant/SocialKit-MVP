package co.aquario.mvp;

import android.app.Application;
import android.util.Log;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import co.aquario.mvp.di.components.ApplicationComponent;
import co.aquario.mvp.handler.ApiBus;
import co.aquario.mvp.handler.ApiHandler;
import co.aquario.mvp.handler.ApiService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Mac on 3/2/15.
 */
public class MainApplication extends Application {

    private static final String ENDPOINT = "http://ihdmovie.xyz/root";
    private ApplicationComponent applicationComponent;
    private ApiHandler someApiHandler;

    @Override public void onCreate() {
        super.onCreate();
        someApiHandler = new ApiHandler(this, buildApi(), ApiBus.getInstance());
        someApiHandler.registerForEvents();
        CustomActivityOnCrash.install(this);
    }



    ApiService buildApi() {

        Log.e("HEY!","after post");
        Log.e("sdsdsdsd", ENDPOINT);
        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override public void intercept(RequestFacade request) {
                        //request.addQueryParam("p1", "var1");
                        //request.addQueryParam("p2", "");
                    }
                })
                .build()
                .create(ApiService.class);
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
