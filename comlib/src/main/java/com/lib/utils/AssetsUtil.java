package com.lib.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shenwenjie on 19/7/2016.
 */
public class AssetsUtil {


    private static final String TAG = "AssetsUtil";

    /**
     *
     * @param context
     * @param filePath eg:config
     * @return
     */
    public static boolean copyAssetsToDestination(Context context, String filePath) {
        boolean isSuccess = true;
        AssetManager assetManager = context.getAssets();
        try {
            String[] files = assetManager.list(filePath);
            for (String file : files) {
                boolean result = copyAssetsFileToDestination(context, filePath, file);
                isSuccess = isSuccess & result;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "could not find the assets dir " + filePath);
        }
        Log.d(TAG, "copy assets on path " +isSuccess);
        return isSuccess;
    }


    /**
     * @param context
     * @param filePath eg:/config
     * @param fileName eg:bankinfo.json
     * @return true if copy the file success,else false ;
     */
    public static boolean copyAssetsFileToDestination(Context context, String filePath, String fileName) {
        AssetManager assetManager = context.getAssets();
        //拷贝到系统data/data/packname/files/目录下
        String densitinationPath = context.getFilesDir().getAbsolutePath() + File.separator + fileName;
        Log.d(TAG, "copy assets file to destination >>>> " + densitinationPath);
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            inputStream = assetManager.open(filePath + File.separator + fileName);
            File destinationFile = new File(densitinationPath);
            if(!destinationFile.exists()){
                destinationFile.createNewFile();
            }else{
                Log.d(TAG, densitinationPath+ ">>>>>>file isExist");
                return true;
            }
            fileOutputStream = new FileOutputStream(destinationFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            fileOutputStream.flush();
            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "copy assets file to destination >>>> " + densitinationPath + "  >>>> failed");
            return false;
        } finally {
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        Log.d(TAG, "copy assets file to destination >>>> " + densitinationPath + "  >>>> true");
        return true;
    }
}
