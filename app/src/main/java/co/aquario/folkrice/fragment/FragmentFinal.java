package co.aquario.folkrice.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.aquario.folkrice.Config;
import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.activities.MainActivity;
import co.aquario.folkrice.model.Country;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;
import co.aquario.folkrices.R;


public class FragmentFinal extends StatedFragment {
    private int i = -1;
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String EXTRA_PRICE = "Item Price";
    private int mPage;
    Button btn_check, btn_home;
    private List<Product> mCartList = new ArrayList<>();

    public static FragmentFinal newInstance(int page, double price) {
        FragmentFinal fragment = new FragmentFinal();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putDouble(EXTRA_PRICE, price);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_final, container, false);
        btn_check = (Button) rootView.findViewById(R.id.btn_check);
        btn_home = (Button) rootView.findViewById(R.id.btn_home);
        mCartList = ShoppingCartHelper.getCartList();


        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mCartList.size(); i++) {
                    ShoppingCartHelper.removeProduct(mCartList.get(i));
                }
                mCartList.clear();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}