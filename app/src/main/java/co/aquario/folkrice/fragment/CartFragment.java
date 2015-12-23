package co.aquario.folkrice.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

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
import co.aquario.folkrice.UserManager;
import co.aquario.folkrice.activities.Activity_main_PaymentDetail;
import co.aquario.folkrice.activities.Activity_main_login;
import co.aquario.folkrice.adapter.ProductAdapter;
import co.aquario.folkrice.model.JsonArr;
import co.aquario.folkrice.model.PostData;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;

import co.aquario.folkrices.R;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class CartFragment extends DialogFragment {
    private static final int REQUEST_SIMPLE_DIALOG = 42;
    private List<Product> mCartList = new ArrayList<>();
    ProductAdapter mAdapter;
    Dialog dialog2;
    TextView productPriceTextView, number_items;
    ListView list;
    UserManager mManager;
    PrefManager pref;
    Boolean isLogin = false;
    double subTotal = 0;
    MainApplication aController;
    RelativeLayout cancelBtn;
    boolean isCheckProduct = false;
    String userId;
    JsonArray myCustomArray;
    int quantityArr;
    List<JsonArr> productJson = new ArrayList<>();
    int productIdArr;
    String orderId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartList = ShoppingCartHelper.getCartList();
        pref = MainApplication.getPrefManager();
        // Initialize items
        userId = pref.userId().getOr("");
        pref.userId().put(userId);
        pref.commit();
        Log.e("Chonlaant", userId);

        for (Product p : mCartList) {
            quantityArr = ShoppingCartHelper.getProductQuantity(p);
            productIdArr = p.getProductId();
            Log.e("123456790", quantityArr + "");
            JsonArr arrProduct = new JsonArr();
            arrProduct.setProduct_id(productIdArr);
            arrProduct.setQuantity(quantityArr);
            productJson.add(arrProduct);
            Log.e("productJson", productJson + "");
            Gson gson = new GsonBuilder().create();
            myCustomArray = gson.toJsonTree(productJson).getAsJsonArray();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Remove the title
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        cancelBtn = (RelativeLayout) v.findViewById(R.id.cancelBtn);
        pref = MainApplication.getPrefManager();
        mManager = new UserManager(getActivity());
        aController = (MainApplication) getActivity().getApplicationContext();
        dialog2 = new Dialog(getActivity());

        productPriceTextView = (TextView) v.findViewById(R.id.textView2);
        number_items = (TextView) v.findViewById(R.id.number_items);

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            for (int i = 0; i < mCartList.size(); i++) {
                mCartList.get(i).selected = false;
            }
        }

        // Create the list
        list = (ListView) v.findViewById(R.id.cart_items_list);
        mAdapter = new ProductAdapter(mCartList, getActivity().getLayoutInflater(), true, getActivity());
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                removeItemFromList(position);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                    }
//                }, 500);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("ลบสินค้า")
                        .setMessage("คุณต้องการลบสินค้าหรือไม่?")
                        .setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                for (int i = 0; i < mCartList.size(); i++) {
                                    ShoppingCartHelper.removeProduct(mCartList.get(i));
                                }
                                mCartList.clear();
                                getDialog().dismiss();


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
        // Cancel button
        ImageView cancelButton = (ImageView) v.findViewById(R.id.x_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                getDialog().dismiss();
                getActivity().finish();
            }
        });


        // Set Normal Checkout Button
        TextView checkoutButton = (TextView) v.findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2 = new Dialog(getActivity(), R.style.FullHeightDialog);
                dialog2.setContentView(R.layout.dialog_check);

                uploadProduct();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("ดำเนินการต่อ")
                        .setMessage("คุณต้องการชำระเงินเพื่อซื้อสินค้าหรือไม่?")
                        .setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {

                                isLogin = pref.isLogin().getOr(false);
                                isCheckProduct = pref.isCheckProduct().getOr(false);

                                if (isLogin != true) {
                                    dialog2.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent i = new Intent(getContext(), Activity_main_login.class);
                                            startActivity(i);
                                            pref.isLogin().put(true);
                                            dialog.dismiss();
                                            dialog2.dismiss();
                                        }
                                    }, 2000);

                                } else {
                                    if (isCheckProduct != false) {
                                        dialog2.show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                isCheckProduct = false;
                                                pref.isCheckProduct().put(isCheckProduct);
                                                pref.commit();

                                                Intent i = new Intent(getActivity(), Activity_main_PaymentDetail.class);
                                                startActivity(i);
                                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                ft.detach(CartFragment.this).attach(CartFragment.this).commit();
                                                dialog.dismiss();
                                                dialog2.dismiss();

                                            }
                                        }, 2500);


                                    } else {
                                        Toast.makeText(getActivity(), "กรุณาเลือกซื้อสินค้าก่อน", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }


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
        return v;
    }

    protected void removeItemFromList(final int position) {
        final int deletePosition = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(
                getActivity());

        alert.setTitle("ลบ");
        alert.setMessage("คุณต้องการลบสินค้านี้หรือไม่?");
        alert.setPositiveButton("ตกลง", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (int i = 0; i < mCartList.size(); i++) {
                    Log.e("i", i + "");
                }
                ShoppingCartHelper.removeProduct(mCartList.get(deletePosition));
                mCartList.remove(deletePosition);
                Log.e("99999", mCartList.size() + "");
                double priceSum1 = 0;

                int quantity = 0;
                for (Product p : mCartList) {
                    quantity = ShoppingCartHelper.getProductQuantity(p);
                    subTotal += p.getPrice() * quantity;

                    // ShoppingCartHelper.setQuantity(p, quantity);

                    priceSum1 = p.getPrice();
                    productPriceTextView.setText("ราคารวม:" + priceSum1);
                    number_items.setText("จำนวน: " + quantity);
                    Log.e("ราคารวม", priceSum1 + "");
                }


                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                    productPriceTextView.setText("ราคารวม:" + priceSum1);
                    number_items.setText("จำนวน: " + quantity);
                }

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(CartFragment.this).attach(CartFragment.this).commit();


            }
        });
        alert.setNegativeButton("ยกเลิก", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        alert.show();

    }


    private void uploadProduct() {

        Charset chars = Charset.forName("UTF-8");
        String url = "http://api.folkrice.com/order/add";

        Log.e("NONONONON", userId + "");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("items", myCustomArray);
        params.put("account", userId);

        AQuery aq = new AQuery(getActivity());
        aq.ajax(url, params, JSONObject.class, this, "updateProduct");
    }

    public void updateProduct(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        Log.e("Json Return", jo.toString(4));
        orderId = jo.getJSONObject("order").optString("id");
        Log.e("091", orderId + "");
        pref.orderId().put(orderId);
        pref.commit();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Make Dialog fill parent width and height
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the data
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();

        }

        int quantity = 0;
        for (Product p : mCartList) {
            quantity = ShoppingCartHelper.getProductQuantity(p);
            subTotal += p.getPrice() * quantity;

            ShoppingCartHelper.setQuantity(p, quantity);


        }
        productPriceTextView.setText("ราคารวม:" + subTotal);
        number_items.setText("จำนวน: " + quantity);
    }


}
