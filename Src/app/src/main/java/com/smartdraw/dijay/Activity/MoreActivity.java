package com.smartdraw.dijay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.smartdraw.R;
import com.smartdraw.hawx.BaseActivity;

/**
 * Author：DJ
 * Time：2016/6/17 17:14
 * Name：Src
 * Description：
 */
public class MoreActivity extends BaseActivity
{
//    @BindView(R.id.llAbout)
//    LinearLayout llAbout;
//    @BindView (R.id.llSuggestion)
//    LinearLayout llSuggestion;

    private LinearLayout llAbout;
    private LinearLayout llSuggestion;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_more);



        llAbout = (LinearLayout) findViewById(R.id.llAbout);
        llSuggestion = (LinearLayout) findViewById(R.id.llSuggestion);

        llAbout.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent ();
                intent.setClass (MoreActivity.this, AboutActivity.class);
                startActivity (intent);
            }
        });

        llSuggestion.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent ();
                intent.setClass (MoreActivity.this, ContactUsActivity.class);
                startActivity (intent);
            }
        });
    }

}