package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_DuiZhangPuZi_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_JianTuShiKe_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_JingCaiLuXian_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_ReMenDuiZhang_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_YouJiShiPin_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeYouPu_Adapter;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianModel;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianYouJiShiPinModel;
import com.yiwo.friendscometogether.newmodel.HomeYouPuModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.RoomInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.LiveRoomActivity;
import com.yiwo.friendscometogether.widget.FullyLinearLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeShopGoodsFragment extends BaseFragment {

    View rootView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private View view;
    private Unbinder unbinder;
    private SpImp spImp;
    private int page4 = 1;


    //友铺
    RecyclerView rv_youpu;//小视频
    private HomeYouPu_Adapter youPuAdapter;//视频列表适配器
    private List<HomeYouPuModel.ObjBean> listYouPu = new ArrayList<>();

    public static HomeShopGoodsFragment newInstance(){//status  不传或传100 全部     传1待处理  2已处理   3已完成   4退款
        Bundle args = new Bundle();
        HomeShopGoodsFragment fragment = new HomeShopGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_lay_youpu, null);
        unbinder = ButterKnife.bind(this, rootView);
        spImp = new SpImp(getContext());
        initView(rootView);
        initData();
        return rootView;
    }

    private void initData() {
        //友铺
        ViseHttp.POST(NetConfig.homeGoodsList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGoodsList))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HomeYouPuModel model = gson.fromJson(data, HomeYouPuModel.class);
                                page4 = 2;
                                listYouPu = model.getObj();
                                // /设置布局管理器为2列，纵向
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return true;
                                    }
                                };
                                rv_youpu.setLayoutManager(mLayoutManager);
                                youPuAdapter = new HomeYouPu_Adapter(listYouPu);
                                rv_youpu.setAdapter(youPuAdapter);
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

    private void  initView(View view2){
        rv_youpu = view2.findViewById(R.id.rv_youpu);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.homeGoodsList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGoodsList))
                        .addParam("uid", spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeYouPuModel model = gson.fromJson(data, HomeYouPuModel.class);
                                        page4 = 2;
                                        listYouPu.clear();
                                        listYouPu.addAll(model.getObj());
                                        youPuAdapter.notifyDataSetChanged();
//                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
//                                WeiboDialogUtils.closeDialog(dialog_loading);
                            }
                        });
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.homeGoodsList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGoodsList))
                        .addParam("uid", spImp.getUID())
                        .addParam("page",page4+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeYouPuModel model = gson.fromJson(data, HomeYouPuModel.class);
                                        if (model.getObj().size()>0){
                                            listYouPu.addAll(model.getObj());
                                            youPuAdapter.notifyDataSetChanged();
                                            page4++;
                                        }
                                        refreshLayout.finishLoadMore(1000);
                                    }

                                } catch (JSONException e) {
                                    refreshLayout.finishLoadMore(1000);
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishLoadMore(1000);
                                toToast(getContext(),"加载失败");
                            }
                        });
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
