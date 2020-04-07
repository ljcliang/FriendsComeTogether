package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.EditorLabelListAdapter;
import com.yiwo.friendscometogether.newmodel.EditorLabelModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditorLabelListActivity extends BaseActivity {

    private Context context = EditorLabelListActivity.this;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private EditorLabelListAdapter adapter;
    private List<EditorLabelModel.ObjBean> mList;

    private SpImp spImp;
    private String uid = "";
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_label_list);

        StatusBarUtils.setStatusBar(EditorLabelListActivity.this, Color.parseColor("#D84C37"));
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(EditorLabelListActivity.this);
        spImp = new SpImp(context);
        type = getIntent().getStringExtra("type");

        initData();

    }

    private void initData() {

        uid = spImp.getUID();
        ViseHttp.POST(NetConfig.userlabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.userlabel))
                .addParam("type", type)
                .addParam("uid", uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                EditorLabelModel model = gson.fromJson(data, EditorLabelModel.class);
                                mList = model.getObj();
                                LinearLayoutManager manager = new LinearLayoutManager(context);
                                recyclerView.setLayoutManager(manager);
                                adapter = new EditorLabelListAdapter(mList);
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

    @OnClick({R.id.rl_back, R.id.rl_add, R.id.rl_save})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_add:
                intent.setClass(context, EditorMyLabelActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_save:
                List<EditorLabelModel.ObjBean> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++){
                    if(mList.get(i).getDatetype().equals("1")){
                        list.add(mList.get(i));
                    }
                }
                String label = "";
                for (int a = 0; a < list.size(); a++){
                    if(a == list.size()-1){
                        label = label + list.get(a).getT_name();
                    }else {
                        label = label + list.get(a).getT_name() + ",";
                    }
                }
                Intent intent1 = new Intent();
                intent1.putExtra("type", type);
                intent1.putExtra("label", label);
                setResult(1, intent1);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            String label = data.getStringExtra("value");
            mList.add(0, new EditorLabelModel.ObjBean(label, "", "1"));
            adapter.notifyDataSetChanged();
        }
    }
}
