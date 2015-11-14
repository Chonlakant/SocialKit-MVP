package co.aquario.folkrice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

    String contry;
    String district;
    String postal;
    String home;
    String lastName;
    String note;
    String email;
    String landmarks;
    int delivery_total;
    int orderId;
    private static final int PROGRESS = 0x1;
    private int mProgressStatus = 0;

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
        orderId = pref.order().getOr(1);

        Log.e("323", orderId + "");

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
                checkOut();



                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("ดำเนินการต่อ")
                        .setMessage("ไปหน้าสรุปการสั่งซื้อ")
                        .setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Bundle bundle = new Bundle();
                                FragmentPayMentsDetail oneFragment = new FragmentPayMentsDetail();
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container, oneFragment);
                                bundle.putDouble("Price", subTotal);
                                oneFragment.setArguments(bundle);
                                transaction.commit();
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Dismiss dialog and open cart
                                dialog.dismiss();

                            }
                        }).create().show();


            }
        });


        return rootView;

    }

    private void checkOut() {
//        int id = Integer.parseInt(userId);
        Charset chars = Charset.forName("UTF-8");

        String url = "http://api.folkrice.com/order/" + orderId + "/checkout";

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
        pref.delivery_total().put(delivery_total);
        pref.commit();
    }


}