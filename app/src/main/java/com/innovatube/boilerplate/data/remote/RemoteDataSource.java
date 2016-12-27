package com.innovatube.boilerplate.data.remote;


import com.innovatube.boilerplate.data.model.UserId;

import rx.Observable;

/**
 * Created by quanlt on 27/12/2016.
 */

public interface RemoteDataSource {
    Observable<UserId> createJobSeekerAccount(String firstName,
                                              String lastName,
                                              String emailAddress,
                                              String password,
                                              String confirmPassword,
                                              String dob,
                                              String promotionCode);
}
