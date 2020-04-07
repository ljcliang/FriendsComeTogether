package com.yiwo.friendscometogether.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.utils.AndTools;


public class HorizontalFooter extends ConstraintLayout implements RefreshFooter {

    private TextView mTvTitle;
    private ImageView mIvIcon;
    private RefreshKernel mRefreshKernel;

    public HorizontalFooter(Context context) {
        this(context, null);
    }

    public HorizontalFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.widget_footer_horizonal, this);
        mIvIcon = findViewById(R.id.footer_icon);
        mTvTitle = findViewById(R.id.footer_title);
        if (!isInEditMode() && getPaddingTop() == 0 && getPaddingBottom() == 0) {
            int padding = AndTools.dp2px(getContext(),10);
            setPadding(getPaddingLeft(), padding, getPaddingRight(), padding);
        }
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mRefreshKernel = kernel;
        kernel.getRefreshLayout().setEnableAutoLoadMore(false);
    }

    @Override
    public void onReleased(@NonNull RefreshLayout layout, int height, int maxDragHeight) {
        if (mRefreshKernel != null) {
            mRefreshKernel.setState(RefreshState.None);
            //onReleased 的时候 调用 setState(RefreshState.None); 并不会立刻改变成 None
            //而是先执行一个回弹动画，LoadFinish 是介于 Refreshing 和 None 之间的状态
            //LoadFinish 用于在回弹动画结束时候能顺利改变为 None
            mRefreshKernel.setState(RefreshState.LoadFinish);
        }
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return false;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        if (newState == RefreshState.ReleaseToLoad) {
            mIvIcon.animate().rotation(0);
            mTvTitle.setText(R.string.footer_detail_horizontal_release);
        } else {
            mIvIcon.animate().rotation(180);
            mTvTitle.setText(R.string.footer_detail_horizontal_pulling);
        }
    }
}
