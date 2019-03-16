package com.zspirytus.mylibrarytest;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zspirytus.zspermission.ZSPermission;

public class MainActivity extends AppCompatActivity implements ZSPermission.OnPermissionListener {

    private static final String TAG = "MainActivity";

    private static int GO_TO_SETTINGS = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grant();
    }

    private void grant() {
        final int REQUEST_CAMERA = 233;
        ZSPermission.getInstance()
                .at(this)
                .requestCode(REQUEST_CAMERA)
                .permission(Manifest.permission.CAMERA)
                .permission(Manifest.permission.RECORD_AUDIO)
                .listenBy(this)
                .request();
    }

    @Override
    public void onGranted() {
        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDenied() {
        PermissionDeniedDialog.getInstance(R.string.permission_denied_tip_text)
                .setOnButtonClickListener(new PermissionDeniedDialog.OnButtonClickListener() {
                    @Override
                    public void onPositiveBtnClick() {
                        ZSPermission.getInstance().requestAgain();
                    }

                    @Override
                    public void onNegativeBtnClick() {
                    }
                }).show(getSupportFragmentManager(), "PermissionDeniedDialog");
    }

    @Override
    public void onNeverAsk() {
        PermissionDeniedDialog.getInstance(R.string.permission_never_ask_tip_text)
                .setOnButtonClickListener(new PermissionDeniedDialog.OnButtonClickListener() {
                    @Override
                    public void onPositiveBtnClick() {
                        goToAppSetting();
                    }

                    @Override
                    public void onNegativeBtnClick() {
                    }
                }).show(getSupportFragmentManager(), "PermissionDeniedDialog");
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
        if (requestCode == GO_TO_SETTINGS) {
            ZSPermission.getInstance().requestAgain();
        }
    }
}
