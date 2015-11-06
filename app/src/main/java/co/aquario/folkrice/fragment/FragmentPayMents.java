package co.aquario.folkrice.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

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
import co.aquario.folkrice.R;
import co.aquario.folkrice.activities.MainActivity;
import co.aquario.folkrice.adapter.ImageListAdapter;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;

import me.drakeet.materialdialog.MaterialDialog;


public class FragmentPayMents extends StatedFragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String EXTRA_PRICE = "Item Price";
    Button btn_add;
    private int mPage;
    PrefManager pref;
    ImageListAdapter imageListAdapter;
    ListView listView;
    Button btn_back;
    double subTotal = 0;
    private List<Product> mCartList = new ArrayList<>();
    MaterialDialog mMaterialDialog;
    public static String[] eatFoodyImages = {
            "http://tech.thaivisa.com/wp-content/uploads/2015/07/paypal.jpg",
            "http://www.thaidnsservice.com/wp-content/uploads/2015/07/kbang.jpg",
            "http://buzzinmediagroup.com/wp-content/uploads/2015/07/SCB_logo.jpg"
    };
    String[] title = {"PayPal", "ธนาคารกสิกรไทย", "ธนาคารไทยพาณิชย์"};

    private static PayPalConfiguration paypalConfig = new PayPalConfiguration().environment(Config.PAYPAL_ENVIRONMENT).clientId(
            Config.PAYPAL_CLIENT_ID);
    String name;

    double price;

    String contry;
    String district;
    String postal;
    String home;
    String lastName;
    String note;
    String email;
    String landmarks;

    String userId;

    public static FragmentPayMents newInstance(int page, double price) {
        FragmentPayMents fragment = new FragmentPayMents();
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
        Bundle args = getArguments();
        //mItem = DataSource.get(getActivity()).getItem(args.getString(EXTRA_NAME));

        if (args != null) {

            price = args.getDouble("Price");


        }
        pref = MainApplication.getPrefManager();

        userId = pref.userId().getOr("");

        name = pref.fristName().getOr("");
        contry = pref.country().getOr("");
        district = pref.district().getOr("");
        postal = pref.postal().getOr("");
        home = pref.home().getOr("");
        lastName = pref.lastName().getOr("");
        note = pref.note().getOr("");
        email = pref.email().getOr("");
        landmarks = pref.landmarks().getOr("");
        mCartList = ShoppingCartHelper.getCartList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.payments, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        btn_back = (Button) rootView.findViewById(R.id.btn_back);
        mMaterialDialog = new MaterialDialog(getActivity());


        //mItem = DataSource.get(getActivity()).getItem(args.getString(EXTRA_NAME));


        imageListAdapter = new ImageListAdapter(getActivity(), eatFoodyImages, title);
        listView.setAdapter(imageListAdapter);

        for (int i = 0; i < mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }

        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        getActivity().startService(intent);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    onBuyPressed();
                }
                if (i == 1) {
                    SimpleDialogFragment.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                            .setTitle("การชำระเงิน").setMessage
                            ("โอนเงิน เข้าบัญชี ธนสคารกสิกรไทย(KBANK) นายอนุกูล ทรายเพชร และ นาย ธนานนท์ เงินถาวร 730-2-12382-5 เซนทรัลลาดพร้าวแจ้งโอนเงินมาที่ Folkrice.th@gmail.com หรือ Line FolkRice")
                            .setNegativeButtonText("Close")
                            .show();
                }
                if (i == 2) {
                    SimpleDialogFragment.createBuilder(getActivity(), getActivity().getSupportFragmentManager())
                            .setTitle("การชำระเงิน").setMessage
                            ("โอนเงิน เข้าบัญชี ธนสคารกสิกรไทย(KBANK) นายอนุกูล ทรายเพชร และ นาย ธนานนท์ เงินถาวร 730-2-12382-5 เซนทรัลลาดพร้าวแจ้งโอนเงินมาที่ Folkrice.th@gmail.com หรือ Line FolkRice")
                            .setNegativeButtonText("Close")
                            .show();
                }
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkOut();

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

    private void checkOut() {
        int id = Integer.parseInt(userId);
        Charset chars = Charset.forName("UTF-8");

        String url =  "http://api.folkrice.com/order/"+id+"/checkout";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("customer[first_name]", name);
        params.put("customer[last_name]", lastName);
        params.put("customer[email]", email);
        params.put("ship_to[first_name]", name);
        params.put("ship_to[last_name]", lastName);
        params.put("ship_to[address_1]", landmarks);
        params.put("ship_to[sub_district]", district);
        params.put("ship_to[district]", district);
        params.put("ship_to[province]", contry);
        params.put("ship_to[postcode]", postal);
        params.put("ship_to[note]", note);
        params.put("lat", "100");
        params.put("lon", "31");
        params.put("ship_to[method]", "grabtaxi");

        AQuery aq = new AQuery(getActivity());
        aq.ajax(url, params, JSONObject.class, this, "checkOut");
    }

    public void checkOut(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        Log.e("Json Return", jo.toString(4));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void onBuyPressed() {

        PayPalPayment payment = new PayPalPayment(new BigDecimal(price), "THB", "ราคารวม", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {

                    String paymentId = confirm.toJSONObject().getJSONObject("response").getString("id");

                    String payment_client = confirm.getPayment()
                            .toJSONObject().toString();

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
}