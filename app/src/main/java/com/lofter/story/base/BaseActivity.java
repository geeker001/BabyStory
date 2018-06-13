package com.lofter.story.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lofter.story.R;


/**
 * activity 基类
 * created 2017.9.27
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected LayoutInflater mInflater;
    protected ViewGroup mViewGroupContent;
    protected RelativeLayout mLayoutMain;
    protected TextView mTextViewTitle;
    protected ImageView mImageViewRight;
    protected ImageView mImageViewRightSub;
    protected RelativeLayout mLayoutTitleBack;
    protected RelativeLayout mLayoutTitleRight;
    protected RelativeLayout mLayoutTitleRightSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置侵入式状态栏
        setStatusBar();
        setLayout();
    }

    private void setLayout() {
        mInflater = LayoutInflater.from(this);
        super.setContentView(R.layout.activity_base);

        mLayoutMain = findViewById(R.id.layout_main);
        mTextViewTitle = findViewById(R.id.textview_title_center);
        mImageViewRight = findViewById(R.id.imageview_title_right);
        mImageViewRightSub = findViewById(R.id.imageview_title_right_sub);
        mLayoutTitleBack = findViewById(R.id.layout_title_left);
        mLayoutTitleRight = findViewById(R.id.layout_title_right);
        mLayoutTitleRightSub = findViewById(R.id.layout_title_right_sub);
        mViewGroupContent = findViewById(R.id.layout_activity_content);

        mLayoutTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 隐藏返回按钮
     */
    public void hideLeftImage() {
        mLayoutTitleBack.setVisibility(View.GONE);
    }

    /**
     * 隐藏右边
     */
    public void hideRightText() {
        mImageViewRight.setVisibility(View.GONE);
    }

    /**
     * 隐藏标题栏
     */
    public void hideTitleBar() {
        RelativeLayout titleLayout = findViewById(R.id.layout_title_bar);
        mLayoutMain.removeView(titleLayout);
    }

    /**
     * 加载子页面layout
     *
     * @paramre source 资源文件
     */
    @Override
    public void setContentView(int resource) {
        mViewGroupContent.addView(mInflater.inflate(resource, null, false),
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 设置标题栏文字
     */
    protected void setTitle(String str) {
        mTextViewTitle.setText(str);
    }

    /**
     * 设置标题栏右侧图片
     */
    protected void setRightImage(int resId) {
        mImageViewRight.setVisibility(View.VISIBLE);
        mImageViewRight.setImageResource(resId);
    }

    /**
     * 设置标题栏右侧图片2
     */
    protected void setRightSubImage(int resId) {
        mImageViewRightSub.setVisibility(View.VISIBLE);
        mImageViewRightSub.setImageResource(resId);
    }

    /**
     * Toast提示
     */
    protected void Toast(String psTip) {
        Toast.makeText(this, psTip, Toast.LENGTH_SHORT).show();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //给状态栏设置颜色
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.app_color_green));
            //导航栏保持默认颜色
//            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.app_color_blue));
        }
    }

}
