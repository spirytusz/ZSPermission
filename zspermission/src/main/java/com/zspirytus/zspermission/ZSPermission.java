package com.zspirytus.zspermission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.ArraySet;

import com.zspirytus.zspermission.Util.PermissionUtil;

import java.util.Set;

/**
 * Created by ZSpirytus on 2018/6/22.
 */

public class ZSPermission {

    private static ZSPermission instance;

    private Activity activity;
    private int code;
    private String[] permissions;
    private OnPermissionListener listener;

    private Set<Integer> codes = new ArraySet<>();

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
        if (!codes.contains(requestCode)) {
            code = requestCode;
            codes.add(code);
            return instance;
        } else {
            throw new AssertionError("requestCode should be unique!");
        }
    }

    public ZSPermission permission(String permission) {
        this.permissions = new String[]{permission};
        return instance;
    }

    public ZSPermission permissions(String[] permissions) {
        this.permissions = permissions;
        return instance;
    }

    public ZSPermission listenBy(OnPermissionListener listener) {
        this.listener = listener;
        return instance;
    }

    public void request() {
        if (activity != null && code != 0
                && permissions != null
                && permissions.length != 0) {
            if (PermissionUtil.checkIfGranted(activity,permissions)) {
                if (listener != null) {
                    listener.onGranted();
                } else {
                    throw new AssertionError("You should implement interface:" + OnPermissionListener.class.getSimpleName());
                }
            } else {
                PermissionUtil.requestPermissions(activity, permissions, code);
            }
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (instance != null) {
            if (codes.contains(requestCode)) {
                codes.remove(requestCode);
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (listener != null) {
                        listener.onGranted();
                    } else {
                        throw new AssertionError("You should implement interface:" + OnPermissionListener.class.getSimpleName());
                    }
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                        //用户拒绝权限的申请，并选择了不再提醒
                        if (listener != null) {
                            listener.onNeverAsk();
                        } else {
                            throw new AssertionError("You should implement interface:" + OnPermissionListener.class.getSimpleName());
                        }
                    } else {
                        //用户拒绝权限的申请
                        if (listener != null) {
                            listener.onDenied();
                        } else {
                            throw new AssertionError("You should implement interface:" + OnPermissionListener.class.getSimpleName());
                        }
                    }
                }
            }
        }
    }

}
