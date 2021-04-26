package com.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MarshmallowPermissions {
    // Declare member variables
    final public int PERMISSION_REQUEST_CODE_LOCATION = 01;
    final public static int PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 03;
    final public static int PERMISSION_REQUEST_CODE_CAMERA_AND_STORAGE = 05;


    public static boolean checkPermissionCamera(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissionCamera(Activity activity, int requestCode) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            Toast.makeText(activity,
                    "CAMERA permission allows us to capture images. Please allow in App Settings for additional functionality.",
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    requestCode);
        }
    }

    public static boolean checkPermissionWriteExternalStorage(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    public static void requestPermissionWriteExternalStorage(Activity activity, int requestCode) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity,
                    "Write storage permission allows us to save capture images. Please allow in App Settings for additional functionality.",
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
    }

    public static boolean checkPermissionReadExternalStorage(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void startInstalledAppDetailsActivity(final Context context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public static boolean checkPermissionLocation(Activity mActivity) {
        int result = ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isPermissionAllowed(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissionLocation(Activity activity, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    requestCode);
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    requestCode);
        }
    }

    public static void requestPermission(Activity activity, String[] permission, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)) {
            ActivityCompat.requestPermissions(activity,
                    permission,
                    requestCode);
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                    requestCode);
        }
    }

    public static void requestPermissionCameraAndWriteExternalStorage(Activity activity, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        }
    }
}