package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
import com.yiwo.friendscometogether.newadapter.hometuijian_five_list.JingDianDaKa_Adapter;
import com.yiwo.friendscometogether.newadapter.hometuijian_five_list.WenLvZiXunAdapter;
import com.yiwo.friendscometogether.newadapter.hometuijian_five_list.ZuiXinZhaoMu_Adapter;
import com.yiwo.friendscometogether.newmodel.HomePageSkipListModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ExStaggeredGridLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZiXunListActivity extends BaseActivity {


    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    //    @BindView(R.id.tv_title)
//    TextView tvTitle;
    private SpImp spImp;
    private List<HomePageSkipListModel.ObjBean.InfoListBean> list = new ArrayList<>();
    RecyclerView.Adapter adapter;
    public static final String LIST_TYPE = "list_type";
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_xun_list);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initView();
        initData();
    }

    /**
     * @param type 传type  0最新招募  1文旅资讯  2经典打卡
     */
    public static void open(Context context, String type) {
        Intent intent = new Intent();
        intent.putExtra(LIST_TYPE, type);
        intent.setClass(context, ZiXunListActivity.class);
        context.startActivity(intent);
    }

    private void initData() {
        ViseHttp.POST(NetConfig.homePageSkipList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homePageSkipList))
                .addParam("uid", spImp.getUID())
                .addParam("type", getIntent().getStringExtra(LIST_TYPE))
                .addParam("search_key_word",edtSearch.getText().toString())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("asdasda", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                HomePageSkipListModel model = gson.fromJson(data, HomePageSkipListModel.class);
                                list.clear();
                                adapter.notifyDataSetChanged();
                                list.addAll(model.getObj().getInfoList());
                                adapter.notifyDataSetChanged();
                                page = 2;
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

    private void initView() {

        switch (getIntent().getStringExtra(LIST_TYPE)) {
            case "0"://0最新招募
                LinearLayoutManager manager0 = new LinearLayoutManager(this);
                manager0.setOrientation(LinearLayoutManager.VERTICAL);
                adapter = new ZuiXinZhaoMu_Adapter(list);
//                tvTitle.setText("最新招募");
                rv.setLayoutManager(manager0);
                break;
            case "1"://1文旅资讯
                LinearLayoutManager manager1 = new LinearLayoutManager(this);
                manager1.setOrientation(LinearLayoutManager.VERTICAL);
                adapter = new WenLvZiXunAdapter(list);
//                tvTitle.setText("新闻资讯");
                rv.setLayoutManager(manager1);
                break;
            case "2"://2经典打卡
                ExStaggeredGridLayoutManager manager2 = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
                    @Override
                    public boolean canScrollVertically() {
                        return true;
                    }
                };
                adapter = new JingDianDaKa_Adapter(list);
//                tvTitle.setText("经典打卡");
                rv.setLayoutManager(manager2);
                break;
        }
        rv.setAdapter(adapter);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.homePageSkipList)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homePageSkipList))
                        .addParam("uid", spImp.getUID())
                        .addParam("type", getIntent().getStringExtra(LIST_TYPE))
                        .addParam("search_key_word",edtSearch.getText().toString())
                        .addParam("page", page + "")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        HomePageSkipListModel model = gson.fromJson(data, HomePageSkipListModel.class);
                                        list.addAll(model.getObj().getInfoList());
                                        adapter.notifyDataSetChanged();
                                        page++;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
                refreshLayout.finishLoadMore(1000);
            }

        });
    }

    @OnClick({R.id.rl_back,R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_search:
                initData();
                break;
        }
    }
}
