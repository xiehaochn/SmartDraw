package com.smartdraw.dijay.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.smartdraw.R;
import com.smartdraw.dijay.Adapter.ImageAdapter;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);

        gridView = (GridView) findViewById(R.id.gvPick_picture);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {

            }
        });

    }
}