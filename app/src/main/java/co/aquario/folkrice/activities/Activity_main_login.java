package co.aquario.folkrice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

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
import co.aquario.folkrice.fragment.BaseFragment;
import co.aquario.folkrice.model.AddAddress;
import co.aquario.folkrice.model.Status;
import co.aquario.folkrices.R;


/**
 * Created by root1 on 9/18/15.
 */
public class Activity_main_login extends AppCompatActivity {
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
    String error;
    AlertDialogManager alert = new AlertDialogManager();
    List<AddAddress> list = new ArrayList<>();
    private AQuery aq;
    Toolbar toolbar;
    private BaseFragment _currentFragment;


    PrefManager pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login);
        pref = MainApplication.getPrefManager();
        Log.e("aaaaa", (pref == null) + "");


        aq = new AQuery(getApplication());
        et_mail = (EditText) findViewById(R.id.email);
        et_mail.setHint("อีเมล์");
        et_password = (EditText) findViewById(R.id.password);
        et_password.setHint("รหัสผ่าน");
        statusLogin = new Status();
        statusLogin.setStatus("ok");
        statusLogin.setError("error");


        btn_add = (Button) findViewById(R.id.btn_add);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), Activity_main_register.class);
                startActivity(i);
                finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = et_mail.getText().toString();
                pass = et_password.getText().toString();


                onLoginButtonClick();

            }
        });


    }

    private void onLoginButtonClick() {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "กรุณาใส่อีเมล์", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "กรุณาใส่พาสเวิร์ด", Toast.LENGTH_SHORT).show();
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

        Log.e("Json Return", json.toString(4));
        if(json.get("status").equals("error")){
            Log.e("Json Faile","ผิด");
            Toast.makeText(getApplication(),"อีเมล์หรือพาสเวิดผิด",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplication(),"ล๊อกอินสำเร็จ",Toast.LENGTH_SHORT).show();
        }
        userId = json.getJSONObject("account").optString("id");
        Log.e("accountID",userId);
        pref.userId().put(userId);
        pref.commit();
        ok = json.optString("status");
        error = json.optString("status");

        if (statusLogin.getStatus().equals(ok)) {

            Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentMain);
            finish();

        } else {
            Log.e("1234567", ok);
            Toast.makeText(getApplication(), "Email/PassWord ผิด", Toast.LENGTH_SHORT).show();
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "กรุณาLoginหรือRegister", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplication(), Activity_main_login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
