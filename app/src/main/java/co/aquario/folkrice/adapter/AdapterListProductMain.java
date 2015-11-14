package co.aquario.folkrice.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.activities.Activity_main_login;
import co.aquario.folkrice.activities.ItemChooseActivity;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrices.R;


public class AdapterListProductMain extends bleAdapter<Product> {
    Context context;
    PrefManager pref;

    public AdapterListProductMain(Context context, List<Product> recipes) {
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
        View view = inflater.inflate(R.layout.list_item_product, null, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(final Product recipe, int position, View view) {
        final ViewHolder holder = (ViewHolder) view.getTag();

        pref = MainApplication.getPrefManager();
        Picasso.with(view.getContext())
                .load(recipe.getImage())
                .into(holder.recipeImageView);


        holder.userNameTextView.setText(recipe.getName());
        holder.titleTextView.setText(recipe.getNameTh());
        holder.text_price.setText(recipe.getPrice() + " บาท");

        view.setOnClickListener(new View.OnClickListener() {
            Boolean isLogin = false;
            boolean isCheckDialog = false;

            @Override
            public void onClick(View view) {


                isLogin = pref.isLogin().getOr(false);

                if (pref.isLogin().getOr(true) && !isLogin) {
                    Intent i = new Intent(getContext(), Activity_main_login.class);
                    getContext().startActivity(i);
                    isLogin = true;
                    pref.isLogin().put(isLogin);
                    pref.commit();

                } else {
                    Intent intent = new Intent(getContext(), ItemChooseActivity.class);
                    intent.putExtra("productId", recipe.getProductId());
                    Log.e("AdapterClick", recipe.getProductId() + "");
                    intent.putExtra("title", recipe.getNameTh());
                    intent.putExtra("price", recipe.getPrice());
                    intent.putExtra("decs", recipe.getDesc());
                    intent.putExtra("urlImage", recipe.getImage());
                    getContext().startActivity(intent);

                }


            }
        });

    }


}
