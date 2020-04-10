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
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.XuanZeTuanQiAdapter;
import com.yiwo.friendscometogether.newadapter.XuanZeTuanQiDateAdapter;
import com.yiwo.friendscometogether.newmodel.DuiZhangXuanZeHuoDongModel;
import com.yiwo.friendscometogether.newmodel.XuanZeTuanQiModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XuanZeTuanQiActivity extends BaseActivity {

    @BindView(R.id.edt_search)
    EditText editText;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rv)
    RecyclerView rv;
    DuiZhangXuanZeHuoDongModel.ObjBean yiXuanHuoDong;
    private List<XuanZeTuanQiModel.ObjBean> list = new ArrayList<>();
    private XuanZeTuanQiAdapter adapter;
    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_ze_tuan_qi);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initData();
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(XuanZeTuanQiActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new XuanZeTuanQiAdapter(list, new XuanZeTuanQiAdapter.OnDateCheckedListenner() {
            @Override
            public void onDateChecked(int pfPosition, int phasePosition) {
                for (XuanZeTuanQiModel.ObjBean bean :list){
                    for (XuanZeTuanQiModel.ObjBean.PhaseListBean phaseListBean : bean.getPhaseList()){
                        phaseListBean.setChecked(false);
                    }
                }
                list.get(pfPosition).getPhaseList().get(phasePosition).setChecked(true);
                adapter.notifyDataSetChanged();
            }
        });
        rv.setAdapter(adapter);
        callData();
    }
    private void callData(){
        ViseHttp.POST(NetConfig.pfListDuizhangZhuanShu)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.pfListDuizhangZhuanShu))
                .addParam("uid",spImp.getUID())
                .addParam("keyWord",editText.getText().toString())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                XuanZeTuanQiModel model = gson.fromJson(data,XuanZeTuanQiModel.class);
                                list.clear();
                                list.addAll(model.getObj());
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
    private void saveChoosedPfPhase(){
        ViseHttp.POST(NetConfig.getMission)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.getMission))
                .addParam("uid",spImp.getUID())
                .addParam("pfID",editText.getText().toString())
                .addParam("phase_id",editText.getText().toString())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                XuanZeTuanQiModel model = gson.fromJson(data,XuanZeTuanQiModel.class);
                                list.clear();
                                list.addAll(model.getObj());
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
    @OnClick({R.id.tv_search,R.id.rl_back,R.id.btn_sure})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn_sure:
//                Intent intent = new Intent();
//                intent.putExtra("xuanzehuodong",list.get(postion));
//                setResult(1,intent);
//                finish();
                break;
            case R.id.tv_search:
                callData();
                break;
        }
    }
}
