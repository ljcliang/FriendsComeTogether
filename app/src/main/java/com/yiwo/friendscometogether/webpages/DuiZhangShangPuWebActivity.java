package com.yiwo.friendscometogether.webpages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.model.ActiveShareModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newpage.DuiZhangZhuanShuActivity;
import com.yiwo.friendscometogether.newpage.FaBuShangPinActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.KeyEvent.KEYCODE_BACK;

public class DuiZhangShangPuWebActivity extends BaseWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String url;
    private Unbinder unbinder;
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duizhangshangpu_web);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        spImp = new SpImp(this);
        unbinder = ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        initWebView(webView,url);
        webView.addJavascriptInterface(new DuiZhangShangPuWebActivity.AndroidInterface(),"android");//交互
    }
    @OnClick({R.id.rl_back,R.id.btn_fabushangpin})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                if (webView.canGoBack()) {
                    webView.goBack();
                }else {
                    onBackPressed();
                }
                break;
            case R.id.btn_fabushangpin:
                Intent intent = new Intent();
                intent.setClass(DuiZhangShangPuWebActivity.this, FaBuShangPinActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {        //点击回退键时，不会退出浏览器而是返回网页上一页
         if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
             webView.goBack();
             return true;
         }
         return super.onKeyDown(keyCode, event);
    }
    public class AndroidInterface extends Object{

        /**
         * shareshop()  分享店铺交互方法  传了： 用户id  、用户头像 、 用户店铺昵称、  店铺介绍 、 分享链接
         */
        @JavascriptInterface
        public void shareshop(String userID,String userIcon,String shopName,String shopInfo,String shareUrl){
            Intent intent = new Intent();
            if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                intent.setClass(DuiZhangShangPuWebActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                new ShareAction(DuiZhangShangPuWebActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                ShareUtils.shareWeb(DuiZhangShangPuWebActivity.this, shareUrl, shopName,
                                        shopInfo, userIcon, share_media);
                            }
                        }).open();
            }
        }
        /**
         * toshare()  分享商品页的交互方法  传了商品id  商品名称  商品信息  商品图片  商品分享地址   5个参数
         */
        @JavascriptInterface
        public void toshare(String shangpinId,String shangpinName,String shangpinInfo,String shangpinImage,String shareUrl){
            Intent intent = new Intent();
            if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                intent.setClass(DuiZhangShangPuWebActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                new ShareAction(DuiZhangShangPuWebActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                ShareUtils.shareWeb(DuiZhangShangPuWebActivity.this, shareUrl, shangpinName,
                                        shangpinInfo, shangpinImage, share_media);
                            }
                        }).open();
            }
        }

        /**
         * gotoapp()  跳到app首页的交互方法
         */
        @JavascriptInterface
        public void gotoapp(){
            onBackPressed();
            MyApplication.getInstance().exitOneActivity(DuiZhangZhuanShuActivity.class);
            MyApplication.getInstance().getMainActivity().switchFragment(0);
        }
        /**
         * totalk()   咨询交互方法    传了 用户id   用户网易id  用户头像  用户昵称
         */
        @JavascriptInterface
        public void  totalk(String userId,String wyId,String userHead,String userName){
            String uid = spImp.getUID();
            if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                Intent intent = new Intent();
                intent.setClass(DuiZhangShangPuWebActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                liaotian(wyId);
            }
        }
        /**
         * tonowbuy()  立即购买的交互方法   传了一个跳转地址
         */
        @JavascriptInterface
        public void tonowbuy(){

        }
    }
    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(DuiZhangShangPuWebActivity.this, liaotianAccount);
    }
}
