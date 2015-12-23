package co.aquario.folkrice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import co.aquario.folkrice.model.AccountHistory;
import co.aquario.folkrice.model.History;
import co.aquario.folkrices.R;


public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {


    private OnItemClickListener mItemClickListener;

    private final Context context;
    ArrayList<AccountHistory> list = new ArrayList<>();

    public AdapterHistory(Context context, ArrayList<AccountHistory> list) {
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


        holder.txt_name.setText("รหัสสินค้า : "+ item.getAccount_id());
        holder.txt_price.setText("รหัสออเดอร์ : "+item.getOrder_id());
        holder.txt_create.setText("วันที่ซื้อ: " + item.getCreated_at());
        holder.txt_state.setText("สถานะการจัดส่ง: "+item.getState());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_name, txt_state, txt_price, txt_create;
        LinearLayout ls;

        public ViewHolder(View view) {
            super(view);


            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_price = (TextView) view.findViewById(R.id.txt_price);
            txt_create = (TextView) view.findViewById(R.id.txt_create);
            txt_state = (TextView) view.findViewById(R.id.txt_state);
            ls = (LinearLayout) view.findViewById(R.id.ls);

            ls.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.ls:
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());
                    }

            }

        }

    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemVideiosClickListener(final OnItemClickListener mItemClickListener) {
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