package co.aquario.mvp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.aquario.mvp.MainApplication;
import co.aquario.mvp.PrefManager;
import co.aquario.mvp.model.AddAddress;
import co.chonlakant.mvp.R;


public class FragmentRegisterOne extends Fragment {
    EditText et_mail;
    EditText et_password;
    EditText et_confirmPassword;
    String userId;
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    //    private int mPage;
    String email;
    String pass;
    String confirmpassWord;
    PrefManager pref;
    int id;
    List<AddAddress> list = new ArrayList<>();

    public static FragmentRegisterOne newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentRegisterOne fragment = new FragmentRegisterOne();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_one, container, false);

//        String password = pref.passWord().getOr("");
//        String username = pref.userName().getOr("");
        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        et_mail = (EditText) rootView.findViewById(R.id.email);
        et_mail.setHint("อีเมล์");
        et_password = (EditText) rootView.findViewById(R.id.password);
        et_password.setHint("รหัสผ่าน");
        et_confirmPassword = (EditText) rootView.findViewById(R.id.confirm_password);
        et_confirmPassword.setHint("ยืนยันรหัสผ่าน");

//        if (toolbar != null)
//            getActivity().setTitle("ที่อยู่จัดส่ง");

        //  toolbar.setTitle("ที่อยู่จัดส่ง");
        btn_add = (Button) rootView.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                uploadProfile();

                FragmentRegisterTwo oneFragment = new FragmentRegisterTwo();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, oneFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return rootView;
    }

    private void uploadProfile() {

        email = et_mail.getText().toString();
        pass = et_password.getText().toString();
        //confirmpassWord = et_confirmPassword.getText().toString();

        pref.email().put(email);
        pref.passWord().put(pass);
        pref.isAddress().put(true);
        //pref.confirmpassWord().put(confirmpassWord);
        pref.commit();


        Charset chars = Charset.forName("UTF-8");
        String url = "http://api.folkrice.com/account/create";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("password", pass);


        AQuery aq = new AQuery(getActivity());
        aq.ajax(url, params, JSONObject.class, this, "updateProfile");
    }

    public void updateProfile(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        Log.e("hahaha", jo.toString(4));

        userId = jo.getJSONObject("account").optString("id");
       // id = Integer.parseInt(userId);
        pref.userId().put(userId);
        pref.commit();
        Log.e("sdsdsd", userId);

        Toast.makeText(getActivity(), "ขั้นตอนถัดไป", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (pref.isAddress().getOr(false)) {
            et_mail.setText(pref.email().getOr(""));
            et_password.setText(pref.passWord().getOr(""));
//            et_confirmPassword.setText(pref.confirmpassWord().getOr(""));

        }
    }
}