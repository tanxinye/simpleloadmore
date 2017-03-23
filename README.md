# simpleloadmore
# 一个简单基于RecyclerView的上拉加载封装库

使用步骤如下

```
        loadMoreControler = new LoadMoreControler(rvMain);//放进一个RecyclerView
        loadMoreControler.generateLayout(R.layout.item_loadmore);//加载视图
        loadMoreControler.setLoadMoreListener(new LoadMoreControler.OnLoadMoreListener() {//设置监听
            @Override
            public void onLoadMore() {
                loadData();
            }
        });
```