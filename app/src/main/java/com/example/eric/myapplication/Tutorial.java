package com.example.eric.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by eric on 26/02/2018.
 */

public class Tutorial implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

//        if (!lpparam.packageName.equals("com.android.systemui"))
//            return;
        XposedBridge.log("Loaded app:  lpparam.packageName ： " + lpparam.packageName);
        XposedBridge.log("lpparam.appInfo.uid : " + lpparam.appInfo.uid);
//        if (lpparam.appInfo.uid < 10000)
        if (lpparam.appInfo == null ||
                (lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) !=0)
            return;

//        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                TextView tv = (TextView) param.thisObject;
//                String text = tv.getText().toString();
//                tv.setText(text + " :)");
//                tv.setTextColor(Color.GREEN);
//            }
//        });
        XposedHelpers.findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "getRuntime", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XposedBridge.log("hook   Loaded app: java.lang.Runtime.getRuntime()");

//                Toast.makeText(AndroidAppHelper.currentApplication(), "before hook method", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(AndroidAppHelper.currentApplication());
                builder.setTitle("Title")
                        .setMessage("Dialog content.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        })
                         .show();
                }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Toast.makeText(AndroidAppHelper.currentApplication(), "after hook method", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
