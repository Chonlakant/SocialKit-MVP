package co.aquario.folkrice.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import co.aquario.folkrice.fragment.FragmentListProductDetail;
import co.aquario.folkrice.fragment.FragmentPayMentsDetail;
import co.aquario.folkrices.R;


/**
 * Created by root1 on 9/18/15.
 */
public class Activity_main_PaymentDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_test);

        FragmentListProductDetail fragment = new FragmentListProductDetail();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();

    }
}
