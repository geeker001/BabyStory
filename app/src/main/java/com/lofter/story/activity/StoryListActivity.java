package com.lofter.story.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lofter.story.R;
import com.lofter.story.adapter.StoryListAdapter;
import com.lofter.story.base.BaseActivity;
import com.lofter.story.base.BaseRecycleViewAdapter;
import com.lofter.story.db.DbHelper;
import com.lofter.story.entity.StoryVO;
import com.lofter.story.view.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * PACKAGE_NAME：com.lofter.story.activity
 * DATE：2018/1/3 16:58
 * USER: xiantao.jiang
 * DESCRIBE:
 */

public class StoryListActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private StoryListAdapter mAdapter;
    private List<StoryVO> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);
        setTitle("故事列表");

        initView();
        register();

        getData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView_story_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.shape_recyclerview_divider));
        mRecyclerView.addItemDecoration(divider);

        mAdapter = new StoryListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void register(){
        mAdapter.setOnRecyclerViewItemClickListener(new BaseRecycleViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onRecyclerViewItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(StoryListActivity.this, StoryDetailActivity.class);
                intent.putExtra("title", mList.get(position).getTitle());
                intent.putExtra("content", mList.get(position).getContent());
                startActivity(intent);
            }
        });
    }

    private void getData() {
        mList = DbHelper.getStoryList(this);

        mAdapter.resetData(mList);
    }

}
