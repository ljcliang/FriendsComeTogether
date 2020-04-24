package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.SearchListAdapter;
import com.yiwo.friendscometogether.adapter.SuoShuHuoDongListAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.GetFriendActiveListModel;
import com.yiwo.friendscometogether.model.SearchListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuoShuHuoDongActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.edt)
    EditText editText;
    @BindView(R.id.tv_sousuo)
    TextView btn_sousuo;
    private  List<GetFriendActiveListModel.ObjBean> dataList = new ArrayList<>();

    SuoShuHuoDongListAdapter adapter;
    SpImp spImp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suo_shuo_huo_dong);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initRv();
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.getFriendActiveListUrl)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl+NetConfig.getFriendActiveListUrl))
                .addParam("searchword",editText.getText().toString())
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                GetFriendActiveListModel model = gson.fromJson(data, GetFriendActiveListModel.class);
                                dataList.clear();
                                dataList.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
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

    public void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(SuoShuHuoDongActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new SuoShuHuoDongListAdapter(dataList);
        adapter.setListionner(new SuoShuHuoDongListAdapter.ItemClickListionner() {
            @Override
            public void onClick(int postion) {
                Intent intent = new Intent();
                intent.putExtra("suoshuhuodong",dataList.get(postion));
                setResult(1,intent);
                finish();
            }
        });
        rv.setAdapter(adapter);
    }

    @OnClick({R.id.rl_back,R.id.tv_sousuo})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_sousuo:
                initData();
                break;
        }
    }

}
