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
import com.yiwo.friendscometogether.dbmodel.GoodsWebInfoDbModel;
import com.yiwo.friendscometogether.dbmodel.WebInfoOfDbUntils;
import com.yiwo.friendscometogether.dbmodel.YouJiWebInfoDbModel;
import com.yiwo.friendscometogether.dbmodel.YouJuHuoDongWebInfoDbModel;
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
import com.yiwo.friendscometogether.newmodel.LocalWebInfoModel;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private WebInfoOfDbUntils webInfoOfDbUntils;

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
        webInfoOfDbUntils = new WebInfoOfDbUntils(getContext());
        initView(rootView);
        initData();
        return rootView;
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    public void preGoods(List<HomeYouPuModel.ObjBean> list){

        for (HomeYouPuModel.ObjBean bean : list){
            if (!webInfoOfDbUntils.hasThisId_Goods(bean.getGoodsID())){
                insertWebList("2",bean.getGoodsID());
            }
        }

    }
    //创建及执行
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    private void insertWebList(String type,String fmId){
        InsertWeb2DbRunnable insertWeb2DbRunnable = new InsertWeb2DbRunnable(type,fmId);
        fixedThreadPool.execute(insertWeb2DbRunnable);
    }

    /**
     * 请求接口  获取webInFo的线程
     */
    int iii =  0;
    public class InsertWeb2DbRunnable implements Runnable {

        private String type,fId;


        /**
         *
         * @param type web  类型  0是友记，1是友聚活动，2是商品
         * @param f_id 友记、活动、商品的ID
         */
        public InsertWeb2DbRunnable(String type,String f_id){
            this.type = type;
            this.fId = f_id;
        }
        @Override
        public void run() {
            ViseHttp.POST(NetConfig.articleInfo)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleInfo))
                    .addParam("uid", spImp.getUID())
                    .addParam("fmID",fId)
                    .addParam("type",type)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    Log.d("qingqiushuju:",new Date().toLocaleString()+"diaa::"+iii );
                                    iii++;
                                    Gson gson = new Gson();
                                    LocalWebInfoModel mode =  gson.fromJson(data,LocalWebInfoModel.class);
                                    switch (type){
                                        case "0":
                                            YouJiWebInfoDbModel youJiWebInfoDbModel = new YouJiWebInfoDbModel();
                                            youJiWebInfoDbModel.setWeb_info(mode.getObj().getStr());
                                            youJiWebInfoDbModel.setFm_id(fId);
                                            webInfoOfDbUntils.insertYouJiModel(youJiWebInfoDbModel);
                                            break;
                                        case "1":
                                            YouJuHuoDongWebInfoDbModel youJuWebInfoDbModel = new YouJuHuoDongWebInfoDbModel();
                                            youJuWebInfoDbModel.setWeb_info(mode.getObj().getStr());
                                            youJuWebInfoDbModel.setPf_id(fId);
                                            webInfoOfDbUntils.insertYouJuHuoDongModel(youJuWebInfoDbModel);
                                            break;
                                        case "2":
                                            GoodsWebInfoDbModel goodsWebInfoDbModel = new GoodsWebInfoDbModel();
                                            goodsWebInfoDbModel.setWeb_info(mode.getObj().getStr());
                                            goodsWebInfoDbModel.setGood_id(fId);
                                            webInfoOfDbUntils.insertGoodModel(goodsWebInfoDbModel);
                                            break;
                                    }
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
                                listYouPu.clear();
                                listYouPu.addAll(model.getObj());
                                youPuAdapter.notifyDataSetChanged();
                                preGoods(listYouPu);
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
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                initData();
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
                                            preGoods(model.getObj());
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
