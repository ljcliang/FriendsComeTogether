package com.yiwo.friendscometogether.newfragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
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
    private MyRememberAdapter adapter;
    private List<UserRememberModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_remember, null);

        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());

        initData();

        return view;
    }

    private void initData() {

        uid = spImp.getUID();
        ViseHttp.POST(NetConfig.userRemember)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userRemember))
                .addParam("uid", uid)
//                .addParam("type", "0")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                UserRememberModel userRememberModel = gson.fromJson(data, UserRememberModel.class);
                                mList = userRememberModel.getObj();
                                adapter = new MyRememberAdapter(mList);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                rv1.setLayoutManager(manager);
                                rv1.setAdapter(adapter);
                                adapter.setListener(new MyRememberAdapter.OnDeleteListener() {
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
