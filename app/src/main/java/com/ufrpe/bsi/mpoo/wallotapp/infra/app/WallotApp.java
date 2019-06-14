package com.ufrpe.bsi.mpoo.wallotapp.infra.app;

import android.app.Application;
import android.content.Context;

public class WallotApp extends Application {
    private static Context Context;

    @Override
    public void onCreate() {
        super.onCreate();
        Context = this;
    }

    public static Context getContext(){
        return Context;
    }

}
