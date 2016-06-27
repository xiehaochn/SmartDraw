package com.smartdraw.dijay.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.smartdraw.R;
import com.smartdraw.dijay.Activity.SelectPictureActivity;
import com.smartdraw.dijay.Activity.SketchpadActivity;
import com.smartdraw.dijay.Utils.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2016/6/27.
 */

public class SelectPicAdapter extends RecyclerView.Adapter<SelectPicAdapter.PicViewHolder> {
    Context context;
    int activity_num;
    Activity activity;
    private int[] pictures = {R.drawable.apple1, R.drawable.elephant1, R.drawable.goose1, R
            .drawable.plane11, R.drawable.monkey51, R.drawable.car, R.drawable.melon, R.drawable
            .ship, R.drawable.turnip};
    public SelectPicAdapter(Context context, int activity_num,Activity activity) {
        this.context = context;
        this.activity_num = activity_num;
        this.activity=activity;
    }

    @Override
    public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.itemview_selectpic,parent,false);
        return new PicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PicViewHolder holder, final int position) {
        int newWidth= Utils.getWindowWidth(context)/2;
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),pictures[position]);
        Bitmap resized = Utils.scaleDown(bitmap,newWidth,false);
        holder.imageView.setImageBitmap(resized);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity_num ==0)
                {
                    Intent intent = new Intent ();
                    //把返回数据存入Intent
                    intent.putExtra ("position", position);
                    //设置返回数据
                     activity.setResult(RESULT_OK, intent);
                    activity.finish ();
                }else {
                    Intent intent = new Intent (context,SketchpadActivity.class);
                    //把返回数据存入Intent
                    intent.putExtra ("position", position);
                    intent.putExtra ("INIT_STATUS",false);
                    activity.startActivity (intent);
                    activity.finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pictures.length;
    }

    class PicViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public PicViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.itemview_imageview);

        }
    }
}
