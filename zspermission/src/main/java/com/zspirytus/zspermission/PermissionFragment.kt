package com.zspirytus.zspermission

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.zspirytus.zspermission.utils.PermissionUtils

class PermissionFragment : Fragment() {

    private var mActivity: FragmentActivity? = null
    private var mCallbacks: HashMap<Int, ((Boolean, Array<out String>) -> Unit)?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        mActivity = activity
        mCallbacks = HashMap()
    }

    fun requestPermission(
        permissions: Array<String>,
        callback: ((isGranted: Boolean, deniedPermissions: Array<out String>) -> Unit)?
    ) {
        val requestCode = generateRequestCode()
        mCallbacks?.put(requestCode, callback)
        requestPermissions(permissions, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val callback = mCallbacks?.get(requestCode)
        val deniedPermissions = PermissionUtils.checkGrantResults(permissions, grantResults)
        mActivity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
        callback?.invoke(deniedPermissions.isEmpty(), deniedPermissions)
    }

    private fun generateRequestCode(): Int {
        return 233
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