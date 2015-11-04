package co.aquario.folkrice.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.aquario.folkrice.R;
import co.aquario.folkrice.activities.ItemActivity;
import co.aquario.folkrice.model.PostDataNew;



public class RecipeListAdapter extends BindableAdapter<PostDataNew> {
    Context context;
    public RecipeListAdapter(Context context, List<PostDataNew> recipes) {
        super(context, recipes);
    }

    static class ViewHolder {
        @Bind(R.id.recipe_image)
        ImageView recipeImageView;
        @Bind(R.id.user_image)
        ImageView userImageView;
        @Bind(R.id.user_name_text)
        TextView userNameTextView;
        @Bind(R.id.title_text)
        TextView titleTextView;
        @Bind(R.id.text_price)
        TextView text_price;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view = inflater.inflate(R.layout.list_item_recipe, null, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(final PostDataNew recipe, int position, View view) {
        final ViewHolder holder = (ViewHolder) view.getTag();


        Picasso.with(view.getContext())
                .load(recipe.getImage())
                .into(holder.recipeImageView);

//        Picasso.with(view.getContext())
//                .load(recipe.getImage())
//                .into(holder.userImageView);


        holder.userNameTextView.setText(recipe.getName());
        holder.titleTextView.setText(recipe.getNameTh());
        holder.text_price.setText(recipe.getPrice()+ " บาท");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra("productId",recipe.getProductId());
                Log.e("AdapterClick",recipe.getProductId()+"");
                intent.putExtra("title",recipe.getNameTh());
                intent.putExtra("price",recipe.getPrice());
                intent.putExtra("decs",recipe.getDesc());
                intent.putExtra("urlImage", recipe.getImage());
                getContext().startActivity(intent);
            }
        });

    }
}
