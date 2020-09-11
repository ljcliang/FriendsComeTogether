package com.yiwo.friendscometogether.pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.EditorFriendRememberAdapter;
import com.yiwo.friendscometogether.adapter.EditorLuXianAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.EditorFriendRememberModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.ActivityEditorModel;
import com.yiwo.friendscometogether.newmodel.Fabu_Xiugai_LuXian_model;
import com.yiwo.friendscometogether.newpage.FaBu_XiuGai_LuXianActivity;
import com.yiwo.friendscometogether.newpage.XuXieHuoDongActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//编辑我的友记和续写
public class EditorLuXianActivity extends BaseActivity {

    @BindView(R.id.activity_editor_friend_remember_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_editor_friend_remember_tv_title)
    TextView tvTitle;
    @BindView(R.id.activity_editor_friend_remember_iv_title)
    ImageView ivTitle;
    @BindView(R.id.activity_editor_friend_remember_tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_fabu_time)
    TextView tvFabuTime;
    @BindView(R.id.tv_xiangguanhuodong)
    TextView tvXiangGuanHuoDong;
    @BindView(R.id.activity_editor_friend_remember_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_editor_friend_remember_tv_add)
    TextView tvAdd;
    @BindView(R.id.rl_modify)
    RelativeLayout rlModify;
    @BindView(R.id.tv_title)
    TextView tvTopTitle;
    @BindView(R.id.rl_complete)
    RelativeLayout rlComplete;

    private EditorLuXianAdapter adapter;
    private List<ActivityEditorModel.ObjBean.RenewListBean> mList;

    private String id = "";
    private String draft = "";// 状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_lu_xian);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        draft = intent.getStringExtra("draft");//草稿 状态是 0 ，已发布状态是 1
//        if(draft.equals("2")){
//            rlComplete.setVisibility(View.VISIBLE);
//        }
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        Log.d("idididid",id);
        ViseHttp.POST(NetConfig.activityEditor)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.editorFriendRememberUrl))
                .addParam("pfID", id)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            Log.d("idididid::",data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                ActivityEditorModel model = gson.fromJson(data, ActivityEditorModel.class);
                                tvTitle.setText(model.getObj().getFriendsList().getPftitle());
                                Glide.with(EditorLuXianActivity.this).load(model.getObj().getFriendsList().getPfpic()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(ivTitle);
                                tvPrice.setText("人均费用: ¥" + model.getObj().getFriendsList().getPrice());
                                tvFabuTime.setText("发团时间："+ model.getObj().getFriendsList().getGotime());
                                tvXiangGuanHuoDong.setText("行程地点: " + model.getObj().getFriendsList().getPfaddress());
                                LinearLayoutManager manager = new LinearLayoutManager(EditorLuXianActivity.this){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                mList = model.getObj().getRenewList();
                                adapter = new EditorLuXianAdapter(model.getObj().getRenewList(), EditorLuXianActivity.this, new EditorLuXianAdapter.OndeleteListenner() {
                                    @Override
                                    public void delete(int pos) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(EditorLuXianActivity.this);
                                        builder.setMessage("确定删除？")
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        deleteXuXie(pos);
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                }).show();
                                    }
                                }, new EditorLuXianAdapter.OnEditListenner() {
                                    @Override
                                    public void edit(int pos) {
                                        Intent intent = new Intent();
                                        intent.putExtra("id", mList.get(pos).getId());
                                        intent.setClass(EditorLuXianActivity.this, ModifyLuXianXuXieActivity.class);
                                        startActivityForResult(intent,1);
                                    }
                                });
                                recyclerView.setAdapter(adapter);
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
    private void deleteXuXie(int pos){
        ViseHttp.POST(NetConfig.delActivityContent)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.delActivityContent))
                .addParam("delID", mList.get(pos).getId())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                mList.remove(pos);
                                adapter.notifyDataSetChanged();
                                toToast(EditorLuXianActivity.this, "删除成功");
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

    @OnClick({R.id.activity_editor_friend_remember_rl_back, R.id.activity_editor_friend_remember_tv_add, R.id.rl_modify, R.id.rl_complete,R.id.rl_xuxie})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.activity_editor_friend_remember_rl_back:
                onBackPressed();
                break;
            case R.id.rl_xuxie:
            case R.id.activity_editor_friend_remember_tv_add:
                intent.putExtra("id", id+"");
                if (draft.equals("1")){//已发布
                    intent.putExtra("type", "1");
                }else {//未发布
                    intent.putExtra("type", "2");
                }
                intent.setClass(EditorLuXianActivity.this, XuXieHuoDongActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,1);
                break;
            case R.id.rl_modify:
                FaBu_XiuGai_LuXianActivity.open(EditorLuXianActivity.this,id);
                break;
            case R.id.rl_complete:
//                ViseHttp.POST(NetConfig.releaseDraftUrl)
//                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.releaseDraftUrl))
//                        .addParam("id", id)
//                        .request(new ACallback<String>() {
//                            @Override
//                            public void onSuccess(String data) {
//                                Log.d("asaasas",data);
//                                try {
//                                    JSONObject jsonObject = new JSONObject(data);
//                                    if(jsonObject.getInt("code") == 200){
//                                        toToast(EditorLuXianActivity.this, jsonObject.getString("message"));
//                                        initData();
//                                    }else {
//                                        toToast(EditorLuXianActivity.this, jsonObject.getString("message"));
//                                        intent.setClass(EditorLuXianActivity.this, ModifyFriendRememberActivity.class);
//                                        intent.putExtra("id", id);
//                                        startActivityForResult(intent,1);
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onFail(int errCode, String errMsg) {
//
//                            }
//                        });
//                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EditorLuXianActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }
}
