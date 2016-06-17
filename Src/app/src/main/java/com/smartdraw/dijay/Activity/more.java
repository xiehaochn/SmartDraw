package com.smartdraw.dijay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.smartdraw.R;
import com.smartdraw.hawx.BaseActivity;

import butterknife.BindView;

/**
 * Author：DJ
 * Time：2016/6/17 17:14
 * Name：Src
 * Description：
 */
public class more extends BaseActivity
{
    @BindView(R.id.llAbout)
    LinearLayout llAbout;
    @BindView (R.id.llSuggestion)
    LinearLayout llSuggestion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setHideStatusBar (true);
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_more);
        setNeedToolBar (true);

        llAbout.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent ();
                intent.setClass (more.this, about.class);
                startActivity (intent);
            }
        });

        llSuggestion.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent ();
                intent.setClass (more.this, contact_us.class);
                startActivity (intent);
            }
        });
    }

}