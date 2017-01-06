package com.innovatube.boilerplate.utils;

import android.support.test.espresso.IdlingResource;

/**
 * Created by quanlt on 06/01/2017.
 */
public class EspressoIdlingResource {
    private static final String RESOURCE_NAME = "Innovatube";

    private static SimpleCountingIdlingResource mCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE_NAME);

    public static void increment() {
        mCountingIdlingResource.increment();
    }

    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}
