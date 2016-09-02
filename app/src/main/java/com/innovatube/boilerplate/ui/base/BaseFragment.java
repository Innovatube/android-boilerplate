package com.innovatube.boilerplate.ui.base;

import android.support.v4.app.Fragment;

import com.innovatube.boilerplate.InnovatubeApplication;
import com.innovatube.boilerplate.injection.component.ActivityComponent;
import com.innovatube.boilerplate.injection.component.DaggerActivityComponent;


/**
 * Created by TOIDV on 5/19/2016.
 */
public class BaseFragment extends Fragment {

    ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(InnovatubeApplication.get(getActivity()).getComponent())
                    .build();
        }
        return activityComponent;
    }

}
