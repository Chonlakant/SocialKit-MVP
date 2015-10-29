package co.aquario.mvp;

import android.content.SharedPreferences;

import com.tale.prettysharedpreferences.BooleanEditor;
import com.tale.prettysharedpreferences.IntegerEditor;
import com.tale.prettysharedpreferences.LongEditor;
import com.tale.prettysharedpreferences.PrettySharedPreferences;
import com.tale.prettysharedpreferences.StringEditor;

/**
 * Created by TALE on 10/28/2014.
 */
public class PrefManager extends PrettySharedPreferences<PrefManager> {

    public PrefManager(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

    public StringEditor<PrefManager> email() {
        return getStringEditor("email");
    }

    public StringEditor<PrefManager> passWord() {
        return getStringEditor("passWord");
    }

    public StringEditor<PrefManager> confirmpassWord() {
        return getStringEditor("confirmpassWord");
    }

    public BooleanEditor<PrefManager> isLogin() {
        return getBooleanEditor("isLogin");
    }

    public BooleanEditor<PrefManager> isAddress() {
        return getBooleanEditor("isAddress");
    }

    public BooleanEditor<PrefManager> isAddressRegister() {
        return getBooleanEditor("isAddressRegister");
    }

    public BooleanEditor<PrefManager> isAddressRegister2() {
        return getBooleanEditor("isAddressRegister2");
    }

    public StringEditor<PrefManager> name() {
        return getStringEditor("name");
    }
    public StringEditor<PrefManager> lastName() {
        return getStringEditor("lastName");
    }
    public StringEditor<PrefManager> note() {
        return getStringEditor("note");
    }

    public StringEditor<PrefManager> phone() {
        return getStringEditor("phone");
    }

    public StringEditor<PrefManager> district() {
        return getStringEditor("district");
    }

    public StringEditor<PrefManager> country() {
        return getStringEditor("country");
    }

    public StringEditor<PrefManager> postal() {
        return getStringEditor("postal");
    }

    public StringEditor<PrefManager> home() {
        return getStringEditor("home");
    }

    public StringEditor<PrefManager> area() {
        return getStringEditor("area");
    }

    public StringEditor<PrefManager> landmarks() {
        return getStringEditor("landmarks");
    }

    public StringEditor<PrefManager> road() {
        return getStringEditor("road");
    }

    public StringEditor<PrefManager> nameTitle() {
        return getStringEditor("nameTitle");
    }

    public StringEditor<PrefManager> fristName() {
        return getStringEditor("fristName");
    }



    public StringEditor<PrefManager> userId() {
        return getStringEditor("userId");
    }

//    public IntegerEditor<PrefManager> userId() {
//        return getIntegerEditor("userId");
//    }



}
