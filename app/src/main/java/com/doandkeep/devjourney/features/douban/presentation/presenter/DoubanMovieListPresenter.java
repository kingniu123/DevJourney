package com.doandkeep.devjourney.features.douban.presentation.presenter;

import com.doandkeep.devjourney.base.domain.DefaultSubscriber;
import com.doandkeep.devjourney.base.domain.UseCase;
import com.doandkeep.devjourney.base.presentation.Presenter;
import com.doandkeep.devjourney.features.douban.data.entity.DoubanMovieEntity;
import com.doandkeep.devjourney.features.douban.presentation.view.movie.DoubanMovieListView;

import java.util.List;


/**
 * Created by zhangtao on 2016/11/23.
 */

public class DoubanMovieListPresenter implements Presenter {

    private UseCase mUseCase;
    private DoubanMovieListView mView;

    public DoubanMovieListPresenter(UseCase useCase) {
        this.mUseCase = useCase;
    }

    public void setView(DoubanMovieListView doubanMovieListView) {
        this.mView = doubanMovieListView;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.mUseCase.unsubscribe();
        this.mView = null;
    }

    public void init() {
        loadMovieList();
    }


    public void refresh() {
        refreshMovieList();
    }


    private void loadMovieList() {
        showLoadingView();
        hideRetryView();
        getMovieList(false);
    }

    private void refreshMovieList() {
        showRefreshView();
        getMovieList(true);
    }

    private void getMovieList(boolean isRefrsh) {
        if (isRefrsh) {
            mUseCase.execute(new RefreshMoviesSubscriber());
        } else {
            mUseCase.execute(new GetMoivesSubscriber());
        }
    }

    private void showLoadingView() {
        mView.showLoading();
    }

    private void hideLoadingView() {
        mView.hideLoading();
    }

    private void showRefreshView() {
        mView.showRefresh();
    }

    private void hideRefreshView() {
        mView.hideRefresh();
    }

    private void showRetryView() {
        mView.showRetry();
    }

    private void hideRetryView() {
        mView.hideRetry();
    }

    private void showErrorMsg(String msg) {
        mView.showError(msg);
    }

    private void showMovieListInView(List<DoubanMovieEntity> movies) {
        mView.renderMovie(movies);
    }

    private final class GetMoivesSubscriber extends DefaultSubscriber<List<DoubanMovieEntity>> {
        @Override
        public void onCompleted() {
            super.onCompleted();
            DoubanMovieListPresenter.this.hideLoadingView();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            DoubanMovieListPresenter.this.hideLoadingView();
            DoubanMovieListPresenter.this.showRetryView();
            DoubanMovieListPresenter.this.showErrorMsg("do with this error");
        }

        @Override
        public void onNext(List<DoubanMovieEntity> movies) {
            super.onNext(movies);
            DoubanMovieListPresenter.this.showMovieListInView(movies);
        }
    }

    private final class RefreshMoviesSubscriber extends DefaultSubscriber<List<DoubanMovieEntity>> {
        @Override
        public void onCompleted() {
            super.onCompleted();
            DoubanMovieListPresenter.this.hideRefreshView();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            DoubanMovieListPresenter.this.hideRefreshView();
            DoubanMovieListPresenter.this.showErrorMsg("do with this error");
        }

        @Override
        public void onNext(List<DoubanMovieEntity> movies) {
            super.onNext(movies);
            DoubanMovieListPresenter.this.showMovieListInView(movies);
        }
    }

}
