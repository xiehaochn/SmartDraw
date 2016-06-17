package com.smartdraw.dijay.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smartdraw.R;
import com.smartdraw.hawx.BaseActivity;

/**
 * Author：DJ
 * Time：2016/6/17 17:14
 * Name：Src
 * Description：
 */
public class AboutActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setHideStatusBar (true);
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_about);
        setNeedToolBar (true);
    }
}