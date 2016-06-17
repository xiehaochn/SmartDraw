package com.smartdraw.dijay.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.smartdraw.R;

/**
 * Author：DJ
 * Time：2016/6/17 17:16
 * Name：Src
 * Description：
 */
public class gridview_adapter extends BaseAdapter
{
    private int[] pictures = {R.drawable.apple1, R.drawable.elephant1,
            R.drawable.goose1, R.drawable.plane11, R.drawable.monkey51,
            R.drawable.car, R.drawable.melon, R.drawable.ship, R.drawable.turnip};

    @Override
    public int getCount()
    {
        return pictures.length;
    }

    @Override
    public Object getItem(int position)
    {
        return pictures[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return null;
        ImageView imageView;

        if (convertView != null)
        {
            imageView = (ImageView) convertView;
        }
    }
}
