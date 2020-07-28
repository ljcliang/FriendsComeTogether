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
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeGuanZhu_DuiZhangDaiDui_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeGuanZhu_YouJiShiPin_Adapter;
import com.yiwo.friendscometogether.newadapter.Home_GuanZhu_YouJiShiPin_Adapter;
import com.yiwo.friendscometogether.newmodel.HomeGuanZhuModel;
import com.yiwo.friendscometogether.newpage.GuanZhuDuiZhangListActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ExStaggeredGridLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                    preLoadYouJi_guanzhu(mListGuanzhu);
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void preLoadYouJi_guanzhu(List<HomeGuanZhuModel.ObjBean.YjVideoBean> list) {
        Log.d("读写内存权限","youquanxian");
        for (int i = 0 ;i<list.size();i++){
            if (list.get(i).getTp().equals("2")){//为友记  的情况
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
    }
    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
}
