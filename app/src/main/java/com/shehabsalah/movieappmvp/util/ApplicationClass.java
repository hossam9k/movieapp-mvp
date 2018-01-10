package com.shehabsalah.movieappmvp.util;

import android.app.Application;
import android.content.Context;


public class ApplicationClass extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ApplicationClass.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ApplicationClass.context;
    }
}
