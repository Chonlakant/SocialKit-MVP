package co.aquario.folkrice.di.components;

import android.app.Activity;
import android.app.Application;

import co.aquario.folkrice.di.ActivityScope;
import co.aquario.folkrice.di.modules.ActivityModule;
import dagger.Component;


@ActivityScope
@Component(dependencies = Application.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
