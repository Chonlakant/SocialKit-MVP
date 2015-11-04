package co.aquario.folkrice.adapter;

import android.content.Context;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.aquario.folkrice.R;
import co.aquario.folkrice.model.PostDataNew;



/**
 * Created by root1 on 9/11/15.
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemHolder> {
    //ArrayList<Item> items;
    private AdapterViewCompat.OnItemClickListener mItemClickListener;

    public static final String EXTRA_NAME = "Subcategory name";
    ArrayList<PostDataNew> productses = new ArrayList<>();
    public Context context;
    public ItemListAdapter(ArrayList<PostDataNew> productses, Context context) {
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


        final PostDataNew i = productses.get(position);
        holder.nameTv.setText(i.getName());
        holder.itemPrice.setText(i.getPrice()+ "");

        Picasso.with(context)
                .load(i.getImage())
//                .resize(200, 250)
                .into(holder.image);

            holder.heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Change the heart status
                    if (i.ismHeart()) {
                        holder.heart.setImageResource(android.R.color.transparent);
                      i.setmHeart(false);
                    } else {
                        holder.heart.setImageResource(R.drawable.heart_filled);
                        i.setmHeart(true);
                    }
                }
            });
//        holder.image.setOnTouchListener(new View.OnTouchListener() {
//            // Listen for double taps
//            private GestureDetector detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onSingleTapConfirmed(MotionEvent e) {
//                    // Open item page on single tap
//                    Intent intent = new Intent(context, ItemActivity.class);
//                    intent.putExtra(EXTRA_NAME, i.getName());
//                    context.startActivity(intent);
//                    Toast.makeText(context, "ไปยังหน้ารายละเอียด" + i.getName(), Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//
//                @Override
//                public boolean onDoubleTap(MotionEvent e) {
//                    // Set the liked button
//                        if (i.ismHeart()) {
//                            Toast.makeText(context, "Unliked", Toast.LENGTH_SHORT).show();
//                            holder.heart.setImageResource(android.R.color.transparent);
//                           i.setmHeart(false);
//                        } else { // Clear the heart
//                            Toast.makeText(context, "Hearted!", Toast.LENGTH_SHORT).show();
//                            holder.heart.setImageResource(R.drawable.heart_filled);
//                            i.setmHeart(true);
//                        }
//                    return true;
//                }
//            });
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                detector.onTouchEvent(event);
//                return true;
//            }
//        });

        // Set the liked button
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

    public class ItemHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
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
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.item_picture:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(null,view, getPosition(),view.getId());
                    }


            }
        }
    }
    public void SetOnItemClickListener(final AdapterViewCompat.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public AdapterViewCompat.OnItemClickListener getmItemClickListener() {
        return mItemClickListener;
    }
}