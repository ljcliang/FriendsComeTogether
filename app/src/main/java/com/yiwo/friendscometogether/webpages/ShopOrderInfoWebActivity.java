package com.yiwo.friendscometogether.webpages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopOrderInfoWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_return)
    RelativeLayout mRlReturn;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_web);
        ButterKnife.bind(this);
        initIntentSonic(getIntent().getStringExtra("url"), mWebView);
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, ShopOrderInfoWebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @OnClick(R.id.rl_return)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_return:
                onBackPressed();
                break;
        }
    }
}
