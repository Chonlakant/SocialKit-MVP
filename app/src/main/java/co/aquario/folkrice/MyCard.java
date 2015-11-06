package co.aquario.folkrice;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import it.gmariotti.cardslib.library.internal.Card;


public class MyCard extends Card {

    public MyCard(Context context) {
        super(context, R.layout.list_item_card_abount);
    }

    public MyCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView header = (TextView) view.findViewById(R.id.header);
        title.setText("http://www.androiddom.com/2011/06/android-shopping-cart-tutorial-part-2.html : GitHub https://github.com/dreamdom/Shopping-Cart-Tutorial-part-2");
        header.setText("Credit");
    }
}