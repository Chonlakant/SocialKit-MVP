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

import java.util.ArrayList;
import java.util.List;

import co.aquario.mvp.MainApplication;
import co.aquario.mvp.PrefManager;
import co.aquario.mvp.model.AddAddress;
import co.chonlakant.mvp.R;


public class FragmentRegisterTwo extends Fragment {
    EditText et_contry;
    EditText et_area;
    EditText et_district;
    EditText et_landmarks;
    EditText et_road;
    EditText et_zip_code;
    EditText et_home;
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    //    private int mPage;
    String contry;
    String area;
    String district;
    String landmarks;
    String road;
    String zipCode;
    String home;

    String userId;
    List<AddAddress> list = new ArrayList<>();

    public static FragmentRegisterTwo newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentRegisterTwo fragment = new FragmentRegisterTwo();
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
        View rootView = inflater.inflate(R.layout.register_two, container, false);

//        String password = pref.passWord().getOr("");
//        String username = pref.userName().getOr("");
        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            userId = bundle.getString("userId");
            Log.e("เหี้ย",userId+"");
        }

        et_contry = (EditText) rootView.findViewById(R.id.et_contry);
        et_area = (EditText) rootView.findViewById(R.id.et_area);
        et_district = (EditText) rootView.findViewById(R.id.et_district);
        et_landmarks = (EditText) rootView.findViewById(R.id.et_landmarks);
        et_road = (EditText) rootView.findViewById(R.id.et_road);
        et_zip_code = (EditText) rootView.findViewById(R.id.et_zip_code);
        et_home = (EditText) rootView.findViewById(R.id.et_home);

//        if (toolbar != null)
//            getActivity().setTitle("ที่อยู่จัดส่ง");

        //  toolbar.setTitle("ที่อยู่จัดส่ง");
        btn_add = (Button) rootView.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contry = et_contry.getText().toString();
                area = et_area.getText().toString();
                district = et_district.getText().toString();
                landmarks = et_landmarks.getText().toString();
                road = et_road.getText().toString();
                zipCode = et_zip_code.getText().toString();
                home = et_home.getText().toString();


                pref.country().put(contry);
                pref.area().put(area);
                pref.district().put(district);
                pref.landmarks().put(landmarks);
                pref.road().put(road);
                pref.postal().put(zipCode);
                pref.home().put(home);
                pref.isAddressRegister().put(true);
                pref.userId().put(userId);
                pref.commit();


                FragmentRegisterThree oneFragment = new FragmentRegisterThree();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, oneFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getActivity(),"ขั้นตอนถัดไป",Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (pref.isAddressRegister().getOr(false)) {

            et_contry.setText(pref.country().getOr(""));
            et_area.setText(pref.area().getOr(""));
            et_district.setText(pref.district().getOr(""));
            et_landmarks.setText(pref.landmarks().getOr(""));
            et_road.setText(pref.road().getOr(""));
            et_zip_code.setText(pref.postal().getOr(""));
            et_home.setText(pref.home().getOr(""));

        }
    }
}