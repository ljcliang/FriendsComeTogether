package com.yiwo.friendscometogether.webpages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GoodsCartWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String url;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_car_web);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        unbinder = ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        initWebView(webView,url);
        initIntentSonic(url,webView);
        webView.addJavascriptInterface(new GoodsCartWebActivity.AndroidInterface(),"android");//交互
    }
    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void gotopay(String url){
            Log.d("ssssdddd",url);
            Intent intent = new Intent();
            intent.setClass(GoodsCartWebActivity.this, ShopGoodsBuyWebActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
    }
    public static void open(Context context,String url){
        Log.d("ssssdddd_carturl:",url);
        Intent intent = new Intent();
        intent.setClass(context, GoodsCartWebActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
    @OnClick({R.id.rl_back})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
