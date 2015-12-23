package co.aquario.folkrice.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.adapter.AdapterListProductMain;
import co.aquario.folkrice.data.ListProduct;
import co.aquario.folkrice.di.components.DrawerHeaderView;
import co.aquario.folkrice.di.components.SearchView;
import co.aquario.folkrice.fragment.CartFragment;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;
import co.aquario.folkrice.presenter.MainPresenter;
import co.aquario.folkrice.services.ChannelService;
import co.aquario.folkrices.R;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.subscriptions.Subscriptions;


public class MainActivity extends BaseActivity implements ListProduct {
    private int count = 0;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.search_view)
    SearchView searchView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.channel_recipe_list)
    StaggeredGridView recipeListView;

    @Bind(R.id.query_input)
    public EditText queryInput;

    private int mNavItemId;
    private AdapterListProductMain recipeListAdapter;
    MainPresenter mMainPresenter;
    PrefManager pref;
    FloatingActionButton fabBtn;
    private Subscription channelsSubscription = Subscriptions.empty();
    private Subscription querySubscription = Subscriptions.empty();
    private Subscription suggestionsSubscription = Subscriptions.empty();

    private List<Product> mCartList = new ArrayList<>();
    boolean check;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    String title = "";
    Boolean isLogin = false;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupActionBar();
        LayoutInflater layoutInflater = getLayoutInflater();
        hideKeyboard(queryInput);
//        ParseAnalytics.trackAppOpened(getIntent());
//
//        ParseInstallation.getCurrentInstallation().saveInBackground();
        //Log.e("34444444", articles.get(0).getName());
        fabBtn = (FloatingActionButton) findViewById(R.id.fabBtn);
        View header = layoutInflater.inflate(R.layout.list_header_channel_recipe, null);
        recipeListView.addHeaderView(header);
        mCartList = ShoppingCartHelper.getCartList();


        for (int i = 0; i < mCartList.size(); i++) {
            Log.e("mCartList", mCartList.get(i) + "");
            count = i;
        }

        setupViews();
        pref = MainApplication.getPrefManager();
        Log.e("MainActivity", pref.userId().getOr("มาไหม") + "");
        userId = pref.userId().getOr("มาไหม");
        pref.userId().put(userId);
        pref.commit();
        initView();
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(drawerLayout, "QR Code", Snackbar.LENGTH_LONG);
                snackbar.show();
                Intent i = new Intent(getApplicationContext(), DecoderActivity.class);
                startActivity(i);

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_list, menu);

        MenuItem menuItem = menu.findItem(R.id.testAction);
        menuItem.setIcon(buildCounterDrawable(count, R.drawable.shopping_bag));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.testAction) { // Show the cart
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

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    private void initView() {
        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(this);
        mMainPresenter.loadData();
    }

    @Override
    public void setArticles(List<Product> articles) {
        recipeListAdapter = new AdapterListProductMain(this, articles);
        recipeListView.setAdapter(recipeListAdapter);
    }

    private void setupViews() {
        navigationView.addHeaderView(new DrawerHeaderView(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_home:
                        Intent i = new Intent(getApplication(), HistoryActivity.class);
                        startActivity(i);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.navigation_my_recipes:
//                        boolean isCheck = pref.isLogin().getOr(false);
//                        MainApplication.logout(getApplicationContext());
//                        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
//                        pref.isCheckProduct().put(false);
//                        pref.commit();
//                        if (pref.isLogin().getOr(true) && !isCheck) {
//                            menuItem.setTitle("Logout");
//                        }

//                        login();
//                        menuItem.setTitle(title);


                        isLogin = pref.isLogin().getOr(false);

                        if (pref.isLogin().getOr(true) && !isLogin) {
                            Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                            Intent ai = new Intent(getApplicationContext(), Activity_main_login.class);
                            startActivity(ai);
                            isLogin = true;
                            pref.isLogin().put(isLogin);
                            pref.commit();

                        }

                        drawerLayout.closeDrawers();
                        break;

                    case R.id.log_out:
                        MainApplication.logout(getApplicationContext());
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.abount:
                        Intent abount = new Intent(getApplication(), ActivityAbount.class);
                        startActivity(abount);

                        drawerLayout.closeDrawers();
                        break;
                }

                Log.d("MENU ITEM", menuItem.getTitle().toString());
                return false;
            }
        });

        channelsSubscription = AppObservable.bindActivity(this, new ChannelService().getList()).subscribe();
        querySubscription = AppObservable.bindActivity(this, searchView.observe())
                .subscribe();
    }

    public void login() {

        title = "Logout";
        isLogin = pref.isLogin().getOr(false);

        if (pref.isLogin().getOr(true) && !isLogin) {

            Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), Activity_main_login.class);
            startActivity(i);
            isLogin = true;
            pref.isLogin().put(isLogin);
            pref.commit();
            title = "Logout";
        } else {
            MainApplication.logout(getApplicationContext());
            title = "Logout";
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
//    boolean doubleBackToExitPressedOnce = false;
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "แตะ BACK อีกครั้งเพื่อออกจาก Folkrice", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
//    }

}
