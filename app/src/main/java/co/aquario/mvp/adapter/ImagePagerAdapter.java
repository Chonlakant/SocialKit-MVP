package co.aquario.mvp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import co.aquario.mvp.model.PostDataNew;
import co.chonlakant.mvp.R;

/**
 * Created by Joseph on 7/14/15.
 */
public class ImagePagerAdapter extends PagerAdapter {

    Context mContext;
    ArrayList<PostDataNew> mItem;
    LayoutInflater mLayoutInflater;

    public ImagePagerAdapter(Context context, ArrayList<PostDataNew> mItem) {
        mContext = context;
        this.mItem = mItem;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_pager, container, false);

        PostDataNew i = mItem.get(position);


        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Picasso.with(mContext)
                .load(i.getImage())
                .into(imageView);
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
