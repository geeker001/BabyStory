package com.lofter.story.adapter;

import android.content.Context;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lofter.story.R;
import com.lofter.story.base.BaseRecycleViewAdapter;
import com.lofter.story.entity.StoryVO;

/**
 * Created by asus on 2018-1-3.
 */

public class StoryListAdapter extends BaseRecycleViewAdapter<StoryVO> {
    public StoryListAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_story_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder vh = (ViewHolder) holder;
        StoryVO story = mList.get(position);

        vh.name.setText((position + 1) + "  " + story.getTitle());

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerViewItemClickListener.onRecyclerViewItemClick(position);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textview_name);
        }
    }
}
