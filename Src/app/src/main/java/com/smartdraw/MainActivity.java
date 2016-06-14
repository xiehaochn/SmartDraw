package com.smartdraw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartdraw.hawx.BaseActivity;

public class MainActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setHideStatusBar(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
