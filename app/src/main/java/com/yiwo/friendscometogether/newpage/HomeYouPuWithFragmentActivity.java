package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
    @BindView(R.id.edt_search)
    EditText edtSearch;
    HomeShopGoodsFragment homeShopGoodsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_you_pu_with_fragment);
        ButterKnife.bind(this);
         homeShopGoodsFragment = new HomeShopGoodsFragment();
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.fragment_container,homeShopGoodsFragment )   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                homeShopGoodsFragment.search_key_word = edtSearch.getText().toString();
            }
        });
    }

    public static void open(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, HomeYouPuWithFragmentActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_search:
                homeShopGoodsFragment.search_key_word = edtSearch.getText().toString();
                homeShopGoodsFragment.initData();
                break;
        }
    }
}
