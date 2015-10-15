package co.aquario.mvp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.aquario.mvp.MainApplication;
import co.aquario.mvp.PrefManager;
import co.aquario.mvp.activities.Activity_main_register;
import co.aquario.mvp.activities.AlertDialogManager;
import co.aquario.mvp.model.AddAddress;
import co.chonlakant.mvp.R;


public class FragmentLogin extends Fragment {
    EditText et_mail;
    EditText et_password;
    EditText et_confirmPassword;

    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    Button btn_register;
    //    private int mPage;
    String email;
    String pass;
    String confirmpassWord;
    String emailPref;
    String passPref;
    AlertDialogManager alert = new AlertDialogManager();
    List<AddAddress> list = new ArrayList<>();

    public static FragmentLogin newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentLogin fragment = new FragmentLogin();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = MainApplication.getPrefManager();
        Log.e("aaaaa", (pref == null) + "");
        //  mPage = getArguments().getInt(ARG_PAGE);

    }

    PrefManager pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_login, container, false);

        et_mail = (EditText) rootView.findViewById(R.id.email);
        et_mail.setHint("อีเมล์");
        et_password = (EditText) rootView.findViewById(R.id.password);
        et_password.setHint("รหัสผ่าน");




         emailPref = pref.email().getOr("test_folkrice");
         passPref = pref.passWord().getOr("1234");

        Log.e("prefEmail", emailPref);
        Log.e("prefPassWold", passPref);

        btn_add = (Button) rootView.findViewById(R.id.btn_add);
        btn_register = (Button) rootView.findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Activity_main_register.class);
                startActivity(i);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadProfile();

            }
        });

        return rootView;
    }


    private void uploadProfile() {


        email = et_mail.getText().toString();
        pass = et_password.getText().toString();




        if (email.equals(emailPref) && pass.equals(passPref)) {

            Charset chars = Charset.forName("UTF-8");
            String url = "http://api.folkrice.com/account/authenticate";

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("email", email);
            params.put("password", pass);


            AQuery aq = new AQuery(getActivity());
            aq.ajax(url, params, JSONObject.class, this, "updateProfile");


            FragmentPayMentsDetail oneFragment = new FragmentPayMentsDetail();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, oneFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            Toast.makeText(getActivity(), "เข้าสู่ระบบ", Toast.LENGTH_SHORT).show();
        } else {
            alert.showAlertDialog(getActivity(), "ล๊อกอิน ผิดพลาด..", "อีเมล์/พาสเวิด ผิด", false);
        }

    }

    public void updateProfile(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        Log.e("hahaha", jo.toString(4));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pref.isAddress().getOr(false)) {
            et_mail.setText(pref.email().getOr(""));
            et_password.setText(pref.passWord().getOr(""));
        }
    }


}