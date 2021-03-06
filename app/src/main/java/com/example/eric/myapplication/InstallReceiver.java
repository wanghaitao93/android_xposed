package com.example.eric.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by eric on 2018/3/13.
 */

public class InstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")){
            String packageName = intent.getDataString();
            System.out.println("update:" + packageName + " package name");
//            startApp(context);
        }

        //  receive install broadcast
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            System.out.println("installed : " + packageName + " package name");
//            startApp(context);
        }

        //  receive uninstall broadcast
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            System.out.println("uninstall : "  + packageName + " package name");
        }
    }

    //  receive update broadcast
//    public void startApp(Context context)
// {
//        // e open installed apk order to package nam
//        Intent resolveIntent = context.getPackageManager().getLaunchIntentForPackage("com.test.testapk");
//        context.startActivity(resolveIntent);
//
//        // oepn app update
////        Intent intent = new Intent(context, MainActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        context.startActivity(intent);
//    }
}
