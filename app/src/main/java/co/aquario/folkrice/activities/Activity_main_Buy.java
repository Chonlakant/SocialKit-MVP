package co.aquario.folkrice.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import co.aquario.folkrice.fragment.FragmentAddressAdd;
import co.aquario.folkrices.R;


/**
 * Created by root1 on 9/18/15.
 */
public class Activity_main_Buy extends AppCompatActivity {
    Double  sumTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_test);

        sumTotal = getIntent().getDoubleExtra("sumTotal",0);
        FragmentAddressAdd fragment = new FragmentAddressAdd();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();

    }
}
