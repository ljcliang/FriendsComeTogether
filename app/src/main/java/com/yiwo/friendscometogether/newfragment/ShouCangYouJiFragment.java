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

public class ShouCangYouJiFragment extends BaseFragment {

    @BindView(R.id.rv2)
    RecyclerView rv2;
    private AllCollectionAdapter adapter1;
    private List<UserCollectionModel.ObjBean> mList1;

    private SpImp spImp;
    private String uid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_shoucang_remember, null);

        ScreenAdapterTools.getInstance().loadView(view);
        ButterKnife.bind(this, view);
        spImp = new SpImp(getContext());

        initData();

        return view;
    }

    private void initData() {

        uid = spImp.getUID();

        ViseHttp.POST(NetConfig.userCollection)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userCollection))
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                UserCollectionModel userCollectionModel = gson.fromJson(data, UserCollectionModel.class);
                                mList1 = userCollectionModel.getObj();
                                adapter1 = new AllCollectionAdapter(mList1);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                rv2.setLayoutManager(manager);
                                rv2.setAdapter(adapter1);
                                adapter1.setListener(new AllCollectionAdapter.OnCancelListener() {
                                    @Override
                                    public void onCancel(final int i) {
                                        toDialog(getContext(), "提示", "是否取消收藏", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, int which) {
                                                String token = getToken(NetConfig.BaseUrl + NetConfig.deleteCollectionUrl);
                                                ViseHttp.POST(NetConfig.deleteCollectionUrl)
                                                        .addParam("app_key", token)
                                                        .addParam("id", mList1.get(i).getFID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject1 = new JSONObject(data);
                                                                    if(jsonObject1.getInt("code") == 200){
                                                                        toToast(getContext(), "取消收藏成功");
                                                                        mList1.remove(i);
                                                                        adapter1.notifyDataSetChanged();
                                                                        dialog.dismiss();
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
