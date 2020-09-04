package com.yiwo.friendscometogether.webpages;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.GoodShareModel;
import com.yiwo.friendscometogether.newpage.FaBu_XiuGaiShangPinActivity;
import com.yiwo.friendscometogether.newpage.FaBu_XiuGai_LuXianActivity;
import com.yiwo.friendscometogether.newpage.PeiSongSettingActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanLiXingChengWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.rl_fabu)
    RelativeLayout mRlFabu;
    @BindView(R.id.ll_btn_serch)
    LinearLayout mLlBtnSerch;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    private String url;

    Dialog dialog;
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_xingcheng_web);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        initIntentSonic(url, webView);
        spImp = new SpImp(this);
        webView.addJavascriptInterface(new AndroidInterface(), "android");
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, GuanLiXingChengWebActivity.class);
        Log.d("asdasd",url);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_return,R.id.rl_fabu, R.id.ll_btn_serch})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_return:
                onBackPressed();
                break;
            case R.id.rl_fabu:
                FaBu_XiuGai_LuXianActivity.open(GuanLiXingChengWebActivity.this);
                break;
            case R.id.ll_btn_serch:
                webView.loadUrl("javascript:sousuo('" + mEdtSearch.getText().toString() + "')");
                mEdtSearch.setText("");
                break;
        }
    }

    public class AndroidInterface extends Object {
        @JavascriptInterface
        public void editactivity(String pfID ) {
            FaBu_XiuGai_LuXianActivity.open(GuanLiXingChengWebActivity.this,pfID);
        }
        @JavascriptInterface
        public void gotoactivity(String pfID){
            Intent intent = new Intent();
            intent.putExtra("pfID", pfID);
            intent.setClass(GuanLiXingChengWebActivity.this, DetailsOfFriendTogetherWebLocalActivity.class);
            startActivity(intent);
        }
    }
}
