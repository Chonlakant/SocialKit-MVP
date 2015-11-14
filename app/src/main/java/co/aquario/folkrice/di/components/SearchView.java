package co.aquario.folkrice.di.components;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import co.aquario.folkrice.adapter.SearchtAdapter;
import co.aquario.folkrice.adapter.TextWatcherAdapter;
import co.aquario.folkrices.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.internal.Assertions;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchView extends FrameLayout implements Observable.OnSubscribe<String> {

    private static final int DEBOUNCE_WAIT = 300;

    @Bind(R.id.query_input)
    EditText queryInput;
    @Bind(R.id.button_search)
    View button_search;
    @Bind(R.id.suggestion_list)
    ListView suggestionListView;

    private SearchtAdapter suggestionListAdapter;

    public SearchView(Context context) {
        super(context);
        setup();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        View.inflate(getContext(), R.layout.view_search, this);
        ButterKnife.bind(this);

        suggestionListAdapter = new SearchtAdapter(getContext());
        suggestionListView.setAdapter(suggestionListAdapter);
    }

    public void updateSuggestions(List<String> suggestions) {
        suggestionListAdapter.update(suggestions);
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        Log.e("debugging", "subscriber is comming");
        Assertions.assertUiThread();

        final TextWatcherAdapter listener = new TextWatcherAdapter() {
            @Override
            public void onTextChanged(String text, int length) {

            }
        };

    }

    public Observable<String> observe() {
        return Observable.create(this)

                .debounce(DEBOUNCE_WAIT, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    @OnClick(R.id.button_search)
    void onClearButtonClick() {
        Toast.makeText(getContext(),"ค้าหา",Toast.LENGTH_LONG).show();
        String url = "http://www.folkrice.com";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        getContext().startActivity(i);
    }
}
