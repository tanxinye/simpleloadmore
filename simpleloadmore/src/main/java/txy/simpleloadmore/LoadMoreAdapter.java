package txy.simpleloadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 封装Adapter
 */
public final class LoadMoreAdapter extends RecyclerView.Adapter {

    private int mCount;

    private static final int TYPE_LOADMORE = 0x12345;
    private LoadMoreViewHolder loadMoreViewHolder;
    private RecyclerView.Adapter mAdapter;
    private View mFooterView;

    public LoadMoreAdapter(final RecyclerView.Adapter adapter, View footerView) {
        mCount = adapter.getItemCount();
        mAdapter = adapter;
        mFooterView = footerView;
    }

    public void changeCount(int count) {
        mCount = count;
    }

    public int getCount() {
        return mCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADMORE) {
            loadMoreViewHolder = new LoadMoreViewHolder(mFooterView);
            return loadMoreViewHolder;
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < mCount) {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    //因为多出一个LoadMore视图，要记得数量上加多一个
    @Override
    public int getItemCount() {
        return mCount + 1;
    }

    //根据位置显示Type
    @Override
    public int getItemViewType(int position) {
        if (position == mCount) {
            return TYPE_LOADMORE;
        } else {
            return mAdapter.getItemViewType(position);
        }
    }

    public void setLoadMore(boolean loadMore) {
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private int height;
        private int width;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
