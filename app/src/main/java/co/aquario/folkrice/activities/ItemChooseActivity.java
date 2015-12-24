package co.aquario.folkrice.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.constant.Constant;
import co.aquario.folkrice.fragment.CartFragment;
import co.aquario.folkrice.fragment.ItemProductFragment;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;
import co.aquario.folkrices.R;


/**
 * Created by Joseph on 7/7/15.
 */
public class ItemChooseActivity extends AppCompatActivity {
    int countToobar;
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
    Spinner spQuantity;
    boolean isCheckProduct = false;
    private List<Product> mCartList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_viewpager);
        pref = MainApplication.getPrefManager();
        spQuantity = (Spinner) findViewById(R.id.spQuantity);
        aController = (MainApplication) getApplicationContext();

        mCartList = ShoppingCartHelper.getCartList();
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, Constant.QUANTITY_LIST);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spQuantity.setAdapter(dataAdapter);

        for (int i = 0; i < mCartList.size(); i++) {
            countToobar = i;
        }


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
                quantity = Integer.valueOf(spQuantity.getSelectedItem().toString());

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
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
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
        MenuItem menuItem = menu.findItem(R.id.testAction);
        menuItem.setIcon(buildCounterDrawable(countToobar, R.drawable.shopping_bag));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.testAction) {
            CartFragment cartFrag = new CartFragment();
            cartFrag.show(getSupportFragmentManager(), "My Cart");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.counter_menuitem_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void doIncrease() {
        count++;
        invalidateOptionsMenu();
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
