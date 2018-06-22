package com.zspirytus.zspermission.Util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by zhangkunwei on 2018/6/22.
 */

public class PermissionUtil {

    private PermissionUtil(){
        throw new AssertionError();
    }

    public static boolean checkIfGranted(Activity activity,String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(activity, permissions[i]) == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
}
