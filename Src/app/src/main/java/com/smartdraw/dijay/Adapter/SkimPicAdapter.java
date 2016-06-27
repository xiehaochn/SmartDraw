package com.smartdraw.dijay.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.smartdraw.R;
import com.smartdraw.dijay.Utils.Utils;

import java.util.List;

/**
 * Created by Administrator on 2016/6/27.
 */

public class SkimPicAdapter extends RecyclerView.Adapter<SkimPicAdapter.SkimPicVH> {
    private Context context;
    private List<Integer> datas;

    public SkimPicAdapter(Context context, List<Integer> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public SkimPicVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.itemview_selectpic,parent,false);
        return new SkimPicVH(view);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onBindViewHolder(SkimPicVH holder, final int position) {
        int newWidth= Utils.getWindowWidth(context)/2;
        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),datas.get(position));
        Bitmap resized = Utils.scaleDown(bitmap,newWidth,false);
        holder.imageView.setImageBitmap(resized);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Position"+position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    class SkimPicVH extends RecyclerView.ViewHolder{
        ImageView imageView;
        public SkimPicVH(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.itemview_imageview);
        }
    }
}
