package com.lofter.story.base;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

/**
 * @Package: com.youqi.ecsp.foundation.ecsp
 * @Description: ListView适配器封装
 * @Author: jingxt
 * @Date: 2016/9/12
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected Context ctx;
    public LayoutInflater mInflater;
    public Dialog mDialog;


    public BaseAdapter(Context ctx) {
        this.ctx = ctx;
        this.mInflater = LayoutInflater.from(ctx);
    }

    protected final List<T> mList = new ArrayList<T>();

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

    public void appendToTopList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(0, list);
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
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (position > mList.size() - 1) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
