package txy.simpleloadmore;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 控制RecyclerView、Adapter和FooterView
 */
public final class LoadMoreControler {

    private RecyclerView mRecyclerView;
    private boolean canLoadMore = true;
    private boolean isLoading = false;
    private LoadMoreAdapter loadMoreAdapter;
    private RecyclerView.Adapter mAdapter;
    private final LoadMoreFooterView loadMoreFooterView;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public LoadMoreControler(@NonNull RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new NullPointerException("RecyclerView must not mull");
        }
        if (recyclerView.getAdapter() == null) {
            throw new NullPointerException("Adapter must not mull");
        }
        mRecyclerView = recyclerView;
        mAdapter = recyclerView.getAdapter();
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                int count = mAdapter.getItemCount();
                if (count == loadMoreAdapter.getCount()) {
                    canLoadMore = false;
                } else {
                    loadMoreAdapter.changeCount(count);
                    loadMoreAdapter.notifyDataSetChanged();
                }
                hideLoadMore();
            }
        });
        loadMoreFooterView = new LoadMoreFooterView(recyclerView.getContext());

        loadMoreAdapter = new LoadMoreAdapter(mAdapter,loadMoreFooterView);
        mRecyclerView.setAdapter(loadMoreAdapter);
    }

    public void setLoadMoreListener(final OnLoadMoreListener loadMoreListener) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1) && canLoadMore && !isLoading
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isLoading = true;
                    loadMoreAdapter.setLoadMore(true);
                    if (loadMoreListener != null) {
                        loadMoreListener.onLoadMore();
                    }
                }
            }

        });
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    public boolean isLoading() {
        return isLoading;
    }

    private void hideLoadMore() {
        loadMoreAdapter.setLoadMore(false);
        isLoading = false;
    }

    public View generateLayout(int resId){
        return loadMoreFooterView.generateLayout(resId);
    }
}
