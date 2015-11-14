package co.aquario.folkrice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.folkrice.model.Product;
import co.aquario.folkrices.R;


/**
 * Created by root1 on 9/11/15.
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemHolder> {
    private OnItemClickListener mItemClickListener;
    public static final String EXTRA_NAME = "Subcategory name";
    ArrayList<Product> productses = new ArrayList<>();
    public Context context;

    public ItemListAdapter(ArrayList<Product> productses, Context context) {
        this.productses = productses;
        this.context = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View holder = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_holder, viewGroup, false);
        return new ItemHolder(holder);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {


        final Product i = productses.get(position);
        holder.nameTv.setText(i.getName());
        holder.itemPrice.setText(i.getPrice() + "");

        Picasso.with(context)
                .load(i.getImage())
                .into(holder.image);

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i.ismHeart()) {
                    holder.heart.setImageResource(android.R.color.transparent);
                    i.setmHeart(false);
                } else {
                    holder.heart.setImageResource(R.drawable.heart_filled);
                    i.setmHeart(true);
                }
            }
        });

        if (i.ismHeart()) {
            holder.heart.setImageResource(R.drawable.heart_filled);
        } else {
            holder.heart.setImageResource(android.R.color.transparent);
        }
    }

    @Override
    public int getItemCount() {
        return productses.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTv;
        TextView itemPrice;
        ImageView image;
        ImageButton heart;

        public ItemHolder(View v) {
            super(v);
            nameTv = (TextView) v.findViewById(R.id.item_name);
            itemPrice = (TextView) v.findViewById(R.id.item_price);
            image = (ImageView) v.findViewById(R.id.item_picture);
            heart = (ImageButton) v.findViewById(R.id.heart_icon);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.item_picture:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }

            }

        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}