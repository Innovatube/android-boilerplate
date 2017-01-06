package com.innovatube.boilerplate;

import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;

/**
 * Created by quanlt on 04/01/2017.
 */

public class LoadingProgressIdlingResource implements IdlingResource {
    private final FragmentManager fragmentManager;
    private final String tag;
    private ResourceCallback resourceCallback;

    public LoadingProgressIdlingResource(FragmentManager fragmentManager, String tag) {
        this.fragmentManager = fragmentManager;
        this.tag = tag;
    }

    @Override
    public String getName() {
        return LoadingProgressIdlingResource.class.getSimpleName() + ":" + tag;
    }

    @Override
    public boolean isIdleNow() {
        boolean isIdling = fragmentManager.findFragmentByTag(tag) == null;
        if (isIdling) {
            resourceCallback.onTransitionToIdle();
        }
        return isIdling;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
