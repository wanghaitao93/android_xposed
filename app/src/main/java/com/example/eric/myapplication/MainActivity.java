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
    private Button bt_check_root;
    private Button bt_install_apk;
    private Button bt_open_app;
    private Button bt_uninstall_app;

    // xposed package info
    private static String apkName = "de.robv.android.xposed.installer_v33_36570c.apk";
    private static String packageName = "de.robv.android.xposed.installer";
    private static String className = "WelcomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_check_root = findViewById(R.id.btn_check_root);
        bt_install_apk = findViewById(R.id.btn_install_apk);
        bt_uninstall_app = findViewById(R.id.btn_uninstall_app);
        bt_open_app = findViewById(R.id.btn_open_app);

        // check machine whether root
        bt_check_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RootUtils.isRooted())
                {
                    Log.i(TAG, "The machine is rooted");
                }
            }
        });

        // install apk
        bt_install_apk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // acquire apk from assets floder & install apk
                installFromAssets();
            }
        });

        // uninstall app
        bt_uninstall_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                uninstalllAPP();
            }
        });

        // open app
        bt_open_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startApp(mContext);
            }
        });

    }

    // acquire apk from assets floder & install apk
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
     * install apk but hidden install interface
     **/
    public boolean slientInstall()
    {
        createFile();
        Log.i(TAG, "start slient install apk");
        File file = new File(tempPath);
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        Log.i(TAG , "file.getPath()：" + file.getPath());
        if (file.exists()) {
            System.out.println(file.getPath() + "==");
            try {
                process = Runtime.getRuntime().exec("su");
                out = process.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(out);
                // acquire file all privilege
                dataOutputStream.writeBytes("chmod 777 " + file.getPath()
                        + "\n");
                // install command
                dataOutputStream
                        .writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r "
                                + file.getPath());
                dataOutputStream.flush();
                // close stream operation
                dataOutputStream.close();
                out.close();
                int value = process.waitFor();

                if (value == 0) {
                    Log.i(TAG , "Install Success");
                    result = true;
                } else if (value == 1) {
                    Log.i(TAG , "Install Fail！");
                    result = false;
                } else {
                    Log.i(TAG , "Unknown Situation");
                    result = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!result) {
                Log.i(TAG , "root privilege acquire fail，next normal install");
                normalInstall(mContext);
                result = true;
            }
        }
        return result;
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
        startApp(mContext);
    }

    /*
    *  uninstall app
    *
     */
    public void uninstalllAPP()
    {
        Uri packageUri = Uri.parse("package:"+ packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageUri);
        startActivity(intent);
    }


    /**
     * installed & start app
     */
    private static void startApp(Context context)
    {
        Log.i(TAG, "now startApp:  ");
        String s = "am start -S  " + packageName + "/"
                + packageName + "." + className + " \n";
        Log.i(TAG, s );
        execRootShellCmd(s);
    }

    /**
     * exe shell command
     *
     * @param cmds
     * @return
     */
    private static boolean execRootShellCmd(String... cmds)
    {
        if (cmds == null || cmds.length == 0) {
            return false;
        }

        DataOutputStream dos = null;
        InputStream dis = null;
        Process p = null;
        try
        {
            p = Runtime.getRuntime().exec("su");// 经过Root处理的android系统即有su命令
            dos = new DataOutputStream(p.getOutputStream());

            for (int i = 0; i < cmds.length; i++) {
                dos.writeBytes(cmds[i] + " \n");
            }
            dos.writeBytes("exit \n");

            int code = p.waitFor();

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
                if (p != null) {
                    p.destroy();
                    p = null;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return false;
    }
}
