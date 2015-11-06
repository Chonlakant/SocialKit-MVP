package co.aquario.folkrice.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.R;
import co.aquario.folkrice.fragment.CartFragment;
import co.aquario.folkrice.fragment.ItemProductFragment;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;



/**
 * Created by Joseph on 7/7/15.
 */
public class ItemChooseActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ArrayList<Product> person;
    TextView countTv;
    TextView mBuyButton;
    PrefManager pref;
    String title;
    String decs;
    Double price;
    String urlImage;
    int ProductsSize;
    int productId;
    int quantity = 0;
    Product list;
    MainApplication aController;
    TextView editTextQuantity;
    TextView textCount1, textCount2;
    int count;
    int counSum = 1;
    boolean isCheckProduct = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_viewpager);
        pref = MainApplication.getPrefManager();

        editTextQuantity = (TextView) findViewById(R.id.editTextQuantity);
        editTextQuantity.setGravity(Gravity.CENTER);
        textCount1 = (TextView) findViewById(R.id.textCount1);
        textCount2 = (TextView) findViewById(R.id.textCount2);
        aController = (MainApplication) getApplicationContext();

        int one = 0;
        String i = String.valueOf(counSum);
        editTextQuantity.setText(i);
        textCount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                counSum = count;
                String strI = String.valueOf(counSum);
                editTextQuantity.setText(strI);
            }
        });

        textCount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                counSum = count;
                String strI = String.valueOf(counSum);
                editTextQuantity.setText(strI);
            }
        });


        Bundle extras = getIntent().getExtras();
        productId = extras.getInt("productId");
        title = extras.getString("title");
        price = extras.getDouble("price");
        decs = extras.getString("decs");
        urlImage = extras.getString("urlImage");


        Log.e("ItemActivity_Product", productId + "");
        list = new Product();
        list.setName(title);
        list.setPrice(price);
        list.setImage(urlImage);
        list.setProductId(productId);

        aController.setProducts(list);


        ProductsSize = aController.getProductsArraylistSize();


        ItemProductFragment frag = ItemProductFragment.newInstance(title, price, decs, urlImage, productId);
        getSupportFragmentManager().beginTransaction().add(R.id.item_container, frag).commit();

        // Initialize and set up the toolbar
        mToolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // Set the buy button
        mBuyButton = (TextView) findViewById(R.id.buy_button);
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = counSum;
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemChooseActivity.this);
                builder.setTitle(R.string.add_cart)
                        .setMessage(title + " คุณต้องการเพิ่มในรายการของคุณไหม?")
                        .setNegativeButton(R.string.show_cart, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Close the dialog window
                                dialog.dismiss();
                                CartFragment cartFrag = new CartFragment();
                                cartFrag.show(getSupportFragmentManager(), "My Cart");
                            }
                        })
                        .setPositiveButton( R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss dialog and open cart

                                isCheckProduct = true;

                                pref.isCheckProduct().put(isCheckProduct);
                                pref.commit();
                                ShoppingCartHelper.setQuantity(list, quantity);

                                dialog.dismiss();

                            }
                        }).create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart) {
            CartFragment cartFrag = new CartFragment();
            cartFrag.show(getSupportFragmentManager(), "My Cart");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        // Closes the activity if end of back stack
        if (count <= 1) {
            finish();
        } else {
            getFragmentManager().popBackStack();
        }

    }

}
