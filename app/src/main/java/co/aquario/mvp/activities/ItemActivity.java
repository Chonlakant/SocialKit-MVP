package co.aquario.mvp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.aquario.mvp.MainApplication;
import co.aquario.mvp.fragment.CartFragment;
import co.aquario.mvp.fragment.FragmentPayMentsDetail;
import co.aquario.mvp.fragment.ItemFragment;
import co.aquario.mvp.model.PostDataNew;
import co.aquario.mvp.model.ShoppingCartHelper;
import co.chonlakant.mvp.R;


/**
 * Created by Joseph on 7/7/15.
 */
public class ItemActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ArrayList<PostDataNew> person;
    TextView countTv;
    TextView mBuyButton;

    String title;
    String decs;
    Double price;
    String urlImage;
    int ProductsSize;
    int productId;
    int quantity = 0;
    PostDataNew list;
    MainApplication aController;
    TextView editTextQuantity;
    TextView textCount1, textCount2;
    int count;
    int counSum = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_viewpager);

        editTextQuantity = (TextView) findViewById(R.id.editTextQuantity);
        editTextQuantity.setGravity(Gravity.CENTER);
        textCount1 = (TextView) findViewById(R.id.textCount1);
        textCount2 = (TextView) findViewById(R.id.textCount2);
        aController = (MainApplication) getApplicationContext();

        //editTextQuantity.setInputType(InputType.TYPE_NULL);
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
                Log.e("counSum1", counSum + "");
            }
        });

        textCount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                counSum = count;
                String strI = String.valueOf(counSum);
                editTextQuantity.setText(strI);
                Log.e("counSum2", counSum + "");
            }
        });


        // Get the Item extras
        Bundle extras = getIntent().getExtras();
        productId = extras.getInt("productId");
        title = extras.getString("title");
        price = extras.getDouble("price");
        decs = extras.getString("decs");
        urlImage = extras.getString("urlImage");
//        String itemName = extras.getString(ItemFragment.EXTRA_NAME);
//        curItem = DataSource.get(this).getItem(itemName);


        Log.e("ItemActivity_Product", productId + "");
        list = new PostDataNew();
        list.setName(title);
        list.setPrice(price);
        list.setImage(urlImage);
        list.setProductId(productId);

        aController.setProducts(list);


        ProductsSize = aController.getProductsArraylistSize();

        Log.e("ProductsSize", ProductsSize + "");


        // Inflate the ItemFragment

        ItemFragment frag = ItemFragment.newInstance(title, price, decs, urlImage, productId);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
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
                                ShoppingCartHelper.setQuantity(list, quantity);
                                Log.e("lisy33", list.getProductId() + "");
                                Log.e("lisy33", quantity + "");
                                dialog.dismiss();

                            }
                        }).create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart) { // Show the cart
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
