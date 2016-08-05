package com.odinarts.android.storagescanner;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class Utils {
    public static final int TASK_COMPLETED = -1;
    public static final int TASK_CANCELLED = -2;

    public static int getTotalNumberOfFiles() {
        final ArrayList<File> fileList = new ArrayList<File>();
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) == true) {
            File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (root != null) {
                getFile(fileList, root);

                // Populate db.
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<File> list = fileList;
                    }
                });

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

    public static ArrayList<File> getAllFiles() {
        final ArrayList<File> fileList = new ArrayList<File>();
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) == true) {
            File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (root != null) {
                getFile(fileList, root);

                // TODO: figure out if we can do inserts here.
                // Populate db.
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<File> list = fileList;
                    }
                });

                return fileList;
            }
            else {
                return null;
            }
        }
        else {
            return null;  // SDCard is not mounted.
        }
    }


    private static void getFile(ArrayList<File> fileList, File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    //fileList.add(listFile[i]);
                    getFile(fileList, listFile[i]);

                }
                else {
                    fileList.add(listFile[i]);
                }
            }
        }
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        else {
            return "";
        }
    }
}
