package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.DuiZhangZhuanShuModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.EnterLiveActivity;
import com.yiwo.friendscometogether.webpages.RenWuWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DuiZhangZhuanShuActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.iv_level)
    ImageView ivLevel;
    @BindView(R.id.tv_level_name)
    TextView tvLevelName;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;

    private SpImp spImp;
    private DuiZhangZhuanShuModel duiZhangZhuanShuModel;
    private UMShareAPI mShareAPI;
    private UMAuthListener umAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dui_zhang_zhuan_shu);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initUM();
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.missionInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.missionInfo))
                .addParam("uid",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                duiZhangZhuanShuModel = gson.fromJson(data,DuiZhangZhuanShuModel.class);
                                Glide.with(DuiZhangZhuanShuActivity.this).load(duiZhangZhuanShuModel.getObj().getUserpic())
                                        .apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(ivHead);
                                tvName.setText(duiZhangZhuanShuModel.getObj().getUsername());
                                tvLevel.setText("Lv."+duiZhangZhuanShuModel.getObj().getUsergrade());
                                switch (duiZhangZhuanShuModel.getObj().getLevelName()){
                                    case "0":
                                        ivLevel.setImageResource(R.mipmap.level_qingtong);
                                        tvLevelName.setText("等级:青铜队长");
                                        break;
                                    case "1":
                                        ivLevel.setImageResource(R.mipmap.level_baiyin);
                                        tvLevelName.setText("等级:白银队长");
                                        break;
                                    case "2":
                                        ivLevel.setImageResource(R.mipmap.level_huangjin);
                                        tvLevelName.setText("等级:黄金队长");
                                        break;
                                    case "3":
                                        ivLevel.setImageResource(R.mipmap.level_bojin);
                                        tvLevelName.setText("等级:铂金队长");
                                        break;
                                    case "4":
                                        ivLevel.setImageResource(R.mipmap.level_zuanshi);
                                        tvLevelName.setText("等级:钻石队长");
                                        break;
                                    case "5":
                                        ivLevel.setImageResource(R.mipmap.level_huangguan);
                                        tvLevelName.setText("等级:皇冠队长");
                                        break;
                                }
                                tvShopName.setText(duiZhangZhuanShuModel.getObj().getShopName());
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

    @OnClick({R.id.rl_btn_bangding,R.id.rl_btn_startlive,R.id.rl_back,
            R.id.rl_btn_game_start1,R.id.rl_btn_game_start2,R.id.rl_btn_game_start3,R.id.rl_btn_game_start4,R.id.rl_btn_game_start5})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rl_btn_bangding:
                mShareAPI.getPlatformInfo(DuiZhangZhuanShuActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);//绑定微信
                break;
            case R.id.rl_btn_startlive:
                EnterLiveActivity.start(DuiZhangZhuanShuActivity.this);
                break;
            case R.id.rl_btn_game_start1://知识问答
                startWeb("http://www.tongbanapp.com/action/ac_coupon/questionAnswerGame");//知识问答链接
                break;
            case R.id.rl_btn_game_start2://图文游戏
                Intent intent = new Intent();
                intent.setClass(DuiZhangZhuanShuActivity.this,DuiZhangGameListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_btn_game_start3://场地游戏
                break;
            case R.id.rl_btn_game_start4://你画我猜
                break;
            case R.id.rl_btn_game_start5://队员分组
                break;
        }
    }
    private void startWeb(String string){
        Intent intent = new Intent();
        intent.setClass(DuiZhangZhuanShuActivity.this, RenWuWebActivity.class);
        intent.putExtra("url",string);
        startActivity(intent);
    }
    private void initUM() {
        mShareAPI = UMShareAPI.get(DuiZhangZhuanShuActivity.this);
        umAuthListener = new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {}
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                final String wx_unionid ;
                if (data!=null && data.size()>0){
                    wx_unionid = data.get("unionid");
                    for (Map.Entry<String,String> entry : data.entrySet()){
                        Log.d("weixindenglu::://KEY:",entry.getKey()+"||Value:"+entry.getValue());
                    }
                    Log.d("weixindenglu:UNIONID",wx_unionid);
                    ViseHttp.POST(NetConfig.addWXUnionid)
                            .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.addWXUnionid))
                            .addParam("unionid",wx_unionid)
                            .addParam("uid",spImp.getUID())
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200){
                                            toToast(DuiZhangZhuanShuActivity.this,"绑定成功！");
                                            if (TextUtils.isEmpty(spImp.getWXUnionID())){
                                                spImp.setWXUnionID(wx_unionid);
                                            }
                                            Log.d("weixindenglu:UNSPIMP:",spImp.getWXUnionID());
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
                // Logger.e("openid: " + data.get("uid"));
                // Logger.e("昵称: " + data.get("name"));
                // Logger.e("头像: " + data.get("iconurl"));
                // Logger.e("性别: " + data.get("gender"));
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {

            }
        };

    }
}
