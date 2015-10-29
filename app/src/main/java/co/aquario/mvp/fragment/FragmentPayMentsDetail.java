package co.aquario.mvp.fragment;

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
import co.aquario.mvp.activities.Activity_main_edit_adress;
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
    String userId;
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
    TextView txtName, txtCountry, txtDistrict, txtPostal, txtHome, sumPrice, sum, Text_edit;
    PostDataNew p;
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
//        mPage = getArguments().getInt(ARG_PAGE);
        pref = MainApplication.getPrefManager();
        Log.e("aaaaa", (pref == null) + "");
        userId = pref.userId().getOr("");
       // userId = pref.userId().getOr("44");
        Log.e("OKOKOKOKO", userId);
        Log.e("mmmm",pref.fristName().getOr(""));
        Log.e("mmmm", pref.lastName().getOr(""));
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
                checkOut();

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
        Log.e("hahaha", jo.toString(4));
    }


    private void checkOut() {
        int id = Integer.parseInt(userId);
        Charset chars = Charset.forName("UTF-8");

       String url =  "http://api.folkrice.com/order/"+id+"/checkout";

        Log.e("99900",url);

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
        Log.e("1234567890", jo.toString(4));
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
        for (PostDataNew p : mCartList) {
            quantity = ShoppingCartHelper.getProductQuantity(p);
            sumAll += p.getPrice() * quantity;
            subTotal = sumAll + i;
            ShoppingCartHelper.setQuantity(p, quantity);
        }
        sum.setText("ราคารวม:" + subTotal);
        Log.e("sdsdsd", sumAll + "");
        sumPrice.setText(sumAll + "บาท");

    }

}