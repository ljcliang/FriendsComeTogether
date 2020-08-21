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
import com.yiwo.friendscometogether.model.UserCollectionModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.AllCollectionAdapter;
import com.yiwo.friendscometogether.newadapter.WoGuanZhuDeHuoDongAdapter;
import com.yiwo.friendscometogether.newmodel.GuanZhuHuoDongModel;
import com.yiwo.friendscometogether.newpage.GuanZhuActivity;
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

public class ShouCangHuoDongFragment extends BaseFragment {

    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    private List<GuanZhuHuoDongModel.ObjBean> mGuanZhuHuoDongList = new ArrayList<>();
    private WoGuanZhuDeHuoDongAdapter guanZhuDeHuoDongAdapter;

    private SpImp spImp;
    private String uid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_shoucang_huodong_remember, null);

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
                refreshLayout.finishLoadMore(1000);
            }
        });
    }

    private void initRv() {
        guanZhuDeHuoDongAdapter = new WoGuanZhuDeHuoDongAdapter(mGuanZhuHuoDongList);
        guanZhuDeHuoDongAdapter.setCancelGuanZHuLis(new WoGuanZhuDeHuoDongAdapter.CancelGuanZhuListion() {
            @Override
            public void cancleGuanzhu(final int posion) {
                toDialog(getContext(), "提示：", "是否取消收藏", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.focusOnToFriendTogetherUrl))
                                .addParam("userID", spImp.getUID())
                                .addParam("pfID", mGuanZhuHuoDongList.get(posion).getPfID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                toToast(getContext(),"取消关注成功");
                                                mGuanZhuHuoDongList.remove(posion);
                                                guanZhuDeHuoDongAdapter.notifyDataSetChanged();
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
        rv2.setAdapter(guanZhuDeHuoDongAdapter);
    }

    private void initData() {

        uid = spImp.getUID();

        ViseHttp.POST(NetConfig.MyFocusActiveUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.MyFocusActiveUrl))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("ljc_",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                GuanZhuHuoDongModel model = gson.fromJson(data, GuanZhuHuoDongModel.class);
                                mGuanZhuHuoDongList.clear();
                                mGuanZhuHuoDongList.addAll(model.getObj());
                                guanZhuDeHuoDongAdapter.notifyDataSetChanged();
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
