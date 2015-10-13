package co.aquario.mvp.fragment;

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


import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import co.aquario.mvp.Config;
import co.aquario.mvp.activities.MainActivity;
import co.aquario.mvp.adapter.ImageListAdapter;
import co.aquario.mvp.model.PostDataNew;
import co.aquario.mvp.model.ShoppingCartHelper;
import co.chonlakant.mvp.R;
import me.drakeet.materialdialog.MaterialDialog;


public class FragmentPayMents extends StatedFragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_add;
    private int mPage;
    ImageListAdapter imageListAdapter;
    ListView listView;
    Button btn_back;
    double subTotal = 0;
    private List<PostDataNew> mCartList = new ArrayList<>();
    MaterialDialog mMaterialDialog;
    public static String[] eatFoodyImages = {
            "http://tech.thaivisa.com/wp-content/uploads/2015/07/paypal.jpg",
            "http://www.thaidnsservice.com/wp-content/uploads/2015/07/kbang.jpg",
            "http://buzzinmediagroup.com/wp-content/uploads/2015/07/SCB_logo.jpg"
    };
    String[] title = {"PayPal","ธนาคารกสิกรไทย","ธนาคารไทยพาณิชย์"};

    private static PayPalConfiguration paypalConfig = new PayPalConfiguration().environment(Config.PAYPAL_ENVIRONMENT).clientId(
            Config.PAYPAL_CLIENT_ID);
    String name;


    public static FragmentPayMents newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentPayMents fragment = new FragmentPayMents();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mPage = getArguments().getInt(ARG_PAGE);
        mCartList = ShoppingCartHelper.getCartList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.payments, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        btn_back = (Button) rootView.findViewById(R.id.btn_back);
        mMaterialDialog = new MaterialDialog(getActivity());
        imageListAdapter = new ImageListAdapter(getActivity(),eatFoodyImages,title);
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
//                    PaypalFragment oneFragment = new PaypalFragment();
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.container, oneFragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
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
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                mCartList.remove(mCartList);
                getActivity().finish();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();


        subTotal = 0;
        double sumAll = 0;
        int quantity = 0;
        for (PostDataNew p : mCartList) {
            quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.getPrice() * quantity + 40;
            sumAll += p.getPrice() * quantity;
            ShoppingCartHelper.setQuantity(p, quantity);
            name = p.getName();

        }

        Log.e("ราคารวม", subTotal + "");



//        number_items.setText("จำนวน: " + quantity);
    }

    public void onBuyPressed() {

        // PAYMENT_INTENT_SALE will cause the payment to complete immediately.
        // Change PAYMENT_INTENT_SALE to
        //   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
        //   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
        //     later via calls from your server.

        PayPalPayment payment = new PayPalPayment(new BigDecimal(subTotal), "THB", "ราคารวม",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(getActivity(), PaymentActivity.class);

        // send the same configuration for restart resiliency
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
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                    Log.e("2222", confirm.toJSONObject().toString(4));
                    Log.e("5555", confirm.getPayment().toJSONObject()
                            .toString(4));

                    String paymentId = confirm.toJSONObject()
                            .getJSONObject("response").getString("id");

                    String payment_client = confirm.getPayment()
                            .toJSONObject().toString();

                    Log.e("Check():", "paymentId: " + paymentId
                            + ", payment_json: " + payment_client);

                    Toast.makeText(getActivity(),payment_client +"" ,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
}