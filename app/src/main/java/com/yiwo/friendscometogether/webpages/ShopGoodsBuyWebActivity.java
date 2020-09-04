package com.yiwo.friendscometogether.webpages;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.model.Paymodel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.network.UMConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShopGoodsBuyWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String url;
    private Unbinder unbinder;
    private SpImp spImp;

    private IWXAPI api;
    private static final int SDK_PAY_FLAG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_goods_buy);
        spImp = new SpImp(this);
        unbinder = ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        api = WXAPIFactory.createWXAPI(this, null);
        initIntentSonic(url,webView);
        webView.addJavascriptInterface(new ShopGoodsBuyWebActivity.AndroidInterface(),"android");//交互
    }
    public class AndroidInterface extends Object{
        /**
         * gopaygoods()  支付交互方法  返回参数 依次：收货地址 、 收货人、 收货电话、 商品id 、 规格id 、 用户id 、 购买数量 、  备注 、 支付类型
         *
         * 获得参数后  走 action/ac_goods/insertGoodsOrder 下单接口
         * 传参：getAddress收货地址、getPerson收货人、getTel收货电话、goodsID商品id、specID规格id、uid用户id、num购买数量、beizhu备注、payType支付类型
         */
        @JavascriptInterface
        public void gopaygoods(String getAddress,String getPerson,String getTel,String goodsID,String specID,String uid,String num,String beizhu,String payType){
            Log.d("22222",getAddress+"\n"+getPerson);
            ViseHttp.POST(NetConfig.insertGoodsOrder)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.insertGoodsOrder))
                    .addParam("getAddress", getAddress)
                    .addParam("getPerson", getPerson)
                    .addParam("getTel", getTel)
                    .addParam("goodsID",goodsID )
                    .addParam("specID", specID)
                    .addParam("uid", uid)
                    .addParam("num", num)
                    .addParam("beizhu",beizhu)
                    .addParam("payType",payType)
                    .request(new ACallback<String>() {
                        @Override
                        public void onFail(int errCode, String errMsg) {
                            Log.e("222", errCode+""+errMsg);
                        }

                        @Override
                        public void onSuccess(String data) {
                            Log.e("222", data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 100) {
                                    Paymodel paymodel = new Gson().fromJson(data, Paymodel.class);
                                    toToast(ShopGoodsBuyWebActivity.this, "微信支付");
                                    wxPay(paymodel.getObj());
                                }else if(jsonObject.getInt("code") == 200){
                                    toToast(ShopGoodsBuyWebActivity.this, "支付宝支付");
                                    Log.e("123123", jsonObject.optString("obj"));
                                    aliPay(jsonObject.optString("obj"));
                                }  else if (jsonObject.getInt("code") == 400) {
                                    toToast(ShopGoodsBuyWebActivity.this, jsonObject.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }
    public void wxPay(Paymodel.ObjBean model) {
        api.registerApp(UMConfig.WECHAT_APPID);
        PayReq req = new PayReq();
        req.appId = model.getAppId();
        req.partnerId = model.getPartnerId();
        req.prepayId = model.getPrepayId();
        req.nonceStr = model.getNonceStr();
        req.timeStamp = model.getTimestamp() + "";
        req.packageValue = model.getPackageX();
        req.sign = model.getSign();
        req.extData = "app data";
        api.sendReq(req);
        finish();
    }

    public void aliPay(String info) {
        final String orderInfo = info;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ShopGoodsBuyWebActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    if(result.get("resultStatus").equals("9000")){
                        Toast.makeText(ShopGoodsBuyWebActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
            }
        }

    };
    @OnClick({R.id.rl_back})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }
}
