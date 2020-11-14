package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.ShopHomeModel;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;
import com.yiwo.friendscometogether.webpages.ShopGoodsDetailsWebLocalActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ShopHomeActivity extends BaseWebActivity {

    @BindView(R.id.iv_top_bg)
    ImageView iv_top_bg;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_level)
    TextView tv_level;
    @BindView(R.id.iv_level)
    ImageView iv_level;
    @BindView(R.id.iv_guanzhu)
    ImageView iv_guanzhu;
    @BindView(R.id.tv_all_goods)
    TextView tv_all_goods;
    @BindView(R.id.edt_sousuo)
    EditText edt_sousuo;
    @BindView(R.id.tv_sousuo)
    TextView tv_sousuo;
    @BindView(R.id.web_view)
    WebView webView;
    private SpImp spImp;
    private String shopUserID;
    public static final String SHOP_OWNNER_ID = "shopUserID";
//    private List<ShopHomeModel.ObjBean.GoodsListBean> dataGoods = new ArrayList<>();
//    private ShopHomeGoods_Adapter shopHomeGoodsAdapter;
    private int page = 1;
    private int isFollow = 0;
    private ShopHomeModel.ObjBean.UserInfoBean bean;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarTransparent(ShopHomeActivity.this);
        spImp = new SpImp(this);
        shopUserID = getIntent().getStringExtra(SHOP_OWNNER_ID);
        initRv();
        initData();
        //uid=店铺id&myID=我的id
        initWebView(webView,NetConfig.inMyShop+"uid="+shopUserID+"&myID="+spImp.getUID());
        webView.addJavascriptInterface(new ShopHomeActivity.AndroidInterface(),"android");//交互
        int webViewStartY = webView.getScrollY();
    }
    public class AndroidInterface{
        @JavascriptInterface
        public void goodsweb(String gid,String shopUid){
            Log.d("asdasd",gid+"///"+shopUid);
            ShopGoodsDetailsWebLocalActivity.open(ShopHomeActivity.this,gid,shopUid);
        }
    }
    private void initRv() {
//        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//        rv.setLayoutManager(mLayoutManager);
//        shopHomeGoodsAdapter = new ShopHomeGoods_Adapter(dataGoods);
//        rv.setAdapter(shopHomeGoodsAdapter);

    }

    public static void start(Context context,String shopOwnnerId){
        Intent intent = new Intent();
        intent.setClass(context,ShopHomeActivity.class);
        intent.putExtra(SHOP_OWNNER_ID,shopOwnnerId);
        context.startActivity(intent);
    }
    private void initData() {
        ViseHttp.POST(NetConfig.inShop)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.inShop))
                .addParam("uid", spImp.getUID())
                .addParam("shopUserID",shopUserID)
                .addParam("keyWord",edt_sousuo.getText().toString())
                .addParam("page","1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("sadasda",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                ShopHomeModel model = gson.fromJson(data,ShopHomeModel.class);
                                bean = model.getObj().getUserInfo();
                                tv_name.setText(bean.getUsername());
                                tv_level.setText("Lv."+bean.getUsergrade());
                                Glide.with(ShopHomeActivity.this).load(bean.getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(ivHead);
                                Glide.with(ShopHomeActivity.this).load(bean.getUserpic())
                                        .apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian).transforms(new BlurTransformation(25))).into(iv_top_bg);
                                switch (bean.getLevelName()){
                                    case "0":
                                        iv_level.setImageResource(R.mipmap.level_qingtong);
                                        break;
                                    case "1":
                                        iv_level.setImageResource(R.mipmap.level_baiyin);
                                        break;
                                    case "2":
                                        iv_level.setImageResource(R.mipmap.level_huangjin);
                                        break;
                                    case "3":
                                        iv_level.setImageResource(R.mipmap.level_bojin);
                                        break;
                                    case "4":
                                        iv_level.setImageResource(R.mipmap.level_zuanshi);
                                        break;
                                    case "5":
                                        iv_level.setImageResource(R.mipmap.level_huangguan);
                                        break;
                                }
                                isFollow = bean.getLikeUser().equals("1")? 1 : 0;
                                iv_guanzhu.setImageResource(bean.getLikeUser().equals("1") ? R.mipmap.shop_home_guanzhu_true:R.mipmap.shop_home_guanzhu_false );
//                                dataGoods.clear();
//                                dataGoods.addAll(model.getObj().getGoodsList());
//                                shopHomeGoodsAdapter.notifyDataSetChanged();
                                page = 2 ;
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
    private void loadMore() {
        ViseHttp.POST(NetConfig.inShop)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.inShop))
                .addParam("uid", spImp.getUID())
                .addParam("shopUserID",shopUserID)
                .addParam("keyWord",edt_sousuo.getText().toString())
                .addParam("page",page + "")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("sadasda",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                ShopHomeModel model = gson.fromJson(data,ShopHomeModel.class);
//                                dataGoods.addAll(model.getObj().getGoodsList());
//                                shopHomeGoodsAdapter.notifyDataSetChanged();
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
    @OnClick({R.id.rl_back,R.id.rl_guanzhu,R.id.rl_fenxiang,R.id.tv_sousuo,R.id.tv_all_goods})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rl_guanzhu:
                guanzhu();
                break;
            case R.id.rl_fenxiang:
                share();
                break;
            case R.id.tv_sousuo:
                webView.loadUrl("javascript:showdiv('" + edt_sousuo.getText().toString() + "')");
                break;
            case R.id.tv_all_goods:
                webView.reload();
                break;
        }
    }

    private void share() {
        Intent intent = new Intent();
        if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            new ShareAction(this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setShareboardclickCallback(new ShareBoardlistener() {
                        @Override
                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                            ShareUtils.shareWeb(ShopHomeActivity.this, bean.getShareUrl(), bean.getUsername(),
                                    bean.getUsername(), bean.getUserpic(), share_media);
                        }
                    }).open();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShareUtils.closeDialog();
    }

    private void guanzhu() {
        if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
            Intent intent = new Intent();
            intent.setClass(ShopHomeActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            if (isFollow == 0){
                ViseHttp.POST(NetConfig.userFocusUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                        .addParam("uid", spImp.getUID())
                        .addParam("likeId", shopUserID)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        toToast(ShopHomeActivity.this, "关注成功");
                                        Glide.with(ShopHomeActivity.this).load(R.mipmap.shop_home_guanzhu_true).into(iv_guanzhu);
                                        isFollow = 1;
                                    } else {
                                        toToast(ShopHomeActivity.this, jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
            }else if (isFollow == 1){
                ViseHttp.POST(NetConfig.removeConcerns)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.removeConcerns))
                        .addParam("uid", spImp.getUID())
                        .addParam("bid", shopUserID)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        toToast(ShopHomeActivity.this, "取消关注成功");
                                        Glide.with(ShopHomeActivity.this).load(R.mipmap.shop_home_guanzhu_false).into(iv_guanzhu);
                                        isFollow = 0;
                                    } else {
                                        toToast(ShopHomeActivity.this, jsonObject.getString("message"));
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
