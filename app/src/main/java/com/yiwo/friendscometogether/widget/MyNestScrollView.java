package com.yiwo.friendscometogether.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyNestScrollView extends NestedScrollView {

    private int myHeaderHeight = 0 ;
    private int originScroll = 0 ;
    public MyNestScrollView(Context context) {
        super(context);
    }

    public MyNestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (originScroll < myHeaderHeight){
            scrollBy(dx,dy);
            consumed[0] = dx ;
            consumed[1] = dy ;
        }
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        originScroll = t ;
        Log.d("sdasd",t+"");
        super.onScrollChanged(l, t, oldl, oldt);

    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

    }

    public void setMyHeaderHeight(int myHeaderHeight) {
        this.myHeaderHeight = myHeaderHeight;
    }
}
