package co.aquario.folkrice.fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.AdapterView.OnItemClickListener;

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


public class FragmentPayMents extends StatedFragment {
    private int i = -1;
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String EXTRA_PRICE = "Item Price";
    Button btn_add;
    private int mPage;
    Dialog dialog;
    PrefManager pref;
    ImageListAdapter imageListAdapter;
    ArrayList<Country> list = new ArrayList<>();
    ListView listView;
    Button btn_back;
    double subTotal = 0;
    boolean isCheck = false;
    String bank;
    private List<Product> mCartList = new ArrayList<>();

    public static String[] eatFoodyImages = {
            "http://www.thaidnsservice.com/wp-content/uploads/2015/07/kbang.jpg",
            "http://buzzinmediagroup.com/wp-content/uploads/2015/07/SCB_logo.jpg"
    };
    String[] title = {"ธนาคารกสิกรไทย", "ธนาคารไทยพาณิชย์"};

    private static PayPalConfiguration paypalConfig = new PayPalConfiguration().environment(Config.PAYPAL_ENVIRONMENT).clientId(
            Config.PAYPAL_CLIENT_ID);
    String userId;
    double price;
    String name;
    String contry;
    String district;
    String postal;
    String home;
    String lastName;
    String note;
    String email;
    String landmarks;
    int delivery_total;
    LinearLayout layout_view, layout_add;
    TextView txt_add;
    String orderId;
    int total_price;
    int sub_total;

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
        userId = pref.userId().getOr("7");
        orderId = pref.orderId().getOr("184");
        delivery_total = pref.delivery_total().getOr(0);
        Log.e("delivery_total", delivery_total + "");
        Log.e("userId", userId + "");
        Log.e("orderId", orderId + "");
        name = pref.fristName().getOr("");
        contry = pref.country().getOr("");
        district = pref.district().getOr("");
        postal = pref.postal().getOr("");
        home = pref.home().getOr("");
        lastName = pref.lastName().getOr("");
        note = pref.note().getOr("");
        email = pref.email().getOr("");
        landmarks = pref.landmarks().getOr("");
        total_price = pref.total_price().getOr(0);
        sub_total = pref.sub_total().getOr(0);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.payments, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        btn_back = (Button) rootView.findViewById(R.id.btn_back);
        mCartList = ShoppingCartHelper.getCartList();
        ArrayList<Country> countryList = new ArrayList<Country>();
        Country country = new Country("ธนาคารกสิกรไทย", "http://www.thaidnsservice.com/wp-content/uploads/2015/07/kbang.jpg", false);
        countryList.add(country);
        country = new Country("ธนาคารไทยพาณิชย์", "http://job168.fahsiam.com/wp-content/uploads/2015/05/%E0%B8%98%E0%B8%99%E0%B8%B2%E0%B8%84%E0%B8%B2%E0%B8%A3%E0%B9%84%E0%B8%97%E0%B8%A2%E0%B8%9E%E0%B8%B2%E0%B8%93%E0%B8%B4%E0%B8%8A%E0%B8%A2%E0%B9%8C.jpg", false);
        countryList.add(country);
        dialog = new Dialog(getActivity());
        //create an ArrayAdaptar from the String Array
        imageListAdapter = new ImageListAdapter(getActivity(), R.layout.listview_item_image, countryList);


        listView.setAdapter(imageListAdapter);

        for (int i = 0; i < mCartList.size(); i++) {
            mCartList.get(i).selected = false;
        }

        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
        getActivity().startService(intent);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Country country = (Country) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOut();

                dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.dialog_final);
                dialog.show();
                final LinearLayout linearLayout_progress = (LinearLayout) dialog.findViewById(R.id.linearLayout_progress);
                final LinearLayout linearLayout_check = (LinearLayout) dialog.findViewById(R.id.linearLayout_check);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayout_progress.setVisibility(View.VISIBLE);
                        isCheck = true;
                        if (isCheck != false) {
                            linearLayout_progress.setVisibility(View.GONE);
                            linearLayout_check.setVisibility(View.VISIBLE);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < mCartList.size(); i++) {
                                        ShoppingCartHelper.removeProduct(mCartList.get(i));
                                    }
                                    mCartList.clear();
                                    Intent i = new Intent(getActivity(), MainActivity.class);
                                    startActivity(i);
                                    getActivity().finish();
                                }
                            }, 2500);


                        }
                    }
                }, 2500);


            }
        });
        return rootView;
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

    public class ImageListAdapter extends ArrayAdapter<Country> {

        private ArrayList<Country> countryList;

        public ImageListAdapter(Context context, int textViewResourceId,
                                ArrayList<Country> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Country>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            ImageView item_image;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.listview_item_image, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.item_name);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox);
                holder.item_image = (ImageView) convertView.findViewById(R.id.item_image);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Country country = (Country) cb.getTag();
                        country.setSelected(cb.isChecked());
                        bank = cb.getText().toString();
                        Log.e("bank", bank);
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Country country = countryList.get(position);
            holder.name.setText(country.getCode());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            Picasso.with(getActivity())
                    .load(country.getName())
//                .resize(200, 250)
                    .into(holder.item_image);

            return convertView;

        }

    }

    private void checkOut() {
//        int id = Integer.parseInt(userId);
        Charset chars = Charset.forName("UTF-8");

        String url = "http://api.folkrice.com/order/" + orderId + "/checkout";
        Log.e("urlACtive", url);

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
        params.put("ship_to[method]", bank);

        AQuery aq = new AQuery(getActivity());
        aq.ajax(url, params, JSONObject.class, this, "checkOut");
    }

    public void checkOut(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        Log.e("Json Return", jo.toString(4));

        delivery_total = jo.getInt("delivery_total");
        total_price = jo.getInt("total_price");
        sub_total = jo.optInt("sub_total");
        pref.delivery_total().put(delivery_total);
        pref.total_price().put(total_price);
        pref.sub_total().put(sub_total);
        pref.commit();
    }
}