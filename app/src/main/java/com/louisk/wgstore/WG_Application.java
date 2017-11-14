package com.louisk.wgstore;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.louisk.wgstore.utils.DbUtils;
import com.louisk.wgstore.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class WG_Application extends Application {

    private static WG_Application mApp;

    private List<Activity> activities = new ArrayList<Activity>();

    private SQLiteDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        PreferenceUtil.init(mApp);
        try {
            DbUtils.getInstance().openDb(this);//打开数据库
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return mApp;
    }


    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : activities) {
            activity.finish();
        }

        System.exit(0);
    }
}
