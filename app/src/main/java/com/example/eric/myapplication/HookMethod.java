package com.example.eric.myapplication;

import android.app.AndroidAppHelper;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

import java.io.File;
import java.util.regex.Pattern;

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

        XposedHelpers.findAndHookMethod("java.lang.Runtime", lpparam.classLoader, "exec", String[].class, String[].class, File.class, new XC_MethodReplacement() {

            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {


                String[] execArray = (String[])param.args[0];

                XposedBridge.log("java.lang.Runtime exec : " + execArray[0]);

                if ((execArray != null) && (execArray.length >= 1))
                {
                    String firstParam = execArray[0];

                    String hook_sort = "";

                    if (firstParam.equals("su") || firstParam.endsWith("/su"))
                    {
                        hook_sort = "apply root privilege";
                    }else if (firstParam.contains("chmod") && (firstParam.equals("chmod") || firstParam.endsWith("chmod")))
                    {
                        hook_sort = "apply modify file privilege";
                    }else if (firstParam.contains("sh") && (firstParam.equals("sh") || firstParam.endsWith("sh")))
                    {
                        hook_sort = "apply run shell file privilege";
                    }else if (firstParam.contains("ps") && (firstParam.equals("ps") || firstParam.endsWith("ps")))
                    {
                        hook_sort = "apply process list privilege";
                    }else
                    {
                        hook_sort = "apply other senior privilege";
                    }

                    XposedBridge.log("firstParam : " + firstParam);
                    XposedBridge.log("hook_sort: " + hook_sort);

                    Toast.makeText(AndroidAppHelper.currentApplication(), "hook runtime exec  : " + hook_sort, Toast.LENGTH_SHORT).show();
                }


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
