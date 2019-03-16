package com.zspirytus.mylibrarytest;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class PermissionDeniedDialog extends DialogFragment {

    private static final String CONTENT_KEY = "content";

    private TextView mTitle;
    private TextView mTip;
    private TextView mOkBtn;
    private TextView mCancelBtn;

    private OnButtonClickListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PermissionDeniedDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_permission_denied, container, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle = view.findViewById(R.id.title);
        mTip = view.findViewById(R.id.tip_text);
        mOkBtn = view.findViewById(R.id.ok_btn);
        mCancelBtn = view.findViewById(R.id.cancel_btn);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();

        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.dimAmount = 0.8f;
        lp.width = getPixelsConfig()[0] - 2 * dp2px(getContext(), 40);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(lp);

        getDialog().getWindow().setAttributes(lp);
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        getDialog().setCanceledOnTouchOutside(true);
    }

    protected void initView() {
        int contentResId = getArguments().getInt(CONTENT_KEY);
        mTip.setText(contentResId);
        if (mListener != null) {
            mOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onPositiveBtnClick();
                    dismiss();
                }
            });
            mCancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onNegativeBtnClick();
                    dismiss();
                }
            });
        }
    }

    public PermissionDeniedDialog setOnButtonClickListener(OnButtonClickListener listener) {
        mListener = listener;
        return this;
    }

    private int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private int[] getPixelsConfig() {
        int[] pixelsConfig = new int[2];
        pixelsConfig[0] = Resources.getSystem().getDisplayMetrics().widthPixels;
        pixelsConfig[1] = Resources.getSystem().getDisplayMetrics().heightPixels;
        return pixelsConfig;
    }

    public static PermissionDeniedDialog getInstance(@StringRes int content) {
        PermissionDeniedDialog dialog = new PermissionDeniedDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(CONTENT_KEY, content);
        dialog.setArguments(bundle);
        return dialog;
    }

    public interface OnButtonClickListener {
        void onPositiveBtnClick();

        void onNegativeBtnClick();
    }
}
