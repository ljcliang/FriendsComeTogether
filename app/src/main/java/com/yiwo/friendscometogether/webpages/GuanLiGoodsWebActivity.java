package com.yiwo.friendscometogether.webpages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.newpage.FaBu_XiuGaiShangPinActivity;
import com.yiwo.friendscometogether.newpage.JuBaoActivity;
import com.yiwo.friendscometogether.newpage.PeiSongSettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanLiGoodsWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.rl_fabu)
    RelativeLayout mRlFabu;
    @BindView(R.id.ll_btn_serch)
    LinearLayout mLlBtnSerch;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    private String url;
    @BindView(R.id.rl_show_more)
    RelativeLayout rl_show_more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_goods_web);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        initIntentSonic(url, webView);
        webView.addJavascriptInterface(new AndroidInterface(), "android");
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, GuanLiGoodsWebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_return, R.id.rl_share,R.id.rl_fabu, R.id.ll_btn_serch,R.id.rl_show_more})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_show_more:
                showMore(rl_show_more);
                break;
            case R.id.rl_return:
                onBackPressed();
                break;
            case R.id.rl_share:

                break;
            case R.id.rl_fabu:
                Intent intent = new Intent();
                intent.setClass(GuanLiGoodsWebActivity.this, FaBu_XiuGaiShangPinActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_btn_serch:
                webView.loadUrl("javascript:sousuo('" + mEdtSearch.getText().toString() + "')");
                mEdtSearch.setText("");
                break;
        }
    }

    public class AndroidInterface extends Object {
        @JavascriptInterface
        public void editgood(String gid) {
            Intent intent = new Intent();
            intent.setClass(GuanLiGoodsWebActivity.this, FaBu_XiuGaiShangPinActivity.class);
            intent.putExtra(FaBu_XiuGaiShangPinActivity.GID, gid);
            startActivity(intent);
        }
    }
    private void showMore(final View view_p) {

        View view = LayoutInflater.from(GuanLiGoodsWebActivity.this).inflate(R.layout.popupwindow_goods_manger_web_activity_show_more, null);
        final PopupWindow popupWindow;
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        LinearLayout ll_peisong = view.findViewById(R.id.ll_peisong);
        ll_peisong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeiSongSettingActivity.open(GuanLiGoodsWebActivity.this);
                popupWindow.dismiss();
            }
        });
        ScreenAdapterTools.getInstance().loadView(view);//确定后dp
        LinearLayout ll_share_shop = view.findViewById(R.id.ll_share_shop);
        ll_share_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        LinearLayout ll_add_goods = view.findViewById(R.id.ll_add_goods);
        ll_add_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(GuanLiGoodsWebActivity.this, FaBu_XiuGaiShangPinActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(view_p,0,0);
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
            }
        });


    }

    private void share() {

    }
}
