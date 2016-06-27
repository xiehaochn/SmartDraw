package com.smartdraw.hawx;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.smartdraw.R;

/**
 * Created by Administrator on 2016/6/14.
 */

public class BaseActivity extends AppCompatActivity {
    private boolean hideStatusBar=true;
    private int version;
    private final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private View decorView;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        version= Build.VERSION.SDK_INT;
        if (version>= Build.VERSION_CODES.KITKAT&&version<=Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(hideStatusBar){
            decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    decorView.setSystemUiVisibility(flags);
                }
            });

        }else{

            toolbar= (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setNavigationIcon(R.mipmap.icon_navigation_back);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    public void setHideStatusBar(boolean hideStatusBar) {
        this.hideStatusBar = hideStatusBar;
    }

}
