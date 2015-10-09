package co.aquario.mvp.di.components;

import android.app.Activity;
import android.app.Application;

import co.aquario.mvp.di.ActivityScope;
import co.aquario.mvp.di.modules.ActivityModule;
import dagger.Component;


@ActivityScope
@Component(dependencies = Application.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
