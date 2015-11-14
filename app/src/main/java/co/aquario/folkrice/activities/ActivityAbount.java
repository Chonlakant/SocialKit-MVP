package co.aquario.folkrice.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import co.aquario.folkrice.adapter.AdapterAbout;

import co.aquario.folkrices.R;


/**
 * Created by Joseph on 7/7/15.
 */
public class ActivityAbount extends AppCompatActivity {
    RecyclerView rvContacts;
    AdapterAbout mAdapter;
    String[] str = {"http://www.androiddom.com/2011/06/android-shopping-cart-tutorial-part-2.html : GitHub https://github.com/dreamdom/Shopping-Cart-Tutorial-part-2"
            , "etsy/AndroidStaggeredGrid GiiHub:https://github.com/etsy/AndroidStaggeredGrid", "dlazaro66/QRCodeReaderView Github:https://github.com/dlazaro66/QRCodeReaderView"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abount);
        mAdapter = new AdapterAbout(getApplicationContext(), str);
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getApplication()));

        rvContacts.setAdapter(mAdapter);
    }

}
