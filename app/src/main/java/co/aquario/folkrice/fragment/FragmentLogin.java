package co.aquario.folkrice.fragment;

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
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.R;
import co.aquario.folkrice.activities.Activity_main_register;
import co.aquario.folkrice.activities.AlertDialogManager;
import co.aquario.folkrice.model.AddAddress;
import co.aquario.folkrice.model.Status;



public class FragmentLogin extends Fragment {
    EditText et_mail;
    EditText et_password;
    EditText et_confirmPassword;
    String userId;
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    Button btn_register;
    //    private int mPage;
    String email;
    String pass;
    String confirmpassWord;
    String emailPref;
    String passPref;
    Status statusLogin;
    String ok;
    AlertDialogManager alert = new AlertDialogManager();
    List<AddAddress> list = new ArrayList<>();
    private AQuery aq;
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
        aq = new AQuery(getActivity());
        et_mail = (EditText) rootView.findViewById(R.id.email);
        et_mail.setHint("อีเมล์");
        et_password = (EditText) rootView.findViewById(R.id.password);
        et_password.setHint("รหัสผ่าน");
        statusLogin = new Status();
        statusLogin.setStatus("error");


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
                email = et_mail.getText().toString();
                pass = et_password.getText().toString();



                onLoginButtonClick();

                // checkLogin();
            }
        });

        return rootView;
    }


    private void onLoginButtonClick() {

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(),"กรุณาใส่อีเมล์",Toast.LENGTH_SHORT).show();
            return;
        }if(TextUtils.isEmpty(pass)) {
            Toast.makeText(getActivity(),"กรุณาใส่พาสเวิร์ด",Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "http://api.folkrice.com/account/authenticate";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("password", pass);

        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
        cb.url(url).type(JSONObject.class).params(params).weakHandler(this, "loginCallback");
        cb.header("Content-Type", "application/x-www-form-urlencoded");
        aq.ajax(cb);

    }

    public void loginCallback(String url, JSONObject json, AjaxStatus status) throws JSONException {

        Log.e("fdfd", json.toString(4));
        userId = json.getJSONObject("account").optString("id");

        String emailJosn = json.getJSONObject("account").optString("email");
        Log.e("7777", userId);
        pref.userId().put(userId);
        pref.commit();
        ok = json.optString("status");

        if(email.equals(emailJosn)){
            FragmentPayMentsDetail oneFragment = new FragmentPayMentsDetail();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, oneFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            Toast.makeText(getActivity(), "เข้าสู่ระบบ", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
//        if (pref.isAddress().getOr(false)) {
//            et_mail.setText(pref.email().getOr(""));
//            et_password.setText(pref.passWord().getOr(""));
//        }
    }


}