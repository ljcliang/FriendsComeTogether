package com.yiwo.friendscometogether.newfragment;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.yiwo.friendscometogether.model.UserRememberModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.WoDeChawenAdapter;
import com.yiwo.friendscometogether.newmodel.WoDeChaWenModel;
import com.yiwo.friendscometogether.pages.ModifyIntercalationActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/18.
 */

public class AllChawenFragment extends BaseFragment {

    @BindView(R.id.rv1)
    RecyclerView rv1;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    private WoDeChawenAdapter adapter;
    private List<WoDeChaWenModel.ObjBean> mList = new ArrayList<>();

    private SpImp spImp;
    private String uid = "";
    private int page = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_chawen, null);

        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
        adapter = new WoDeChawenAdapter(mList, new WoDeChawenAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int position) {
                toDialog(getContext(), "提示", "是否删除插文", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        ViseHttp.POST(NetConfig.userDeleteIntercalationFocusUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userDeleteIntercalationFocusUrl))
                                .addParam("id", mList.get(position).getFfID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject1 = new JSONObject(data);
                                            if(jsonObject1.getInt("code") == 200){
                                                toToast(getContext(), "删除成功");
                                                mList.remove(position);
                                                adapter.notifyDataSetChanged();
                                                dialogInterface.dismiss();
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
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
            }
        }, new WoDeChawenAdapter.OnEditListenner() {
            @Override
            public void onEdit(int posion) {
                Intent intent = new Intent();
                intent.putExtra("id", mList.get(posion).getFfID());
                intent.setClass(getContext(), ModifyIntercalationActivity.class);
                startActivityForResult(intent,1);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv1.setLayoutManager(manager);
        rv1.setAdapter(adapter);
        initRefresh();
        initData();

        return view;
    }
    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
                refreshLayout.finishRefresh(500);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.userIntercalationListUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userIntercalationListUrl))
                        .addParam("uid", uid)
                        .addParam("page",page+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        WoDeChaWenModel model = gson.fromJson(data, WoDeChaWenModel.class);
                                        mList.addAll(model.getObj());
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
                refreshLayout.finishLoadMore(500);
            }
        });
    }
    private void initData() {

        uid = spImp.getUID();
        ViseHttp.POST(NetConfig.userIntercalationListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userIntercalationListUrl))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                WoDeChaWenModel model = gson.fromJson(data, WoDeChaWenModel.class);
                                mList.clear();
                                mList.addAll(model.getObj());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }

    @OnClick({})
    public void onClick(View view){
        switch (view.getId()){

        }
    }

}
