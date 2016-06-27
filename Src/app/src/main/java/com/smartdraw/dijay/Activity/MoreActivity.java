package com.smartdraw.dijay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smartdraw.R;
import com.smartdraw.hawx.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：DJ
 * Time：2016/6/17 17:14
 * Name：Src
 * Description：
 */
public class MoreActivity extends BaseActivity implements View.OnClickListener
{
    LinearLayout llAbout;
    LinearLayout llSuggestion;
    LinearLayout llUpdata;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setHideStatusBar(false);
        setContentView (R.layout.activity_more);
        super.onCreate (savedInstanceState);


        llAbout = (LinearLayout) findViewById(R.id.llAbout);
        llSuggestion = (LinearLayout) findViewById(R.id.llSuggestion);
        llUpdata= (LinearLayout) findViewById(R.id.llupdata);

        llAbout.setOnClickListener(this);
        llSuggestion.setOnClickListener (this);
        llUpdata.setOnClickListener(this);

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
            case R.id.llupdata:{
                Toast.makeText(this, "Check Updata", Toast.LENGTH_SHORT).show();
                break;
            }

        }
    }

}