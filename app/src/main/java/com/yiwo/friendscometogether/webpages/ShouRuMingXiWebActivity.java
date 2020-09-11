package com.yiwo.friendscometogether.webpages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.TiXianResultModel;
import com.yiwo.friendscometogether.newpage.TiXianShuoMingActivity;
import com.yiwo.friendscometogether.newpage.renzheng.RenZheng0_BeginActivity;
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
    @BindView(R.id.refresh_layout)
    RefreshLayout refresh_layout;
    private String url;
    private Unbinder unbinder;
    private SpImp spImp;
    private Dialog weiBoDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shourumingxi_web);
        unbinder = ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        spImp = new SpImp(this);
        initWebView(webView,url);
        refresh_layout.setEnableLoadMore(false);
        refresh_layout.setRefreshHeader(new ClassicsHeader(this));
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                webView.reload();
                refresh_layout.finishRefresh(500);
            }
        });
        webView.addJavascriptInterface(new ShouRuMingXiWebActivity.AndroidInterface(),"Android");//交互
    }
    @OnClick({R.id.rl_back,R.id.rl_tixian})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_tixian:
                AlertDialog.Builder builder = new AlertDialog.Builder(ShouRuMingXiWebActivity.this);
                builder.setMessage("确定提现？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tiXian();
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent intent = new Intent();
                        intent.setClass(ShouRuMingXiWebActivity.this, TiXianShuoMingActivity.class);
                        startActivity(intent);
                    }
                }).show();
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
    private void tiXian() {
        weiBoDialog = WeiboDialogUtils.createLoadingDialog(this,"加载中...");
        ViseHttp.POST(NetConfig.getMoneyFromWx)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getMoneyFromWx))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        WeiboDialogUtils.closeDialog(weiBoDialog);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                TiXianResultModel model = gson.fromJson(data,TiXianResultModel.class);
                                switch (model.getObj().getStatus()){
                                    case "0"://未认证
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ShouRuMingXiWebActivity.this);
                                        builder.setMessage("您还没有完成商户认证")
                                                .setPositiveButton("去认证", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        RenZheng0_BeginActivity.openActivity(ShouRuMingXiWebActivity.this);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                    case "1"://余额不足
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShouRuMingXiWebActivity.this);
                                        builder1.setMessage("提现余额不足")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                    case "2"://mei有绑定银行卡
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(ShouRuMingXiWebActivity.this);
                                        builder2.setMessage("您还没有绑定银行卡")
                                                .setPositiveButton("查看绑定步骤", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent();
                                                        intent.setClass(ShouRuMingXiWebActivity.this, TiXianShuoMingActivity.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                    case "3"://绑定银行卡youwu
                                        AlertDialog.Builder builder3 = new AlertDialog.Builder(ShouRuMingXiWebActivity.this);
                                        builder3.setMessage("您绑定的银行卡信息有误")
                                                .setPositiveButton("查看绑定步骤", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent intent = new Intent();
                                                        intent.setClass(ShouRuMingXiWebActivity.this, TiXianShuoMingActivity.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                    case "4"://提示成功
                                        AlertDialog.Builder builder4 = new AlertDialog.Builder(ShouRuMingXiWebActivity.this);
                                        builder4.setMessage("提现申请成功，将在24小时内打入绑定银行账户，请注意查收")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        dialogInterface.dismiss();
                                                    }
                                                }).show();
                                        break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(weiBoDialog);
                    }
                });
    }
}
