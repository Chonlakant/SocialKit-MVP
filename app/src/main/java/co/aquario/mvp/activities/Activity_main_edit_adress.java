package co.aquario.mvp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;

import co.aquario.mvp.PrefManager;
import co.aquario.mvp.fragment.FragmentAddressAdd;
import co.aquario.mvp.fragment.FragmentRegisterOne;
import co.chonlakant.mvp.R;


/**
 * Created by root1 on 9/18/15.
 */
public class Activity_main_edit_adress extends AppCompatActivity {
    Boolean status = false;
    RadioButton radioButton1,radioButton2,radioButton3;
    PrefManager pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_edit_address);


        FragmentAddressAdd fragment = new FragmentAddressAdd();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.commit();

    }
}
