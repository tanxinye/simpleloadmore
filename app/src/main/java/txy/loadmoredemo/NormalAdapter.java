package txy.loadmoredemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by tanxinye on 2017/3/22.
 */
public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.NormalViewHolder> {

    private int count;

    public NormalAdapter(int count) {
        this.count = count;
    }

    @Override
    public NormalAdapter.NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_normal, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalAdapter.NormalViewHolder holder, int position) {
        holder.tvItem.setText("第" + position + "个");
    }

    //因为多出一个LoadMore视图，要记得数量上加多一个
    @Override
    public int getItemCount() {
        return count;
    }

    public void addCount(int i) {
        count += i;
        notifyDataSetChanged();
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public NormalViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

}
