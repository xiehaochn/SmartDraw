package com.smartdraw.dijay.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smartdraw.R;

import java.util.List;

/**
 * Author：DJ
 * Time：2016/6/22 14:26
 * Name：Src
 * Description：
 */
public class SkimOtherPictureAdapter extends RecyclerView.Adapter<SkimOtherPictureAdapter.SkimOtherPictureViewHolder>
{
    private LayoutInflater mLayoutInflater;
    private List<Integer> mDatas;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public SkimOtherPictureAdapter(Context context, List<Integer> datas)
    {
        mLayoutInflater = LayoutInflater.from (context);
        mDatas = datas;
    }

    @Override
    public SkimOtherPictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mLayoutInflater.inflate (R.layout.item_skim_other_picture, parent, false);
        SkimOtherPictureViewHolder skimOtherPictureViewHolder = new SkimOtherPictureViewHolder (view);
        return skimOtherPictureViewHolder;
    }

    @Override
    public void onBindViewHolder(final SkimOtherPictureViewHolder holder, final int position)
    {
        holder.ivSkimOther.setImageResource (mDatas.get (position));

        if (mOnItemClickListener != null)
        {
            holder.itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickListener.onItemClick (holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size ();
    }

    public static class SkimOtherPictureViewHolder extends RecyclerView.ViewHolder {
        public SkimOtherPictureViewHolder(View itemView)
        {
            super (itemView);
            ivSkimOther=(ImageView)itemView.findViewById (R.id.ivSkimOther);
        }

        ImageView ivSkimOther;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }
}
