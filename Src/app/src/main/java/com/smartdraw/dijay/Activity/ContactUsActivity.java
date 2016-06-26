package com.smartdraw.dijay.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.smartdraw.R;
import com.smartdraw.hawx.BaseActivity;

/**
 * Author：DJ
 * Time：2016/6/17 17:14
 * Name：Src
 * Description：
 */
public class ContactUsActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_contact_us);
        ImageView ivBack = (ImageView) findViewById (R.id.ivBack);
        if (ivBack != null)
        {
            ivBack.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v)
                {
                    finish ();
                }
            });
        }
    }
}