package com.yiwo.friendscometogether.webpages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HasTitleWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private String url;
    private Unbinder unbinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_has_title_web);
//        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
//        StatusBarUtils.setStatusBarTransparent(HasTitleWebActivity.this);
        unbinder = ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        tv_title.setText(getIntent().getStringExtra("title"));
        initWebView(webView,url);
        initIntentSonic(url,webView);

    }
    public static void open(Context context,String url,String title){
        Intent intent = new Intent();
        intent.setClass(context, HasTitleWebActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }
    public class AndroidInterface extends Object{

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
