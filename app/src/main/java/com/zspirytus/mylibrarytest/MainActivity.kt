package com.zspirytus.mylibrarytest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.zspirytus.zspermission.PermissionFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permissions = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        )
        PermissionFragment.getInstance(this).requestPermission(permissions) { isGranted, deniedPermissions ->
            if (isGranted) {
                Toast.makeText(MainActivity@ this, "Granted.", Toast.LENGTH_SHORT).show()
            } else {
                deniedPermissions.forEach {
                    Log.e(TAG, "not grant: $it")
                }
            }
        }
    }

    private fun goToAppSetting() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, GO_TO_SETTINGS)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val GO_TO_SETTINGS = 666
    }
}
