package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.model.BaiduCityModel;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeGuanZhu_DuiZhangDaiDui_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeGuanZhu_YouJiShiPin_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_DuiZhangPuZi_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_JianTuShiKe_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_JingCaiLuXian_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_ReMenDuiZhang_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeTuiJian_YouJiShiPin_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeYouPu_Adapter;
import com.yiwo.friendscometogether.newadapter.Home_YouJiShiPin_0407_Adapter;
import com.yiwo.friendscometogether.newmodel.HomeGuanZhuModel;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianModel;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianYouJiShiPinModel;
import com.yiwo.friendscometogether.newmodel.HomeYouPuModel;
import com.yiwo.friendscometogether.newmodel.Home_youjiShiPin_0407_model;
import com.yiwo.friendscometogether.newpage.GuanZhuDuiZhangListActivity;
import com.yiwo.friendscometogether.newpage.HomeSearchActivity;
import com.yiwo.friendscometogether.newpage.MessageActivity;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.AndTools;
import com.yiwo.friendscometogether.utils.AppUpdateUtil;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.utils.UserUtils;
import com.yiwo.friendscometogether.vas_sonic.TBSonicRuntime;
import com.yiwo.friendscometogether.wangyiyunshipin.DemoCache;
import com.yiwo.friendscometogether.wangyiyunshipin.server.entity.RoomInfoEntity;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.LiveRoomActivity;
import com.yiwo.friendscometogether.webpages.MyJiFenActivity;
import com.yiwo.friendscometogether.widget.FullyLinearLayoutManager;
import com.yiwo.friendscometogether.widget.ScrollListenScrollView;
import com.yiwo.friendscometogether.widget.ViewPagerForScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/4.
 */

public class HomeFragment4 extends BaseFragment {

    View rootView;

    @BindView(R.id.home_refreshlayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.vp)
    ViewPagerForScrollView viewPager;
    @BindView(R.id.tv_city_name)
    TextView cityTv;

    @BindView(R.id.tv_rl1)
    TextView tvRl1;
    @BindView(R.id.tv_rl2)
    TextView tvRl2;
    @BindView(R.id.tv_rl3)
    TextView tvRl3;
    @BindView(R.id.v1)
    ImageView v1;
    @BindView(R.id.v2)
    ImageView v2;
    @BindView(R.id.v3)
    ImageView v3;

    @BindView(R.id.tv_weiduxiaoxi)
    TextView tvWeiduxiaoxi;

    @BindView(R.id.rl_ball)
    RelativeLayout rl_ball;
    @BindView(R.id.rl_xiaoxi_num)
    RelativeLayout rl_xiaoxi_num;
//    @BindView(R.id.rl_top)
//    RelativeLayout rl_top;
    //轮播图
    private List<HomeTuiJianModel.ObjBean.BannerBean> listBanner = new ArrayList<>();//轮播图list
    private List<String> listBannerImages = new ArrayList<>();
    private XBanner banner;
    //直播列表
    @BindView(R.id.scroll_view)
    ScrollListenScrollView scrollView;
    private int scollYTuiJian = 0 ;
    private int scollGuanZhu = 0 ;
    private int scollYouJi = 0 ;
    private int scollShiPin = 0 ;

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1001;

    private LocationManager locationManager;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String latLongString = "";

    private SpImp spImp;
    private String uid = "";
    private Dialog dialog_loading;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getCity();
                    break;
            }
        }

    };

    //关注页面
    RecyclerView rvGuanZhuYoujishipin;//关注
    private HomeGuanZhu_YouJiShiPin_Adapter adapterGuanzhuYouJi;//
    private List<HomeGuanZhuModel.ObjBean.YjVideoBean> mListGuanzhu = new ArrayList<>();//

    RecyclerView rvGuanZhuDuiZhangDaiDui;
    RefreshLayout refreshHorizontal;
    private HomeGuanZhu_DuiZhangDaiDui_Adapter adapterGuanZhuDuiZhangDaiDui;
    private List<HomeGuanZhuModel.ObjBean.CaptainPfBean> mlistDuiZhangDaiDui = new ArrayList<>();

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



    //友铺
    RecyclerView rv_youpu;//小视频
    private HomeYouPu_Adapter youPuAdapter;//视频列表适配器
    private List<HomeYouPuModel.ObjBean> listYouPu = new ArrayList<>();

    //友记
    RecyclerView rv_youji;//友记
    private Home_YouJiShiPin_0407_Adapter adapterYouji;//友记列表适配器
    private List<Home_youjiShiPin_0407_model.ObjBean> mListYouJi = new ArrayList<>();//友记列表list

    private int page1 = 1;
    private int page2 = 1;
    private int page3 = 1;
    private int page4 = 1;

    private String cityId = "";
    private String type = "2";
    private String cityName = "";

    private List<View> viewList = new ArrayList<>();
    private View view1,view2,view3,view4;
    private NotifyAdatpterBroadcastReceiver broadcastReceiver = new NotifyAdatpterBroadcastReceiver();
    private PreLoadWebYouJiBroadcastReceiver preLoadWebYouJiBroadcastReceiver = new PreLoadWebYouJiBroadcastReceiver();

    private boolean  isShowFloatImage = true  ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home4, null);
        ButterKnife.bind(this, rootView);
        ScreenAdapterTools.getInstance().loadView(rootView);
        spImp = new SpImp(getContext());
        AppUpdateUtil.checkUpdate(getActivity(),true);
        registerBroadCaset();
        initSonicEngine();
        getLocation();
        initData();
        initRv_Vp();
        return rootView;
    }
    private void initRv_Vp() {


        scrollView.setOnScrollListener(new ScrollListenScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                switch (type){
                    case "1":
                        scollYTuiJian = scrollY;
                        break;
                    case "2":
                        scollGuanZhu = scrollY;
                        break;
                    case "3":
                        scollYouJi = scrollY;
                        break;
                    case "4":
                        scollShiPin = scrollY;
                        break;
                }
            }
        });

        view1 = getLayoutInflater().inflate(R.layout.home_lay_guanzhu, null);
        view2 = getLayoutInflater().inflate(R.layout.home_lay_tuijian, null);
        view3 = getLayoutInflater().inflate(R.layout.home_lay_youji, null);
        view4 = getLayoutInflater().inflate(R.layout.home_lay_youpu, null);
        viewList.add(view1);
        viewList.add(view2);
//        viewList.add(view3);
        viewList.add(view4);

        ScreenAdapterTools.getInstance().loadView(view2);
        //轮播图
        banner = view2.findViewById(R.id.fragment_home_banner);

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
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
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

        //关注 友记视频
        rvGuanZhuYoujishipin = view1.findViewById(R.id.rv_guanzhu_youjishipin);
        adapterGuanzhuYouJi= new HomeGuanZhu_YouJiShiPin_Adapter(mListGuanzhu);
        LinearLayoutManager managerGuanZhuYouJi = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvGuanZhuYoujishipin.setLayoutManager(managerGuanZhuYouJi);
        rvGuanZhuYoujishipin.setAdapter(adapterGuanzhuYouJi);

        //关注 队长带队
        rvGuanZhuDuiZhangDaiDui = view1.findViewById(R.id.rv_duizhangdaidui);
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

        refreshHorizontal = view1.findViewById(R.id.refresh_horizontal);
        refreshHorizontal.setRefreshHeader(new ClassicsHeader(getContext()));
//        ClassicsFooter classicsFooter = new ClassicsFooter(getContext());
//        refreshHorizontal.setRefreshFooter(classicsFooter);
//        refreshHorizontal.setRefreshHeader(new MaterialHeader(getContext()));
//        refreshHorizontal.setRefreshFooter(new RefreshFooterWrapper(new MaterialHeader(getContext())), -1, -2);
        refreshHorizontal.setEnableRefresh(false);

        refreshHorizontal.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Intent intent = new Intent();
                intent.setClass(getContext(), GuanZhuDuiZhangListActivity.class);
                startActivity(intent);
            }
        });


        rv_youji = view3.findViewById(R.id.rv_youji);
        rv_youpu = view4.findViewById(R.id.rv_youpu);

        PagerAdapter pagerAdapter = new PagerAdapter() {

                    @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                                // TODO Auto-generated method stub
                                return arg0 == arg1;
                            }

                    @Override
            public int getCount() {
                                // TODO Auto-generated method stub
                                return viewList.size();
                            }

                    @Override
            public void destroyItem(ViewGroup container, int position,
                    Object object) {
                                // TODO Auto-generated method stub
                                container.removeView(viewList.get(position));
                            }

                    @Override
            public Object instantiateItem(ViewGroup container, int position) {
                                // TODO Auto-generated method stub
                                container.addView(viewList.get(position));
                                viewPager.setObjectForPosition(viewList.get(position),position);
                                return viewList.get(position);
                            }
        };
        //初始化 tvRl
        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
        tvRl1.setTextColor(Color.parseColor("#333333"));
        tvRl2.setTextColor(Color.parseColor("#999999"));
        tvRl3.setTextColor(Color.parseColor("#999999"));
//        rl_top.setBackgroundColor(Color.parseColor("#ffffff"));
//                        tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
        type = "1";

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if ((mListGuanzhu.size() == 0&&position == 0)){//关注无数据时
                    Log.d("aaaaa","guanzhuwushuju");
                }else {
                    viewPager.resetHeight(position);
                    Log.d("aaaaa","guanzhuyoushuju");
                }
                switch (position){
                    case 0:
                        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                            tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
                            tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                            tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                            tvRl1.setTextColor(Color.parseColor("#333333"));
                            tvRl2.setTextColor(Color.parseColor("#999999"));
                            tvRl3.setTextColor(Color.parseColor("#999999"));
//                            rl_top.setBackgroundColor(Color.parseColor("#ffffff"));
    //                        tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    //                        tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    //                        tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
    //                        tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            v1.setVisibility(View.VISIBLE);
                            v2.setVisibility(View.GONE);
                            v3.setVisibility(View.GONE);

                            type = "1";
                            scrollView.scrollTo(0,scollYTuiJian);
                            Log.d("scollYYY_tuijian_to::",scollYTuiJian+"");
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(getContext(), LoginActivity.class);
                            startActivity(intent);
                            viewPager.setCurrentItem(1);
                        }
                        break;
                    case 1:
                            tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                            tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
                            tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                            tvRl1.setTextColor(Color.parseColor("#999999"));
                            tvRl2.setTextColor(Color.parseColor("#333333"));
                            tvRl3.setTextColor(Color.parseColor("#999999"));
//                            rl_top.setBackgroundColor(Color.parseColor("#ffffff"));
//                            tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                            tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                            tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                            tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            v1.setVisibility(View.GONE);
                            v2.setVisibility(View.VISIBLE);
                            v3.setVisibility(View.GONE);
                            type = "2";
                            scrollView.scrollTo(0,scollGuanZhu);
                            Log.d("scollYYY_guanzhu_to::",scollGuanZhu+"");

                        break;
                    case 2:
                        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
                        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
                        tvRl1.setTextColor(Color.parseColor("#999999"));
                        tvRl2.setTextColor(Color.parseColor("#999999"));
                        tvRl3.setTextColor(Color.parseColor("#333333"));
//                        rl_top.setBackgroundResource(R.drawable.bg_white_down_40px);
//                        tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                        tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                        tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        v1.setVisibility(View.GONE);
                        v2.setVisibility(View.GONE);
                        v3.setVisibility(View.VISIBLE);
                        type = "3";
                        scrollView.scrollTo(0,scollYouJi);
                        Log.d("scollYYY_youji_to::",scollYouJi+"");
                        break;
//                    case 3:
//                        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
//                        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
//                        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
//                        tvRl1.setTextColor(Color.parseColor("#999999"));
//                        tvRl2.setTextColor(Color.parseColor("#999999"));
//                        tvRl3.setTextColor(Color.parseColor("#999999"));
//                        rl_top.setBackgroundResource(R.drawable.bg_white_down_40px);
////                        tvRl1.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
////                        tvRl2.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
////                        tvRl3.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
////                        tvRl4.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                        v1.setVisibility(View.GONE);
//                        v2.setVisibility(View.GONE);
//                        v3.setVisibility(View.GONE);
//                        type = "4";
//                        scrollView.scrollTo(0,scollShiPin);
//                        Log.d("scollYYY_shipin_to::",scollShiPin+"");
//                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(1);
    }
    public void initBanner(XBanner banner, final List<String> images) {

//        //设置banner样式
//        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
//        banner.setClipChildren(false);
//
//        banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//
//            }
//        });
//        //设置图片加载器
//        banner.setImageLoader(new GlideImageLoader());
//        //设置图片集合
//        banner.setImages(images);
//        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.Default);
//        //设置标题集合（当banner样式有显示title时）
////        banner.setBannerTitles(titles);
//        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
//        //设置轮播时间
//        banner.setDelayTime(5000);
//        //设置指示器位置（当banner模式中有指示器时）
//        banner.setIndicatorGravity(BannerConfig.CENTER);
//        //banner设置方法全部调用完毕时最后调用
//        banner.start();
//        banner.setPageTransformer(Transformer.Default);
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
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            getLocation();
            initData();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            getLocation();
            initData();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    private void initData() {

        /*
           首页顶部日期  未读消息数
         */
        ViseHttp.POST(NetConfig.dataInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.dataInfo))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                String s1 = jsonObject.getJSONObject("obj").getString("news");
                                String s2 = jsonObject.getJSONObject("obj").getString("day");
                                tvWeiduxiaoxi.setText(""+s1+"");
                                if (s1.equals("0")){
                                    rl_xiaoxi_num.setVisibility(View.GONE);
                                }else {
                                    rl_xiaoxi_num.setVisibility(View.VISIBLE);
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
        uid = spImp.getUID();
        //推荐
        ViseHttp.POST(NetConfig.homeTuiJian)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeTuiJian))
                .addParam("uid", uid)
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });

        ViseHttp.POST(NetConfig.yj_video)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.yj_video))
                .addParam("uid", uid)
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

        //关注
        ViseHttp.POST(NetConfig.homePageGz)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homePageGz))
                .addParam("uid", uid)
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
                                    if (hasPermission()){
                                        preLoadYouJi_guanzhu(mListGuanzhu);
                                    }else {
                                        requestPermission();
                                    }
                                }
                                adapterGuanzhuYouJi.notifyDataSetChanged();

                                mlistDuiZhangDaiDui.clear();
                                mlistDuiZhangDaiDui.addAll(model.getObj().getCaptainPf());
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
        //友记
        ViseHttp.POST(NetConfig.yjVideoList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.yjVideoList))
                .addParam("uid", uid)
//                .addParam("city", cityName)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                Home_youjiShiPin_0407_model model = gson.fromJson(data, Home_youjiShiPin_0407_model.class);
                                page3 = 2;
                                mListYouJi = model.getObj();
                                if (mListYouJi.size()>0){
                                    if (hasPermission()){
                                        preLoadYouJi_youji(mListYouJi);
                                    }else {
                                        requestPermission();
                                    }
                                }
                                adapterYouji = new Home_YouJiShiPin_0407_Adapter(mListYouJi);
                                // /设置布局管理器为2列，纵向
                                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                rv_youji.setLayoutManager(mLayoutManager);
                                rv_youji.setAdapter(adapterYouji);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });
        //友铺
        ViseHttp.POST(NetConfig.homeGoodsList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGoodsList))
                .addParam("uid", uid)
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
                                        return false;
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
        ClassicsHeader header = new ClassicsHeader(getContext());
//        header.setAccentColor(Color.WHITE);
//        header.setPrimaryColor(Color.parseColor("#d84c37"));
        refreshLayout.setRefreshHeader(header);
        ClassicsFooter footer = new ClassicsFooter(getContext());
//        footer.setPrimaryColor(Color.parseColor("#F8F8F8"));
        refreshLayout.setRefreshFooter(footer);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                switch (type){
                    case "2":
                        ViseHttp.POST(NetConfig.yj_video)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.yj_video))
                                .addParam("uid", uid)
                                .addParam("city", cityName)
                                .addParam("page",page1+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("123123", type+"--------"+uid);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                HomeTuiJianYouJiShiPinModel model = gson.fromJson(data, HomeTuiJianYouJiShiPinModel.class);
                                                listYouJiShiPin.addAll(model.getObj());
                                                youJiShiPinAdapter.notifyDataSetChanged();
                                                if (model.getObj().size()>0){
                                                    listYouJiShiPin.addAll(model.getObj());
                                                    preLoadYouJi_tuijain_list(model.getObj());
                                                    if (listYouJiShiPin!=null && youJiShiPinAdapter!=null){
                                                        youJiShiPinAdapter.notifyDataSetChanged();
                                                    }
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
                        break;
                    case "1":
                        ViseHttp.POST(NetConfig.homePageGz)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homePageGz))
                                .addParam("uid", uid)
                                .addParam("page",page2+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("123123", type+"--------"+uid);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                HomeGuanZhuModel model = gson.fromJson(data, HomeGuanZhuModel.class);
                                                if (model.getObj().getYj_video().size()>0){
                                                    mListGuanzhu.addAll(model.getObj().getYj_video());
                                                    preLoadYouJi_guanzhu(model.getObj().getYj_video());
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
                        break;
                    case "3":
                        ViseHttp.POST(NetConfig.yjVideoList)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.yjVideoList))
                                .addParam("uid", uid)
//                                .addParam("city", cityName)
                                .addParam("page",page3+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("123123", type+"--------"+uid);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                Home_youjiShiPin_0407_model model = gson.fromJson(data, Home_youjiShiPin_0407_model.class);
//                                        mList.clear();
                                                if (model.getObj().size()>0){
                                                    mListYouJi.addAll(model.getObj());
                                                    preLoadYouJi_youji(model.getObj());
                                                    adapterYouji.notifyDataSetChanged();
                                                    page3++;
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
                        break;
                    case "4":
                        ViseHttp.POST(NetConfig.homeGoodsList)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGoodsList))
                                .addParam("uid", uid)
                                .addParam("page",page4+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.e("123123", type+"--------"+uid);
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
                        break;
                }
            }
        });

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
    @Override
    public void onStart() {
        super.onStart();
        uid = spImp.getUID();
    }

    public void getLocation() {
        Log.i("查找城市", "哈尔滨");
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        new Thread() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Log.i("请求权限", "请求了");
                    return;
                }
                @SuppressLint("MissingPermission") Location location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude(); // 经度
                    longitude = location.getLongitude(); // 纬度
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    public void getCity() {
        try {
            // 去谷歌的地理位置获取中去解析经纬度对应的地理位置
//            String url = "http://maps.google.cn/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&sensor=true&language=zh-CN";
            String url = "http://api.map.baidu.com/geocoder?output=json&location=" + latitude + "," + longitude + "&key=8dDPAEEMwPNZgxg4YhNUXqWoV8GNItO1";

            ViseHttp.GET(url)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getString("status").equals("OK")) {
                                    Gson gson = new Gson();
                                    BaiduCityModel model = gson.fromJson(data, BaiduCityModel.class);
                                    latLongString = model.getResult().getAddressComponent().getCity();
                                    cityTv.setText(""+latLongString);
                                    cityName = latLongString;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick({R.id.locationRl, R.id.ll_search, R.id.iv_msg,
    R.id.rl1, R.id.rl2, R.id.rl3,R.id.rl_xiaoxi,R.id.rl_ball})
    public void onClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.locationRl:
                Intent it = new Intent(getActivity(), CityActivity.class);
                CityModel model = new CityModel();
                model.setName("哈尔滨");
                model.setId("-1");
                UserUtils.saveCity(getActivity(), model);
                it.putExtra(ActivityConfig.ACTIVITY, "home");
//                it.putExtra("model",model);
                startActivityForResult(it, 1);
                break;
            case R.id.ll_search:
//                intent.setClass(getContext(), SearchActivity.class);
//                intent.putExtra("type", "1");
//                startActivity(intent);
                intent.setClass(getContext(), HomeSearchActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                break;
            case R.id.iv_msg:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MessageActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_xiaoxi:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MessageActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl1:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    type = "1";
                    viewPager.setCurrentItem(0);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl2:
                viewPager.setCurrentItem(1);
                type = "2";
                break;
            case R.id.rl3:
                viewPager.setCurrentItem(2);
                type = "3";
                break;
            case R.id.rl_ball:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyJiFenActivity.class);
                    intent.putExtra("url","http://www.tongbanapp.com/action/ac_mission/mission?uid="+spImp.getUID());
                    startActivityForResult(intent,2);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private void refresh(){
//        if (!isNetConnect()){
//            toToast(getContext(),"当前无网络！");
//            return;
//        }
//        dialog_loading = WeiboDialogUtils.createLoadingDialog(getContext(),"加载中...");
        switch (type){
            case "2":
                ViseHttp.POST(NetConfig.homeTuiJian)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeTuiJian))
                        .addParam("uid", uid)
                        .addParam("city", cityName)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("123123", type+"--------"+uid);
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
                        .addParam("uid", uid)
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
                break;
            case "1":
                ViseHttp.POST(NetConfig.homePageGz)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homePageGz))
                        .addParam("uid", uid)
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
                                        preLoadYouJi_guanzhu(mListGuanzhu);
                                        adapterGuanzhuYouJi.notifyDataSetChanged();
//                                        WeiboDialogUtils.closeDialog(dialog_loading);
                                        mlistDuiZhangDaiDui.clear();
                                        mlistDuiZhangDaiDui.addAll(model.getObj().getCaptainPf());
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
                break;
            case "3":
                ViseHttp.POST(NetConfig.yjVideoList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.yjVideoList))
                        .addParam("uid", uid)
//                        .addParam("city", cityName)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        Home_youjiShiPin_0407_model model = gson.fromJson(data, Home_youjiShiPin_0407_model.class);
                                        page3 = 2;
                                        mListYouJi.clear();
                                        mListYouJi.addAll(model.getObj());
                                        if (hasPermission()){
                                            preLoadYouJi_youji(mListYouJi);
                                        }else {
                                            requestPermission();
                                        }
                                        adapterYouji.notifyDataSetChanged();
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
                break;
            case "4":
                ViseHttp.POST(NetConfig.homeGoodsList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homeGoodsList))
                        .addParam("uid", uid)
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
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode == 1 城市页面 请求码 // 2 任务 页面请求码
        if (requestCode == 1){
            if (data != null && resultCode == 1) {
                CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
                cityTv.setText(""+model.getName());
                cityName = model.getName();
                cityId = model.getId();
            } else if (resultCode == 2) {
                cityId = "";
                cityName = "";
//            cityTv.setText(latLongString);
                cityTv.setText("选择城市");
            } else if (resultCode == 3) {
                String city = data.getStringExtra("city");
                cityId = data.getStringExtra("cityid");
                cityName = city;
                cityTv.setText(""+city);
            }
            refresh();
        }

        if (requestCode == 2){//任务
            switch (resultCode){
                case 1://跳转友记
                    viewPager.setCurrentItem(2);
                    type = "3";
                    break;
                case 2://跳转友聚
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.switchFragment(1);
                    mainActivity.startHuoDong();
                    break;
                case 3://跳转视频
//                    viewPager.setCurrentItem(3);
//                    type = "4";
                    viewPager.setCurrentItem(2);
                    type = "3";
                    break;
            }
        }
    }
    public void scroll2top(){
//        scrollView.fullScroll(View.FOCUS_UP);
    }
    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(broadcastReceiver);
        getContext().unregisterReceiver(preLoadWebYouJiBroadcastReceiver);
        SonicEngine.getInstance().cleanCache();

        super.onDestroy();
    }

    private void registerBroadCaset() {
        IntentFilter filter =new IntentFilter();
        filter.addAction("android.friendscometogether.HomeFragment.TuiJian_Youji");
        filter.addAction("android.friendscometogether.HomeFragment.TuiJian_Youju");
        filter.addAction("android.friendscometogether.HomeFragment.GuanZhu");
        filter.addAction("android.friendscometogether.HomeFragment.YouJi");
        filter.addAction("android.friendscometogether.HomeFragment.Video");
        getContext().registerReceiver(broadcastReceiver, filter);

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("android.friendscometogether.HomeFragment.PreLoadWebYouJiBroadcastReceiver");
        getContext().registerReceiver(preLoadWebYouJiBroadcastReceiver,filter1);
    }

    public boolean isShowFloatImage() {
        return isShowFloatImage;
    }

    private class NotifyAdatpterBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
// TODO Auto-generated method stub
            String action = intent.getAction();
            String id = intent.getStringExtra("deleteID");
            switch (action){
//                case "android.friendscometogether.HomeFragment.TuiJian_Youji":
//                    for (int i =0;i<mListTuiJian_youji.size();i++){
//                        if (mListTuiJian_youji.get(i).getPfID().equals(id)){
//                            mListTuiJian_youji.remove(i);
//                            adapterTuiJian_youji.notifyDataSetChanged();
//                            break;
//                        }
//                    }
//                    break;
//                case "android.friendscometogether.HomeFragment.TuiJian_Youju":
//                    for (int i =0;i<dataHuoDongLiveModelList.size();i++){
//                        if (dataHuoDongLiveModelList.get(i).getPfID().equals(id)){
//                            dataHuoDongLiveModelList.remove(i);
//                            jingCaiLuXianAdapter.notifyDataSetChanged();
//                            break;
//                        }
//                    }
//                        break;
                case "android.friendscometogether.HomeFragment.GuanZhu":
                    for (int i =0;i<mListGuanzhu.size();i++){
                        if (mListGuanzhu.get(i).getFmID().equals(id)){
                            mListGuanzhu.remove(i);
                            adapterGuanzhuYouJi.notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
                case "android.friendscometogether.HomeFragment.YouJi":
                    for (int i =0;i<mListYouJi.size();i++){
                        if (mListYouJi.get(i).getFmID().equals(id)){
                            mListYouJi.remove(i);
                            adapterYouji.notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
                case "android.friendscometogether.HomeFragment.Video":
//                    for (int i =0;i<listYouPu.size();i++){
//                        if (listYouPu.get(i).getVID().equals(id)){
//                            listYouPu.remove(i);
//                            adapterVideos.notifyDataSetChanged();
//                            break;
//                        }
//                    }
                    break;
            }
        }
    }
    private void initSonicEngine() {
        // init sonic engine
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicConfig config = new SonicConfig.Builder()
                                    .setMaxPreloadSessionCount(100)
                                    .setMaxNumOfDownloadingTasks(10)
                                    .build();

            SonicEngine.createInstance(new TBSonicRuntime(getContext()),config);
            Log.d("SonicEngine.create","webPage_aaa");
        }

    }
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d("读写内存权限,","permissionsSize:"+permissions.length+"///"+"grantResultsSize:"+grantResults.length);
        for (int i = 0;i<permissions.length;i++){
            if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    requestPermission();
                } else {
                    preLoadYouJi_youji(mListYouJi);
                    preLoadYouJi_guanzhu(mListGuanzhu);
                    preLoadYouJi_tuijain(listJianTuShiKe);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void preLoadYouJi_youji(List<Home_youjiShiPin_0407_model.ObjBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
                            String url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+list.get(i).getFmID()+"&uid="+uid;
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
    private void preLoadYouJi_guanzhu(List<HomeGuanZhuModel.ObjBean.YjVideoBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
            if (list.get(i).getTp().equals("2")){//为友记  的情况
                String url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+list.get(i).getFmID()+"&uid="+uid;
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
    }
    private void preLoadYouJi_tuijain(List<HomeTuiJianModel.ObjBean.YouJiBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
            String url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+list.get(i).getFmID()+"&uid="+uid;
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
    private void preLoadYouJi_tuijain_list(List<HomeTuiJianYouJiShiPinModel.ObjBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
            String url = NetConfig.BaseUrl+"action/ac_article/youJiWeb?id="+list.get(i).getFmID()+"&uid="+uid;
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
    private class PreLoadWebYouJiBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            uid = spImp.getUID();
            if (hasPermission()){
                preLoadYouJi_tuijain(listJianTuShiKe);
                preLoadYouJi_guanzhu(mListGuanzhu);
                preLoadYouJi_youji(mListYouJi);
            }else {
                requestPermission();
            }
        }
    }


//     直播列表关注
    private void guanZhuLivePerson(final int position){
        Log.d("adasds",position+"");
        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
            if (listJingCaiLuXian.get(position).getCaptain().getFollow().equals("0")){//未关注
                ViseHttp.POST(NetConfig.userFocusUrl)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                        .addParam("uid", uid)
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


    private int[] getDisplayMetrics(Context context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int W = mDisplayMetrics.widthPixels;
        int H = mDisplayMetrics.heightPixels;
        int array[] = {W, H};
        return array;
    }
    public void hideFloatImage() {
        if (!(rl_ball.getVisibility()==View.VISIBLE)){
            return;
        }
        isShowFloatImage  = false;
        //位移动画
        TranslateAnimation ta = new TranslateAnimation(
                0,//起始x坐标,10表示与初始位置相距10
                getDisplayMetrics(getContext())[0] - rl_ball.getRight()+(rl_ball.getMeasuredWidth()*0.75f),//结束x坐标
                0,//起始y坐标
                0);//结束y坐标（正数向下移动）
        ta.setDuration(300);

        //渐变动画
        AlphaAnimation al = new AlphaAnimation(1f, 0.5f);
        al.setDuration(300);

        AnimationSet set = new AnimationSet(true);
        //动画完成后不回到原位
        set.setFillAfter(true);
        set.addAnimation(ta);
        set.addAnimation(al);
        rl_ball.startAnimation(set);
    }
    public void showFloatImage() {
        if (!(rl_ball.getVisibility()==View.VISIBLE)){
            return;
        }
        isShowFloatImage  = true;
        //位移动画
        TranslateAnimation ta = new TranslateAnimation(
                getDisplayMetrics(getContext())[0] - rl_ball.getRight()+(rl_ball.getMeasuredWidth()*0.75f),//起始x坐标
                0,//结束x坐标
                0,//起始y坐标
                0);//结束y坐标（正数向下移动）
        ta.setDuration(300);

        //渐变动画
        AlphaAnimation al = new AlphaAnimation(0.5f, 1f);
        al.setDuration(300);

        AnimationSet set = new AnimationSet(true);
        //动画完成后不回到原位
        set.setFillAfter(true);
        set.addAnimation(ta);
        set.addAnimation(al);
        rl_ball.startAnimation(set);
    }
}
