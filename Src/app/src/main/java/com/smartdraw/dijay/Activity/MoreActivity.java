package com.smartdraw.dijay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smartdraw.R;
import com.smartdraw.hawx.BaseActivity;

/**
 * Author：DJ
 * Time：2016/6/17 17:14
 * Name：Src
 * Description：
 */
public class MoreActivity extends BaseActivity implements View.OnClickListener
{
//    @BindView(R.id.llAbout)
//    LinearLayout llAbout;
//    @BindView (R.id.llSuggestion)
//    LinearLayout llSuggestion;

    private LinearLayout llAbout;
    private LinearLayout llSuggestion;
    private ImageView ivBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_more);



        llAbout = (LinearLayout) findViewById(R.id.llAbout);
        llSuggestion = (LinearLayout) findViewById(R.id.llSuggestion);
        ivBack = (ImageView) findViewById (R.id.ivBack);

        llAbout.setOnClickListener(this);
        llSuggestion.setOnClickListener (this);
        ivBack.setOnClickListener (this);

    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId ())
        {
            case R.id.llAbout:
                Intent intent1 = new Intent ();
                intent1.setClass (MoreActivity.this, AboutActivity.class);
                startActivity (intent1);
                break;
            case R.id.llSuggestion:
                Intent intent2 = new Intent ();
                intent2.setClass (MoreActivity.this, ContactUsActivity.class);
                startActivity (intent2);
                break;
            case R.id.ivBack:
                finish ();
                break;
        }
    }

}