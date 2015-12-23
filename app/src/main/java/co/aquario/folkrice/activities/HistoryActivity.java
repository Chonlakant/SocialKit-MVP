package co.aquario.folkrice.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.adapter.AdapterHistory;
import co.aquario.folkrice.model.AccountHistory;
import co.aquario.folkrices.R;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView rvContacts;
    AdapterHistory mAdapter;
    PrefManager pref;
    int idOder;
    String accountId;
    Toolbar toolbar;
    ArrayList<AccountHistory> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_history_test);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ประวัติการสั่งซื้อ");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        AQuery aq = new AQuery(getApplicationContext());

        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(getApplication()));

        pref = MainApplication.getPrefManager();

        idOder = pref.order().getOr(0);
        accountId = pref.userId().getOr("ok google");
        String url = "http://api.folkrice.com/order/index?account=" + accountId + "&android=1";
        aq.ajax(url, JSONObject.class, this, "getjson");
        Log.e("ddd", url);
        mAdapter = new AdapterHistory(getApplicationContext(), list);

        rvContacts.setAdapter(mAdapter);

        mAdapter.SetOnItemVideiosClickListener(new AdapterHistory.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String orderId = list.get(position).getOrder_id();
                String title = list.get(position).getNameProduct();
                String product_id = list.get(position).getProduct_id();
                Intent i =  new Intent(getApplicationContext(),ActivityHistoryDetail.class);
                i.putExtra("order_id",orderId);
                i.putExtra("name_product",title);
                i.putExtra("product_id",product_id);
                startActivity(i);
            }
        });


    }

    public void getjson(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        AQUtility.debug("jo", jo);

        int idOderitem;
        int accounOrdertId;
        String createAt;
        String state;
        String product_id;
        if (jo != null) {
            JSONArray ja = jo.optJSONArray("orders");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject obj = ja.optJSONObject(i);

                accounOrdertId = obj.optInt("account_id");
                Log.e("accountId", accounOrdertId + "");
                createAt = obj.optString("created_at");
                state = obj.optString("state");
                Log.e("state", state);


                JSONArray arr = obj.getJSONArray("order_items");
                for (int a = 0; a < arr.length(); a++) {

                    JSONObject job = arr.optJSONObject(a);

                    product_id = job.optString("product_id");

                    Log.e("1342",product_id+"");

                    String   orderId =  job.getString("order_id");

                    idOderitem = job.optInt("id");


                    JSONObject aa = job.getJSONObject("product");
                    String title = aa.optString("name");
                    Log.e("ProductName", title);


                    AccountHistory item = new AccountHistory();
                    item.setAccount_id(idOderitem);
                    item.setState(state);
                    item.setOrder_id(orderId);
                    item.setCreated_at(createAt);
                    item.setNameProduct(title);
                    item.setProduct_id(product_id);

                    list.add(item);
                }





                mAdapter.notifyDataSetChanged();

            }
            mAdapter.notifyDataSetChanged();
            AQUtility.debug("done");

        } else {
            AQUtility.debug("error!");
        }
    }

}
