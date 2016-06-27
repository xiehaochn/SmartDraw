package com.smartdraw.dijay.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.smartdraw.R;
import com.smartdraw.dijay.Adapter.SkimPicAdapter;
import com.smartdraw.hawx.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author：DJ
 * Time：2016/6/17 0017 22:54
 * Name：Src
 * Description：
 */
public class SkimPictureActivity extends BaseActivity
{
    private RecyclerView selfRecyclerView;
    private RecyclerView otherRecyclerView;
    private SkimPicAdapter selfAdapter;
    private SkimPicAdapter otherAdapter;
    private List<Integer> selfDatas;
    private List<Integer> otherDatas;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        setHideStatusBar(false);
        setContentView(R.layout.activity_skim_picture);
        super.onCreate(savedInstanceState);

        initDatas ();
        selfRecyclerView = (RecyclerView) findViewById (R.id.rvSelf);
        otherRecyclerView = (RecyclerView) findViewById (R.id.rvOther);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager (this);
        linearLayoutManager1.setOrientation (LinearLayoutManager.HORIZONTAL);
        selfRecyclerView.setLayoutManager (linearLayoutManager1);
        selfAdapter = new SkimPicAdapter (this, selfDatas);
        selfRecyclerView.setAdapter (selfAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager (this);
        linearLayoutManager2.setOrientation (LinearLayoutManager.HORIZONTAL);
        otherRecyclerView.setLayoutManager(linearLayoutManager2);
        otherAdapter = new SkimPicAdapter (this, otherDatas);
        otherRecyclerView.setAdapter (otherAdapter);


    }

    private void initDatas()
    {
        selfDatas = new ArrayList<> (Arrays.asList (R.drawable.apple1, R.drawable.elephant1, R.drawable.goose1, R
                .drawable.plane11, R.drawable.monkey51, R.drawable.car, R.drawable.melon, R.drawable
                .ship, R.drawable.turnip));

        otherDatas = new ArrayList<> (Arrays.asList (R.drawable.apple1, R.drawable.elephant1, R.drawable.goose1, R
                .drawable.plane11, R.drawable.monkey51, R.drawable.car, R.drawable.melon, R.drawable
                .ship, R.drawable.turnip));

    }
}
