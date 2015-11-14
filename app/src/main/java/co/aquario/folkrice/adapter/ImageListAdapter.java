package co.aquario.folkrice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import co.aquario.folkrices.R;


public class ImageListAdapter extends BaseAdapter {

    Context context;
    String[] imageUrl;
    String[] title;

    public ImageListAdapter(Context context, String[] imageUrl, String[] title) {

        this.context = context;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    @Override
    public int getCount() {
        return imageUrl.length;
    }

    @Override
    public Object getItem(int position) {
        return imageUrl.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewItem item;
        LayoutInflater mInflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item_image, null);

            item = new ViewItem();

            item.productImageView = (ImageView) convertView
                    .findViewById(R.id.item_image);

            item.productTitle = (TextView) convertView
                    .findViewById(R.id.item_name);

            convertView.setTag(item);
        } else {
            item = (ViewItem) convertView.getTag();
        }


        Picasso.with(context)
                .load(imageUrl[position])
//                .resize(200, 250)
                .into(item.productImageView);


        item.productTitle.setText(title[position]);


        return convertView;
    }

    private class ViewItem {
        ImageView productImageView;
        TextView productTitle;
    }
}
