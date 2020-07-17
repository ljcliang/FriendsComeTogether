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
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_DuiZhangPuZi_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_JianTuShiKe_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_JingCaiLuXian_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_ReMenDuiZhang_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_YouJiShiPin_Adapter;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianModel;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianYouJiShiPinModel;
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


public class HomeTuiJianFragment1 extends BaseFragment {

    View rootView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private View view;
    private Unbinder unbinder;
    private SpImp spImp;
    private String cityName = "";
    private Dialog dialog_loading;
    public static final String CITY_NAME_KEY = "cityName";
    //轮播图
    private List<HomeTuiJianModel.ObjBean.BannerBean> listBanner = new ArrayList<>();//轮播图list
    private List<String> listBannerImages = new ArrayList<>();
    private XBanner banner;


    //推荐
    private RelativeLayout rlJianTuShiKe;
    RecyclerView rvTuJianShiKe;
    private HomeTuiJian_JianTuShiKe_Adapter jianTuShiKeAdapter;
    private List<HomeTuiJianModel.ObjBean.YouJiBean> listJianTuShiKe = new ArrayList<>();

    private RelativeLayout rlJingCaiLuXian;
    RecyclerView rvJingCaiLuXian;
    private HomeTuiJian_JingCaiLuXian_Adapter jingCaiLuXianAdapter;
    private List<HomeTuiJianModel.ObjBean.ActivityBean> listJingCaiLuXian = new ArrayList<>();

    private RelativeLayout rlRenMenDuiZHang;
    RecyclerView rvReMenDuiZhang;
    private HomeTuiJian_ReMenDuiZhang_Adapter reMenDuiZhangAdapter;
    private List<HomeTuiJianModel.ObjBean.CaptainBeanX> listReMenDuiZhang = new ArrayList<>();

    private RelativeLayout rlDuiZhangPuZi;
    RecyclerView rvDuizhangPuZi;
    private HomeTuiJian_DuiZhangPuZi_Adapter duiZhangPuZiAdapter;
    private List<HomeTuiJianModel.ObjBean.GoodsBean> listDuiZhangPuZi = new ArrayList<>();

    private RelativeLayout rlYouJiShiPin;
    RecyclerView rvYouJiShiPin;
    private HomeTuiJian_YouJiShiPin_Adapter youJiShiPinAdapter;
    private List<HomeTuiJianYouJiShiPinModel.ObjBean> listYouJiShiPin = new ArrayList<>();
    private int page1 = 1;
    private HomeTuiJianFragment1.PreLoadWebYouJiBroadcastReceiver preLoadWebYouJiBroadcastReceiver = new HomeTuiJianFragment1.PreLoadWebYouJiBroadcastReceiver();


    public static HomeTuiJianFragment1 newInstance(String cityName){//status  不传或传100 全部     传1待处理  2已处理   3已完成   4退款
        Bundle args = new Bundle();
        args.putString(CITY_NAME_KEY, cityName);
        HomeTuiJianFragment1 fragment = new HomeTuiJianFragment1();
        fragment.setArguments(args);
        return fragment;
    }
    public void setCity(String cityName){
        this.cityName = cityName;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_lay_tuijian, null);
        unbinder = ButterKnife.bind(this, rootView);
        ScreenAdapterTools.getInstance().loadView(rootView);
        spImp = new SpImp(getContext());
        cityName = getArguments().getString(CITY_NAME_KEY);
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("android.friendscometogether.HomeFragment.PreLoadWebYouJiBroadcastReceiver");
        getContext().registerReceiver(preLoadWebYouJiBroadcastReceiver,filter1);
        initView(rootView);
        initData();
        return rootView;
    }
    /**
     * 推荐友记视频
     */
    private void initTuiJianYouJiShiPin() {
        ViseHttp.POST(NetConfig.yj_video)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.yj_video))
                .addParam("uid", spImp.getUID())
                .addParam("city", cityName)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                HomeTuiJianYouJiShiPinModel model = gson.fromJson(data, HomeTuiJianYouJiShiPinModel.class);
                                listYouJiShiPin.clear();
                                listYouJiShiPin.addAll(model.getObj());
                                if (listYouJiShiPin.size()>0){
                                    rlYouJiShiPin.setVisibility(View.VISIBLE);
                                }else {
                                    rlYouJiShiPin.setVisibility(View.GONE);
                                }
                                youJiShiPinAdapter.notifyDataSetChanged();
                                page1 = 2;
                                //友记

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
    private void initData() {
        //推荐
        ViseHttp.POST(NetConfig.homeTuiJian)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeTuiJian))
                .addParam("uid", spImp.getUID())
                .addParam("city", cityName)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(final String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                HomeTuiJianModel model = gson.fromJson(data, HomeTuiJianModel.class);
                                //轮播图
                                listBanner.clear();
                                listBanner.addAll(model.getObj().getBanner());
                                listBannerImages.clear();
                                for (int i = 0 ;i<listBanner.size();i++){
                                    listBannerImages.add(listBanner.get(i).getPic());
                                }
                                initBanner(banner,listBannerImages);

                                //荐途时刻
                                listJianTuShiKe.clear();
                                listJianTuShiKe.addAll(model.getObj().getYouJi());
                                jianTuShiKeAdapter.notifyDataSetChanged();
                                if (listJianTuShiKe.size()>0){
                                    if (hasPermission()){
                                        preLoadYouJi_tuijain(listJianTuShiKe);
                                    }else {
                                        requestPermission();
                                    }
                                    rlJianTuShiKe.setVisibility(View.VISIBLE);
                                }else {
                                    rlJianTuShiKe.setVisibility(View.GONE);
                                }
                                //精彩路线
                                listJingCaiLuXian.clear();
                                listJingCaiLuXian.addAll(model.getObj().getActivity());
                                jingCaiLuXianAdapter.notifyDataSetChanged();
                                if (listJingCaiLuXian.size()>0){
                                    rlJingCaiLuXian.setVisibility(View.VISIBLE);
                                }else {
                                    rlJingCaiLuXian.setVisibility(View.GONE);
                                }
                                //热门队长
                                listReMenDuiZhang.clear();
                                listReMenDuiZhang.addAll(model.getObj().getCaptain());
                                reMenDuiZhangAdapter.notifyDataSetChanged();
                                if (listReMenDuiZhang.size()>0){
                                    rlRenMenDuiZHang.setVisibility(View.VISIBLE);
                                }else {
                                    rlRenMenDuiZHang.setVisibility(View.GONE);
                                }
                                //队长铺子
                                listDuiZhangPuZi.clear();
                                listDuiZhangPuZi.addAll(model.getObj().getGoods());
                                duiZhangPuZiAdapter.notifyDataSetChanged();
                                if (listDuiZhangPuZi.size()>0){
                                    rlDuiZhangPuZi.setVisibility(View.VISIBLE);
                                }else {
                                    rlDuiZhangPuZi.setVisibility(View.GONE);
                                }
//                                //悬浮球
//                                if (model.getObj().getStatus2().equals("1")){
//                                    rl_ball.setVisibility(View.VISIBLE);
//                                }else {
//                                    rl_ball.setVisibility(View.GONE);
//                                }
                            }
                            /**
                             * 推荐友记视频
                             */
                            initTuiJianYouJiShiPin();
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
        //轮播图
        banner = view2.findViewById(R.id.fragment_home_banner);
        HomeTuiJianModel.ObjBean.BannerBean bannerBean = new HomeTuiJianModel.ObjBean.BannerBean();
        listBanner.add(bannerBean);
        listBanner.add(bannerBean);
        listBanner.add(bannerBean);
        initBanner(banner,listBannerImages);
        //途荐时刻
        rlJianTuShiKe = view2.findViewById(R.id.rl_jiantushike);
        rvTuJianShiKe = view2.findViewById(R.id.rv_tuijianshike);
        LinearLayoutManager managerTuiJianShiKe = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvTuJianShiKe.setLayoutManager(managerTuiJianShiKe);
        HomeTuiJianModel.ObjBean.YouJiBean bean = new HomeTuiJianModel.ObjBean.YouJiBean();
        listJianTuShiKe.add(bean);
        listJianTuShiKe.add(bean);
        jianTuShiKeAdapter =  new HomeTuiJian_JianTuShiKe_Adapter(listJianTuShiKe);
        rvTuJianShiKe.setAdapter(jianTuShiKeAdapter);


        //精彩路线
        rlJingCaiLuXian = view2.findViewById(R.id.rl_jingcailuxian);
        rvJingCaiLuXian = view2.findViewById(R.id.rv_jingcailuxian);
        LinearLayoutManager mLayoutManagerJingCaiLuXian = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return true;
            }
        };
        mLayoutManagerJingCaiLuXian.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvJingCaiLuXian.setLayoutManager(mLayoutManagerJingCaiLuXian);
        jingCaiLuXianAdapter = new HomeTuiJian_JingCaiLuXian_Adapter(listJingCaiLuXian);
        jingCaiLuXianAdapter.setListener(new HomeTuiJian_JingCaiLuXian_Adapter.LiveListAdapterListener() {
            @Override
            public void onCLickListen(int pos) {
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    enterLiveRoom(listJingCaiLuXian.get(pos).getCaptain());
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onGuanZhuListen(int pos) {
                guanZhuLivePerson(pos);
            }
        });
        rvJingCaiLuXian.setAdapter(jingCaiLuXianAdapter);


        //热门队长
        rlRenMenDuiZHang = view2.findViewById(R.id.rl_renmenduizhang);
        rvReMenDuiZhang = view2.findViewById(R.id.rv_remenduizhang);
        StaggeredGridLayoutManager mLayoutManagerReMenDuiZhang = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvReMenDuiZhang.setLayoutManager(mLayoutManagerReMenDuiZhang);
        reMenDuiZhangAdapter = new HomeTuiJian_ReMenDuiZhang_Adapter(listReMenDuiZhang);
        rvReMenDuiZhang.setAdapter(reMenDuiZhangAdapter);

        //队长铺子
        rlDuiZhangPuZi = view2.findViewById(R.id.rl_duizhangpuzi);
        rvDuizhangPuZi = view2.findViewById(R.id.rv_duizhangpuzi);
        FullyLinearLayoutManager managerDuiZhangPuZi = new FullyLinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        managerDuiZhangPuZi.setOrientation(LinearLayoutManager.VERTICAL);
        rvDuizhangPuZi.setLayoutManager(managerDuiZhangPuZi);
        duiZhangPuZiAdapter =  new HomeTuiJian_DuiZhangPuZi_Adapter(listDuiZhangPuZi);
        rvDuizhangPuZi.setAdapter(duiZhangPuZiAdapter);

        //友记文章视频
        rlYouJiShiPin = view2.findViewById(R.id.rl_youji_shipin);
        rvYouJiShiPin = view2.findViewById(R.id.rv_youjishipin);
        LinearLayoutManager managerYouJiShiPin = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        youJiShiPinAdapter = new HomeTuiJian_YouJiShiPin_Adapter(listYouJiShiPin);
        rvYouJiShiPin.setLayoutManager(managerYouJiShiPin);
        rvYouJiShiPin.setAdapter(youJiShiPinAdapter);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {

                ViseHttp.POST(NetConfig.homeTuiJian)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeTuiJian))
                        .addParam("uid", spImp.getUID())
                        .addParam("city", cityName)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeTuiJianModel model = gson.fromJson(data, HomeTuiJianModel.class);

                                        //轮播图
                                        listBanner.clear();
                                        listBanner.addAll(model.getObj().getBanner());
                                        listBannerImages.clear();
                                        for (int i = 0 ;i<listBanner.size();i++){
                                            listBannerImages.add(listBanner.get(i).getPic());
                                        }
                                        initBanner(banner,listBannerImages);

                                        //荐途时刻
                                        listJianTuShiKe.clear();
                                        listJianTuShiKe.addAll(model.getObj().getYouJi());
                                        jianTuShiKeAdapter.notifyDataSetChanged();
                                        if (listJianTuShiKe.size()>0){
                                            if (hasPermission()){
                                                preLoadYouJi_tuijain(listJianTuShiKe);
                                            }else {
                                                requestPermission();
                                            }
                                            rlJianTuShiKe.setVisibility(View.VISIBLE);
                                        }else {
                                            rlJianTuShiKe.setVisibility(View.GONE);
                                        }
                                        //精彩路线
                                        listJingCaiLuXian.clear();
                                        listJingCaiLuXian.addAll(model.getObj().getActivity());
                                        jingCaiLuXianAdapter.notifyDataSetChanged();
                                        if (listJingCaiLuXian.size()>0){
                                            rlJingCaiLuXian.setVisibility(View.VISIBLE);
                                        }else {
                                            rlJingCaiLuXian.setVisibility(View.GONE);
                                        }
                                        //热门队长
                                        listReMenDuiZhang.clear();
                                        listReMenDuiZhang.addAll(model.getObj().getCaptain());
                                        reMenDuiZhangAdapter.notifyDataSetChanged();
                                        if (listReMenDuiZhang.size()>0){
                                            rlRenMenDuiZHang.setVisibility(View.VISIBLE);
                                        }else {
                                            rlRenMenDuiZHang.setVisibility(View.GONE);
                                        }
                                        //队长铺子
                                        listDuiZhangPuZi.clear();
                                        listDuiZhangPuZi.addAll(model.getObj().getGoods());
                                        duiZhangPuZiAdapter.notifyDataSetChanged();
                                        if (listDuiZhangPuZi.size()>0){
                                            rlDuiZhangPuZi.setVisibility(View.VISIBLE);
                                        }else {
                                            rlDuiZhangPuZi.setVisibility(View.GONE);
                                        }
//                                        //轮播图
//                                        initTuiJianFirstHuoDong(model.getObj().getBannerList());
//                                        if (model.getObj().getActivity().size()>0){
//                                            rlTuiJianHuodongFirst.setVisibility(View.VISIBLE);
//                                        }else {
//                                            rlTuiJianHuodongFirst.setVisibility(View.GONE);
//                                        }
                                        page1 = 2;
                                    }
                                    WeiboDialogUtils.closeDialog(dialog_loading);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                toToast(getContext(),"加载失败:"+"errCode:"+errCode+"///"+errMsg);
//                                WeiboDialogUtils.closeDialog(dialog_loading);
                            }
                        });
                ViseHttp.POST(NetConfig.yj_video)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.yj_video))
                        .addParam("uid", spImp.getUID())
                        .addParam("city", cityName)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        HomeTuiJianYouJiShiPinModel model = gson.fromJson(data, HomeTuiJianYouJiShiPinModel.class);
                                        listYouJiShiPin.clear();
                                        listYouJiShiPin.addAll(model.getObj());
                                        if (listYouJiShiPin.size()>0){
                                            rlYouJiShiPin.setVisibility(View.VISIBLE);
                                        }else {
                                            rlYouJiShiPin.setVisibility(View.GONE);
                                        }
                                        youJiShiPinAdapter.notifyDataSetChanged();
                                        page1 = 2;
                                        //友记

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                ViseHttp.POST(NetConfig.yj_video)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.yj_video))
                        .addParam("uid", spImp.getUID())
                        .addParam("city", cityName)
                        .addParam("page",page1+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        HomeTuiJianYouJiShiPinModel model = gson.fromJson(data, HomeTuiJianYouJiShiPinModel.class);
//                                        if (listYouJiShiPin.size()>50){
//                                            listYouJiShiPin.removeAll(listYouJiShiPin.subList(0,model.getObj().size()-1));
//                                        }
                                        listYouJiShiPin.addAll(model.getObj());
                                        youJiShiPinAdapter.notifyDataSetChanged();
                                        if (model.getObj().size()>0){
                                            listYouJiShiPin.addAll(model.getObj());
                                            preLoadYouJi_tuijain_list(model.getObj());

                                            if (listYouJiShiPin!=null && youJiShiPinAdapter!=null){
                                                youJiShiPinAdapter.notifyDataSetChanged();
                                            }
//                                                    if (listYouJiShiPin.size()>50){
//                                                        listYouJiShiPin.removeAll(listYouJiShiPin.subList(0,model.getObj().size()-1));
//                                                    }
                                            page1++;
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
    public void initBanner(XBanner banner, final List<String> images) {
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                toToast(getContext(),""+position);
            }
        });
        banner.setBannerData(R.layout.lay_banner_img,listBanner);
        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ImageView imageView = (ImageView) view.findViewById(R.id.iv);
                imageView.setTag(null);
                Glide.with(getContext()).load(listBanner.get(position).getPic()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(imageView);
            }
        });
    }
    private void preLoadYouJi_tuijain_list(List<HomeTuiJianYouJiShiPinModel.ObjBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
            String url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+list.get(i).getFmID()+"&uid="+spImp.getUID();
            SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
            sessionConfigBuilder.setSupportLocalServer(true);
            HashMap mapRp = new HashMap();
            String str_vlue = "http://www.91yiwo.com/ylyy/include/activity_web/js/jquery-3.3.1.min.js;"
                    +"http://www.91yiwo.com/ylyy/include/activity_web/css/web_main.css;"
                    +"http://www.91yiwo.com/ylyy/include/activity_web/js/builder.js;";
            mapRp.put("sonic-link",str_vlue);
            sessionConfigBuilder.setCustomResponseHeaders(mapRp);
            boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfigBuilder.build());
            Log.d("preloadpreloadp",preloadSuccess+""+url);
        }
    }
    //     直播列表关注
    private void guanZhuLivePerson(final int position){
        Log.d("adasds",position+"");
        if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
            if (listJingCaiLuXian.get(position).getCaptain().getFollow().equals("0")){//未关注
                ViseHttp.POST(NetConfig.userFocusUrl)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                        .addParam("uid", spImp.getUID())
                        .addParam("likeId", listJingCaiLuXian.get(position).getCaptain().getUid())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.d("adasds",result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getInt("code") == 200) {
                                        listJingCaiLuXian.get(position).getCaptain().setFollow("1");
                                        jingCaiLuXianAdapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
                                    }else if(jsonObject.getInt("code") == 400){

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.d("adasds",errMsg);
                            }
                        });
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(getContext(), LoginActivity.class);
            startActivity(intent);
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
        getContext().unregisterReceiver(preLoadWebYouJiBroadcastReceiver);
        SonicEngine.getInstance().cleanCache();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d("读写内存权限,","permissionsSize:"+permissions.length+"///"+"grantResultsSize:"+grantResults.length);
        for (int i = 0;i<permissions.length;i++){
            if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    preLoadYouJi_tuijain(listJianTuShiKe);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1001;
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
        }
    }
    private void preLoadYouJi_tuijain(List<HomeTuiJianModel.ObjBean.YouJiBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
            String url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+list.get(i).getFmID()+"&uid="+spImp.getUID();
            SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
            sessionConfigBuilder.setSupportLocalServer(true);
            HashMap mapRp = new HashMap();
            String str_vlue = "http://www.91yiwo.com/ylyy/include/activity_web/js/jquery-3.3.1.min.js;"
                    +"http://www.91yiwo.com/ylyy/include/activity_web/css/web_main.css;"
                    +"http://www.91yiwo.com/ylyy/include/activity_web/js/builder.js;";
            mapRp.put("sonic-link",str_vlue);
            sessionConfigBuilder.setCustomResponseHeaders(mapRp);
            boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfigBuilder.build());
            Log.d("preloadpreloadp",preloadSuccess+""+url);
        }
    }
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    private class PreLoadWebYouJiBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (hasPermission()){
                preLoadYouJi_tuijain(listJianTuShiKe);
            }else {
                requestPermission();
            }
        }
    }
    private void enterLiveRoom(final HomeTuiJianModel.ObjBean.ActivityBean.CaptainBean zhiboBean) {
        dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(),"进入房间中");
        Log.d("asdasdas","UID:"+zhiboBean.getUid()+"///"+zhiboBean.getChannel_id());
        if (zhiboBean.getChannel_id() == null || TextUtils.isEmpty(zhiboBean.getChannel_id())){
            Intent intent = new Intent();
            intent.setClass(getContext(), PersonMainActivity1.class);
            intent.putExtra("person_id", zhiboBean.getUid());
            startActivity(intent);
            WeiboDialogUtils.closeDialog(dialog_loading);
        }else {
            ViseHttp.POST(NetConfig.zhiBoInfo)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.zhiBoInfo))
                    .addParam("uid", zhiboBean.getUid())
                    .addParam("cid",zhiboBean.getChannel_id())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    String liveStatus = jsonObject.getJSONObject("obj").getString("zhibostatus");
                                    String start_time = jsonObject.getJSONObject("obj").getString("start_time");
//                                if (true){
                                    if (liveStatus.equals("1")){
                                        RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
                                        roomInfoEntity.setCid(zhiboBean.getChannel_id());
                                        roomInfoEntity.setOwner(zhiboBean.getUsername());
                                        roomInfoEntity.setHlsPullUrl(zhiboBean.getHlsPullUrl());
                                        roomInfoEntity.setHttpPullUrl(zhiboBean.getHttpPullUrl());
                                        roomInfoEntity.setRtmpPullUrl(zhiboBean.getRtmpPullUrl());
                                        roomInfoEntity.setPushUrl(zhiboBean.getPushUrl());
                                        roomInfoEntity.setRoomid(Integer.parseInt(zhiboBean.getRoom_id()));
                                        DemoCache.setRoomInfoEntity(roomInfoEntity);
                                        LiveRoomActivity.startAudience(getContext(), zhiboBean.getRoom_id() + "", zhiboBean.getRtmpPullUrl(), true);
                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                    }else {
                                        Intent intent = new Intent();
                                        intent.setClass(getContext(), PersonMainActivity1.class);
                                        intent.putExtra("is_by_live",true);
                                        intent.putExtra("next_on_live_time",start_time);
                                        intent.putExtra("person_id", zhiboBean.getUid());
                                        startActivity(intent);
                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                WeiboDialogUtils.closeDialog(dialog_loading);
                            }

                        }


                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(dialog_loading);
                            toToast(getContext(),"进入房间失败！");
                        }
                    });
        }
    }
}
