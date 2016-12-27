package com.innovatube.boilerplate.data.local;

import com.innovatube.boilerplate.data.model.UserId;

import io.realm.Realm;
import io.realm.RealmObject;
import rx.Observable;

/**
 * Created by quanlt on 27/12/2016.
 */

public interface LocalDataSource {
    Observable<RealmObject> getUserId(Realm realm);
    void saveUserInfo(final UserId userId, Realm realm);
}
