package com.tensun.additiveanimationsdemo;

import android.app.Application;
import android.content.Context;

/** 應用程式一打開, 上下文就存在了, 直到此應用程式結束 */
public class AAApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
