package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.fragment.HomeShopGoodsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeYouPuWithFragmentActivity extends BaseWebActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_you_pu_with_fragment);
        ButterKnife.bind(this);
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_container,new HomeShopGoodsFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();

    }
    public static void open(Context context){
        Intent intent = new Intent();
        intent.setClass(context,HomeYouPuWithFragmentActivity.class);
        context.startActivity(intent);
    }
    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
