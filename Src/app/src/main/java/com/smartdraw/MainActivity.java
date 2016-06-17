package com.smartdraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.smartdraw.dijay.Activity.more;
import com.smartdraw.hawx.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener
{

    @BindView (R.id.imageview_1)
    ImageView sketchpad;
    @BindView (R.id.imageview_2)
    ImageView select_picture;
    @BindView (R.id.imageview_3)
    ImageView skim_picture;
    @BindView (R.id.imageview_4)
    ImageView more_about;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setHideStatusBar(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sketchpad.setOnClickListener (this);
        select_picture.setOnClickListener (this);
        skim_picture.setOnClickListener (this);
        more_about.setOnClickListener (this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId ())
        {
            case R.id.imageview_1:
                break;
            case R.id.imageview_2:
                break;
            case R.id.imageview_3:
                break;
            case R.id.imageview_4:
                Intent intent = new Intent ();
                intent.setClass (MainActivity.this, more.class);
                startActivity (intent);
                break;
        }
    }
}
