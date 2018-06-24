package com.zspirytus.mylibrarytest;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zspirytus.zspermission.PermissionGroup;
import com.zspirytus.zspermission.ZSPermission;

public class MainActivity extends AppCompatActivity implements ZSPermission.OnPermissionListener {

    private static final String TAG = "MainActivity";

    private static int REQUEST_CAMERA = 233;
    private static int GO_TO_SETTINGS = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grant();
    }

    private void grant() {
        ZSPermission.getInstance()
                .at(this)
                .requestCode(REQUEST_CAMERA)
                .permissions(PermissionGroup.CAMERA_GROUP)
                .permissions(PermissionGroup.PHONE_GROUP)
                .listenBy(this)
                .request();
    }

    @Override
    public void onGranted() {
        Toast.makeText(this, "successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDenied() {
        Toast.makeText(this, "denied by user", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNeverAsk() {
        new AlertDialog.Builder(this)
                .setTitle("我需要权限")
                .setMessage("我需要权限，以确保正常运行，是否前往设置开启？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ZSPermission.getInstance().onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, GO_TO_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        grant();
    }
}
