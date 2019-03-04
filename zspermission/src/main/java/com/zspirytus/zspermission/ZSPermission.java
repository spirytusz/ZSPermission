package com.zspirytus.zspermission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.zspirytus.zspermission.Util.PermissionUtil;

import java.util.Arrays;

/**
 * Created by ZSpirytus on 2018/6/22.
 */

public class ZSPermission {

    private static ZSPermission instance;

    private Activity activity;
    private int code;
    private String[] permissions;
    private OnPermissionListener listener;

    private ZSPermission() {

    }

    public interface OnPermissionListener {

        void onGranted();

        void onDenied();

        void onNeverAsk();

    }

    public static ZSPermission getInstance() {
        if (instance == null) {
            instance = new ZSPermission();
        }
        return instance;
    }

    public ZSPermission at(Activity activity) {
        this.activity = activity;
        return instance;
    }

    public ZSPermission at(android.app.Fragment fragment) {
        this.activity = fragment.getActivity();
        return instance;
    }

    public ZSPermission at(android.support.v4.app.Fragment fragment) {
        this.activity = fragment.getActivity();
        return instance;
    }

    public ZSPermission requestCode(Integer requestCode) {
        code = requestCode;
        return instance;
    }

    public ZSPermission permission(String permission) {
        return permissions(new String[]{permission});
    }

    public ZSPermission permissions(String[] permissions) {
        if (this.permissions == null) {
            this.permissions = permissions;
        } else {
            int len = this.permissions.length;
            this.permissions = Arrays.copyOf(this.permissions, len + permissions.length);
            System.arraycopy(permissions, 0, this.permissions, len, permissions.length);
        }
        return instance;
    }

    public ZSPermission listenBy(OnPermissionListener listener) {
        this.listener = listener;
        return instance;
    }

    public void request() {
        // 运行的安卓版本低于Android M
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (listener != null) {
                listener.onGranted();
                free();
            } else {
                throw new AssertionError("You must implement interface:" + OnPermissionListener.class.getSimpleName());
            }
            return;
        }
        // 运行的安卓版本不低于Android M
        if (activity != null && code != 0
                && permissions != null
                && permissions.length != 0) {
            if (PermissionUtil.checkIfGranted(activity, permissions)) {
                if (listener != null) {
                    listener.onGranted();
                    free();
                } else {
                    throw new AssertionError("You must implement interface:" + OnPermissionListener.class.getSimpleName());
                }
            } else {
                PermissionUtil.requestPermissions(activity, permissions, code);
            }
        }
    }

    private void free() {
        activity = null;
        code = 0;
        permissions = null;
        listener = null;
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (instance != null) {
            if (code == requestCode) {
                if (grantResults.length == 0) {
                    request();
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (listener != null) {
                        listener.onGranted();
                        free();
                    } else {
                        throw new AssertionError("You must implement interface:" + OnPermissionListener.class.getSimpleName());
                    }
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                        //用户拒绝权限的申请，并选择了不再提醒
                        if (listener != null) {
                            listener.onNeverAsk();
                        } else {
                            throw new AssertionError("You must implement interface:" + OnPermissionListener.class.getSimpleName());
                        }
                    } else {
                        //用户拒绝权限的申请
                        if (listener != null) {
                            listener.onDenied();
                        } else {
                            throw new AssertionError("You must implement interface:" + OnPermissionListener.class.getSimpleName());
                        }
                    }
                }
            }
        }
    }

}
