package com.zspirytus.zspermission.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by ZSpirytus on 2018/6/22.
 */

@Suppress("unused", "MemberVisibilityCanBePrivate")
object PermissionUtils {

    fun checkIfPermissionHasGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun checkIfPermissionsHasGranted(context: Context, permissions: Array<String>): Boolean {
        permissions.forEach {
            if (!checkIfPermissionHasGranted(context, it)) {
                return false
            }
        }
        return true
    }

    fun checkGrantResults(permissions: Array<out String>, grantResults: IntArray): Array<out String> {
        return permissions.filterIndexed { index, _ ->
            grantResults[index] != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
    }

    fun requestPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }
}
