package co.aquario.mvp.activities;

import android.content.Intent;
import android.os.Bundle;
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
    PostDataNew list;
    MainApplication aController;
    TextView editTextQuantity;
    TextView textCount1 ,textCount2;
    int count;
    int counSum;
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

        textCount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                counSum = count;
                String strI = String.valueOf(counSum);
                editTextQuantity.setText(strI);
                Log.e("counSum1",counSum+"");
            }
        });

        textCount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                counSum = count;
                String strI = String.valueOf(counSum);
                editTextQuantity.setText(strI);
                Log.e("counSum2",counSum+"");
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


        Log.e("ItemActivity_Product",productId+"");
        list = new PostDataNew();
        list.setName(title);
        list.setPrice(price);
        list.setImage(urlImage);
        list.setProductId(productId);

        aController.setProducts(list);


        ProductsSize = aController.getProductsArraylistSize();

        Log.e("ProductsSize", ProductsSize + "");


        // Inflate the ItemFragment

        ItemFragment frag = ItemFragment.newInstance(title, price, decs, urlImage , productId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_container, frag).commit();

        // Initialize and set up the toolbar
        mToolbar = (Toolbar) findViewById(R.id.action_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



//             for(int i = 0 ; i < count ; count++){
//                 final int index = i;
//                 ProductAquery tempProductObject = aController.getProducts(index);
//                 if(!aController.getCart().checkProductInCart(tempProductObject)){
//                     aController.getCart().setProducts(tempProductObject);
//                 }
//             }





        // Set the buy button
        mBuyButton = (TextView) findViewById(R.id.buy_button);
        mBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int quantity = 0;
                try {
                    quantity = counSum;

                    if (quantity < 0) {
                        Toast.makeText(getBaseContext(),
                                "Please enter a quantity of 0 or higher",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(),
                            "Please enter a numeric quantity",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                // If we make it here, a valid quantity was entered
                ShoppingCartHelper.setQuantity(list, quantity);
                Log.e("lisy33", list.getProductId() + "");
                Log.e("lisy33",quantity+"");
                // Close the activity
                finish();

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
}
