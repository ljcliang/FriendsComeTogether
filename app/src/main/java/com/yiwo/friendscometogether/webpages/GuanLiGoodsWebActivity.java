package com.yiwo.friendscometogether.webpages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseWebActivity;
import com.yiwo.friendscometogether.custom.MyErWeiMaDialog;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.GoodShareModel;
import com.yiwo.friendscometogether.newpage.FaBu_XiuGaiShangPinActivity;
import com.yiwo.friendscometogether.newpage.PeiSongSettingActivity;
import com.yiwo.friendscometogether.newpage.renzheng.RenZheng0_BeginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanLiGoodsWebActivity extends BaseWebActivity {

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
    @BindView(R.id.refresh_layout)
    RefreshLayout refresh_layout;
    Dialog dialog;
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_goods_web);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        initWebView(webView,url);
        initRefresh();
        spImp = new SpImp(this);
        webView.addJavascriptInterface(new AndroidInterface(), "android");
    }

    private void initRefresh() {
        refresh_layout.setEnableLoadMore(false);
        refresh_layout.setRefreshHeader(new ClassicsHeader(GuanLiGoodsWebActivity.this));
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                webView.reload();
                refreshLayout.finishRefresh(500);
            }
        });

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
                share();
                break;
            case R.id.rl_fabu:
                if (spImp.getIfSign().equals("1")){
                    Intent intent = new Intent();
                    intent.setClass(GuanLiGoodsWebActivity.this, FaBu_XiuGaiShangPinActivity.class);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GuanLiGoodsWebActivity.this);
                    builder.setMessage("您还没有认证并绑定微信商户号")
                            .setNegativeButton("去认证", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RenZheng0_BeginActivity.openActivity(GuanLiGoodsWebActivity.this);
                                }
                            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
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
        @JavascriptInterface
        public void goodsweb(String gid,String shopUid){
            ShopGoodsDetailsWebLocalActivity.open(GuanLiGoodsWebActivity.this,gid,shopUid);
        }
        @JavascriptInterface
        public void totalkfzd(String wy_id){
            Log.d("asdasdas",wy_id);
            liaotian(wy_id);
        }
    }

    private void liaotian(String wy_id) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(GuanLiGoodsWebActivity.this, wy_id);
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
                share();
                popupWindow.dismiss();
            }
        });
        LinearLayout ll_add_goods = view.findViewById(R.id.ll_add_goods);
        ll_add_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spImp.getIfSign().equals("1")){
                    Intent intent = new Intent();
                    intent.setClass(GuanLiGoodsWebActivity.this, FaBu_XiuGaiShangPinActivity.class);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GuanLiGoodsWebActivity.this);
                    builder.setMessage("您还没有认证并绑定微信商户号")
                            .setNegativeButton("去认证", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    RenZheng0_BeginActivity.openActivity(GuanLiGoodsWebActivity.this);
                                }
                            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
                popupWindow.dismiss();
            };
        });
        LinearLayout ll_er_wei_ma = view.findViewById(R.id.ll_er_wei_ma);
        ll_er_wei_ma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyErWeiMaDialog dialog = new MyErWeiMaDialog(GuanLiGoodsWebActivity.this, NetConfig.WX_Shop_Home_Url+spImp.getUID());
                dialog.show();
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

    /**
     *
     */
//    new ShareAction(DetailsOfFriendTogetherWebLocalActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
//                                        .setShareboardclickCallback(new ShareBoardlistener() {
//        @Override
//        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//            ShareUtils.shareWeb(DetailsOfFriendTogetherWebLocalActivity.this, shareModel.getObj().getUrl()+"&uid="+spImp.getUID(), shareModel.getObj().getTitle(),
//                    shareModel.getObj().getDesc(), shareModel.getObj().getImages(), share_media);
//        }
//    }).open();
    private void share() {
//        dialog = WeiboDialogUtils.createLoadingDialog(this,"加载中...");
        ViseHttp.POST(NetConfig.shareMyShop)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.shareMyShop))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                final GoodShareModel shareModel = gson.fromJson(data, GoodShareModel.class);
                                new ShareAction(GuanLiGoodsWebActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                        .setShareboardclickCallback(new ShareBoardlistener() {
                                            @Override
                                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                                ShareUtils.shareWeb(GuanLiGoodsWebActivity.this, shareModel.getObj().getShareUrl(), shareModel.getObj().getUsername(),
                                                        shareModel.getObj().getUserautograph(), shareModel.getObj().getUserpic(), share_media);
                                            }
                                        }).open();
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
