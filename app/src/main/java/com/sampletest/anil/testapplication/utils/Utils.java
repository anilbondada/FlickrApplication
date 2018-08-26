package com.sampletest.anil.testapplication.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.sampletest.anil.testapplication.model.Photo;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by H211060 on 8/24/2018.
 */

public class Utils {
    private static final String TAG = "Utils";
    private static int totalNumberOfPages = 0;
    private static int currentPage = 0;
    private static ExecutorService mImageAsyncService;

    public static void doTheTaskInParallel(AsyncTask task,Object... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // Android 4.4 (API 19) and above
            // Parallel AsyncTasks are possible, with the thread-pool size dependent on device
            // hardware
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,params);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) { // Android 3.0 to
            // Android 4.3
            // Parallel AsyncTasks are not possible unless using executeOnExecutor
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else { // Below Android 3.0
            // Parallel AsyncTasks are possible, with fixed thread-pool size
            task.execute(params);
        }
    }

    public static void doTheTaskInSerial(AsyncTask task,Object... params){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // Android 4.4 (API 19) and above
            // Parallel AsyncTasks are possible, with the thread-pool size dependent on device
            // hardware
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,params);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) { // Android 3.0 to
            // Android 4.3
            // Parallel AsyncTasks are not possible unless using executeOnExecutor
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else { // Below Android 3.0
            // Parallel AsyncTasks are possible, with fixed thread-pool size
            task.execute(params);
        }
    }

    public static String getImageUrl(Photo photo) {
        String url = null;
        StringBuilder builder = new StringBuilder(150).append("http://").append("farm").append(photo.getFarm())
                .append(".static.flickr.com/").append(photo.getServer()).append("/")
                .append(photo.getId()).append("_").append(photo.getSecret()).append(".jpg");

        url = builder.toString();
        Log.d(TAG, "getImageUrl: "+url);
        return url;
    }

    public static boolean permissionsAccepted(Context context){
        if(checkWriteExternalPermission(context)){
            return true;
        }
        return false;
    }

    private static boolean checkWriteExternalPermission(Context context)
    {
        String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean isDeviceOnline(Context context){
        ConnectivityManager conMgr =  (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            return false;
        }else{
            return true;
        }
    }

    public static void setCurrentAndTotalPages(int currPage, int totalPages){
        totalNumberOfPages = totalPages;
        currentPage = currPage;
    }

    public static int getTotalNumberOfPages(){
        return totalNumberOfPages;
    }

    public static int getCurrentPage(){
        return currentPage;
    }

    public static void executeOnThreadPool(Runnable r) {
        mImageAsyncService = Executors.newCachedThreadPool();
        mImageAsyncService.submit(r);
    }

    public static void shutDownExecuter(){
        if(mImageAsyncService != null){
            mImageAsyncService.shutdown();
        }
    }
}
