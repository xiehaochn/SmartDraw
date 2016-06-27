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
 * Time：2016/6/22 10:03
 * Name：Src
 * Description：
 */
public class SkimSelfPictureAdapter extends RecyclerView.Adapter<SkimSelfPictureAdapter.SkimSelfPictureViewHolder>
{
    private LayoutInflater mInflater;
    private List<Integer> mDatas;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener)
    {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    public SkimSelfPictureAdapter(Context context, List<Integer> datas)
    {
        mInflater = LayoutInflater.from (context);
        mDatas = datas;
    }


    @Override
    public SkimSelfPictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate (R.layout.item_skim_self_picture, parent, false);
        SkimSelfPictureViewHolder skimSelfPictureViewHolder = new SkimSelfPictureViewHolder (view);
        skimSelfPictureViewHolder.ivSkimSelf = (ImageView) view.findViewById (R.id.ivSkimSelf);
        return skimSelfPictureViewHolder;
    }

    @Override
    public void onBindViewHolder(final SkimSelfPictureViewHolder holder, final int position)
    {
        holder.ivSkimSelf.setImageResource (mDatas.get (position));
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

    public static class SkimSelfPictureViewHolder extends RecyclerView.ViewHolder
    {

        public SkimSelfPictureViewHolder(View itemView)
        {
            super (itemView);
        }

        ImageView ivSkimSelf;

    }

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }
}
