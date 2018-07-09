package com.example.eric.myapplication;

import java.io.File;

public class FileUtils {


    private static final String TAG = "RootUtils";

    // check file whether exist
    public static boolean isExistFile(String file_path) {
        File file = new File(file_path);
        file.isFile();
        return file.exists();
    }

    // delete file
    public static void delFile(String file_path) {
        File file = new File(file_path);
        if (file.exists())
            file.delete();
    }


    // delelte dir and dir file
    public static void delDir(String dir_path) {
        File dir = new File(dir_path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                delDir(dir_path);
        }
        dir.delete();
    }

}
