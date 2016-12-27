package com.innovatube.boilerplate.data.remote;


import com.innovatube.boilerplate.data.model.UserId;

import rx.Observable;

/**
 * Created by quanlt on 27/12/2016.
 */


public class RemoteDataSourceImpl implements RemoteDataSource {
    private final InnovatubeService mInnovatubeService;


    public RemoteDataSourceImpl(InnovatubeService mInnovatubeService) {
        this.mInnovatubeService = mInnovatubeService;
    }

    @Override
    public Observable<UserId> createJobSeekerAccount(String firstName,
                                                     String lastName,
                                                     String emailAddress,
                                                     String password,
                                                     String confirmPassword,
                                                     String dob,
                                                     String promotionCode) {
        return mInnovatubeService.createJobSeekerAccount(firstName,
                lastName, emailAddress, password, confirmPassword, dob, promotionCode);
    }
}
