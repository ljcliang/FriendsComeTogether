package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindALiActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_ali_num)
    EditText edtAliNum;
    @BindView(R.id.edt_al_name)
    EditText edtAlName;
    @BindView(R.id.rl_btn)
    RelativeLayout rlBtn;


    private SpImp spImp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_a_li);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
    }

    @OnClick({R.id.rl_back, R.id.rl_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rl_btn:
                if (TextUtils.isEmpty(edtAliNum.getText().toString())){
                    toToast(BindALiActivity.this,"请输入支付宝账号！");
                    break;
                }
                if (TextUtils.isEmpty(edtAlName.getText().toString())){
                    toToast(BindALiActivity.this,"请输入账号昵称！");
                    break;
                }
                save();
                break;
        }
    }
    public static void open(Context context){
        Intent intent = new Intent();
        intent.setClass(context,BindALiActivity.class);
        context.startActivity(intent);
    }
    private void save() {
        ViseHttp.POST(NetConfig.updateUserAliInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.updateUserAliInfo))
                .addParam("uid", spImp.getUID())
                .addParam("ali",edtAliNum.getText().toString())
                .addParam("ali_name",edtAlName.getText().toString())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                toToast(BindALiActivity.this,"绑定成功！");
                                finish();
                            }else {
                                toToast(BindALiActivity.this,"绑定失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toToast(BindALiActivity.this,"绑定失败！");
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(BindALiActivity.this,"绑定失败！");
                    }
                });
    }
}
