package co.aquario.folkrice.di.modules;

import android.app.Activity;

import co.aquario.folkrice.di.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return this.activity;
    }
}
