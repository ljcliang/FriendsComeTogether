package com.yiwo.friendscometogether.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
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

import com.google.gson.Gson;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.model.BaiduCityModel;
import com.yiwo.friendscometogether.model.CityModel;
import com.yiwo.friendscometogether.network.ActivityConfig;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.Home_YouJiShiPin_0407_Adapter;
import com.yiwo.friendscometogether.newmodel.Home_youjiShiPin_0407_model;
import com.yiwo.friendscometogether.newpage.HomeSearchActivity;
import com.yiwo.friendscometogether.newpage.MessageActivity;
import com.yiwo.friendscometogether.pages.CityActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.AndTools;
import com.yiwo.friendscometogether.utils.AppUpdateUtil;
import com.yiwo.friendscometogether.utils.UserUtils;
import com.yiwo.friendscometogether.vas_sonic.TBSonicRuntime;
import com.yiwo.friendscometogether.webpages.MyJiFenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/4.
 */

public class HomeFragment4 extends BaseFragment {

    View rootView;

    @BindView(R.id.vp)
    ViewPager viewPager;
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



    private String cityId = "";
    private String type = "2";
    private String cityName = "";

    private boolean  isShowFloatImage = true  ;
    PagerAdapter pagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private HomeTuiJianFragment homeTuiJianFragment;
    private HomeShopGoodsFragment homeShopGoodsFragment;
    private HomeGuanZhuFragment homeGuanZhuFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home4, null);
        ButterKnife.bind(this, rootView);
        ScreenAdapterTools.getInstance().loadView(rootView);
        spImp = new SpImp(getContext());
        AppUpdateUtil.checkUpdate(getActivity(),true);
        initSonicEngine();
        getLocation();
        initData();
        initRv_Vp();
        return rootView;
    }
    private void initRv_Vp() {

        //初始化 tvRl
        tvRl1.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),58));
        tvRl2.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
        tvRl3.setTextSize(TypedValue.COMPLEX_UNIT_SP, AndTools.px2sp(getContext(),50));
        tvRl1.setTextColor(Color.parseColor("#333333"));
        tvRl2.setTextColor(Color.parseColor("#999999"));
        tvRl3.setTextColor(Color.parseColor("#999999"));
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
        type = "1";

        homeTuiJianFragment = HomeTuiJianFragment.newInstance(cityName);
        homeShopGoodsFragment = HomeShopGoodsFragment.newInstance();
        homeGuanZhuFragment = HomeGuanZhuFragment.newInstance();
        fragmentList.add(homeTuiJianFragment);
        fragmentList.add(homeGuanZhuFragment);
        fragmentList.add(homeShopGoodsFragment);
        pagerAdapter = new PagerAdapter(getChildFragmentManager(),fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
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
                        break;
                    case 1:
                        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
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
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(getContext(), LoginActivity.class);
                            startActivity(intent);
                            viewPager.setCurrentItem(0);
                        }
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
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
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
    /*
           首页顶部日期  未读消息数
         */
    private void initDateMessage(){
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
    }
    private void initData() {

        /*
           首页顶部日期  未读消息数
         */
        initDateMessage();
        uid = spImp.getUID();
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
                                    if (homeTuiJianFragment!=null) homeTuiJianFragment.setCity(cityName);
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
                type = "1";
                viewPager.setCurrentItem(0);
                break;
            case R.id.rl2:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    viewPager.setCurrentItem(1);
                    type = "2";
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode == 1 城市页面 请求码 // 2 任务 页面请求码
        if (requestCode == 1){
            if (data != null && resultCode == 1) {
                CityModel model = (CityModel) data.getSerializableExtra(ActivityConfig.CITY);
                cityTv.setText(""+model.getName());
                cityName = model.getName();
                if (homeTuiJianFragment!=null) homeTuiJianFragment.setCity(cityName);
                cityId = model.getId();
            } else if (resultCode == 2) {
                cityId = "";
                cityName = "";
                if (homeTuiJianFragment!=null) homeTuiJianFragment.setCity(cityName);
//            cityTv.setText(latLongString);
                cityTv.setText("选择城市");
            } else if (resultCode == 3) {
                String city = data.getStringExtra("city");
                cityId = data.getStringExtra("cityid");
                cityName = city;
                if (homeTuiJianFragment!=null) homeTuiJianFragment.setCity(cityName);
                cityTv.setText(""+city);
            }
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
        SonicEngine.getInstance().cleanCache();

        super.onDestroy();
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
                case "android.friendscometogether.HomeFragment.GuanZhu":
//                    for (int i =0;i<mListGuanzhu.size();i++){
//                        if (mListGuanzhu.get(i).getFmID().equals(id)){
//                            mListGuanzhu.remove(i);
//                            adapterGuanzhuYouJi.notifyDataSetChanged();
//                            break;
//                        }
//                    }
                    break;
                case "android.friendscometogether.HomeFragment.YouJi":
//                    for (int i =0;i<mListYouJi.size();i++){
//                        if (mListYouJi.get(i).getFmID().equals(id)){
//                            mListYouJi.remove(i);
//                            adapterYouji.notifyDataSetChanged();
//                            break;
//                        }
//                    }
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
    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1001;
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
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        private FragmentManager fm;
        public PagerAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.fragments = list;
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
