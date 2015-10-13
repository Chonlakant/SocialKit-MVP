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

import java.util.ArrayList;
import java.util.List;

import co.aquario.mvp.MainApplication;
import co.aquario.mvp.PrefManager;
import co.aquario.mvp.model.AddAddress;
import co.chonlakant.mvp.R;


public class FragmentRegisterThree extends Fragment {
    EditText et_nameTitle;
    EditText et_fristName;
    EditText et_phone;

    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    //    private int mPage;
    String nameTitle;
    String fristName;
    String phone;


    List<AddAddress> list = new ArrayList<>();

    public static FragmentRegisterThree newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentRegisterThree fragment = new FragmentRegisterThree();
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
        View rootView = inflater.inflate(R.layout.register_three, container, false);

//        String password = pref.passWord().getOr("");
//        String username = pref.userName().getOr("");
        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        et_nameTitle = (EditText) rootView.findViewById(R.id.et_nameTitle);
        et_fristName = (EditText) rootView.findViewById(R.id.et_fristName);
        et_phone = (EditText) rootView.findViewById(R.id.et_phone);


//        if (toolbar != null)
//            getActivity().setTitle("ที่อยู่จัดส่ง");

        //  toolbar.setTitle("ที่อยู่จัดส่ง");
        btn_add = (Button) rootView.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameTitle = et_nameTitle.getText().toString();
                fristName = et_fristName.getText().toString();
                phone = et_phone.getText().toString();

                pref.nameTitle().put(nameTitle);
                pref.fristName().put(fristName);
                pref.phone().put(phone);
                pref.isAddressRegister2().put(true);
                pref.commit();

                FragmentPayMentsDetail oneFragment = new FragmentPayMentsDetail();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, oneFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (pref.isAddressRegister2().getOr(false)) {
            et_nameTitle.setText(pref.nameTitle().getOr(""));
            et_fristName.setText(pref.fristName().getOr(""));
            et_phone.setText(pref.phone().getOr(""));

        }
    }
}