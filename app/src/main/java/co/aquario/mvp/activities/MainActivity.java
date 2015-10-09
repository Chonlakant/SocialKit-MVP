package co.aquario.mvp.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.aquario.mvp.adapter.RecipeListAdapter;
import co.aquario.mvp.data.TopMovieListView;
import co.aquario.mvp.di.components.DrawerHeaderView;
import co.aquario.mvp.di.components.SearchView;
import co.aquario.mvp.model.PostDataNew;
import co.aquario.mvp.presenter.MainPresenter;
import co.aquario.mvp.services.ChannelService;
import co.chonlakant.mvp.R;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.subscriptions.Subscriptions;


public class MainActivity extends BaseActivity implements TopMovieListView {

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

    private RecipeListAdapter recipeListAdapter;
    MainPresenter mMainPresenter;
    private Subscription channelsSubscription = Subscriptions.empty();
    private Subscription querySubscription = Subscriptions.empty();
    private Subscription suggestionsSubscription = Subscriptions.empty();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"1111",Toast.LENGTH_LONG).show();
        ButterKnife.bind(this);
        setupActionBar();
        LayoutInflater layoutInflater = getLayoutInflater();
        //Log.e("34444444", articles.get(0).getName());
        View header = layoutInflater.inflate(R.layout.list_header_channel_recipe, null);
        recipeListView.addHeaderView(header);
//        setupViews();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void setArticles(List<PostDataNew> articles) {

        recipeListAdapter = new RecipeListAdapter(this, articles);
        recipeListView.setAdapter(recipeListAdapter);
    }

    private void setupViews() {
        navigationView.addHeaderView(new DrawerHeaderView(this));

        channelsSubscription = AppObservable.bindActivity(this, new ChannelService().getList()).subscribe();
        querySubscription = AppObservable.bindActivity(this, searchView.observe())
                .subscribe();
    }
//
//    private void updateSuggestions(String query) {
//        suggestionsSubscription.unsubscribe();
//        suggestionsSubscription = AppObservable.bindActivity(this, new SuggestionService().get(query))
//                .subscribe(searchView::updateSuggestions);
//    }

}
