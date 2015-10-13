package co.aquario.mvp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.astuetz.PagerSlidingTabStrip;

import co.aquario.mvp.fragment.FragmentAddressAdd;
import co.aquario.mvp.fragment.FragmentPayMentsDetail;
import co.chonlakant.mvp.R;


/**
 * Created by root1 on 9/18/15.
 */
public class Activity_main_PaymentDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_test);

        FragmentPayMentsDetail fragment = new FragmentPayMentsDetail();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();

    }
}
