package txy.simpleloadmore;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

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

    public LoadMoreControler(@NonNull RecyclerView recyclerView, @NonNull Builder builder) {
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
                    loadMoreFooterView.showComplete();
                } else {
                    loadMoreAdapter.changeCount(count);
                    loadMoreAdapter.notifyDataSetChanged();
                    loadMoreFooterView.hide();
                }
                loaded();
            }
        });
        loadMoreFooterView = new LoadMoreFooterView(recyclerView.getContext(), builder);

        loadMoreAdapter = new LoadMoreAdapter(mAdapter, loadMoreFooterView);
        mRecyclerView.setAdapter(loadMoreAdapter);
    }

    public void setLoadMoreListener(final OnLoadMoreListener loadMoreListener) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1) && canLoadMore && !isLoading
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loadMoreFooterView.showLoading();
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

    private void loaded() {
        loadMoreAdapter.setLoadMore(false);
        isLoading = false;
    }

    public static class Builder {
        private String completeText = "没有更多了";
        private String loadingText = "正在加载...";
        private int textColor = Color.parseColor("#000000");

        public String getCompleteText() {
            return completeText;
        }

        public Builder setCompleteText(String completeText) {
            this.completeText = completeText;
            return this;
        }

        public String getLoadingText() {
            return loadingText;
        }

        public Builder setLoadingText(String loadingText) {
            this.loadingText = loadingText;
            return this;
        }

        public int getTextColor() {
            return textColor;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }
    }
}
