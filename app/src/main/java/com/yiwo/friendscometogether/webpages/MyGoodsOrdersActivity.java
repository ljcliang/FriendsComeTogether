package com.yiwo.friendscometogether.webpages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyGoodsOrdersActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.webView)
    WebView webView;
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goods_orders);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initIntentSonic(NetConfig.orderList + spImp.getUID(),webView);
    }
    public static void open(Context context){
        Intent intent = new Intent();
        intent.setClass(context,MyGoodsOrdersActivity.class);
        context.startActivity(intent);
    }
    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
    }
}
