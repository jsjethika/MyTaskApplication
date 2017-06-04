package com.android.fluktask.mytaskapplication;

import android.app.Application;
import android.content.Context;
import org.androidannotations.annotations.EApplication;


/**
 * Created by admin on 6/1/2017.
 */


@EApplication
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getName();


    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }


    public static Context getContext() {
        return mContext;
    }

}
