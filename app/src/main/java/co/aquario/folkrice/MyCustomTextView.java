package co.aquario.folkrice;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by root1 on 10/14/15.
 */
public class MyCustomTextView extends TextView {
    public MyCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/ThaiSansNeueBold.ttf"));

    }
}
