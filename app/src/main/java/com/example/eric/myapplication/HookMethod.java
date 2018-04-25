package com.example.eric.myapplication;

import android.app.AndroidAppHelper;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by eric on 26/02/2018.
 */

public class HookMethod implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        XposedBridge.log("Loaded app:  lpparam.packageName ： " + lpparam.packageName);

        // neglect system app and android_xposed app
        if (lpparam.appInfo == null ||
                (lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) !=0 ||
                lpparam.packageName.equals("com.example.eric.myapplication") ||
                lpparam.packageName.equals("de.robv.android.xposed.installer"))
            return;

        XposedHelpers.findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "getRuntime", new XC_MethodReplacement() {

            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {

                Toast.makeText(AndroidAppHelper.currentApplication(), "find app request root privilege", Toast.LENGTH_SHORT).show();

//                AlertDialog.Builder builder = new AlertDialog.Builder(AndroidAppHelper.currentApplication());
//                builder.setTitle("dangrous")
//                        .setMessage(lpparam.packageName + "want to visit root privilege, whether is not stop this request")
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                            }
//                        })
//                         .show();
                return null;
            }
        });


        // hook system clock class
//        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                TextView tv = (TextView) param.thisObject;
//                String text = tv.getText().toString();
//                tv.setText(text + " :)");
//                tv.setTextColor(Color.GREEN);
//            }
//        });

    }
}
