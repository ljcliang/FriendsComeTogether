package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.TitleMessageOkDialog;
import com.yiwo.friendscometogether.dbmodel.DuiZhangFenZuDbModel;
import com.yiwo.friendscometogether.model.ActiveShareModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.JiangLiRenWuAdapter;
import com.yiwo.friendscometogether.newadapter.LiShuGongSiChooseAdapter;
import com.yiwo.friendscometogether.newadapter.TongBiPriceAdapter;
import com.yiwo.friendscometogether.newmodel.DuiZhangXuanZeHuoDongModel;
import com.yiwo.friendscometogether.newmodel.DuiZhangZhuanShuModel;
import com.yiwo.friendscometogether.newmodel.LiShuGongSiSearchModel;
import com.yiwo.friendscometogether.newmodel.TongBiPriceModel;
import com.yiwo.friendscometogether.pages.ApplyActivity;
import com.yiwo.friendscometogether.pages.UserAgreementActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.ShareUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.EnterLiveActivity;
import com.yiwo.friendscometogether.webpages.DuiZhangShangPuWebActivity;
import com.yiwo.friendscometogether.webpages.RenWuWebActivity;
import com.yiwo.friendscometogether.webpages.ShouRuMingXiWebActivity;
import com.yiwo.friendscometogether.webpages.YouHuiQuanWebActivity;
import com.yiwo.friendscometogether.widget.CustomDatePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

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
    @BindView(R.id.tv_huodong_tuanqi)
    TextView tvHuoDongTuanqi;
    @BindView(R.id.tv_live_time)
    TextView tv_live_time;
    @BindView(R.id.rv_jianglirenwu)
    RecyclerView rvJiangLiRenWu;
    @BindView(R.id.ll_jianglirenwu)
    LinearLayout ll_jianglirenwu;
    @BindView(R.id.tv_jindu)
    TextView tv_jindu;

    @BindView(R.id.iv_message_xuanzehuodong)
    ImageView ivTiShiHuoDong;
    @BindView(R.id.iv_game_tishi1)
    ImageView ivGameTiShi1;
    @BindView(R.id.iv_game_tishi2)
    ImageView ivGameTiShi2;
    @BindView(R.id.iv_game_tishi3)
    ImageView ivGameTiShi3;
    @BindView(R.id.iv_game_tishi4)
    ImageView ivGameTiShi4;
    @BindView(R.id.iv_game_tishi5)
    ImageView ivGameTiShi5;

    private JiangLiRenWuAdapter jiangLiRenWuAdapter;
    private CustomDatePicker customDatePicker;
    private String now;
    private SpImp spImp;
    private DuiZhangZhuanShuModel duiZhangZhuanShuModel;
    private UMShareAPI mShareAPI;
    private UMAuthListener umAuthListener;

    private RecyclerView rvChooseGongSi;
    private LiShuGongSiChooseAdapter liShuGongSiChooseAdapter;
    private List<DuiZhangZhuanShuModel.ObjBean.ShopNameBean> listLiShuGongSi = new ArrayList<>();
    private PopupWindow popupWindowLiShuGongSi;//选择隶属公司
    private List<DuiZhangZhuanShuModel.ObjBean.ShopNameBean> listSearchGongSiTemp = new ArrayList<>();
    private EditText edtSearch;
    private DuiZhangXuanZeHuoDongModel.ObjBean yiXuanHuoDongModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dui_zhang_zhuan_shu);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        tv_live_time.setText(TextUtils.isEmpty(spImp.getLiveTime())? "直播时间设置":spImp.getLiveTime());
        initUM();
        initDatePicker();
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
                                String strShopNames = "";
                                for (DuiZhangZhuanShuModel.ObjBean.ShopNameBean bean : duiZhangZhuanShuModel.getObj().getShopName()){
                                    if (TextUtils.isEmpty(strShopNames)){
                                        strShopNames = bean.getShopname();
                                    }else {
                                        strShopNames = strShopNames + "," +bean.getShopname();
                                    }
                                }
                                if (TextUtils.isEmpty(strShopNames)){
                                    tvShopName.setText("选择隶属公司");
                                }else {
                                    tvShopName.setText("隶属："+ strShopNames);
                                }
                                tvShopName.setSelected(true);
                                tvHuoDongTuanqi.setText(TextUtils.isEmpty(duiZhangZhuanShuModel.getObj().getPftitle())? "选择团期" : duiZhangZhuanShuModel.getObj().getPftitle());
                                tv_jindu.setText("随机奖励金 最高188元   完成 "+duiZhangZhuanShuModel.getObj().getJindu());
                                if (duiZhangZhuanShuModel.getObj().getMission().size()>0){
                                    ll_jianglirenwu.setVisibility(View.VISIBLE);
                                    jiangLiRenWuAdapter = new JiangLiRenWuAdapter(duiZhangZhuanShuModel.getObj().getMission(), new JiangLiRenWuAdapter.SharePf() {
                                        @Override
                                        public void share() {
                                            sharePf(duiZhangZhuanShuModel.getObj().getPfID());
                                        }
                                    });
                                    LinearLayoutManager manager = new LinearLayoutManager(DuiZhangZhuanShuActivity.this);
                                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                                    rvJiangLiRenWu.setLayoutManager(manager);
                                    rvJiangLiRenWu.setAdapter(jiangLiRenWuAdapter);
                                    if (duiZhangZhuanShuModel.getObj().getIfover().equals("1")){
//                                        tv_jindu.setTextColor(getResources().getColor(R.color.white_ffffff));
                                        tv_jindu.setBackgroundResource(R.drawable.bg_d84c37_30px);
                                    }else {
//                                        tv_jindu.setTextColor(getResources().getColor(R.color.black_101010));
                                        tv_jindu.setBackgroundResource(R.drawable.bg_d8d8d8_30px);
                                    }
                                }else {
                                    ll_jianglirenwu.setVisibility(View.GONE);
                                }
                                yiXuanHuoDongModel = new DuiZhangXuanZeHuoDongModel.ObjBean();
                                yiXuanHuoDongModel.setPfID(duiZhangZhuanShuModel.getObj().getPfID());
                                yiXuanHuoDongModel.setPfpic("");
                                yiXuanHuoDongModel.setPftitle(duiZhangZhuanShuModel.getObj().getPftitle());
                                yiXuanHuoDongModel.setPhase_id(duiZhangZhuanShuModel.getObj().getPhase_id());
                                yiXuanHuoDongModel.setPhase_begin_time(duiZhangZhuanShuModel.getObj().getBeginTime());
                                yiXuanHuoDongModel.setPhase_num(duiZhangZhuanShuModel.getObj().getBeginTime());
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

    @OnClick({R.id.rl_btn_bangding,R.id.rl_btn_startlive,R.id.rl_btn_setlive,R.id.rl_btn_xuanzehuodong,R.id.rl_back,R.id.ll_wodeshangpu,R.id.ll_shourumingxi,
            R.id.iv_message_xuanzehuodong,R.id.iv_game_tishi1,R.id.iv_game_tishi2,R.id.iv_game_tishi3,R.id.iv_game_tishi4,R.id.iv_game_tishi5,R.id.iv_zhanghu_tishi1,
            R.id.rl_btn_game_start1,R.id.rl_btn_game_start2,R.id.rl_btn_game_start3,R.id.rl_btn_game_start4,R.id.rl_btn_game_start5,
            R.id.tv_shop_name})
    public void onClick(View v){
        Intent intent = new Intent();
        AlertDialog.Builder builder = new AlertDialog.Builder(DuiZhangZhuanShuActivity.this);
        switch (v.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.ll_wodeshangpu:
                intent.setClass(DuiZhangZhuanShuActivity.this, DuiZhangShangPuWebActivity.class);
                intent.putExtra("url",duiZhangZhuanShuModel.getObj().getGoodsShop());
                startActivity(intent);
                break;
            case R.id.ll_shourumingxi:
                intent.setClass(DuiZhangZhuanShuActivity.this, ShouRuMingXiWebActivity.class);
                intent.putExtra("url",duiZhangZhuanShuModel.getObj().getComeInInfo());
                startActivity(intent);
                break;
            case R.id.rl_btn_bangding:
                mShareAPI.getPlatformInfo(DuiZhangZhuanShuActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);//绑定微信
                break;
            case R.id.rl_btn_setlive:
                customDatePicker.show(now);
                break;
            case R.id.rl_btn_startlive:
                EnterLiveActivity.start(DuiZhangZhuanShuActivity.this);
                break;
            case R.id.rl_btn_xuanzehuodong:
                XuanZeTuanQiActivity.startActivity(DuiZhangZhuanShuActivity.this,yiXuanHuoDongModel,duiZhangZhuanShuModel.getObj().getIfover());
                break;
            case R.id.rl_btn_game_start1://知识问答
                startWeb("http://www.tongbanapp.com/action/ac_coupon/questionAnswerGame");//知识问答链接
                break;
            case R.id.rl_btn_game_start2://图文游戏
                intent.setClass(DuiZhangZhuanShuActivity.this,DuiZhangGameListActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_btn_game_start3://场地游戏
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.rl_btn_game_start4://你画我猜
                startWeb("http://www.tongbanapp.com/action/ac_coupon/gameInfo");//其它链接
                break;
            case R.id.rl_btn_game_start5://队员分组
                if (yiXuanHuoDongModel == null|| TextUtils.isEmpty(yiXuanHuoDongModel.getPfID())||yiXuanHuoDongModel.getPfID() == null){
                    builder.setMessage("当前未选择活动，请选择活动！")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    XuanZeTuanQiActivity.startActivity(DuiZhangZhuanShuActivity.this,yiXuanHuoDongModel,duiZhangZhuanShuModel.getObj().getIfover());
                                }
                            })
                            .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    break;
                }
                youxiFenZu();
                break;
            case R.id.tv_shop_name:
                showPopupWindowChooseGongSi();
                break;
            case R.id.iv_message_xuanzehuodong:
                TitleMessageOkDialog titleMessageOkDialog = new TitleMessageOkDialog(DuiZhangZhuanShuActivity.this, "",
                        duiZhangZhuanShuModel.getObj().getMesMission(), "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog.show();
                break;
            case R.id.iv_game_tishi1:
//                duiZhangZhuanShuModel.getObj().getMesQuestion()
                TitleMessageOkDialog titleMessageOkDialog1 = new TitleMessageOkDialog(DuiZhangZhuanShuActivity.this, "",
                        duiZhangZhuanShuModel.getObj().getMesQuestion() , "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog1.show();
                break;
            case R.id.iv_game_tishi2:
                TitleMessageOkDialog titleMessageOkDialog2 = new TitleMessageOkDialog(DuiZhangZhuanShuActivity.this, "",
                        duiZhangZhuanShuModel.getObj().getMesPictxt(), "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog2.show();
                break;
            case R.id.iv_game_tishi3:
                TitleMessageOkDialog titleMessageOkDialog3 = new TitleMessageOkDialog(DuiZhangZhuanShuActivity.this, "",
                        duiZhangZhuanShuModel.getObj().getMesArea(), "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog3.show();
                break;
            case R.id.iv_game_tishi4:
                TitleMessageOkDialog titleMessageOkDialog4 = new TitleMessageOkDialog(DuiZhangZhuanShuActivity.this, "",
                        duiZhangZhuanShuModel.getObj().getMesGuess(), "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog4.show();
                break;
            case R.id.iv_game_tishi5:
                TitleMessageOkDialog titleMessageOkDialog5 = new TitleMessageOkDialog(DuiZhangZhuanShuActivity.this, "",
                        duiZhangZhuanShuModel.getObj().getMesGroup(), "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog5.show();
                break;
            case R.id.iv_zhanghu_tishi1:
                TitleMessageOkDialog titleMessageOkDialog6 = new TitleMessageOkDialog(DuiZhangZhuanShuActivity.this, "",
                        duiZhangZhuanShuModel.getObj().getMesWx(), "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog6.show();
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
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());

        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                ViseHttp.POST(NetConfig.settZhiBoTime)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.settZhiBoTime))
                        .addParam("uid", spImp.getUID())
                        .addParam("starttime",tv_live_time.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        spImp.setLiveTime(time);
                                        tv_live_time.setText(time);
                                        toToast(DuiZhangZhuanShuActivity.this,"保存成功");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                toToast(DuiZhangZhuanShuActivity.this,"保存失败");
                            }
                        });
            }
        }, now, "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 不显示时和分
        customDatePicker.setIsLoop(false); // 不允许循环滚动
    }
    private void showPopupWindowChooseGongSi(){
        View view = LayoutInflater.from(DuiZhangZhuanShuActivity.this).inflate(R.layout.popupwindow_sousuogongsi,null);
//        ScreenAdapterTools.getInstance().loadView(view);
        rvChooseGongSi = view.findViewById(R.id.rv_lishu_gongsi);
        LinearLayoutManager manager = new LinearLayoutManager(DuiZhangZhuanShuActivity.this){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        listLiShuGongSi.clear();
        listLiShuGongSi.addAll(duiZhangZhuanShuModel.getObj().getShopName());
        copySearchData2Temp();
        liShuGongSiChooseAdapter = new LiShuGongSiChooseAdapter(listLiShuGongSi);
        rvChooseGongSi.setLayoutManager(manager);
        rvChooseGongSi.setAdapter(liShuGongSiChooseAdapter);
        edtSearch = view.findViewById(R.id.edt_search);
        TextView btnSearch = view.findViewById(R.id.tv_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkChoosedGongsiListChange()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(DuiZhangZhuanShuActivity.this);
                    builder.setMessage("隶属公司有更改，是否保存？")
                            .setNegativeButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    saveLiShuGongSi(true);
                                }
                            }).setPositiveButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            searchLiShuGongSi();
                        }
                    }).show();
                }else {
                    searchLiShuGongSi();
                }
            }
        });
        TextView btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLiShuGongSi(false);

            }
        });
        popupWindowLiShuGongSi = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindowLiShuGongSi.setTouchable(true);
        popupWindowLiShuGongSi.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindowLiShuGongSi.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindowLiShuGongSi.setOutsideTouchable(true);
        popupWindowLiShuGongSi.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        // 设置popWindow的显示和消失动画
        popupWindowLiShuGongSi.setAnimationStyle(R.style.mypopwindow_anim_style);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindowLiShuGongSi.update();

        popupWindowLiShuGongSi.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }
    private void searchLiShuGongSi(){
        ViseHttp.POST(NetConfig.searchShop)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.searchShop))
                .addParam("uid",spImp.getUID())
                .addParam("keyWord",edtSearch.getText().toString())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                LiShuGongSiSearchModel model = gson.fromJson(data,LiShuGongSiSearchModel.class);
                                listLiShuGongSi.clear();
                                listLiShuGongSi.addAll(model.getObj());
                                copySearchData2Temp();
                                liShuGongSiChooseAdapter.notifyDataSetChanged();
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
    private void saveLiShuGongSi(boolean fromSearch) { // 判断是否为查询前保存
        String str_shopID = "";
        for (DuiZhangZhuanShuModel.ObjBean.ShopNameBean bean :listLiShuGongSi){
            if (bean.getCheckIn().equals("1")){
                if (TextUtils.isEmpty(str_shopID)){
                    str_shopID = bean.getId();
                }else {
                    str_shopID = str_shopID + "," +bean.getId();
                }
            }
        }
        Log.d("asdadsa",str_shopID);
        ViseHttp.POST(NetConfig.keepShop)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.keepShop))
                .addParam("uid",spImp.getUID())
                .addParam("shopID",str_shopID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                LiShuGongSiSearchModel model = gson.fromJson(data,LiShuGongSiSearchModel.class);
                                String str_name = "";
                                for (DuiZhangZhuanShuModel.ObjBean.ShopNameBean bean :model.getObj()){
                                    if (bean.getCheckIn().equals("1")){
                                        if (TextUtils.isEmpty(str_name)){
                                            str_name = bean.getShopname();
                                        }else {
                                            str_name = str_name + "," +bean.getShopname();
                                        }
                                    }
                                }
                                duiZhangZhuanShuModel.getObj().setShopName(model.getObj());//更新队长页  已选择的公司列表
                                if (TextUtils.isEmpty(str_name)){
                                    tvShopName.setText("选择隶属公司");
                                }else {
                                    tvShopName.setText("隶属："+ str_name);
                                }
                                if (fromSearch){
                                    searchLiShuGongSi();
                                }else{
                                    popupWindowLiShuGongSi.dismiss();
                                }
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

    private void copySearchData2Temp() { //储存查询出的公司，用于监听隶属状态是否 发生变化
        listSearchGongSiTemp.clear();
        for (DuiZhangZhuanShuModel.ObjBean.ShopNameBean bean : listLiShuGongSi){
            DuiZhangZhuanShuModel.ObjBean.ShopNameBean beanNew = new DuiZhangZhuanShuModel.ObjBean.ShopNameBean();
            beanNew.setId(bean.getId());
            beanNew.setCheckIn(bean.getCheckIn());
            beanNew.setShopname(bean.getShopname());
            listSearchGongSiTemp.add(beanNew);
        }
    }
    private void youxiFenZu() {
        Intent intent = new Intent(DuiZhangZhuanShuActivity.this,YouXiFenZuActivity.class);
        intent.putExtra("yiXuanHuoDongModel",yiXuanHuoDongModel);
        startActivity(intent);

    }

    private boolean checkChoosedGongsiListChange(){//判断列表选项框状态是否发生变化
        for (int  i = 0 ; i<listSearchGongSiTemp.size(); i++){
            Log.d(listSearchGongSiTemp.get(i).getShopname()+"asdasdasd:::"+listLiShuGongSi.get(i).getShopname(),listSearchGongSiTemp.get(i).getCheckIn()+"-----"+listLiShuGongSi.get(i).getCheckIn());
            if (!listSearchGongSiTemp.get(i).getCheckIn().equals(listLiShuGongSi.get(i).getCheckIn())){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(DuiZhangZhuanShuActivity.this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == XuanZeTuanQiActivity.REQUEST_CODE_XUAN_ZE_HUO_DONG && resultCode == 1){
            yiXuanHuoDongModel = (DuiZhangXuanZeHuoDongModel.ObjBean) data.getSerializableExtra("xuanzehuodong");
            if (yiXuanHuoDongModel != null){
//                tvHuoDongTuanqi.setText(yiXuanHuoDongModel.getPftitle() + "["+yiXuanHuoDongModel.getPhase_begin_time()+"]");
                initData();
           }
        }
    }
    private void sharePf(String pfID){

        ViseHttp.POST(NetConfig.shareMission)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.shareMission))
                .addParam("pfID", pfID)
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("saddsadsad",data);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        ViseHttp.POST(NetConfig.activeShareUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activeShareUrl))
                .addParam("id", pfID)
                .addParam("type", "0")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                final ActiveShareModel shareModel = gson.fromJson(data, ActiveShareModel.class);
                                new ShareAction(DuiZhangZhuanShuActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                        .setShareboardclickCallback(new ShareBoardlistener() {
                                            @Override
                                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                                ShareUtils.shareWeb(DuiZhangZhuanShuActivity.this, shareModel.getObj().getUrl()+"&uid="+spImp.getUID(), shareModel.getObj().getTitle(),
                                                        shareModel.getObj().getDesc(), shareModel.getObj().getImages(), share_media);
                                            }
                                        }).open();
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
