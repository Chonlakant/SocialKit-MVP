package co.aquario.folkrice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
import co.aquario.folkrice.activities.Activity_main_PaymentDetail;
import co.aquario.folkrice.adapter.ProductAdapter;
import co.aquario.folkrice.model.JsonArr;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;
import co.aquario.folkrices.R;


public class FragmentListProductDetail extends BaseFragment {


    public static final String ARG_PAGE = "ARG_PAGE";
    Button btn_price;
    private int mPage;
    PrefManager pref;
    ProductAdapter mAdapter;
    ListView list;
    String name;
    Dialog dialog;
    double price;
    double subTotal = 0;

    private List<Product> mCartList = new ArrayList<>();
    JsonArray myCustomArray;
    String contry;
    String district;
    String postal;
    String home;
    String lastName;
    String note;
    String email;
    String landmarks;
    int delivery_total;
    String orderId;
    private static final int PROGRESS = 0x1;
    private int mProgressStatus = 0;
    int quantityArr;
    int productIdArr;
    List<JsonArr> productJson = new ArrayList<>();
    String userId;
    int total_price;
    int sub_total;

    public static FragmentListProductDetail newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FragmentListProductDetail fragment = new FragmentListProductDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = MainApplication.getPrefManager();
        userId = pref.userId().getOr("7");
        Log.e("userId", userId + "");
        orderId = pref.orderId().getOr("0");
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
        mCartList = ShoppingCartHelper.getCartList();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.main_list_product_detail, container, false);
        list = (ListView) rootView.findViewById(R.id.cart_items_list);
        btn_price = (Button) rootView.findViewById(R.id.btn_price);
        mCartList = ShoppingCartHelper.getCartList();
        mAdapter = new ProductAdapter(mCartList, getActivity().getLayoutInflater(), true, getActivity());
        checkOut();
        for (Product p : mCartList) {
            quantityArr = ShoppingCartHelper.getProductQuantity(p);
            productIdArr = p.getProductId();
        }


        JsonArr arrProduct = new JsonArr();
        arrProduct.setProduct_id(productIdArr);
        arrProduct.setQuantity(quantityArr);
        productJson.add(arrProduct);

        Log.e("ProductJson", productJson + "");
        Gson gson = new GsonBuilder().create();
        myCustomArray = gson.toJsonTree(productJson).getAsJsonArray();


        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < mCartList.size(); i++) {
                mCartList.get(i).selected = false;
            }
        }

        list.setAdapter(mAdapter);
        dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialog);

        btn_price.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                //uploadProduct();
                dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog.setContentView(R.layout.dialog_check);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        FragmentPayMentsDetail oneFragment = new FragmentPayMentsDetail();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, oneFragment);
                        bundle.putDouble("Price", subTotal);
                        oneFragment.setArguments(bundle);
                        transaction.commit();
                        dialog.dismiss();

                    }
                }, 2500);

            }
        });


        return rootView;

    }

    private void checkOut() {
//        int id = Integer.parseInt(userId);
        Charset chars = Charset.forName("UTF-8");

        String url = "http://api.folkrice.com/order/"+orderId ;
        Log.e("urlACtive", url);

        AQuery aq = new AQuery(getActivity());
        aq.ajax(url,JSONObject.class, this, "checkOut");
    }

    public void checkOut(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        Log.e("Json Return 500", jo.toString(4));

        delivery_total = jo.getInt("delivery_total");
        total_price = jo.getInt("total_price");
        sub_total = jo.optInt("sub_total");
        pref.delivery_total().put(delivery_total);
        pref.total_price().put(total_price);
        pref.sub_total().put(sub_total);
        pref.commit();
    }

}