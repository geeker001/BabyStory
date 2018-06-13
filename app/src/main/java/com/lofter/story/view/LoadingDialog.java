package com.lofter.story.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.lofter.story.R;


public class LoadingDialog {

    private TextView mTextViewTip;
    private Dialog mDialog;

    public LoadingDialog(Context context) {
        mDialog = new Dialog(context, R.style.loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        mDialog.setContentView(R.layout.item_dialog_loading);
//        View view = inflater.inflate(R.layout.item_dialog_loading, null);
//        mDialog.setContentView(view, new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        mDialog.setCancelable(false);

        mTextViewTip = mDialog.findViewById(R.id.texview_loading_tip);
    }

    public void showMessage(String message) {
        mTextViewTip.setText(message);
        mDialog.show();
    }

    public void hide() {
        mDialog.dismiss();
    }
}
