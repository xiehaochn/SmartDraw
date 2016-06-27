package com.smartdraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.smartdraw.dijay.Activity.MoreActivity;
import com.smartdraw.dijay.Activity.SelectPictureActivity;
import com.smartdraw.dijay.Activity.SketchpadActivity;
import com.smartdraw.dijay.Activity.SkimPictureActivity;
import com.smartdraw.hawx.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener
{
//
//        @BindView(R.id.imageview_1)
//        ImageView sketchpad;
//        @BindView (R.id.imageview_2)
//        ImageView select_picture;
//        @BindView (R.id.imageview_3)
//        ImageView skim_picture;
//        @BindView (R.id.imageview_4)
//        ImageView more_about;

    private ImageView sketchpad;
    private ImageView select_picture;
    private ImageView skim_picture;
    private ImageView more_about;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sketchpad = (ImageView) findViewById(R.id.imageview_1);
        select_picture = (ImageView) findViewById(R.id.imageview_2);
        skim_picture = (ImageView) findViewById(R.id.imageview_3);
        more_about = (ImageView) findViewById(R.id.imageview_4);


        sketchpad.setOnClickListener(this);
        select_picture.setOnClickListener(this);
        skim_picture.setOnClickListener(this);
        more_about.setOnClickListener(this);


    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imageview_1:
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, SketchpadActivity.class);
                startActivity(intent1);
                break;
            case R.id.imageview_2:
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this, SelectPictureActivity.class);
                startActivity(intent2);
                break;
            case R.id.imageview_3:
                Intent intent3 = new Intent();
                intent3.setClass(MainActivity.this, SkimPictureActivity.class);
                startActivity(intent3);
                break;
            case R.id.imageview_4:
                Intent intent4 = new Intent();
                intent4.setClass(MainActivity.this, MoreActivity.class);
                startActivity(intent4);
                break;
        }
    }


}
