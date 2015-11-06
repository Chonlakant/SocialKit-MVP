package co.aquario.folkrice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.aquario.folkrice.R;
import co.aquario.folkrice.adapter.ImagePagerAdapter;
import co.aquario.folkrice.model.Product;


/**
 * Created by Joseph on 7/9/15.
 */
public class ItemProductFragment extends Fragment {

    public static final String EXTRA_NAME = "Item Name";
    public static final String EXTRA_PRICE = "Item Price";
    public static final String EXTRA_DECS = "Item Decs";
    public static final String EXTRA_IMAGE = "Item Image";
    public static final String EXTRA_PRODUCTID = "Item ProductId";
    ArrayList<Product> mItem;
    Toolbar mToolbar;
    ViewPager mViewPager;
    ImagePagerAdapter mAdapter;
    String title;
    Double price;
    String decs;
    String urlImage;
    int productId;
    TextView nutrition_button;

    public static ItemProductFragment newInstance(String title, Double price, String decs, String image, int productId) {
        ItemProductFragment frag = new ItemProductFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, title);
        args.putDouble(EXTRA_PRICE, price);
        args.putString(EXTRA_DECS, decs);
        args.putString(EXTRA_IMAGE, image);
        args.putInt(EXTRA_PRODUCTID, productId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mItem = new ArrayList<>();
        if (args != null) {
            title = args.getString(EXTRA_NAME);
            price = args.getDouble(EXTRA_PRICE);
            decs = args.getString(EXTRA_DECS);
            urlImage = args.getString(EXTRA_IMAGE);
            productId = args.getInt(EXTRA_PRODUCTID);
            Product list_item = new Product();
            list_item.setName(title);
            list_item.setImage(urlImage);
            mItem.add(list_item);
        }
        mToolbar = (Toolbar) getActivity().findViewById(R.id.action_toolbar);
        TextView titleView = (TextView) mToolbar.findViewById(R.id.title);
        ImageView x_button = (ImageView) mToolbar.findViewById(R.id.x_button);
        titleView.setText(title);
        x_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_item, container, false);

        TextView priceTitle = (TextView) v.findViewById(R.id.price);
        TextView nameTitle = (TextView) v.findViewById(R.id.name);
        TextView descTitle = (TextView) v.findViewById(R.id.item_desc);
        nutrition_button = (TextView) v.findViewById(R.id.nutrition_button);
        priceTitle.setText(price + " บาท");
        nameTitle.setText(title);
        descTitle.setText(decs);
        mViewPager = (ViewPager) v.findViewById(R.id.item_viewpager);
        mAdapter = new ImagePagerAdapter(getActivity(), mItem);
        mViewPager.setAdapter(mAdapter);

        nutrition_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }


}
