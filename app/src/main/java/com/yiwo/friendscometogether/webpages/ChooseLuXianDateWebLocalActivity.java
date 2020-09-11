package com.yiwo.friendscometogether.webpages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.newmodel.FabuLuXianChooseDateWithWebModel;
import com.yiwo.friendscometogether.newmodel.Fabu_Xiugai_LuXian_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChooseLuXianDateWebLocalActivity extends BaseSonicWebActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String url;
    private Unbinder unbinder;
    private String chooseDataJson = "";
    private FabuLuXianChooseDateWithWebModel chooseModel;
    public static final String CHOOSED_LIST_KEY = "CHOOSED_LIST_KEY";
    public static final String CHOOSED_MODE_KEY = "CHOOSED_MODE_KEY";
    public static final int CHOOSED_RESULT_CODE = 1;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_luxi_date_web);
        unbinder = ButterKnife.bind(this);
        url = "file:///android_asset/htmlfile/seltime.html";
        initWebView(webView,url);
        initIntentSonic(url,webView);
        gson = new Gson();
        webView.addJavascriptInterface(new AndroidInterface(),"android");//交互
        chooseModel = (FabuLuXianChooseDateWithWebModel)getIntent().getSerializableExtra(CHOOSED_LIST_KEY);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    if (chooseModel.getDatearr()!=null&&chooseModel.getDatearr().size()>0){
                        webView.loadUrl("javascript:getAndroidDatearr('"+gson.toJson(chooseModel)+"')");
                    }else {
                        webView.loadUrl("javascript:getAndroidDatearr('')");
                    }
                }
            }
        });
    }
    public static void open(Activity activity,int reqCode ,FabuLuXianChooseDateWithWebModel phaseInfosBeanList){
        Intent intent = new Intent();
        intent.putExtra(CHOOSED_LIST_KEY, (Serializable) phaseInfosBeanList);//发现可以把list强转成Serializable类型也可传递
        intent.setClass(activity,ChooseLuXianDateWebLocalActivity.class);
        activity.startActivityForResult(intent,reqCode);
    }
    public static void open(Activity activity,int reqCode){
        FabuLuXianChooseDateWithWebModel model = new FabuLuXianChooseDateWithWebModel();
        List<Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean> list = new ArrayList<>();
        model.setDatearr(list);
        open(activity,reqCode,model);
    }
    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void hideKeyBoard(){
            Log.d("交互方法","hideKeyBoard");
            hideKey();
        }
        @JavascriptInterface
        public void saveseltime(String datearrJson){
            Log.d("交互方法saveseltime",datearrJson);
            chooseModel = gson.fromJson(datearrJson,FabuLuXianChooseDateWithWebModel.class);
            Intent intent = new Intent();
            intent.putExtra(CHOOSED_MODE_KEY,chooseModel);
            setResult(CHOOSED_RESULT_CODE,intent);
            finish();
        }
    }
    public void hideKey(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }
    @OnClick({R.id.rl_back,R.id.btn_hide})
    public void OnClick(View view){
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_hide:
                hideKey();
                break;
        }
    }
}
