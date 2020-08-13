package com.yiwo.friendscometogether.webpages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newpage.renzheng.RenZhengModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShouRuMingXiWebActivity extends BaseWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String url;
    private Unbinder unbinder;
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shourumingxi_web);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        unbinder = ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        spImp = new SpImp(this);
//        ViseHttp.POST(NetConfig.wxQuery)
//                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.wxQuery))
//                .addParam("uid", spImp.getUID())
//                .request(new ACallback<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//                        Log.d("saasdasd",data);
//                        try {
//                            JSONObject jsonObject = new JSONObject(data);
//                            if (jsonObject.getInt("code") == 200){
//                                Gson gson = new Gson();
//                                RenZhengModel model = gson.fromJson(data,RenZhengModel.class);
//                                String sBindStaus = model.getObj().getSign();
////                                sBindStaus = "2";
//                                String sRenZhengFeiStaus = model.getObj().getPay();
////                                sRenZhengFeiStaus = "1";
//                                if (sBindStaus.equals("3")){
//                                    if (sRenZhengFeiStaus.equals("1")||sRenZhengFeiStaus.equals("2")){
//                                        spImp.setIfSign("1");
//                                    }
//                                }
//                            }else {
//                                spImp.setIfSign("0");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFail(int errCode, String errMsg) {
//
//                    }
//                });
        initWebView(webView,url);
        webView.addJavascriptInterface(new ShouRuMingXiWebActivity.AndroidInterface(),"Android");//交互
    }
    @OnClick({R.id.rl_back})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
        }
    }

    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void usecoupon(String youhui_id,String youhui_price){//优惠券ID、优惠金额
            Intent intent  =new Intent();
            intent.putExtra("youhui_id",youhui_id);
            intent.putExtra("youhui_price",youhui_price);
            Log.d("sadasdas","sddasdasd");
            setResult(1,intent);
            finish();
        }
    }
}
