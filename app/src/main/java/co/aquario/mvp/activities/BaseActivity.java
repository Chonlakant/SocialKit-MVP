package co.aquario.mvp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import co.aquario.mvp.MainApplication;
import co.aquario.mvp.di.components.ApplicationComponent;
import co.aquario.mvp.di.modules.ActivityModule;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((MainApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
