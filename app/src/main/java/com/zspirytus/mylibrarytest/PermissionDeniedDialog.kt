package com.zspirytus.mylibrarytest

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView

class PermissionDeniedDialog : DialogFragment() {

    private var mTitle: TextView? = null
    private var mTip: TextView? = null
    private var mOkBtn: TextView? = null
    private var mCancelBtn: TextView? = null

    private var mListener: OnButtonClickListener? = null

    private val pixelsConfig: IntArray
        get() {
            val pixelsConfig = IntArray(2)
            pixelsConfig[0] = Resources.getSystem().displayMetrics.widthPixels
            pixelsConfig[1] = Resources.getSystem().displayMetrics.heightPixels
            return pixelsConfig
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PermissionDeniedDialog)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_permission_denied, container, true)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTitle = view!!.findViewById(R.id.title)
        mTip = view.findViewById(R.id.tip_text)
        mOkBtn = view.findViewById(R.id.ok_btn)
        mCancelBtn = view.findViewById(R.id.cancel_btn)
        initView()
    }

    override fun onStart() {
        super.onStart()

        val lp = dialog.window!!.attributes
        lp.dimAmount = 0.8f
        lp.width = pixelsConfig[0] - 2 * dp2px(context, 40)
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp

        dialog.window!!.attributes = lp
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        dialog.setCanceledOnTouchOutside(true)
    }

    protected fun initView() {
        val contentResId = arguments.getInt(CONTENT_KEY)
        mTip!!.setText(contentResId)
        if (mListener != null) {
            mOkBtn!!.setOnClickListener {
                mListener!!.onPositiveBtnClick()
                dismiss()
            }
            mCancelBtn!!.setOnClickListener {
                mListener!!.onNegativeBtnClick()
                dismiss()
            }
        }
    }

    fun setOnButtonClickListener(listener: OnButtonClickListener): PermissionDeniedDialog {
        mListener = listener
        return this
    }

    private fun dp2px(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    interface OnButtonClickListener {
        fun onPositiveBtnClick()

        fun onNegativeBtnClick()
    }

    companion object {

        private val CONTENT_KEY = "content"

        fun getInstance(@StringRes content: Int): PermissionDeniedDialog {
            val dialog = PermissionDeniedDialog()
            val bundle = Bundle()
            bundle.putInt(CONTENT_KEY, content)
            dialog.arguments = bundle
            return dialog
        }
    }
}
