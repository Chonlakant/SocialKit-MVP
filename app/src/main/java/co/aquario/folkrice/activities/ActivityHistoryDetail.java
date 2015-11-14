package co.aquario.folkrice.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.adapter.AdapterAbout;
import co.aquario.folkrice.adapter.AdapterHistory;
import co.aquario.folkrice.adapter.AdapterHistoryDetail;
import co.aquario.folkrice.model.AccountHistory;
import co.aquario.folkrice.model.History;
import co.aquario.folkrice.model.HistoryDetail;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrices.R;
import cz.msebera.android.httpclient.Header;


/**
 * Created by Joseph on 7/7/15.
 */
public class ActivityHistoryDetail extends AppCompatActivity {
    RecyclerView rvContacts;
    AdapterHistoryDetail mAdapter;
    PrefManager pref;
    int idOder;
    String accountId;
    String titleProductId1 = "Black folk rice";
    String titleProductId2 = "Red folk rice";
    String titleProductId3 = "Brown folk rice";
    String titleProductId4 = "White folk rice";
    ArrayList<HistoryDetail> list = new ArrayList<>();

    String url2 = "http://ihdmovie.xyz/root/api_movie/get_movie2.php?uid=1&cat=1";
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("รายละเอียดการสั่งซื้อ");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        AQuery aq = new AQuery(getApplicationContext());

        int order_id = getIntent().getIntExtra("order_id", 0);
        Log.e("ID", order_id + "");

        String url = "http://api.folkrice.com/order/" + order_id;
        Log.e("12345", url);
        mAdapter = new AdapterHistoryDetail(getApplicationContext(), list);
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getApplication()));

        rvContacts.setAdapter(mAdapter);
        aq.ajax(url, JSONObject.class, this, "getjson");
    }


    public void getjson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);

        if (jo != null) {
            //JSONArray ja = jo.optJSONArray("orders");


            double sub_total = jo.optDouble("sub_total");
            double total_price = jo.optDouble("total_price");
            String state = jo.optString("state");
            Log.e("sdsdsd", sub_total + "");





            HistoryDetail item = new HistoryDetail();
            item.setSub_total(sub_total);
            item.setTotal_price(total_price);
            item.setState(state);
            list.add(item);


            mAdapter.notifyDataSetChanged();

            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
    }
}
