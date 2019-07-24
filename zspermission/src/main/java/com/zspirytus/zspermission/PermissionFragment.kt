package com.zspirytus.zspermission

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.zspirytus.zspermission.utils.PermissionUtils

class PermissionFragment : Fragment() {

    private var mActivity: FragmentActivity? = null
    private var mCallback: ((Boolean, Array<out String>, Boolean) -> Unit)? = null
    private var mRequestCode: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        mActivity = activity
    }

    fun requestPermission(
        permissions: Array<String>,
        callback: ((Boolean, Array<out String>, Boolean) -> Unit)?
    ) {
        mRequestCode = 233
        mCallback = callback
        requestPermissions(permissions, mRequestCode as Int)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val deniedPermissions = PermissionUtils.checkGrantResults(permissions, grantResults)
        mActivity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
        val shouldShowRequestPermissionRationale: Boolean = if (deniedPermissions.isEmpty()) {
            false
        } else {
            mActivity?.let { !ActivityCompat.shouldShowRequestPermissionRationale(it, deniedPermissions[0]) }
                ?: false
        }
        mCallback?.invoke(deniedPermissions.isEmpty(), deniedPermissions, shouldShowRequestPermissionRationale)
    }

    companion object {
        private const val TAG = "PermissionFragment"

        fun getInstance(activity: FragmentActivity): PermissionFragment {
            val manager = activity.supportFragmentManager
            return if (manager.findFragmentByTag(TAG) == null) {
                val fragment = PermissionFragment()
                manager.beginTransaction().add(fragment, TAG)?.commitAllowingStateLoss()
                manager.executePendingTransactions()
                fragment
            } else {
                manager.findFragmentByTag(TAG) as PermissionFragment
            }
        }
    }
}