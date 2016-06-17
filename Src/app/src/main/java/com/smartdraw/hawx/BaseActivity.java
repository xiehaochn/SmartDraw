package com.smartdraw.hawx;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.smartdraw.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/14.
 */

public class BaseActivity extends AppCompatActivity {
    private boolean hideStatusBar=false;
    private int version;
    private final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private View decorView;


    private boolean needToolbar=false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivMore)
    ImageView ivMore;
    @BindView (R.id.ivBack)
    ImageView ivBack;

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView (layoutResID);
        bindViews ();
    }

    protected void bindViews()
    {
        ButterKnife.bind (this);
        setupToolbar ();
    }


    protected void setupToolbar()
    {
        if (toolbar != null&&needToolbar)
        {
            setSupportActionBar (toolbar);
        }
        ivBack.setVisibility (View.INVISIBLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(hideStatusBar){
            decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    decorView.setSystemUiVisibility(flags);
                }
            });
            version= Build.VERSION.SDK_INT;
            if (version>= Build.VERSION_CODES.KITKAT&&version<=Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                // Translucent status bar
                window.setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    public void setHideStatusBar(boolean hideStatusBar) {
        this.hideStatusBar = hideStatusBar;
    }

    public void setNeedToolBar(boolean needToolBar)
    {
        this.needToolbar = needToolBar;
    }

    public Toolbar getToolbar()
    {
        return toolbar;
    }

    public ImageView getIvMore()
    {
        return ivMore;
    }

    public ImageView getIvBack()
    {
        return ivBack;
    }
}
