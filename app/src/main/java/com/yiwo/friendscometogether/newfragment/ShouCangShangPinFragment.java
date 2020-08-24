package com.yiwo.friendscometogether.newfragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.ShouCangShangPin2Adapter;
import com.yiwo.friendscometogether.newadapter.ShouCangShangPinAdapter;
import com.yiwo.friendscometogether.newadapter.WoGuanZhuDeHuoDongAdapter;
import com.yiwo.friendscometogether.newmodel.GuanZhuHuoDongModel;
import com.yiwo.friendscometogether.newmodel.ShouCangShangPinModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/18.
 */

public class ShouCangShangPinFragment extends BaseFragment {

    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    private List<ShouCangShangPinModel.ObjBean> shouCangShangPin = new ArrayList<>();
    private ShouCangShangPin2Adapter shouCangShangPinAdapter;

    private SpImp spImp;
    private int page = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_shoucang_shangpin, null);

        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
        initRv();
        initData();
        initRefresh();
        return view;
    }

    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
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
                ViseHttp.POST(NetConfig.myCollectGoods)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.myCollectGoods))
                        .addParam("uid", spImp.getUID())
                        .addParam("page",page+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.d("ljc_",data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        page++;
                                        Gson gson = new Gson();
                                        ShouCangShangPinModel model = gson.fromJson(data, ShouCangShangPinModel.class);
                                        shouCangShangPin.addAll(model.getObj());
                                        shouCangShangPinAdapter.notifyDataSetChanged();
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

    private void initRv() {
        shouCangShangPinAdapter = new ShouCangShangPin2Adapter(shouCangShangPin);
        shouCangShangPinAdapter.setCancelGuanZHuLis(new ShouCangShangPin2Adapter.CancelGuanZhuListion() {
            @Override
            public void cancleGuanzhu(int posion) {
                toDialog(getContext(), "提示：", "是否取消收藏", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViseHttp.POST(NetConfig.collectGoods)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.collectGoods))
                                .addParam("uid", spImp.getUID())
                                .addParam("goodsID", shouCangShangPin.get(posion).getGoodsID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                toToast(getContext(),jsonObject.getString("message"));
                                                shouCangShangPin.remove(posion);
                                                shouCangShangPinAdapter.notifyDataSetChanged();
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
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv2.setLayoutManager(manager);
        rv2.setAdapter(shouCangShangPinAdapter);
    }

    private void initData() {

        ViseHttp.POST(NetConfig.myCollectGoods)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.myCollectGoods))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("ljc_",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                page = 2;
                                Gson gson = new Gson();
                                ShouCangShangPinModel model = gson.fromJson(data, ShouCangShangPinModel.class);
                                shouCangShangPin.clear();
                                shouCangShangPin.addAll(model.getObj());
                                shouCangShangPinAdapter.notifyDataSetChanged();
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
