package com.innovatube.boilerplate.data.local;

import com.innovatube.boilerplate.data.model.UserId;

import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmObject;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by TOIDV on 8/18/2016.
 */
@Singleton
public class RealmHelper {

    public Observable<RealmObject> getUserId(Realm realm) {
        return realm.where(UserId.class).findFirstAsync()
                .asObservable()
                .filter(new Func1<RealmObject, Boolean>() {
                    @Override
                    public Boolean call(RealmObject realmObject) {
                        return realmObject.isLoaded();
                    }
                })
                .filter(new Func1<RealmObject, Boolean>() {
                    @Override
                    public Boolean call(RealmObject realmObject) {
                        return realmObject.isValid();
                    }
                });
    }

    public void saveUserInfo(final UserId userId, Realm realm) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(userId);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }
}
