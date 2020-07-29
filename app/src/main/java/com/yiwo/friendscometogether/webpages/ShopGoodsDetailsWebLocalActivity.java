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
import com.netease.nim.uikit.api.NimUIKit;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.FileUtils;
import com.yiwo.friendscometogether.utils.ShareUtils;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_goods_details);
        spImp = new SpImp(this);
        unbinder = ButterKnife.bind(this);
        StatusBarUtils.setStatusBarTransparent(ShopGoodsDetailsWebLocalActivity.this);
//        url = "http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID="+getIntent().getStringExtra(GOOD_ID_KEY)+"&uid="+spImp.getUID();
        url = "file:///android_asset/htmlfile/demoN.html";
        Log.d("asdasd",url);
//        url = getIntent().getStringExtra("url");
        initIntentSonic(url,webView);
        webView.addJavascriptInterface(new ShopGoodsDetailsWebLocalActivity.AndroidInterface(),"android");//交互
        String str = "uploads/xingcheng/20191111/2-0d822c33a29656244255ddc22dcae64d6742.jpeg$$$@@@uploads/xingcheng/20191113/2-98eef1686a0243956adf2923afd0d4ec7995.jpg|&|&|5|&|&|用户昵称|&|&|Lv.1|&|&| |&|&|2020-08-10|&|&|新华社|&|&|+关注$$$@@@内容内容内容$$$@@@uploads/xingcheng/20191219/1-094c1e183945869a478294ab70a68f9f9996.jpg|&|&|uploads/xingcheng/20191111/0-cf3b6ce69545a69ce122866f123548ec8699.jpg$$$@@@续写标题|@#|@#|续写内容|@#|@#|uploads/xingcheng/20191219/2-771b628f5a836007b580067abfaedebe1448.jpeg|@|@|续写图1描述 |&|&| 续写标题1|@#|@#|续写内容1|@#|@#| $$$@@@uploads/xingcheng/20191219/2-771b628f5a836007b580067abfaedebe1448.jpeg|@|@|111|@|@|评论用户昵称|@|@|3天前|@|@|主要内容|@#|@#| |&|&|uploads/xingcheng/20191219/2-771b628f5a836007b580067abfaedebe1448.jpeg|@|@|111|@|@|评论用户昵称1|@|@|3.1天前|@|@|主要内容1|@#|@#|uploads/xingcheng/20191219/2-771b628f5a836007b580067abfaedebe1448.jpeg|@|@|111|@|@|回复用户昵称|@|@|4天前|@|@|回复主要内容|#|#|uploads/xingcheng/20191219/2-771b628f5a836007b580067abfaedebe1448.jpeg|@|@|111|@|@|回复用户昵称1|@|@|4.1天前|@|@|回复主要内容1|#|#|uploads/xingcheng/20191219/2-771b628f5a836007b580067abfaedebe1448.jpeg|@|@|111|@|@|回复用户昵称2|@|@|4.2天前|@|@|回复主要内容2";
        String strr1 = str;
        String strr2 = getIntent().getStringExtra(GOOD_ID_KEY);
        String strr3 = "5";
//        String strr1 = "{\"txtstr\" : \""+str+"\",";
//        String strr2 = "\"idpath\" : "+getIntent().getStringExtra(GOOD_ID_KEY)+",";
//        String strr3 = "\"type\" : "+"5"+"}";
        Log.d("adsadasd",strr1+""+strr2+""+strr3);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100 && isFirst){
                    isFirst = false;
                    webView.loadUrl("javascript:getTongbanDataAndroid('"+strr1+"','"+strr2+"','"+strr3+"')");
                }
            }
        });
    }
    public static void open(Context context,String goodId){
        Intent intent = new Intent();
        intent.putExtra(GOOD_ID_KEY,goodId);
        intent.setClass(context, ShopGoodsDetailsWebLocalActivity.class);
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
