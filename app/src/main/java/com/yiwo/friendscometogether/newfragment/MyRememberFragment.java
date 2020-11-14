package com.yiwo.friendscometogether.newfragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.yiwo.friendscometogether.model.UserRememberModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.AllCollectionAdapter;
import com.yiwo.friendscometogether.newadapter.MyRememberAdapter;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/18.
 */

public class MyRememberFragment extends BaseFragment {

    @BindView(R.id.rv1)
    RecyclerView rv1;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    private MyRememberAdapter adapter;
    private List<UserRememberModel.ObjBean> mList;

    public String keyWord = "";

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    public void searchData(){
        initData();
    }
    private SpImp spImp;
    private String uid = "";
    private int page = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_remember, null);

        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());
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
                ViseHttp.POST(NetConfig.userRemember)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userRemember))
                        .addParam("uid", uid)
                        .addParam("searchedityouji",keyWord)
                        .addParam("page",page+"")
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        UserRememberModel userRememberModel = gson.fromJson(data, UserRememberModel.class);
                                        if (userRememberModel.getObj().size()>0){
                                            mList.addAll(userRememberModel.getObj());
                                            adapter.notifyDataSetChanged();
                                            page ++;
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
                refreshLayout.finishLoadMore(500);
            }
        });
    }

    private void initData() {

        uid = spImp.getUID();
        Log.d("asddsadasyouji","key"+keyWord);
        ViseHttp.POST(NetConfig.userRemember)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userRemember))
                .addParam("uid", uid)
                .addParam("searchedityouji",keyWord)
//                .addParam("type", "0")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("asddsadasyouji","key"+keyWord);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                UserRememberModel userRememberModel = gson.fromJson(data, UserRememberModel.class);
                                mList = userRememberModel.getObj();
                                adapter = new MyRememberAdapter(mList,new MyRememberAdapter.OnDeleteListener() {
                                    @Override
                                    public void onDelete(final int i) {
                                        toDialog(getContext(), "提示", "是否删除友记", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialogInterface, int which) {
                                                ViseHttp.POST(NetConfig.deleteFriendRememberUrl)
                                                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.deleteFriendRememberUrl))
                                                        .addParam("id", mList.get(i).getFmID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(data);
                                                                    if(jsonObject.getInt("code") == 200){
                                                                        toToast(getContext(), jsonObject.getString("message"));
                                                                        mList.remove(i);
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
                                            public void onClick(DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                    }
                                });
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                rv1.setLayoutManager(manager);
                                rv1.setAdapter(adapter);
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
}
