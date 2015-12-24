package co.aquario.folkrice.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.activities.Activity_main_edit_adress;
import co.aquario.folkrice.adapter.ProductAdapter;
import co.aquario.folkrice.model.JsonArr;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;
import co.aquario.folkrices.R;


public class FragmentPayMentsDetail extends BaseFragment {


    double lat, lng;
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_price;
    private int mPage;
    PrefManager pref;
    ProductAdapter mAdapter;
    String userId;
    double sumAll = 0;
    double subTotal = 0;
    double subTotalAll = 0;
    Dialog dialog;
    int quantityArr;
    int productIdArr;
    private List<Product> mCartList = new ArrayList<>();
    List<JsonArr> productJson = new ArrayList<>();
    JsonArray myCustomArray;
    TextView txtName, txtCountry, txtDistrict, txtPostal, txtHome, sumPrice, sum, Text_edit, price_car;
    Product p;
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

    public static FragmentPayMentsDetail newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentPayMentsDetail fragment = new FragmentPayMentsDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        final View rootView = inflater.inflate(R.layout.main_list_buy, container, false);

        mCartList = ShoppingCartHelper.getCartList();
        layout_view = (LinearLayout) rootView.findViewById(R.id.layout_view);
        layout_add = (LinearLayout) rootView.findViewById(R.id.layout_add);
        txt_add = (TextView) rootView.findViewById(R.id.txt_add);

        if (pref.isCheckView().getOr(false) != false) {
            layout_view.setVisibility(View.VISIBLE);
            layout_add.setVisibility(View.GONE);
            pref.isCheckView().put(true);
        } else {
            layout_add.setVisibility(View.VISIBLE);
            layout_view.setVisibility(View.GONE);
            pref.isCheckView().put(false);
        }


        mAdapter = new ProductAdapter(mCartList, getActivity().getLayoutInflater(), true, getActivity());
        txtName = (TextView) rootView.findViewById(R.id.txt_name);
        txtCountry = (TextView) rootView.findViewById(R.id.txt_contry);
        txtDistrict = (TextView) rootView.findViewById(R.id.txt_district);
        txtPostal = (TextView) rootView.findViewById(R.id.txt_postal);
        txtHome = (TextView) rootView.findViewById(R.id.txt_home);
        sumPrice = (TextView) rootView.findViewById(R.id.sum_price);
        sum = (TextView) rootView.findViewById(R.id.sum);
        price_car = (TextView) rootView.findViewById(R.id.price_car);
        btn_price = (Button) rootView.findViewById(R.id.btn_price);
        Text_edit = (TextView) rootView.findViewById(R.id.Text_edit);
        sum.setText(total_price + " บาท");
        sumPrice.setText(sub_total + " บาท");

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < mCartList.size(); i++) {
                mCartList.get(i).selected = false;
            }
        }

        txtName.setText(name);
        txtCountry.setText(contry);
        txtDistrict.setText(district);
        txtPostal.setText(postal);
        txtHome.setText(home);
        price_car.setText(delivery_total + "บาท");

        for (Product p : mCartList) {
            quantityArr = ShoppingCartHelper.getProductQuantity(p);
            productIdArr = p.getProductId();
        }


        JsonArr arrProduct = new JsonArr();
        arrProduct.setProduct_id(productIdArr);
        arrProduct.setQuantity(quantityArr);
        productJson.add(arrProduct);


        Gson gson = new GsonBuilder().create();
        myCustomArray = gson.toJsonTree(productJson).getAsJsonArray();

        Text_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Activity_main_edit_adress.class);
                startActivity(i);
            }
        });
        btn_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  checkOut();

                dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.dialog_check);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Bundle bundle = new Bundle();

                        FragmentCheckout oneFragment = new FragmentCheckout();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, oneFragment);
                        bundle.putDouble("Price", subTotal);
                        oneFragment.setArguments(bundle);
                        transaction.commit();
                        dialog.dismiss();

                    }
                }, 1500);


            }
        });

        txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Activity_main_edit_adress.class);
                startActivity(i);
            }
        });


        return rootView;

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
        params.put("ship_to[method]", "grabtaxi");

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