package co.aquario.folkrice;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;

import java.util.ArrayList;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import co.aquario.folkrice.di.components.ApplicationComponent;
import co.aquario.folkrice.model.ModelCart;
import co.aquario.folkrice.model.Product;


/**
 * Created by Mac on 3/2/15.
 */
public class MainApplication extends Application {

    private static final String ENDPOINT = "http://ihdmovie.xyz/root";
    private ApplicationComponent applicationComponent;

    private static PrefManager prefManager;
    private ArrayList<Product> myProducts = new ArrayList<Product>();
    private ModelCart myCart = new ModelCart();

    @Override
    public void onCreate() {
        super.onCreate();
        prefManager = new PrefManager(getSharedPreferences("App", MODE_PRIVATE));
        Parse.initialize(this, "9yOCaTAxOHo7umxMfNZs9ekcjNyUJchZ9Sh0NZV5", "1QZLnxLv3JjdXWrUrtMERqoHh5X4Eh8r71Daz4SM");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        CustomActivityOnCrash.install(this);
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

    public Product getProducts(int pPosition) {

        return myProducts.get(pPosition);
    }

    public void setProducts(Product Products) {

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
        Log.e("isLogin", ":::" + isLogin);
        Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show();
    }

}
