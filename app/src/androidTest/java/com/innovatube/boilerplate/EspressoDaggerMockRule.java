package com.innovatube.boilerplate;

import android.support.test.InstrumentationRegistry;

import com.innovatube.boilerplate.injection.component.ApplicationComponent;
import com.innovatube.boilerplate.injection.module.ApplicationModule;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by quanlt on 27/12/2016.
 */

public class EspressoDaggerMockRule extends DaggerMockRule<ApplicationComponent> {
    public EspressoDaggerMockRule() {
        super(ApplicationComponent.class, new ApplicationModule(getApp()));
        set(new ComponentSetter<ApplicationComponent>() {
            @Override
            public void setComponent(ApplicationComponent applicationComponent) {
                getApp().setApplicationComponent(applicationComponent);
            }
        });
    }

    public static InnovatubeApplication getApp() {
        return (InnovatubeApplication) InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext()
                .getApplicationContext();
    }
}
