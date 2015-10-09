package co.aquario.mvp.di.components;

import android.content.Context;

import javax.inject.Singleton;

import co.aquario.mvp.di.modules.ApplicationModule;
import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context context();
}
