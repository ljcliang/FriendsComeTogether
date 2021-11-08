package com.yiwo.friendscometogether.pages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.module.MsgForwardFilter;
import com.netease.nim.uikit.business.session.module.MsgRevokeFilter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.MixPushConfig;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
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
import com.yiwo.friendscometogether.network.UMConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
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
            initSDK();
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

    /**
     * 初始化第三方的SDK
     */
    private void initSDK(){
        UMShareAPI.get(getApplication());
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(getApplication(), "5b5579fbb27b0a608200000d"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        {
            PlatformConfig.setWeixin(UMConfig.WECHAT_APPID, UMConfig.WECHAT_APPSECRET);
        }
        CrashReport.initCrashReport(getApplicationContext(), "20d02c310e", false);
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
    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        Log.d("getgetwangyitoken", "aaaa|||" + spImp.getYXID() + "|||" + spImp.getYXTOKEN());
        // 从本地读取上次登录成功时保存的用户登录信息
        String account = spImp.getYXID();
        String token = spImp.getYXTOKEN();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account);
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }
    // 如果返回值为 null，则全部使用默认参数。xn_config为小米
    private SDKOptions options(MixPushConfig xm_config) {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.logo_gray;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
        options.mixPushConfig = xm_config;
        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
//        String sdkPath = getAppCacheDir(context) + "/nim"; // 可以不设置，那么将采用默认路径
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
//        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
//        options.thumbnailSize = ${Screen.width} / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionTypeEnum, String s) {
                return null;
            }
        };
        return options;
    }
    private void showAgreeDialog() {
        XieYiDialog dialog = new XieYiDialog(WelcomeActivity.this, new XieYiDialog.XieYiDialogListener() {
            @Override
            public void agreeBtnListen() {
                spImp.setIsAgreeXieYi(true);
                initSDK();
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
