package co.aquario.mvp.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.aquario.mvp.MainApplication;
import co.aquario.mvp.PrefManager;
import co.aquario.mvp.adapter.ProductAdapter;
import co.aquario.mvp.model.JsonArr;
import co.aquario.mvp.model.PostDataNew;
import co.aquario.mvp.model.ShoppingCartHelper;
import co.chonlakant.mvp.R;


public class FragmentPayMentsDetail extends BaseFragment {

    ObjectMapper mapper = new ObjectMapper();

    double lat, lng;
    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_price;
    private int mPage;
    PrefManager pref;
    ProductAdapter mAdapter;
    ListView list;
    double sumAll = 0;
    double subTotal = 0;
    double subTotalAll = 0;
    double latitude;
    double longitude;
    int quantityArr;
    int productIdArr;
    private List<PostDataNew> mCartList = new ArrayList<>();
    List<JsonArr> productJson = new ArrayList<>();
    JsonArray myCustomArray;
    TextView txtName, txtCountry, txtDistrict, txtPostal, txtHome, sumPrice, sum;

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
//        mPage = getArguments().getInt(ARG_PAGE);
        pref = MainApplication.getPrefManager();
        Log.e("aaaaa", (pref == null) + "");
        mCartList = ShoppingCartHelper.getCartList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_list_buy, container, false);
        list = (ListView) rootView.findViewById(R.id.cart_items_list);



        mAdapter = new ProductAdapter(mCartList, getActivity().getLayoutInflater(), true, getActivity());
        txtName = (TextView) rootView.findViewById(R.id.txt_name);
        txtCountry = (TextView) rootView.findViewById(R.id.txt_contry);
        txtDistrict = (TextView) rootView.findViewById(R.id.txt_district);
        txtPostal = (TextView) rootView.findViewById(R.id.txt_postal);
        txtHome = (TextView) rootView.findViewById(R.id.txt_home);
        sumPrice = (TextView) rootView.findViewById(R.id.sum_price);
        sum = (TextView) rootView.findViewById(R.id.sum);
        btn_price = (Button) rootView.findViewById(R.id.btn_price);


        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < mCartList.size(); i++) {
                mCartList.get(i).selected = false;
            }
        }

        sum.setText("ราคารวม:" + subTotal);
        sumPrice.setText(sumAll + "บาท");

        list.setAdapter(mAdapter);
        String name = pref.name().getOr("");
        String contry = pref.country().getOr("");
        String district = pref.district().getOr("");
        String postal = pref.postal().getOr("");
        String home = pref.home().getOr("");

        txtName.setText(name);
        txtCountry.setText(contry);
        txtDistrict.setText(district);
        txtPostal.setText(postal);
        txtHome.setText(home);

        for (PostDataNew p : mCartList) {
            quantityArr = ShoppingCartHelper.getProductQuantity(p);
            Log.e("2222", quantityArr + "");
            productIdArr = p.getProductId();
            Log.e("9999", productIdArr + "");
        }


        JsonArr arrProduct = new JsonArr();
        arrProduct.setProduct_id(productIdArr);
        arrProduct.setQuantity(quantityArr);
        productJson.add(arrProduct);


        Gson gson = new GsonBuilder().create();
        myCustomArray = gson.toJsonTree(productJson).getAsJsonArray();
        Log.e("2222", myCustomArray + "");

        btn_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadProduct();
                FragmentPayMents oneFragment = new FragmentPayMents();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, oneFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });


        return rootView;

    }

    private void uploadProduct() {

        Charset chars = Charset.forName("UTF-8");
        String url = "http://api.folkrice.com/order/add";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", myCustomArray);
        params.put("account", 44);


        AQuery aq = new AQuery(getActivity());
        aq.ajax(url, params, JSONObject.class, this, "updateProduct");
    }

    public void updateProduct(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        Log.e("hahaha", jo.toString(4));
        Toast.makeText(getActivity(), "Update complete!", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResume() {
        super.onResume();
        double i = 40.00;
        // Refresh the data
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

        int quantity = 0;
        for (PostDataNew p : mCartList) {
            quantity = ShoppingCartHelper.getProductQuantity(p);
            sumAll += p.getPrice() * quantity;
            subTotal = sumAll + i;
            ShoppingCartHelper.setQuantity(p, quantity);


        }
        sum.setText("ราคารวม:" + subTotal);

        sumPrice.setText(sumAll + "บาท");

//        number_items.setText("จำนวน: " + quantity);
    }

}