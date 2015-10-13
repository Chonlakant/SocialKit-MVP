package co.aquario.mvp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import co.aquario.mvp.MainApplication;
import co.aquario.mvp.PrefManager;
import co.aquario.mvp.model.AddAddress;
import co.aquario.mvp.model.Storage;
import co.chonlakant.mvp.R;


public class FragmentAddressAdd extends Fragment {
    MaterialEditText et_name;
    MaterialEditText et_phone;
    MaterialEditText et_contry;
    MaterialEditText et_district;
    MaterialEditText et_postal;
    MaterialEditText et_home;

    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
//    private int mPage;
    String name;
    String phone;
    String contry;
    String district;
    String postal;
    String home;
    Toolbar toolbar;
    List<AddAddress> list = new ArrayList<>();
    public static FragmentAddressAdd newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentAddressAdd fragment = new FragmentAddressAdd();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = MainApplication.getPrefManager();
        Log.e("aaaaa",(pref == null) + "" );
      //  mPage = getArguments().getInt(ARG_PAGE);

    }

    PrefManager pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_address, container, false);

//        String password = pref.passWord().getOr("");
//        String username = pref.userName().getOr("");
        //toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        et_name = (MaterialEditText) rootView.findViewById(R.id.et_name);
        et_phone = (MaterialEditText) rootView.findViewById(R.id.et_phone);
        et_contry = (MaterialEditText) rootView.findViewById(R.id.et_contry);
        et_district = (MaterialEditText) rootView.findViewById(R.id.et_district);
        et_postal = (MaterialEditText) rootView.findViewById(R.id.et_postal);
        et_home = (MaterialEditText) rootView.findViewById(R.id.et_home);

//        if (toolbar != null)
//            getActivity().setTitle("ที่อยู่จัดส่ง");

      //  toolbar.setTitle("ที่อยู่จัดส่ง");
        btn_add = (Button) rootView.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString();
                phone = et_phone.getText().toString();
                contry = et_contry.getText().toString();
                district = et_district.getText().toString();
                postal = et_postal.getText().toString();
                home = et_home.getText().toString();
                Log.e("name455", name);


                pref.name().put(name);
                pref.phone().put(phone);
                pref.country().put(contry);
                pref.district().put(district);
                pref.postal().put(postal);
                pref.home().put(home);
                pref.isAddress().put(true);
                pref.commit();

                AddAddress add = new AddAddress(name, phone, contry, district, postal, home);
                list.add(add);

                Storage.address = add;

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
        if(pref.isAddress().getOr(false)){
            et_name.setText(pref.name().getOr("null"));
            et_phone.setText(pref.phone().getOr("null"));
            et_contry.setText(pref.country().getOr("null"));
            et_district.setText(pref.district().getOr("null"));
            et_postal.setText(pref.postal().getOr("null"));
            et_home.setText(pref.home().getOr("null"));

        }
    }
}