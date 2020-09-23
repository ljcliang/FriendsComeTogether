package com.yiwo.friendscometogether.newpage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeGuanZhu_DuiZhangDaiDui_Adapter;
import com.yiwo.friendscometogether.newadapter.HomeGuanZhu_DuiZhangDaiDui_list_Adapter;
import com.yiwo.friendscometogether.newmodel.GuanzhuDuizhangDaiduiListModel;
import com.yiwo.friendscometogether.newmodel.HomeGuanZhuModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanZhuDuiZhangListActivity extends BaseActivity {

    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private SpImp spImp;
    private int page = 1;
    private HomeGuanZhu_DuiZhangDaiDui_list_Adapter adapterGuanZhuDuiZhangDaiDui;
    private List<HomeGuanZhuModel.ObjBean.CaptainPfBean> mlistDuiZhangDaiDui = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_zhu_dui_zhang_list);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initRv();
        initData();
    }

    private void initData() {
        //关注
        ViseHttp.POST(NetConfig.gzCaptainList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.gzCaptainList))
                .addParam("uid", spImp.getUID())
                .addParam("page","1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                GuanzhuDuizhangDaiduiListModel model = gson.fromJson(data, GuanzhuDuizhangDaiduiListModel.class);
                                page = 2;
                                mlistDuiZhangDaiDui.clear();
                                mlistDuiZhangDaiDui.addAll(model.getObj());
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
    private void loadMore(){
        ViseHttp.POST(NetConfig.gzCaptainList)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.gzCaptainList))
                .addParam("uid", spImp.getUID())
                .addParam("page",page+"")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                GuanzhuDuizhangDaiduiListModel model = gson.fromJson(data, GuanzhuDuizhangDaiduiListModel.class);
                                page++;
                                mlistDuiZhangDaiDui.addAll(model.getObj());
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
    private void initRv() {
        adapterGuanZhuDuiZhangDaiDui = new HomeGuanZhu_DuiZhangDaiDui_list_Adapter(mlistDuiZhangDaiDui);
        LinearLayoutManager manager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterGuanZhuDuiZhangDaiDui);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
                refreshLayout.finishLoadMore(1000);
            }
        });
    }
    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }
}
