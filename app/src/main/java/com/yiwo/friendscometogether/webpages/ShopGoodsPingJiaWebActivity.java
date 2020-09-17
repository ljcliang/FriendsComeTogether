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

public class ShopGoodsPingJiaWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.wv)
    WebView wv;

    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_goods_ping_jia_web);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initIntentSonic(NetConfig.GoodsCommentUrl+spImp.getUID(), wv);
    }
    public static void open(Context context){
        Intent intent = new Intent();
        intent.setClass(context,ShopGoodsPingJiaWebActivity.class);
        context.startActivity(intent);
    }
    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
