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

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
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
import co.aquario.folkrice.model.PostDataNew;
import co.aquario.folkrice.model.ShoppingCartHelper;


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
        Log.e("mmmm", pref.fristName().getOr(""));
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



        //แก้Idด้วย
        int id = Integer.parseInt(userId);

        Log.e("NONONONON", id + "");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", myCustomArray);
        params.put("account", id);

        AQuery aq = new AQuery(getActivity());
        aq.ajax(url, params, JSONObject.class, this, "updateProduct");
    }

    public void updateProduct(String url, JSONObject jo, AjaxStatus status) throws JSONException {
         Log.e("hahahaha", jo.toString(4));

//        // Log.e("fffff",jo.getJSONArray("order_items").optString(0)+"");
//        if (jo != null) {
//            JSONArray ja = jo.getJSONArray("order_items");
//            for (int i = 0; i < ja.length(); i++) {
//                JSONObject obj = ja.getJSONObject(i);
//                Log.e("tttt", obj.optString("order_id"));
//            }
//        }


    }


//    private void checkOut() {
//        int id = Integer.parseInt(userId);
//        Charset chars = Charset.forName("UTF-8");
//
//       String url =  "http://api.folkrice.com/order/"+id+"/checkout";
//
//        Log.e("99900",url);
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("customer[first_name]", name);
//        params.put("customer[last_name]", lastName);
//        params.put("customer[email]", email);
//        params.put("ship_to[first_name]", name);
//        params.put("ship_to[last_name]", lastName);
//        params.put("ship_to[address_1]", landmarks);
//        params.put("ship_to[sub_district]", district);
//        params.put("ship_to[district]", district);
//        params.put("ship_to[province]", contry);
//        params.put("ship_to[postcode]", postal);
//        params.put("ship_to[note]", note);
//        params.put("lat", "100");
//        params.put("lon", "31");
//        params.put("ship_to[method]", "grabtaxi");
//
//        AQuery aq = new AQuery(getActivity());
//        aq.ajax(url, params, JSONObject.class, this, "checkOut");
//    }
//
//    public void checkOut(String url, JSONObject jo, AjaxStatus status) throws JSONException {
//        Log.e("1234567890", jo.toString(4));
//    }


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