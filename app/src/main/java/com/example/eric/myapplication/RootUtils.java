package com .example.eric.myapplication;

import android.util.Log;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class RootUtils {

    private static final String TAG = "RootUtils";
    private static Process process = null;

    @Deprecated
    public static boolean isRooted() {
        Process process = null;
        try
        {
            process = Runtime.getRuntime().exec("su");
            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();
            outputStream.write("id\n".getBytes());
            outputStream.flush();
            outputStream.write("exit\n".getBytes());
            outputStream.flush();
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s = bufferedReader.readLine();
            if (s.contains("uid=0")) return true;
        }
        catch (IOException e)
        {
            Log.e(TAG, "don't have root privilege");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally
        {
            if (process != null)
                process.destroy();
        }
        return false;
    }

    public static boolean checkRooted()
    {
        boolean result = false;
        try
        {
            result = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * exe shell command
     */
    public static boolean execRootShellCmd(String... cmds)
    {
        if (cmds == null || cmds.length == 0) {
            return false;
        }

        DataOutputStream dos = null;
        InputStream dis = null;
        try
        {
            process = Runtime.getRuntime().exec("su");

            dos = new DataOutputStream(process.getOutputStream());

            for (int i = 0; i < cmds.length; i++)
            {
                dos.writeBytes(cmds[i] + " \n");
            }
            dos.writeBytes("exit \n");

            dos.flush();

            int code = process.waitFor();
            Log.i(TAG, "code " + code);
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
                    process.destroy();
                    process = null;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return false;
    }
}