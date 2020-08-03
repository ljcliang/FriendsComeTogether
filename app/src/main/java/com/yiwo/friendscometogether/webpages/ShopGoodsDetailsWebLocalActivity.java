package com.yiwo.friendscometogether.webpages;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.dbmodel.GoodsWebInfoDbModel;
import com.yiwo.friendscometogether.dbmodel.WebInfoOfDbUntils;
import com.yiwo.friendscometogether.dbmodel.YouJuHuoDongWebInfoDbModel;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.LocalWebInfoModel;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.FileUtils;
import com.yiwo.friendscometogether.utils.ShareUtils;
import com.yiwo.friendscometogether.utils.WebUntils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopGoodsDetailsWebLocalActivity extends BaseSonicWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String url;
    private Unbinder unbinder;
    private SpImp spImp;
    public static final String GOOD_ID_KEY = "goodId";
    private boolean isFirst = true;
    WebInfoOfDbUntils webInfoOfDbUntils;
    private String goodId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_goods_details);
        spImp = new SpImp(this);
        unbinder = ButterKnife.bind(this);
        StatusBarUtils.setStatusBarTransparent(ShopGoodsDetailsWebLocalActivity.this);
        goodId = getIntent().getStringExtra(GOOD_ID_KEY);
//        url = "http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID="+getIntent().getStringExtra(GOOD_ID_KEY)+"&uid="+spImp.getUID();
        url = "file:///android_asset/htmlfile/demoG.html";
        Log.d("asdasd",url);
//        url = getIntent().getStringExtra("url");
        initIntentSonic(url,webView);
        webInfoOfDbUntils = new WebInfoOfDbUntils(this);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100 && isFirst){
                    if (isFirst){
                        isFirst = false;
//                        String strr1 = getIntent().getStringExtra("str");
//                        String strr2 = "";
//                        String strr3 = "0";
//                        Log.d("adsadasd",strr1+""+strr2+""+strr3);
//                        webView.loadUrl("javascript:getTongbanDataAndroid('"+strr1+"','"+strr2+"','"+strr3+"')");
                        if (webInfoOfDbUntils.hasThisId_Goods(goodId)){
                            String strr1 = webInfoOfDbUntils.queryGood(goodId).getWeb_info();
                            String strr2 = "";
                            String strr3 = "2";
                            Log.d("adsadasd：：\n",strr1+"\n"+strr2+"\n"+strr3);
                            webView.loadUrl("javascript:getTongbanDataAndroid('"+strr1+"','"+strr2+"','"+strr3+"')");
                            /**
                             * 加载之后再次查询更新数据库
                             */
                            ViseHttp.POST(NetConfig.articleInfo)
                                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleInfo))
                                    .addParam("uid", spImp.getUID())
                                    .addParam("fmID",goodId)
                                    .addParam("type","2")
                                    .request(new ACallback<String>() {
                                        @Override
                                        public void onSuccess(String data) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(data);
                                                if (jsonObject.getInt("code") == 200){
                                                    Gson gson = new Gson();
                                                    LocalWebInfoModel mode =  gson.fromJson(data,LocalWebInfoModel.class);
                                                    GoodsWebInfoDbModel goodsWebInfoDbModel = new GoodsWebInfoDbModel();
                                                    goodsWebInfoDbModel.setWeb_info(mode.getObj().getStr());
                                                    goodsWebInfoDbModel.setGood_id(goodId);
                                                    webInfoOfDbUntils.insertGoodModel(goodsWebInfoDbModel);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFail(int errCode, String errMsg) {

                                        }
                                    });
                        }else {
                            ViseHttp.POST(NetConfig.articleInfo)
                                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleInfo))
                                    .addParam("uid", spImp.getUID())
                                    .addParam("fmID",goodId)
                                    .addParam("type","2")
                                    .request(new ACallback<String>() {
                                        @Override
                                        public void onSuccess(String data) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(data);
                                                if (jsonObject.getInt("code") == 200){
                                                    Gson gson = new Gson();
                                                    LocalWebInfoModel mode =  gson.fromJson(data,LocalWebInfoModel.class);
                                                    String strr1 = mode.getObj().getStr();
                                                    String strr2 = "";
                                                    String strr3 = "2";
                                                    strr1 = WebUntils.replaceStr(strr1);
                                                    Log.d("adsadasd数据库中没有此条数据ID-",goodId+"\n"+strr1+"\n"+strr2+"\n"+strr3);
                                                    webView.loadUrl("javascript:getTongbanDataAndroid('"+strr1+"','"+strr2+"','"+strr3+"')");
                                                    GoodsWebInfoDbModel goodsWebInfoDbModel = new GoodsWebInfoDbModel();
                                                    goodsWebInfoDbModel.setWeb_info(mode.getObj().getStr());
                                                    goodsWebInfoDbModel.setGood_id(goodId);
                                                    webInfoOfDbUntils.insertGoodModel(goodsWebInfoDbModel);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFail(int errCode, String errMsg) {

                                        }
                                    });
                        }
                    }
                }
            }
        });
        webView.addJavascriptInterface(new ShopGoodsDetailsWebLocalActivity.AndroidInterface(),"android");//交互

    }
    public static void open(Context context,String goodId){
        Intent intent = new Intent();
        intent.putExtra(GOOD_ID_KEY,goodId);
        intent.setClass(context, ShopGoodsDetailsWebLocalActivity.class);
        context.startActivity(intent);
    }
    public class AndroidInterface extends Object{

        @JavascriptInterface
        public void backgo(){
            Log.d("交互了","fanhui");
            finish();
        }
        /**
         * toshare()  分享商品页的交互方法  传了商品id  商品名称  商品信息  商品图片  商品分享地址   5个参数
         */
        @JavascriptInterface
        public void tosharegoods(String shangpinId,String shangpinName,String shangpinInfo,String shangpinImage,String shareUrl){
            Intent intent = new Intent();
            if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                intent.setClass(ShopGoodsDetailsWebLocalActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                new ShareAction(ShopGoodsDetailsWebLocalActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                ShareUtils.shareWeb(ShopGoodsDetailsWebLocalActivity.this, shareUrl, shangpinName,
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
            toToast(ShopGoodsDetailsWebLocalActivity.this,"sadasd");
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
                intent.setClass(ShopGoodsDetailsWebLocalActivity.this, LoginActivity.class);
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
            toToast(ShopGoodsDetailsWebLocalActivity.this,"buybuy");
            Intent intent = new Intent();
            intent.setClass(ShopGoodsDetailsWebLocalActivity.this, ShopGoodsBuyWebActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
        @JavascriptInterface
        public void saveImg(String img_url){
            Log.d("asdas",img_url);
            AlertDialog.Builder builder = new AlertDialog.Builder(ShopGoodsDetailsWebLocalActivity.this);
            builder.setMessage("确定保存图片到本地？")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            download(img_url);
                        }
                    }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
        @JavascriptInterface
        public void btnmore(){
        }
    }
    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(ShopGoodsDetailsWebLocalActivity.this, liaotianAccount);
    }
    @OnClick({R.id.rl_back,R.id.btn})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn:
                break;
        }
    }
    // 保存图片到手机
    public void download(final String url) {

        new AsyncTask<Void, Integer, File>() {

            @Override
            protected File doInBackground(Void... params) {
                File file = null;
                try {
                    FutureTarget<File> future = Glide
                            .with(ShopGoodsDetailsWebLocalActivity.this)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    file = future.get();

                    // 首先保存图片
                    File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
//                    File pictureFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);;
                    File appDir = new File(pictureFolder ,"Beauty");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = "瞳伴图片_"+System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);
                    FileUtils.copy(file, destFile);
                    // 最后通知图库更新
                    ShopGoodsDetailsWebLocalActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));


                } catch (Exception e) {
                    Log.e("123132", e.getMessage());
                }
                return file;
            }

            @Override
            protected void onPostExecute(File file) {

                Toast.makeText(ShopGoodsDetailsWebLocalActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
    }
}
