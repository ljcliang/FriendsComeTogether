package com.yiwo.friendscometogether.webpages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LiWuMingXiWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.wb)
    WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_li_wu_ming_xi);
        ButterKnife.bind(this);
        initIntentSonic("https://www.baudu.com",wb);
    }

    public static void open(Context context){
        Intent intent = new Intent();
        intent.setClass(context,LiWuMingXiWebActivity.class);
        context.startActivity(intent);
    }
    @OnClick(R.id.rl_back)
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }
}
