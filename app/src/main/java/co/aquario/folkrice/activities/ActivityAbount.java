package co.aquario.folkrice.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import co.aquario.folkrice.MainApplication;
import co.aquario.folkrice.MyCard;
import co.aquario.folkrice.PrefManager;
import co.aquario.folkrice.R;
import co.aquario.folkrice.fragment.CartFragment;
import co.aquario.folkrice.fragment.ItemProductFragment;
import co.aquario.folkrice.model.Product;
import co.aquario.folkrice.model.ShoppingCartHelper;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardListView;


/**
 * Created by Joseph on 7/7/15.
 */
public class ActivityAbount extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abount);

        ArrayList<Card> cards = new ArrayList<Card>();


        MyCard c = new MyCard(this);

        cards.add(c);


        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this, cards);

        CardListView listView = (CardListView) findViewById(R.id.card_list);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }

    }

}
