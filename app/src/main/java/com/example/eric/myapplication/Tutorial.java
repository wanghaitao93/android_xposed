package com.example.eric.myapplication;

import android.graphics.Color;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by eric on 26/02/2018.
 */

public class Tutorial implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.android.systemui"))
            return;

        //XposedBridge.log("Loaded app: " + lpparam.packageName);


        XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpparam.classLoader, "updateClock", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();
                tv.setText(text + " :)");
                tv.setTextColor(Color.GREEN);
            }
        });
    }
}
