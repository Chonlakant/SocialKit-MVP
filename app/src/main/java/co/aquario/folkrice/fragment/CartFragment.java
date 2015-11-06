package co.aquario.folkrice.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.R;
import co.aquario.folkrice.UserManager;
import co.aquario.folkrice.activities.Activity_main_PaymentDetail;
import co.aquario.folkrice.activities.Activity_main_login;
import co.aquario.folkrice.adapter.ProductAdapter;
import co.aquario.folkrice.model.PostData;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class CartFragment extends DialogFragment {
    private static final int REQUEST_SIMPLE_DIALOG = 42;
    private List<Product> mCartList = new ArrayList<>();
    ProductAdapter mAdapter;

    TextView productPriceTextView, number_items;
    ListView list;
    PostData curProduct;
    MaterialDialog mMaterialDialog;
    UserManager mManager;
    PrefManager pref;
    Boolean isLogin = false;
    double subTotal = 0;
    MainApplication aController;
    TextView cancelBtn;
    boolean isCheckProduct = false;
    String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartList = ShoppingCartHelper.getCartList();
        pref = MainApplication.getPrefManager();
        // Initialize items
        userId = pref.userId().getOr("");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Remove the title
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        cancelBtn = (TextView) v.findViewById(R.id.cancelBtn);
        pref = MainApplication.getPrefManager();
        mMaterialDialog = new MaterialDialog(getActivity());
        mManager = new UserManager(getActivity());
        aController = (MainApplication) getActivity().getApplicationContext();


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
                for (int i = 0; i < mCartList.size(); i++) {
                    ShoppingCartHelper.removeProduct(mCartList.get(i));
                }
                getDialog().dismiss();
            }
        });
        // Cancel button
        ImageView cancelButton = (ImageView) v.findViewById(R.id.x_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                getDialog().dismiss();
            }
        });

        // Set Paypal buttons
        RelativeLayout paypalButton = (RelativeLayout) v.findViewById(R.id.paypal_button);
        paypalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open paypal
                getDialog().dismiss();
            }
        });

        // Set Normal Checkout Button
        TextView checkoutButton = (TextView) v.findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("เข้าสู่ระบบเพื่อดำเนินการต่อ")
                        .setMessage("กรุณาเข้าสู่ระบบ หรือสมัคร folkrice เพื่อดำเนินการต่อไป")
                        .setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                isLogin = pref.isLogin().getOr(false);
                                isCheckProduct = pref.isCheckProduct().getOr(false);

                                if (isCheckProduct != false) {
                                    isCheckProduct = false;
                                    pref.isCheckProduct().put(isCheckProduct);
                                    pref.commit();

                                    if (pref.isLogin().getOr(true) && !isLogin) {
                                        Intent i = new Intent(getActivity(), Activity_main_login.class);
                                        startActivity(i);
                                        isLogin = true;
                                        pref.isLogin().put(isLogin);
                                        pref.commit();
                                        dialog.dismiss();
                                    } else {
                                        Intent i = new Intent(getActivity(), Activity_main_PaymentDetail.class);
                                        startActivity(i);
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.detach(CartFragment.this).attach(CartFragment.this).commit();
                                        dialog.dismiss();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "คุณยังไม่ได้เลือกสินค้า", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }


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
