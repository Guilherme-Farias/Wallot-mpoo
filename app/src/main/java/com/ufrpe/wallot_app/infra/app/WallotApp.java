package com.ufrpe.wallot_app.infra.app;

        import android.app.Application;
        import android.content.Context;

public class WallotApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }

}
