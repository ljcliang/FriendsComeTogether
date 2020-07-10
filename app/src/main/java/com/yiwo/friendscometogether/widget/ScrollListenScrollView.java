package com.yiwo.friendscometogether.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by ljc on 2019/12/11.
 */

public class ScrollListenScrollView extends ScrollView{
    private OnScrollListener listener;
    private OnScrollStatusListener onScrollStatusListener;
    public ScrollListenScrollView(Context context) {
        super(context);
    }

    public ScrollListenScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListenScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }


    public interface OnScrollListener{
        void onScroll(int scrollY);
    }
    private Handler mHandler = new Handler() {

        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    if (onScrollStatusListener != null) {
                        onScrollStatusListener.onScrollStop();
                    }
                    break;
            }
        }
    };

    public interface OnScrollStatusListener{
        void onScrollStop();
        void onScrolling();
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null){
            listener.onScroll(t);
        }
        if (onScrollStatusListener != null) {
            onScrollStatusListener.onScrolling();
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(0x01, 200);
        }
    }
    public void setOnScrollStatusListener(OnScrollStatusListener onScrollStatusListener) {
        this.onScrollStatusListener = onScrollStatusListener;
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }
}
