package com.smartdraw.dijay.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;


import com.smartdraw.R;
import com.smartdraw.dijay.Adapter.SelectPicAdapter;

import com.smartdraw.hawx.BaseActivity;

/**
 * Author：DJ
 * Time：2016/6/17 17:15
 * Name：Src
 * Description：
 */

public class SelectPictureActivity extends BaseActivity
{
    private RecyclerView recyclerView;
    private SelectPicAdapter adapter;
    private int activityNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setHideStatusBar(false);
        setContentView(R.layout.activity_select_picture);
        super.onCreate(savedInstanceState);
        activityNum =getIntent ().getIntExtra ("Activity_num",1);
        recyclerView= (RecyclerView) findViewById(R.id.selected_recyclerview);
        adapter=new SelectPicAdapter(this, activityNum,SelectPictureActivity.this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manager);
    }

}