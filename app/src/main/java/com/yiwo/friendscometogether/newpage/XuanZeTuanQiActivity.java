package com.yiwo.friendscometogether.newpage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    DuiZhangXuanZeHuoDongModel.ObjBean yiXuanHuoDong; // 返回选择的活动 model,
    private List<XuanZeTuanQiModel.ObjBean> list = new ArrayList<>();
    private XuanZeTuanQiAdapter adapter;
    private SpImp spImp;
    public static final int REQUEST_CODE_XUAN_ZE_HUO_DONG =1;
    private String dangQianJinDu;
    public static void startActivity(Activity context, DuiZhangXuanZeHuoDongModel.ObjBean yiXuanHuoDong, String dangQianJinDu){
        Intent itHuoDong = new Intent(context, XuanZeTuanQiActivity.class);
        itHuoDong.putExtra("xuanzehuodong",yiXuanHuoDong);
        itHuoDong.putExtra("dangQianJinDu",dangQianJinDu);
        context.startActivityForResult(itHuoDong, REQUEST_CODE_XUAN_ZE_HUO_DONG);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_ze_tuan_qi);
        ButterKnife.bind(this);
        yiXuanHuoDong = (DuiZhangXuanZeHuoDongModel.ObjBean) getIntent().getSerializableExtra("xuanzehuodong");
        dangQianJinDu =getIntent().getStringExtra("dangQianJinDu");
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
                yiXuanHuoDong = new DuiZhangXuanZeHuoDongModel.ObjBean();
                yiXuanHuoDong.setPfID(list.get(pfPosition).getPfID());
                yiXuanHuoDong.setPfpic("");
                yiXuanHuoDong.setPftitle(list.get(pfPosition).getPftitle());
                yiXuanHuoDong.setPhase_id(list.get(pfPosition).getPhaseList().get(phasePosition).getPhase_id());
                yiXuanHuoDong.setPhase_begin_time(list.get(pfPosition).getPhaseList().get(phasePosition).getPhase_begin_time());
                yiXuanHuoDong.setPhase_num(list.get(pfPosition).getPhaseList().get(phasePosition).getPhase_begin_time());
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
                                for (XuanZeTuanQiModel.ObjBean bean:list){
                                    if (bean.getPfID().equals(yiXuanHuoDong.getPfID())){
                                        for (XuanZeTuanQiModel.ObjBean.PhaseListBean phaseListBean : bean.getPhaseList()){
                                            if (phaseListBean.getPhase_id().equals(yiXuanHuoDong.getPhase_id())){
                                                phaseListBean.setChecked(true);
                                            }
                                        }
                                    }
                                }
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
                .addParam("pfID",yiXuanHuoDong.getPfID())
                .addParam("phase_id",yiXuanHuoDong.getPhase_id())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                toToast(XuanZeTuanQiActivity.this,"选择成功！");
                                onBackPressed();
                            }else {
                                toToast(XuanZeTuanQiActivity.this,"选择失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toToast(XuanZeTuanQiActivity.this,"选择失败！");
                        }

                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(XuanZeTuanQiActivity.this,"选择失败！");
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
                if (dangQianJinDu.equals("1")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(XuanZeTuanQiActivity.this);
                    builder.setMessage("当前任务未完成,重新选择团期当前任务会被重置，是否继续？")
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    saveChoosedPfPhase();
                                    Intent intent = new Intent();
                                    intent.putExtra("xuanzehuodong",yiXuanHuoDong);
                                    setResult(1,intent);
                                    finish();

                                }
                            }).show();
                    break;
                }else {
                    saveChoosedPfPhase();
                    Intent intent = new Intent();
                    intent.putExtra("xuanzehuodong",yiXuanHuoDong);
                    setResult(1,intent);
                    finish();
                    break;
                }
            case R.id.tv_search:
                callData();
                break;
        }
    }
}
