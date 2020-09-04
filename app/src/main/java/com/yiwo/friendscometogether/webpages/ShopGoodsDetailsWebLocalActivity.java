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
    private String shop_wy_Id = "";
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
        getWYID();
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

    private void getWYID() {

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
            sharegoods( shangpinId, shangpinName, shangpinInfo, shangpinImage, shareUrl);
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
            Log.d("asdasdasda",url);
            String uid = spImp.getUID();
            if (TextUtils.isEmpty(uid) || uid.equals("0")) {
                Intent intent = new Intent();
                intent.setClass(ShopGoodsDetailsWebLocalActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.setClass(ShopGoodsDetailsWebLocalActivity.this, ShopGoodsBuyWebActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }

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
//addCart(商品id，规格id，数量)
//addBuy(商品id，规格id，数量)
        @JavascriptInterface
        public void addCart(String goodId,String guiGeId,String num){
            if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                Intent intent = new Intent();
                intent.setClass(ShopGoodsDetailsWebLocalActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                addGoodsCar(goodId,guiGeId,num);
            }
            Log.d("ssssdddd",goodId+"///"+guiGeId+".////"+num);

        }
        @JavascriptInterface
        public void buycargo(){
            if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                Intent intent = new Intent();
                intent.setClass(ShopGoodsDetailsWebLocalActivity.this, LoginActivity.class);
                startActivity(intent);
            }else {
                GoodsCartWebActivity.open(ShopGoodsDetailsWebLocalActivity.this,NetConfig.BaseUrl+NetConfig.myCartWebUrl+spImp.getUID());
            }
            Log.d("ssssdddd","openCar");
        }
        @JavascriptInterface
        public void addBuy(String goodId,String guiGeId,String num){
            if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                Intent intent = new Intent();
                intent.setClass(ShopGoodsDetailsWebLocalActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.setClass(ShopGoodsDetailsWebLocalActivity.this, ShopGoodsBuyWebActivity.class);
                String url = NetConfig.BaseUrl+NetConfig.nowBuy+"uid="+spImp.getUID()+"&goodsID="+goodId+"&specID="+guiGeId+"&num="+num;
                Log.d("ssssddddbuy_url",url);
                intent.putExtra("url",url);
                startActivity(intent);
            }
            Log.d("ssssddddbuy",goodId+"///"+guiGeId+".////"+num);
        }
        @JavascriptInterface
        public void sharego(){//分享
            share(goodId);
        }
        @JavascriptInterface
        public void chatgo(){//去聊天
            quLiaoTian();
        }
        @JavascriptInterface
        public void  goodsfav(){
            shouCangShangPin();
        }
    }
    /**
     *
     * @param id id:被关注人的id（当type=4时为当前登录人的ID），
     * @param type type:1友记2友聚3商品4友聚补填人员,
     * @param staus staus:1已收藏0未收藏（当type=4时传啥都无所谓,
     *                                    当id=0时为收藏/取消收藏友记、友聚、商品，
     *                                       ≠0时为关注/取消关注此id的人）)
     */
    public void updateWebStaus(String id,String type,String staus){
        //                    supplement(id:被关注人的id（当type=4时为当前登录人的ID），
//                              type:1友记2友聚3商品4友聚补填人员,
//                              staus1已收藏0未收藏（当type=4时传啥都无所谓,、
//                                                   当id=0时为收藏/取消收藏友记、友聚、商品，
//                                                      ≠0时为关注/取消关注此id的人）)

        webView.loadUrl("javascript:supplement('"+id+"','"+type+"','"+ staus+"')");
    }
    private void shouCangShangPin() {
        if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
            Intent intent = new Intent();
            intent.setClass(ShopGoodsDetailsWebLocalActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            ViseHttp.POST(NetConfig.collectGoods)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.collectGoods))
                    .addParam("goodsID",goodId)
                    .addParam("uid",spImp.getUID())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.d("ssssdddd",data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    toToast(ShopGoodsDetailsWebLocalActivity.this,jsonObject.getString("message"));
                                    JSONObject jsData = jsonObject.getJSONObject("obj");
                                    if (jsData.getString("type").equals("1")){
                                        //更新收藏商品状态
                                        updateWebStaus("0","3","1");
                                    }else {
                                        //更新收藏商品状态
                                        updateWebStaus("0","3","0");
                                    }
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

    private void quLiaoTian(){
        String uid = spImp.getUID();
        if (TextUtils.isEmpty(uid) || uid.equals("0")) {
            Intent intent = new Intent();
            intent.setClass(ShopGoodsDetailsWebLocalActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            ViseHttp.POST(NetConfig.getGoodsInfo)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getGoodsInfo))
                    .addParam("goodsID",goodId)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.d("ssssdddd",data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("obj");
                                    liaotian(jsonObject1.getString("wy_accid"));
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
    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        if (liaotianAccount == null||liaotianAccount.equals("")||account.equals(liaotianAccount)){

        }else {
            NimUIKit.setAccount(account);
            NimUIKit.startP2PSession(ShopGoodsDetailsWebLocalActivity.this, liaotianAccount);
        }
    }
    private void share(String goodId) {
        ViseHttp.POST(NetConfig.getGoodsInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getGoodsInfo))
                .addParam("goodsID",goodId)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("ssssdddd",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                JSONObject jsonObject1 = jsonObject.getJSONObject("obj");
                                sharegoods(goodId,jsonObject1.getString("goodsName"),jsonObject1.getString("goodsInfo"),
                                        jsonObject1.getString("goodsImg"),jsonObject1.getString("shareUrl"));
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

    public void sharegoods(String shangpinId,String shangpinName,String shangpinInfo,String shangpinImage,String shareUrl){
        new ShareAction(ShopGoodsDetailsWebLocalActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        ShareUtils.shareWeb(ShopGoodsDetailsWebLocalActivity.this, shareUrl, shangpinName,
                                shangpinInfo, shangpinImage, share_media);
                    }
                }).open();
    }
    private void addGoodsCar(String goodId,String guiGeId,String num){
        ViseHttp.POST(NetConfig.addCart)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.addCart))
                .addParam("uid",spImp.getUID())
                .addParam("goodsID", goodId)
                .addParam("specID",guiGeId)
                .addParam("num",num)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("ssssdddd",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                webView.loadUrl("javascript:addbuycarok('"+1+"')");
                            }else {
                                webView.loadUrl("javascript:addbuycarok('"+0+"')");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        webView.loadUrl("javascript:addbuycarok('"+0+"')");
                    }
                });
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
