package com.yiwo.friendscometogether.webpages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.netease.nim.uikit.api.NimUIKit;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.newpage.DuiZhangZhuanShuActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopGoodsDetailsWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String url;
    private Unbinder unbinder;
    private SpImp spImp;
    public static final String GOOD_ID_KEY = "goodId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_goods_details);
        spImp = new SpImp(this);
        unbinder = ButterKnife.bind(this);
        url = "http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID="+getIntent().getStringExtra(GOOD_ID_KEY)+"&uid="+spImp.getUID();
        Log.d("asdasd",url);
//        url = getIntent().getStringExtra("url");
        initIntentSonic(url,webView);
        webView.addJavascriptInterface(new ShopGoodsDetailsWebActivity.AndroidInterface(),"android");//交互
    }
    public static void open(Context context,String goodId){
        Intent intent = new Intent();
        intent.putExtra(GOOD_ID_KEY,goodId);
        intent.setClass(context,ShopGoodsDetailsWebActivity.class);
        context.startActivity(intent);
    }
    public class AndroidInterface extends Object{

        /**
         * toshare()  分享商品页的交互方法  传了商品id  商品名称  商品信息  商品图片  商品分享地址   5个参数
         */
        @JavascriptInterface
        public void tosharegoods(String shangpinId,String shangpinName,String shangpinInfo,String shangpinImage,String shareUrl){
            Intent intent = new Intent();
            if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                intent.setClass(ShopGoodsDetailsWebActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                new ShareAction(ShopGoodsDetailsWebActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                ShareUtils.shareWeb(ShopGoodsDetailsWebActivity.this, shareUrl, shangpinName,
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
            toToast(ShopGoodsDetailsWebActivity.this,"sadasd");
//            onBackPressed();
//            MyApplication.getInstance().exitOneActivity(DuiZhangZhuanShuActivity.class);
//            MyApplication.getInstance().getMainActivity().switchFragment(0);
        }
        /**
         * totalk()   咨询交互方法    传了 用户id   用户网易id  用户头像  用户昵称
         */
        @JavascriptInterface
        public void  totalkgoods(String userId,String wyId,String userHead,String userName){
            String uid = spImp.getUID();
            if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                Intent intent = new Intent();
                intent.setClass(ShopGoodsDetailsWebActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                liaotian(wyId);
            }
        }
        /**
         * tonowbuy()  立即购买的交互方法   传了一个跳转地址
         */
        @JavascriptInterface
        public void tonowbuy(String url){
            toToast(ShopGoodsDetailsWebActivity.this,"buybuy");
            Intent intent = new Intent();
            intent.setClass(ShopGoodsDetailsWebActivity.this, ShopGoodsBuyWebActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
    }
    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(ShopGoodsDetailsWebActivity.this, liaotianAccount);
    }
    @OnClick({R.id.rl_back})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }
}
