package co.aquario.folkrice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.activities.Activity_main_PaymentDetail;
import co.aquario.folkrice.activities.MainActivity;
import co.aquario.folkrice.model.AddAddress;
import co.aquario.folkrices.R;


public class FragmentRegisterThree extends Fragment {
    EditText et_nameTitle;
    EditText et_fristName;
    EditText et_phone;
    EditText et_lastName;
    EditText et_note;

    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    //    private int mPage;
    String nameTitle;
    String fristName;
    String phone;
    String lastName;
    String note;


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
    }

    PrefManager pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_three, container, false);

        et_nameTitle = (EditText) rootView.findViewById(R.id.et_nameTitle);
        et_fristName = (EditText) rootView.findViewById(R.id.et_fristName);
        et_phone = (EditText) rootView.findViewById(R.id.et_phone);
        et_lastName = (EditText) rootView.findViewById(R.id.et_lastName);
        et_note = (EditText) rootView.findViewById(R.id.et_note);

        btn_add = (Button) rootView.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameTitle = et_nameTitle.getText().toString();
                fristName = et_fristName.getText().toString();
                phone = et_phone.getText().toString();
                lastName = et_lastName.getText().toString();
                note = et_note.getText().toString();


                pref.nameTitle().put(nameTitle);
                pref.fristName().put(fristName);
                pref.phone().put(phone);
                pref.lastName().put(lastName);
                pref.note().put(note);
                pref.isCheckView().put(true);
                pref.isAddressRegister2().put(true);
                pref.commit();

                Intent i =new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), "ลงทะเบียนสำเร็จ", Toast.LENGTH_LONG).show();
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