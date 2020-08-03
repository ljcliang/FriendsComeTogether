package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.dbmodel.GoodsWebInfoDbModel;
import com.yiwo.friendscometogether.dbmodel.WebInfoOfDbUntils;
import com.yiwo.friendscometogether.dbmodel.YouJiWebInfoDbModel;
import com.yiwo.friendscometogether.dbmodel.YouJuHuoDongWebInfoDbModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeGuanZhu_DuiZhangDaiDui_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeGuanZhu_YouJiShiPin_Adapter;
import com.yiwo.friendscometogether.newadapter.Home_GuanZhu_YouJiShiPin_Adapter;
import com.yiwo.friendscometogether.newmodel.HomeGuanZhuModel;
import com.yiwo.friendscometogether.newmodel.LocalWebInfoModel;
import com.yiwo.friendscometogether.newpage.GuanZhuDuiZhangListActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ExStaggeredGridLayoutManager;

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
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeGuanZhuFragment extends BaseFragment {

    View rootView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_duizhangdaiduizhong)
    RelativeLayout rl_duizhangdaiduizhong;
    private View view;
    private Unbinder unbinder;
    private SpImp spImp;
    private int page2 = 1;
    private WebInfoOfDbUntils webInfoOfDbUntils;
    //关注页面
    RecyclerView rvGuanZhuYoujishipin;//关注
    private Home_GuanZhu_YouJiShiPin_Adapter adapterGuanzhuYouJi;//
    private List<HomeGuanZhuModel.ObjBean.YjVideoBean> mListGuanzhu = new ArrayList<>();//

    RecyclerView rvGuanZhuDuiZhangDaiDui;
    RefreshLayout refreshHorizontal;
    private HomeGuanZhu_DuiZhangDaiDui_Adapter adapterGuanZhuDuiZhangDaiDui;
    private List<HomeGuanZhuModel.ObjBean.CaptainPfBean> mlistDuiZhangDaiDui = new ArrayList<>();

    public static HomeGuanZhuFragment newInstance(){//status  不传或传100 全部     传1待处理  2已处理   3已完成   4退款
        Bundle args = new Bundle();
        HomeGuanZhuFragment fragment = new HomeGuanZhuFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_lay_guanzhu, null);
        unbinder = ButterKnife.bind(this, rootView);
        spImp = new SpImp(getContext());
        webInfoOfDbUntils = new WebInfoOfDbUntils(getContext());
        initView(rootView);
        initData();
        return rootView;
    }
    @OnClick({R.id.rl_duizhangdaiduizhong})
    public  void onClick(View view){
        switch (view.getId()){
            case R.id.rl_duizhangdaiduizhong:
                Intent intent = new Intent();
                intent.setClass(getContext(), GuanZhuDuiZhangListActivity.class);
                startActivity(intent);
                break;
        }
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

    private void initData() {
        //关注
        ViseHttp.POST(NetConfig.homePageGz)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homePageGz))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HomeGuanZhuModel model = gson.fromJson(data, HomeGuanZhuModel.class);
                                page2 = 2;
                                mListGuanzhu.clear();
                                mListGuanzhu.addAll(model.getObj().getYj_video());
                                if (mListGuanzhu.size()>0){
                                    preGuanZhuYouJi(mListGuanzhu);
                                }
                                adapterGuanzhuYouJi.notifyDataSetChanged();
                                mlistDuiZhangDaiDui.clear();
                                mlistDuiZhangDaiDui.addAll(model.getObj().getCaptainPf());
                                preGuanZhuYouJu(mlistDuiZhangDaiDui);
                                adapterGuanZhuDuiZhangDaiDui.notifyDataSetChanged();
                            }else {

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

        //关注 友记视频
        rvGuanZhuYoujishipin = view2.findViewById(R.id.rv_guanzhu_youjishipin);
        adapterGuanzhuYouJi= new Home_GuanZhu_YouJiShiPin_Adapter(mListGuanzhu);
//        LinearLayoutManager managerGuanZhuYouJi = new LinearLayoutManager(getContext()){
//            @Override
//            public boolean canScrollVertically() {
//                return true;
//            }
//        };
        ExStaggeredGridLayoutManager managerGuanZhuYouJi = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        rvGuanZhuYoujishipin.setLayoutManager(managerGuanZhuYouJi);
        rvGuanZhuYoujishipin.setAdapter(adapterGuanzhuYouJi);

        //关注 队长带队
        rvGuanZhuDuiZhangDaiDui = view2.findViewById(R.id.rv_duizhangdaidui);
        adapterGuanZhuDuiZhangDaiDui = new HomeGuanZhu_DuiZhangDaiDui_Adapter(mlistDuiZhangDaiDui);
        LinearLayoutManager managerGuanZhuDuiZhangDaiDui = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        managerGuanZhuDuiZhangDaiDui.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvGuanZhuDuiZhangDaiDui.setLayoutManager(managerGuanZhuDuiZhangDaiDui);
        rvGuanZhuDuiZhangDaiDui.setAdapter(adapterGuanZhuDuiZhangDaiDui);

        refreshHorizontal = view2.findViewById(R.id.refresh_horizontal);
        refreshHorizontal.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshHorizontal.setEnableRefresh(false);

        refreshHorizontal.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Intent intent = new Intent();
                intent.setClass(getContext(), GuanZhuDuiZhangListActivity.class);
                startActivity(intent);
            }
        });
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.homePageGz)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homePageGz))
                        .addParam("uid", spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeGuanZhuModel model = gson.fromJson(data, HomeGuanZhuModel.class);
                                        page2 = 2;
                                        mListGuanzhu.clear();
                                        mListGuanzhu.addAll( model.getObj().getYj_video());
                                        preGuanZhuYouJi(mListGuanzhu);
                                        adapterGuanzhuYouJi.notifyDataSetChanged();
//                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                        mlistDuiZhangDaiDui.clear();
                                        mlistDuiZhangDaiDui.addAll(model.getObj().getCaptainPf());
                                        preGuanZhuYouJu(mlistDuiZhangDaiDui);
                                        adapterGuanZhuDuiZhangDaiDui.notifyDataSetChanged();
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
                ViseHttp.POST(NetConfig.homePageGz)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homePageGz))
                        .addParam("uid", spImp.getUID())
                        .addParam("page",page2+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeGuanZhuModel model = gson.fromJson(data, HomeGuanZhuModel.class);
                                        if (model.getObj().getYj_video().size()>0){
                                            mListGuanzhu.addAll(model.getObj().getYj_video());
                                            preGuanZhuYouJi(model.getObj().getYj_video());
                                            adapterGuanzhuYouJi.notifyDataSetChanged();
                                            page2++;
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

    private void preGuanZhuYouJi(List<HomeGuanZhuModel.ObjBean.YjVideoBean> list){
        for (HomeGuanZhuModel.ObjBean.YjVideoBean bean : list){
            if(!webInfoOfDbUntils.hasThisId_YouJi(bean.getFmID())){
                insertWebList("0",bean.getFmID());
            }
        }
    }
    private void preGuanZhuYouJu(List<HomeGuanZhuModel.ObjBean.CaptainPfBean> list){
        for (HomeGuanZhuModel.ObjBean.CaptainPfBean bean : list){
            if(!webInfoOfDbUntils.hasThisId_HuoDong(bean.getPfID())){
                insertWebList("1",bean.getPfID());
            }
        }
    }
    //创建及执行
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
    private void insertWebList(String type,String fmId){
        InsertWeb2DbRunnable insertWeb2DbRunnable = new InsertWeb2DbRunnable(type,fmId);
        fixedThreadPool.execute(insertWeb2DbRunnable);
    }
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
                                    Log.d("qingqiushuju:",new Date().toLocaleString()+"diaa::"+fId );
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
