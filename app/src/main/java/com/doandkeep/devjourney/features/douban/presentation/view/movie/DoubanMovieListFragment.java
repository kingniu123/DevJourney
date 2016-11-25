package com.doandkeep.devjourney.features.douban.presentation.view.movie;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.doandkeep.devjourney.R;
import com.doandkeep.devjourney.base.presentation.BaseFragment;
import com.doandkeep.devjourney.features.douban.data.entity.DoubanMovieEntity;
import com.doandkeep.devjourney.features.douban.presentation.DoubanMovieAdapter;
import com.doandkeep.devjourney.view.cyclerview.DividerItemDecoration;
import com.doandkeep.devjourney.view.cyclerview.EndlessRecyclerViewScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhangtao on 16/8/3.
 */
public class DoubanMovieListFragment extends BaseFragment implements DoubanMovieListView {

    @BindView(R.id.movie_srl)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.movie_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress_view)
    View mProgressView;
    @BindView(R.id.retry_view)
    View mRetryView;

    private LinearLayoutManager mLayoutManager;
    private DoubanMovieAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_douban_movie;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

            }
        });

        mAdapter = new DoubanMovieAdapter(null);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        itemDecoration.setDivider(getContext().getResources().getDrawable(R.drawable.divider_douban_movie));
        mRecyclerView.addItemDecoration(itemDecoration);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMovies();
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            loadMovies();
        }
    }

    @Override
    public void renderMovie(List<DoubanMovieEntity> movieList) {
        mAdapter.setData(movieList);
    }

    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressView.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        mRetryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mRetryView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    public void showRefresh() {
        // donothing
    }

    @Override
    public void hideRefresh() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @OnClick(R.id.retry_btn)
    void onRetryBtnClicked() {
        DoubanMovieListFragment.this.loadMovies();
    }

    protected void loadMovies() {
    }

    protected void refreshMovies() {
    }
}
