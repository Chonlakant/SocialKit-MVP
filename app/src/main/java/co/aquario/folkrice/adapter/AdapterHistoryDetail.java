package co.aquario.folkrice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import co.aquario.folkrice.model.AccountHistory;
import co.aquario.folkrice.model.HistoryDetail;
import co.aquario.folkrices.R;


public class AdapterHistoryDetail extends RecyclerView.Adapter<AdapterHistoryDetail.ViewHolder> {


    private OnItemClickListener mItemClickListener;

    private final Context context;
    ArrayList<HistoryDetail> list = new ArrayList<>();

    public AdapterHistoryDetail(Context context, ArrayList<HistoryDetail> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item_history_detail, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        HistoryDetail item = list.get(position);


        holder.sub_total.setText("ราคาสินค้า : "+ item.getSub_total()+" บาท");
        holder.total_price.setText("ราคารวม : "+item.getTotal_price()+ "บาท");
        holder.state.setText("สถานะการจัดส่ง : " + item.getState());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView sub_total, total_price, state;


        public ViewHolder(View view) {
            super(view);


            sub_total = (TextView) view.findViewById(R.id.sub_total);
            total_price = (TextView) view.findViewById(R.id.total_price);
            state = (TextView) view.findViewById(R.id.state);





        }

        @Override
        public void onClick(View v) {



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