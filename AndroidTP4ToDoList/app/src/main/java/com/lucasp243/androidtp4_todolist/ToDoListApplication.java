package com.lucasp243.androidtp4_todolist;

import android.app.Application;
import android.content.Context;

public class ToDoListApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
