package co.aquario.folkrice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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
import co.aquario.folkrice.R;
import co.aquario.folkrice.activities.Activity_main_edit_adress;
import co.aquario.folkrice.adapter.ProductAdapter;
import co.aquario.folkrice.model.JsonArr;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;


public class FragmentPayMentsDetail extends BaseFragment {



    double lat, lng;
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_price;
    private int mPage;
    PrefManager pref;
    ProductAdapter mAdapter;
    ListView list;
    String userId;
    double sumAll = 0;
    double subTotal = 0;
    double subTotalAll = 0;
    double latitude;
    double longitude;
    int quantityArr;
    int productIdArr;
    private List<Product> mCartList = new ArrayList<>();
    List<JsonArr> productJson = new ArrayList<>();
    JsonArray myCustomArray;
    TextView txtName, txtCountry, txtDistrict, txtPostal, txtHome, sumPrice, sum, Text_edit;
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
        userId = pref.userId().getOr("");
        Log.e("userId", userId);
        name = pref.fristName().getOr("");
        contry = pref.country().getOr("");
        district = pref.district().getOr("");
        postal = pref.postal().getOr("");
        home = pref.home().getOr("");
        lastName = pref.lastName().getOr("");
        note = pref.note().getOr("");
        email = pref.email().getOr("");
        landmarks = pref.landmarks().getOr("");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_list_buy, container, false);
        list = (ListView) rootView.findViewById(R.id.cart_items_list);
        mCartList = ShoppingCartHelper.getCartList();


        mAdapter = new ProductAdapter(mCartList, getActivity().getLayoutInflater(), true, getActivity());
        txtName = (TextView) rootView.findViewById(R.id.txt_name);
        txtCountry = (TextView) rootView.findViewById(R.id.txt_contry);
        txtDistrict = (TextView) rootView.findViewById(R.id.txt_district);
        txtPostal = (TextView) rootView.findViewById(R.id.txt_postal);
        txtHome = (TextView) rootView.findViewById(R.id.txt_home);
        sumPrice = (TextView) rootView.findViewById(R.id.sum_price);
        sum = (TextView) rootView.findViewById(R.id.sum);
        btn_price = (Button) rootView.findViewById(R.id.btn_price);
        Text_edit = (TextView) rootView.findViewById(R.id.Text_edit);


        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < mCartList.size(); i++) {
                mCartList.get(i).selected = false;
            }
        }

        list.setAdapter(mAdapter);


        txtName.setText(name);
        txtCountry.setText(contry);
        txtDistrict.setText(district);
        txtPostal.setText(postal);
        txtHome.setText(home);

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

                uploadProduct();

                Bundle bundle = new Bundle();

                FragmentPayMents oneFragment = new FragmentPayMents();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, oneFragment);
                bundle.putDouble("Price", subTotal);
                oneFragment.setArguments(bundle);
                transaction.commit();


            }
        });


        return rootView;

    }

    private void uploadProduct() {

        Charset chars = Charset.forName("UTF-8");
        String url = "http://api.folkrice.com/order/add";

        int id = Integer.parseInt(userId);

        Log.e("NONONONON", id + "");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", myCustomArray);
        params.put("account", id);

        AQuery aq = new AQuery(getActivity());
        aq.ajax(url, params, JSONObject.class, this, "updateProduct");
    }

    public void updateProduct(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        Log.e("Json Return", jo.toString(4));

    }


    @Override
    public void onResume() {
        super.onResume();


        double i = 20.00;
        // Refresh the data
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        int quantity = 0;
        for (Product p : mCartList) {
            quantity = ShoppingCartHelper.getProductQuantity(p);
            sumAll += p.getPrice() * quantity;
            subTotal = sumAll + i;
            ShoppingCartHelper.setQuantity(p, quantity);
        }
        sum.setText("ราคารวม:" + subTotal);
        sumPrice.setText(sumAll + "บาท");

    }

}