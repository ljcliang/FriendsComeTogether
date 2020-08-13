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
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.EditorFriendRememberModel;
import com.yiwo.friendscometogether.network.NetConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//编辑我的友记和续写
public class EditorFriendRememberActivity extends BaseActivity {

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

    private EditorFriendRememberAdapter adapter;
    private List<EditorFriendRememberModel.ObjBean.RenewListBean> mList;

    private String id = "";
    private String draft = "";// 状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_friend_remember);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        draft = intent.getStringExtra("draft");//草稿 状态是 2 ，已发布状态是 1
        if(draft.equals("2")){
            rlComplete.setVisibility(View.VISIBLE);
        }
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        Log.d("idididid",id);
        ViseHttp.POST(NetConfig.editorFriendRememberUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.editorFriendRememberUrl))
                .addParam("id", id)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            Log.d("idididid::",data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                EditorFriendRememberModel model = gson.fromJson(data, EditorFriendRememberModel.class);
                                tvTitle.setText(model.getObj().getFriendsList().getFmtitle());
                                Glide.with(EditorFriendRememberActivity.this).load(model.getObj().getFriendsList().getFmpic()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(ivTitle);
                                tvPrice.setText("人均费用: ¥" + model.getObj().getFriendsList().getPrice());
                                tvFabuTime.setText("发表时间："+ model.getObj().getFriendsList().getFmtime());
                                tvXiangGuanHuoDong.setText("相关活动: " + model.getObj().getFriendsList().getPftitle());
                                LinearLayoutManager manager = new LinearLayoutManager(EditorFriendRememberActivity.this);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                mList = model.getObj().getRenewList();
                                adapter = new EditorFriendRememberAdapter(model.getObj().getRenewList(), EditorFriendRememberActivity.this, new EditorFriendRememberAdapter.OndeleteListenner() {
                                    @Override
                                    public void delete(int pos) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(EditorFriendRememberActivity.this);
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
                                }, new EditorFriendRememberAdapter.OnEditListenner() {
                                    @Override
                                    public void edit(int pos) {
                                        Intent intent = new Intent();
                                        intent.putExtra("id", mList.get(pos).getFfID());
                                        intent.setClass(EditorFriendRememberActivity.this, ModifyIntercalationActivity.class);
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
        ViseHttp.POST(NetConfig.deleteRenewUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.deleteRenewUrl))
                .addParam("id", mList.get(pos).getFfID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                mList.remove(pos);
                                adapter.notifyDataSetChanged();
                                toToast(EditorFriendRememberActivity.this, "删除成功");
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
                intent.setClass(EditorFriendRememberActivity.this, CreateIntercalationActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type", draft);
                startActivityForResult(intent,1);
                break;
            case R.id.rl_modify:
                intent.setClass(EditorFriendRememberActivity.this, ModifyFriendRememberActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent,1);
                break;
            case R.id.rl_complete:
                ViseHttp.POST(NetConfig.releaseDraftUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.releaseDraftUrl))
                        .addParam("id", id)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.d("asaasas",data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.getInt("code") == 200){
                                        toToast(EditorFriendRememberActivity.this, jsonObject.getString("message"));
                                        initData();
                                    }else {
                                        toToast(EditorFriendRememberActivity.this, jsonObject.getString("message"));
                                        intent.setClass(EditorFriendRememberActivity.this, ModifyFriendRememberActivity.class);
                                        intent.putExtra("id", id);
                                        startActivityForResult(intent,1);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EditorFriendRememberActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initData();
    }
}
