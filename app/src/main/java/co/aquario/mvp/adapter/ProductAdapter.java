package co.aquario.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.aquario.mvp.model.PostDataNew;
import co.aquario.mvp.model.ShoppingCartHelper;
import co.chonlakant.mvp.R;


public class ProductAdapter extends BaseAdapter {

    private List<PostDataNew> mProductList;
    private LayoutInflater mInflater;
    private boolean mShowQuantity;

    Context context;

    public ProductAdapter(List<PostDataNew> list, LayoutInflater inflater, boolean showQuantity, Context context) {
        mProductList = list;
        mInflater = inflater;
        mShowQuantity = showQuantity;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_cart, null);
            item = new ViewItem();

            item.productImageView = (ImageView) convertView
                    .findViewById(R.id.item_image);

            item.productTitle = (TextView) convertView
                    .findViewById(R.id.item_name);

            item.productQuantity = (TextView) convertView
                    .findViewById(R.id.quantity);
            item.itemPrice = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }

        PostDataNew curProduct = mProductList.get(position);

        Picasso.with(context)
                .load(curProduct.getImage())
//                .resize(200, 250)
                .into(item.productImageView);


        item.productTitle.setText(curProduct.getName());

        double subTotal = 0;
        int priceQuantity;
        priceQuantity =   ShoppingCartHelper.getProductQuantity(curProduct);
        subTotal += curProduct.getPrice() * priceQuantity;
        item.itemPrice.setText(subTotal + "บาท");
        // Show the quantity in the cart or not
        if (mShowQuantity) {
            item.productQuantity.setText("Quantity: "
                    + ShoppingCartHelper.getProductQuantity(curProduct));
        } else {
            // Hid the view
            item.productQuantity.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewItem {
        ImageView productImageView;
        TextView productTitle;
        TextView productQuantity;
        TextView itemPrice;
    }

}