package com.smartdraw.dijay.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.smartdraw.R;
import com.smartdraw.dijay.Adapter.SkimOtherPictureAdapter;
import com.smartdraw.dijay.Adapter.SkimSelfPictureAdapter;
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
    private SkimSelfPictureAdapter selfAdapter;
    private SkimOtherPictureAdapter otherAdapter;
    private List<Integer> selfDatas;
    private List<Integer> otherDatas;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skim_picture);
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

        initDatas ();
        selfRecyclerView = (RecyclerView) findViewById (R.id.rvSelf);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager (this);
        linearLayoutManager1.setOrientation (LinearLayoutManager.HORIZONTAL);
        selfRecyclerView.setLayoutManager (linearLayoutManager1);

        selfAdapter = new SkimSelfPictureAdapter (this, selfDatas);
        selfAdapter.setOnItemClickListener (new SkimSelfPictureAdapter.OnItemClickListener () {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(SkimPictureActivity.this,position+"",Toast.LENGTH_SHORT).show ();
            }
        });
        selfRecyclerView.setAdapter (selfAdapter);



        otherRecyclerView = (RecyclerView) findViewById (R.id.rvOther);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager (this);
        linearLayoutManager2.setOrientation (LinearLayoutManager.HORIZONTAL);
        otherRecyclerView.setLayoutManager (linearLayoutManager2);

        otherAdapter = new SkimOtherPictureAdapter (this, otherDatas);
        otherAdapter.setOnItemClickListener (new SkimOtherPictureAdapter.OnItemClickListener () {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(SkimPictureActivity.this,position+"",Toast.LENGTH_SHORT).show ();
            }
        });
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
