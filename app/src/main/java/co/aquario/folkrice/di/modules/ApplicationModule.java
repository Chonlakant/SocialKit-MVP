package co.aquario.folkrice.di.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import co.aquario.folkrice.MainApplication;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(MainApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }
}
