package co.aquario.folkrice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;
import co.aquario.folkrices.R;


public class SearchtAdapter extends bleAdapter<String> {

    static class ViewHolder {
        @Bind(R.id.suggestion_text)
        TextView suggestionText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public SearchtAdapter(Context context) {
        super(context);
    }

    public void update(List<String> suggestions) {
        clear();
        addAll(suggestions);
        notifyDataSetChanged();
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view = inflater.inflate(R.layout.list_item_suggestion, null, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(String item, int position, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.suggestionText.setText(item);
    }
}
