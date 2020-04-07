package com.yiwo.friendscometogether.newfragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.WoDeChawenAdapter;
import com.yiwo.friendscometogether.newmodel.WoDeChaWenModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

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

    private WoDeChawenAdapter adapter;
    private List<WoDeChaWenModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_chawen, null);

        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());

        initData();

        return view;
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
                                mList = model.getObj();
                                adapter = new WoDeChawenAdapter(mList);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                rv1.setLayoutManager(manager);
                                rv1.setAdapter(adapter);
                                adapter.setListener(new WoDeChawenAdapter.OnDeleteListener() {
                                    @Override
                                    public void onDelete(final int position) {
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
                                });
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

    @OnClick({})
    public void onClick(View view){
        switch (view.getId()){

        }
    }

}
