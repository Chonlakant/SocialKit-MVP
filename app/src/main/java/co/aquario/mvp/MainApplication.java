package co.aquario.mvp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import co.aquario.mvp.di.components.ApplicationComponent;
import co.aquario.mvp.handler.ApiBus;
import co.aquario.mvp.handler.ApiHandler;
import co.aquario.mvp.handler.ApiService;
import co.aquario.mvp.model.ModelCart;
import co.aquario.mvp.model.PostDataNew;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Mac on 3/2/15.
 */
public class MainApplication extends Application {

    private static final String ENDPOINT = "http://ihdmovie.xyz/root";
    private ApplicationComponent applicationComponent;
    private ApiHandler someApiHandler;
    private static PrefManager prefManager;
    private ArrayList<PostDataNew> myProducts = new ArrayList<PostDataNew>();
    private ModelCart myCart = new ModelCart();
    @Override public void onCreate() {
        super.onCreate();
        prefManager = new PrefManager(getSharedPreferences("App", MODE_PRIVATE));
        someApiHandler = new ApiHandler(this, buildApi(), ApiBus.getInstance());
        someApiHandler.registerForEvents();
        CustomActivityOnCrash.install(this);
    }



    ApiService buildApi() {

        Log.e("HEY!", "after post");
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

    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }


    public static PrefManager getPrefManager() {
        return prefManager;
    }
    public PostDataNew getProducts(int pPosition) {

        return myProducts.get(pPosition);
    }

    public void setProducts(PostDataNew Products) {

        myProducts.add(Products);

    }

    public ModelCart getCart() {

        return myCart;

    }

    public int getProductsArraylistSize() {

        return myProducts.size();
    }

    public static void logout(Context context) {
        prefManager.isLogin().put(false).commit();
        prefManager.clear().commit();
        boolean isLogin = prefManager.isLogin().getOr(false);

//        ParsePush.unsubscribeInBackground("EN");
        Log.e("isLogin",":::"+isLogin);
    }

}
