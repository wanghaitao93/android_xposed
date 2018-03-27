package com.example.eric.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by eric on 2018/3/13.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String tempPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.apk";
    private PackageInfo mPackageInfo;
    private Context mContext = MainActivity.this;

    private TextView tv;
    private Button btn_check_root;
    private Button btn_install_apk;
    private Button btn_uninstall_app;
    private Button btn_activate_app;
    private Button btn_open_app;
    private Button btn_close_app;

    private static Process process = null;

    // xposed package info
//    private static String apkName = "de.robv.android.xposed.installer_v33_36570c.apk";
    private static String apkName = "app-release.apk";
    private static String packageName = "de.robv.android.xposed.installer";
    private static String className = "CustomActivity";
    private static String className_Welcome = "WelcomeActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            process = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }

        btn_check_root = findViewById(R.id.btn_check_root);
        btn_install_apk = findViewById(R.id.btn_install_apk);
        btn_uninstall_app = findViewById(R.id.btn_uninstall_app);
        btn_open_app = findViewById(R.id.btn_open_app);
        btn_close_app = findViewById(R.id.btn_close_app);
        btn_activate_app = findViewById(R.id.btn_activate_app);

        // check machine whether root
        btn_check_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RootUtils.isRooted())
                {
                    Log.i(TAG, "The machine is rooted");
                }
            }
        });

        // install apk
        btn_install_apk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                installFromAssets();
            }
        });

        // uninstall app
        btn_uninstall_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                uninstalllAPP();
            }
        });

        // activate app
        btn_activate_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activateApp(mContext);
            }
        });

        // open app
        btn_open_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                openApp(mContext);
            }
        });

        // close app
        btn_close_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                closeApp(mContext);
            }
        });

    }

    /**
     * install apk but hidden install interface
     **/
    public void slientInstall()
    {
        createFile();
        Log.i(TAG, "start slient install apk");
        File file = new File(tempPath);
        Log.i(TAG , "file.getPath()：" + file.getPath());
        if (file.exists()) {
            System.out.println(file.getPath() + "==");
            String s1 = "chmod 777 " + file.getPath()+ "\n";
            String s2 = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + file.getPath();
            execRootShellCmd(s1, s2);
        }
    }

    /*
    *   acquire apk from assets floder & install apk
     */
    public void installFromAssets()
    {
        try
        {
            // acquire package info
            mPackageInfo = getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e)
        {
            mPackageInfo = null;
            e.printStackTrace();
        }

        if (mPackageInfo == null) {
            // start new thread for install apk
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    Log.i(TAG , "installing apk");
                    // install apk but hidden install interface
                    slientInstall();
                }
            }).start();
        } else {
            Log.i(TAG , "installed apk");
        }
    }

    /**
     *  resource change, assets apk move to available read-write floder
     */
    public void createFile() {
        InputStream is = null;
        FileOutputStream fos = null;
        try
        {
            // acquire assets floder xposed apk
            is = getAssets().open(apkName);

            File file = new File(tempPath);
            Log.i(TAG, "tempPath" + tempPath);
            file.createNewFile();
            fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;

            while ((i = is.read(temp)) > 0)
            {
                fos.write(temp, 0, i);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try
                {
                    fos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     *  traditional install
     */
    public void normalInstall(Context context)
    {
        createFile();
        Log.i(TAG, "start normal install App");
        File file = new File(tempPath);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /*
    *  uninstall app
     */
    public void uninstalllAPP()
    {
//        Uri packageUri = Uri.parse("package:"+ packageName);
//        Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
//        startActivity(intent);
        Log.i(TAG, "uninstall app");
        String s = "pm uninstall " + packageName + "\n";
        Log.i(TAG, s);
        execRootShellCmd(s);
    }


    /*
    *   activate app
     */
    private void activateApp(Context context)
    {
        Log.i(TAG, "activate App  ");
        String s = "am start -S  " + packageName + "/"
                + packageName + "." + className + " \n";
        Log.i(TAG, s);
        execRootShellCmd(s);
    }

    /**
     *  open app
     */
    private void openApp(Context context)
    {
        Log.i(TAG, "open App  ");
        String s = "am start -S  " + packageName + "/"
                + packageName + "." + className_Welcome + " \n";
        Log.i(TAG, s);
        execRootShellCmd(s);
    }

    /*
      *  close app
     */
    private void closeApp(Context context)
    {
        Log.i(TAG, "close App");
//        String s = "am force-stop " + packageName + " \n";
        String s = "am force-stop " + packageName + " \n";

        Log.i(TAG, s);
        execRootShellCmd(s);
    }


    /**
     * exe shell command
     *
     * @param cmds
     * @return
     */
    private boolean execRootShellCmd(String... cmds)
    {
        if (cmds == null || cmds.length == 0) {
            return false;
        }

        DataOutputStream dos = null;
        InputStream dis = null;
        try
        {
            dos = new DataOutputStream(process.getOutputStream());

            for (int i = 0; i < cmds.length; i++) {
                dos.writeBytes(cmds[i] + " \n");
            }
            dos.writeBytes("exit \n");

            int code = process.waitFor();

            return code == 0;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (process != null) {
//                    process.destroy();
//                    process = null;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return false;
    }
}

