package com.smartdraw.dijay.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.smartdraw.R;
import com.smartdraw.dijay.View.BaseDialog;
import com.smartdraw.hawx.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：DJ
 * Time：2016/6/17 0017 22:53
 * Name：Src
 * Description：
 */
public class SketchpadActivity extends BaseActivity
{
    public static boolean INIT_STATUS = true;
    private int position;
    private PopupWindow pop;

    List<Map<String, String>> moreList;
    private ListView lvPopupList;// popupwindow中的ListView
    private int NUM_OF_VISIBLE_LIST_ROWS = 2;// 指定popupwindow中Item的数量

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_sketchpad);

        init ();
    }

    public void init()
    {
        INIT_STATUS = getIntent ().getBooleanExtra ("INIT_STATUS", true);

        final ImageView ivMore = (ImageView) findViewById (R.id.ivMore);
        if (ivMore != null)
        {
            ivMore.setVisibility (View.VISIBLE);
            ivMore.setOnClickListener (new View.OnClickListener ()
            {
                @Override
                public void onClick(View v)
                {
                    if (pop.isShowing ())
                    {
                        pop.dismiss ();
                    } else
                    {
                        pop.showAsDropDown (ivMore, 0, 0);
                    }
                }
            });
        }


        ImageView ivBack = (ImageView) findViewById (R.id.ivBack);
        if (ivBack != null)
        {
            ivBack.setOnClickListener (new View.OnClickListener ()
            {
                @Override
                public void onClick(View v)
                {
                    finish ();
                }
            });
        }

        initData();

        initPopupWindow();

        if (INIT_STATUS)
        {
            SelectHint (this);
        } else
        {
            position = getIntent ().getExtras ().getInt ("position");
            TeachingDraw ();
        }
    }

    /*初始化下拉菜单数据*/
    private void initData()
    {
        moreList = new ArrayList<Map<String, String>> ();
        Map<String, String> map;
        map = new HashMap<String, String> ();
        map.put("share_key", "选图");
        moreList.add(map);
        map = new HashMap<String, String>();
        map.put("share_key", "自己画");
        moreList.add(map);
    }

    /*初始化PopupWindow*/
    private void initPopupWindow()
    {
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_menu, null);
        lvPopupList = (ListView) layout.findViewById(R.id.lvPopupList);
        pop = new PopupWindow(layout);
        pop.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件

        lvPopupList.setAdapter(new SimpleAdapter (SketchpadActivity.this, moreList,
                R.layout.popup_item, new String[] { "share_key" },
                new int[] { R.id.tvPopupItem }));
        lvPopupList.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (moreList.get (position).get ("share_key") == "自己画")
                {
                    pop.dismiss ();
                } else if (moreList.get (position).get ("share_key")=="选图")
                {
                    Intent intent = new Intent ();
                    intent.putExtra ("Activity_num", 0);
                    intent.setClass (SketchpadActivity.this, SelectPictureActivity.class);
                    startActivityForResult (intent, 0);
                    pop.dismiss ();
                }
            }
        });

        // 控制popupwindow的宽度和高度自适应
        lvPopupList.measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        pop.setWidth(lvPopupList.getMeasuredWidth());
        pop.setHeight((lvPopupList.getMeasuredHeight() + 20)
                * NUM_OF_VISIBLE_LIST_ROWS);

        // 控制popupwindow点击屏幕其他地方消失
        pop.setBackgroundDrawable(this.getResources().getDrawable(
                R.drawable.blank));// 设置背景图片，不能在布局中设置，要通过代码来设置
        pop.setOutsideTouchable(true);// 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
    }

/*选图提示*/
    public void SelectHint(final Context context)
    {
        final BaseDialog selectDialog = new BaseDialog (context, "提示", "先选图吧", "选图", "取消");
        selectDialog.show ();
        selectDialog.setCancelable (false);

        selectDialog.setDialogClickListener (new BaseDialog.DialogClickListener ()
        {
            @Override
            public void doConfirm()
            {
                selectDialog.dismiss ();
                Intent intent = new Intent ();
                intent.putExtra ("Activity_num", 0);
                intent.setClass (context, SelectPictureActivity.class);
                SketchpadActivity.this.startActivityForResult (intent, 0);
            }

            @Override
            public void doCancel()
            {
                selectDialog.dismiss ();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == 0)
        {
            if (resultCode == RESULT_OK)
            {
                position = data.getExtras ().getInt ("position");
                TeachingDraw ();
            }
        }
    }


    /* 教学导航动画*/

    private void TeachingDraw()
    {

    }

/*自己画提示*/
    public void DrawSelfHint(final Context context)
    {
        final BaseDialog selectDialog = new BaseDialog (context, "Are You Ready?", "开始自己画", "自己画", "再学一遍");
        selectDialog.show ();
        selectDialog.setCancelable (false);

        selectDialog.setDialogClickListener (new BaseDialog.DialogClickListener ()
        {
            @Override
            public void doConfirm()
            {
                /*自己画*/
            }

            @Override
            public void doCancel()
            {
            /*返回跟我画，再重新画一遍*/
            }
        });
    }
}








