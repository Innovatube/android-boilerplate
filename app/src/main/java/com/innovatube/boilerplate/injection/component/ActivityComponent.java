package com.innovatube.boilerplate.injection.component;



import com.innovatube.boilerplate.injection.PerActivity;
import com.innovatube.boilerplate.injection.module.ActivityModule;
import com.innovatube.boilerplate.ui.signup.CreateAccountActivity;

import dagger.Component;

/**
 * Created by TOIDV on 4/4/2016.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)


public interface ActivityComponent {

    void inject(CreateAccountActivity createAccountActivity);


}
