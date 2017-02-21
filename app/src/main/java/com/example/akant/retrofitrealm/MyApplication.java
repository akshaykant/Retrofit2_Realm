package com.example.akant.retrofitrealm;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by akant on 2/21/2017.
 */

public class MyApplication extends Application {

        @Override
        public void onCreate() {
              super.onCreate();

                //Initialize Sthehto in Debug build
                if (BuildConfig.DEBUG) {
                        Stetho.initialize(
                                Stetho.newInitializerBuilder(this)
                                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                                        .build()
                        );
                }
        }
}
