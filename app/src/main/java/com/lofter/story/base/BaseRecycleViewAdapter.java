package com.lofter.story.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.youqi.ecsp.foundation.ecsp
 * @Description:  BaseRecycleView适配器封装
 * @Author: jingxt
 * @Date: 2016/9/12
 */
public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final List<T> mList = new ArrayList<T>();
    public LayoutInflater mInflater;
    protected Context ctx;
    protected OnRecyclerViewItemClickListener mRecyclerViewItemClickListener;

    public BaseRecycleViewAdapter(Context ctx) {
        this.ctx = ctx;
        if (ctx != null) {
            this.mInflater = LayoutInflater.from(ctx);
        }

    }

    public List<T> getList() {
        return mList;
    }

    public void appendToList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void resetData(List<T> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mRecyclerViewItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClick(int position);
    }
}
