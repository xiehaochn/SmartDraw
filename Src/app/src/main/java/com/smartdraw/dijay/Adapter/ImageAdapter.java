package com.smartdraw.dijay.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.smartdraw.R;

/**
 * Author：DJ
 * Time：2016/6/17 17:16
 * Name：Src
 * Description：
 */
public class ImageAdapter extends BaseAdapter
{
    private int[] pictures = {R.drawable.apple1, R.drawable.elephant1, R.drawable.goose1, R
            .drawable.plane11, R.drawable.monkey51, R.drawable.car, R.drawable.melon, R.drawable
            .ship, R.drawable.turnip};

    private Context mContext;

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

        ImageView imageView;

        if(convertView==null){
            imageView=new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams
                    .WRAP_CONTENT, ViewGroup.LayoutParams
                    .WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }else{
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(pictures[position]);
        return imageView;
    }
}
