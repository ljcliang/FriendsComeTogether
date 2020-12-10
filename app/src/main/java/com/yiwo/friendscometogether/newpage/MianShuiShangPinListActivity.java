package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.yiwo.friendscometogether.newadapter.hometuijian_five_list.MianShuiShangPin_Adapter;
import com.yiwo.friendscometogether.newmodel.HomePageSkipGoodsModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MianShuiShangPinListActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_zanwu)
    ImageView ivZanwu;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    private SpImp spImp;
    private List<HomePageSkipGoodsModel.ObjBean> list = new ArrayList<>();
    MianShuiShangPin_Adapter adapter;
    public static final String LIST_TYPE = "list_type";
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian_shui_shang_pin);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initView();
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.homePageSkipGoods)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homePageSkipGoods))
                .addParam("uid", spImp.getUID())
                .addParam("search_key_word",edtSearch.getText().toString())
                .addParam("type", getIntent().getStringExtra(LIST_TYPE))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                HomePageSkipGoodsModel model = gson.fromJson(data, HomePageSkipGoodsModel.class);
                                list.clear();
                                adapter.notifyDataSetChanged();
                                list.addAll(model.getObj());
                                if (list.size() == 0) {
                                    rv.setVisibility(View.GONE);
                                    ivZanwu.setVisibility(View.VISIBLE);
                                } else {
                                    rv.setVisibility(View.VISIBLE);
                                    ivZanwu.setVisibility(View.GONE);
                                }
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

    /**
     * @param context
     * @param type    type  0免税商品  1特价商品
     */
    public static void open(Context context, String type) {
        Intent intent = new Intent();
        intent.putExtra(LIST_TYPE, type);
        intent.setClass(context, MianShuiShangPinListActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        switch (getIntent().getStringExtra(LIST_TYPE)) {
            case "0":
//                tvTitle.setText("特价商品");
                break;
            case "1":
//                tvTitle.setText("队长带货");
                break;
        }
        // /设置布局管理器为2列，纵向
        StaggeredGridLayoutManager managerDuiZhangPuZi = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        rv.setLayoutManager(managerDuiZhangPuZi);
        adapter = new MianShuiShangPin_Adapter(list);
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
                ViseHttp.POST(NetConfig.homePageSkipGoods)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homePageSkipGoods))
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
                                        HomePageSkipGoodsModel model = gson.fromJson(data, HomePageSkipGoodsModel.class);
                                        list.addAll(model.getObj());
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

    @OnClick({R.id.rl_back, R.id.tv_search})
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
