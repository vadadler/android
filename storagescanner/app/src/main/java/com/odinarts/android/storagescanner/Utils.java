package com.odinarts.android.storagescanner;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class Utils {

    public static int getTotalNumberOfFiles() {
        ArrayList<File> fileList = new ArrayList<File>();
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) == true) {
            File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (root != null) {
                getFile(fileList, root);
                return fileList.size();
            }
            else {
                return -1;
            }
        }
        else {
            return -1;  // SDCard is not mounted.
        }
    }


    private static void getFile(ArrayList<File> fileList, File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    fileList.add(listFile[i]);
                    getFile(fileList, listFile[i]);

                }
                else {
                    fileList.add(listFile[i]);
                }

            }
        }
    }
}
