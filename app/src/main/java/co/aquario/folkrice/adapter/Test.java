package co.aquario.folkrice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import co.aquario.folkrice.model.AccountHistory;
import co.aquario.folkrices.R;


public class Test extends RecyclerView.Adapter<Test.ViewHolder> {

    ArrayList<AccountHistory> list = new ArrayList<AccountHistory>();
    public static Context context;

    OnItemClickListener mItemClickListener;


    public Test(Context context, ArrayList<AccountHistory> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_history, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccountHistory item = list.get(position);





        holder.txt_name.setText("sdsd");
        holder.txt_price.setText("sdsd");
        holder.txt_create.setText("dsds");
        holder.txt_state.setText("sds");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView txt_name, txt_state, txt_price, txt_create;

        public ViewHolder(View view) {
            super(view);
            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_price = (TextView) view.findViewById(R.id.txt_price);
            txt_create = (TextView) view.findViewById(R.id.txt_create);
            txt_state = (TextView) view.findViewById(R.id.txt_state);

            txt_name.setOnClickListener(this);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.txt_name:
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


    /*
     * Snippet from http://stackoverflow.com/a/363692/1008278
     */
    public static int randInt(int min, int max) {
        final Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    /* ==========This Part is not necessary========= */

}