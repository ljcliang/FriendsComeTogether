package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.module.MsgForwardFilter;
import com.netease.nim.uikit.business.session.module.MsgRevokeFilter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.vise.xsnow.cache.SpCache;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.MyApplication;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.app_status_manger.AppStatusConstant;
import com.yiwo.friendscometogether.app_status_manger.AppStatusManager;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.XieYiDialog;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.utils.AssetCopyer;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    private String account;
    private SpImp spImp;
    private SpCache spCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusManager.getInstance().setAppStatus(AppStatusConstant.STATUS_NORMAL);//进入应用初始化设置成未登录状态
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        StatusBarUtils.setStatusBarTransparent(WelcomeActivity.this);
        spImp = new SpImp(WelcomeActivity.this);
        spCache = new SpCache(WelcomeActivity.this);
        if (!spImp.isAgree()){
            showAgreeDialog();
        }else {
            initAsset();
            initData();
        }
    }
    private void initAsset() {
        AssetCopyer assetCopyer = new AssetCopyer(this);
        try {
            assetCopyer.copy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initData() {
        account = spImp.getYXID();
        String token = spImp.getYXTOKEN();
        if(TextUtils.isEmpty(account)||account.equals("0")){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    if(TextUtils.isEmpty(spImp.getYd())){
                        intent.setClass(WelcomeActivity.this, GuideActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        intent.setClass(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 2000);

        }else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    if(TextUtils.isEmpty(spImp.getYd())){
                        intent.setClass(WelcomeActivity.this, GuideActivity.class);
                        startActivity(intent);
                    }else {
//                                intent.setClass(WelcomeActivity.this, MainActivity.class);
                        intent.setClass(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
            }, 2000);
            if(!isNetworkAvailable(WelcomeActivity.this)){
                toToast(WelcomeActivity.this,"当前无网络");
                return;
            }
            LoginInfo info = new LoginInfo(account, token);
            RequestCallback<LoginInfo> callback =
                    new RequestCallback<LoginInfo>() {

                        @Override
                        public void onSuccess(LoginInfo loginInfo) {
                            NimUIKit.loginSuccess(account);
                            NimUIKit.setAccount(account);
                            toToast(WelcomeActivity.this, "登录成功");
                            NimUIKit.setMsgForwardFilter(new MsgForwardFilter() {
                                @Override
                                public boolean shouldIgnore(IMMessage message) {
                                    return false;
                                }
                            });
                            NimUIKit.setMsgRevokeFilter(new MsgRevokeFilter() {
                                @Override
                                public boolean shouldIgnore(IMMessage message) {
                                    return false;
                                }
                            });
                            Intent intent = new Intent();
                            if(TextUtils.isEmpty(spImp.getYd())){
                                intent.setClass(WelcomeActivity.this, GuideActivity.class);
                                startActivity(intent);
                            }else {
                                intent.setClass(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }

                        @Override
                        public void onFailed(int i) {
                            Log.d("dsadsda","登录失败："+i);
                            toToast(WelcomeActivity.this, "登录失败");
                            Intent intent = new Intent();
                            if(TextUtils.isEmpty(spImp.getYd())){
                                intent.setClass(WelcomeActivity.this, GuideActivity.class);
                                startActivity(intent);
                            }else {
//                                intent.setClass(WelcomeActivity.this, MainActivity.class);
                                spImp.setUID("0");
                                spImp.setYXID("0");
                                spImp.setYXTOKEN("0");
                                spImp.setIsAdmin("0");
                                spImp.setWyUpAccid("");
                                spImp.setWyUpToken("");
                                spImp.setIsCaptain("");
                                spImp.setIsShop("");
                                spImp.setIfSign("");
                                spImp.setIsBDWX("");
                                spCache.clear();
                                intent.setClass(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            toToast(WelcomeActivity.this, "登录出错");
                            Intent intent = new Intent();
                            if(TextUtils.isEmpty(spImp.getYd())){
                                intent.setClass(WelcomeActivity.this, GuideActivity.class);
                                startActivity(intent);
                            }else {
                                intent.setClass(WelcomeActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            finish();
                        }
                        // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                    };
            Log.d("网易云信登录状态，",""+NIMClient.getStatus());
            if (NIMClient.getStatus() != StatusCode.LOGINED){
                NimUIKit.login(info,callback);
            }else {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private void showAgreeDialog() {
        XieYiDialog dialog = new XieYiDialog(WelcomeActivity.this, new XieYiDialog.XieYiDialogListener() {
            @Override
            public void agreeBtnListen() {
                spImp.setIsAgreeXieYi(true);
                initAsset();
                initData();
            }

            @Override
            public void disAgreeBtnListen() {
                spImp.setIsAgreeXieYi(false);
                MyApplication.getInstance().exit();
            }

            @Override
            public void xieYiTextClickListen() {
                Intent itA = new Intent(WelcomeActivity.this, UserAgreementActivity.class);
                itA.putExtra("title", "用户协议");
                itA.putExtra("url", NetConfig.userAgreementUrl);
                startActivity(itA);
            }

            @Override
            public void zhengCeTextClickListen() {
                Intent itTk = new Intent(WelcomeActivity.this, UserAgreementActivity.class);
                itTk.putExtra("title", "隐私政策");
                itTk.putExtra("url", NetConfig.userAgreementUrl1);
                startActivity(itTk);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}
