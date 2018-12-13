package com.catherine.forrealm.about_utils;

import android.app.Application;


import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
        initFresco();
    }

    private void initFresco() {
        Fresco.initialize(this);
    }

    private void initRealm() {
        Realm.init(this);
//        Realm realm = Realm.getDefaultInstance();
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

    }

}
