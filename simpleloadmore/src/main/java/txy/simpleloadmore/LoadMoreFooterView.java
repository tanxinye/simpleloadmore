package txy.simpleloadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 用于显示底部加载视图
 */
public final class LoadMoreFooterView extends LinearLayout {

    private TextView textView;
    private LoadMoreControler.Builder mBuilder;

    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public LoadMoreFooterView(Context context, LoadMoreControler.Builder builder) {
        super(context, null);
        mBuilder = builder;
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(builder.getLoadingText());
        textView.setTextColor(getResources().getColor(builder.getTextColor()));
        addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(context, 48f)));

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);
    }

    public static int dp2px(Context context, float v) {
        return (int) (v * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public void showComplete() {
        setVisibility(VISIBLE);
        textView.setText(mBuilder.getCompleteText());
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void showLoading() {
        setVisibility(VISIBLE);
        textView.setText(mBuilder.getLoadingText());
    }
}
