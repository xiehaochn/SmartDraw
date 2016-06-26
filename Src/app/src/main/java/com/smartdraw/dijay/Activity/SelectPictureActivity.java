package com.smartdraw.dijay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.smartdraw.R;
import com.smartdraw.dijay.Adapter.SelectPictureAdapter;
import com.smartdraw.hawx.BaseActivity;

/**
 * Author：DJ
 * Time：2016/6/17 17:15
 * Name：Src
 * Description：
 */

public class SelectPictureActivity extends BaseActivity
{
    private GridView gridView;
    private int Activity_num;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);

        Activity_num=getIntent ().getIntExtra ("Activity_num",1);

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

        gridView = (GridView) findViewById(R.id.gvPick_picture);
        gridView.setAdapter(new SelectPictureAdapter (this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if(Activity_num==0)
                {
                    Intent intent = new Intent ();
                    //把返回数据存入Intent
                    intent.putExtra ("position", i);
                    //设置返回数据
                    SelectPictureActivity.this.setResult (RESULT_OK, intent);
                    finish ();
                }else {
                    Intent intent = new Intent (SelectPictureActivity.this,SketchpadActivity.class);
                    //把返回数据存入Intent
                    intent.putExtra ("position", i);
                    intent.putExtra ("INIT_STATUS",false);
                    startActivity (intent);
                }
            }
        });
    }

}