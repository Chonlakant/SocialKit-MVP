package co.aquario.mvp;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.jar.Attributes;


/**
 * Created by root1 on 10/14/15.
 */
public class MyCustomTextView extends TextView {
    public MyCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/ThaiSansNeueBold.ttf"));

    }
}
