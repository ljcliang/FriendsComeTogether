package com.yiwo.friendscometogether.webpages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.RelativeLayout;
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
import com.yiwo.friendscometogether.newpage.ShopHomeActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyGoodsOrdersActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.webView)
    WebView webView;
    private SpImp spImp;
    private IWXAPI api;
    private static final int SDK_PAY_FLAG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goods_orders);
        ButterKnife.bind(this);
        api = WXAPIFactory.createWXAPI(this, null);
        spImp = new SpImp(this);
        initIntentSonic(NetConfig.orderList + spImp.getUID(),webView);
        webView.addJavascriptInterface(new MyGoodsOrdersActivity.AndroidInterface(),"android");//交互
    }
    public static void open(Context context){
        Intent intent = new Intent();
        intent.setClass(context,MyGoodsOrdersActivity.class);
        context.startActivity(intent);
    }
    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        finish();
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
                PayTask alipay = new PayTask(MyGoodsOrdersActivity.this);
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
                        Toast.makeText(MyGoodsOrdersActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
            }
        }

    };
    private class AndroidInterface {
        /**
         * //        goodsweb(gid)  我的订单列表 点击商品 跳转到商品详情交互方法
         */
        @JavascriptInterface
        public void goodsweb(String gid){
            ShopGoodsDetailsWebLocalActivity.open(MyGoodsOrdersActivity.this,gid);
        }
        /**
         * nowpay我的订单立即支付 交互方法 反orderID
         */
        @JavascriptInterface
        public void nowpay(String orderID){
            goPay(orderID);
        }
        /**
         * goshop(u_id)  买家我的订单 点头像 跳到铺子
         */
        @JavascriptInterface
        public void goshop(String u_id){
            Log.d("交互","goshop");
            ShopHomeActivity.start(MyGoodsOrdersActivity.this,u_id);
        }
    }

    private void goPay(String orderID) {
        ViseHttp.POST(NetConfig.myOrderPay)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.myOrderPay))
                .addParam("orderID",orderID)
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
                                toToast(MyGoodsOrdersActivity.this, "微信支付");
                                wxPay(paymodel.getObj());
                            }else if(jsonObject.getInt("code") == 200){
                                toToast(MyGoodsOrdersActivity.this, "支付宝支付");
                                Log.e("123123", jsonObject.optString("obj"));
                                aliPay(jsonObject.optString("obj"));
                            }  else if (jsonObject.getInt("code") == 400) {
                                toToast(MyGoodsOrdersActivity.this, jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
