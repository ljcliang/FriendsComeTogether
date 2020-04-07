package com.yiwo.friendscometogether.pages;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.ApplyChooseDateModel;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.model.InvitationOkModel;
import com.yiwo.friendscometogether.model.Paymodel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.network.UMConfig;
import com.yiwo.friendscometogether.newadapter.ApplyHuoDongChooseDateAdapter;
import com.yiwo.friendscometogether.newadapter.ChooseDateAdapter;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.BigDecimalUtils;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.webpages.ApplyActivityAgreementWebActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.webpages.YouHuiQuanWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyActivity extends BaseActivity {
    @BindView(R.id.apply_name_et)
    TextView tvName;
    @BindView(R.id.apply_phone_et)
    EditText etPhoneNum;
    @BindView(R.id.apply_title_tv)
    TextView tvActiveTitle;
    @BindView(R.id.apply_time_tv)
    TextView tvTimeStart;
    @BindView(R.id.apply_time_tv_end)
    TextView tvTimeEnd;
    @BindView(R.id.apply_cost_tv)
    TextView tvPrice;
    @BindView(R.id.apply_vessel_ll)
    LinearLayout apply_vessel_ll;
    @BindView(R.id.apply_pay_ll)
    LinearLayout apply_pay_ll;
    @BindView(R.id.activity_apply_rl_back)
    RelativeLayout apply_back;
    @BindView(R.id.apply_btn)
    TextView apply_btn;
    @BindView(R.id.apply_num_ll)
    RelativeLayout apply_num_ll;
    @BindView(R.id.iv_jian)
    ImageView ivJian;
    @BindView(R.id.iv_jia)
    ImageView ivJia;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_pay_decs)
    TextView tvPayDecs;
    @BindView(R.id.sex)
    TextView tvSex;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.age)
    TextView tvAge;
    @BindView(R.id.issingle)
    TextView tvIssingle;
    @BindView(R.id.city)
    TextView tvCity;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.all_price)
    TextView tvAllPrice;
    @BindView(R.id.online_pay)
    TextView tvOnlinePay;
    @BindView(R.id.apply_ll_is_noname)
    LinearLayout llIsNoname;
    @BindView(R.id.apply_is_noname)
    TextView tvIsNoname;
    @BindView(R.id.rv_choose_date)
    RecyclerView rv_choose_date;

    @BindView(R.id.ll_qitayaoqiu)
    LinearLayout llQiTaYaoQiu;
    @BindView(R.id.tv_qitayaoqiu)
    TextView tvQiTaYaoQiu;

    @BindView(R.id.cb_allow)
    CheckBox cb_allow;
    @BindView(R.id.tv_allow_agreement)
    TextView tvAllowAgreement;
    @BindView(R.id.ll_allow_agreement)
    LinearLayout llAllowAgreement;

    @BindView(R.id.tv_text_youhuiquan)
    TextView tv_text_youhuiquan;
    @BindView(R.id.apply_ll_youhuiquan)
    LinearLayout apply_ll_youhuiquan;
    private String yourChoice = "";
    private int payState = 0;
    private String pfID = "0";
    private String if_pay = "0";
    private SpImp spImp;
    private IWXAPI api;

    private int num = 1;
    private String money;
    private Double perMoney;
    private Double youhui_money = 0.00;
    private String youhuiquan_id = "";

    private List<ApplyChooseDateModel.ObjBean> applyChooseDateModels;
    private ApplyHuoDongChooseDateAdapter applyHuoDongChooseDateAdapter;
    private String yqid = "0";
    /**
     * 是否匿名
     */
    private String isNoname = "";
    private String isNonameChoice = "";

    private static final int SDK_PAY_FLAG = 1;

    private int chooseDateIndex =0; // 选择日期index
    private String chooseDateID;
    private boolean isAlowAgreement = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        api = WXAPIFactory.createWXAPI(this, null);
        ButterKnife.bind(this);
        spImp = new SpImp(ApplyActivity.this);

        String id = getIntent().getStringExtra("id");
        String tid = getIntent().getStringExtra("tid");
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(tid)) {//邀请入口进入该界面
            String phase_id = getIntent().getStringExtra("phase_id");
            getShowViewTwo(id, tid,phase_id);
        } else {
            getShowView();
        }
        cb_allow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isAlowAgreement = true;
                    tvOnlinePay.setBackgroundResource(R.color.red_d84c37);
                    apply_btn.setBackgroundResource(R.color.red_d84c37);
                }else {
                    isAlowAgreement = false;
                    tvOnlinePay.setBackgroundResource(R.color.gray_c4ced3);
                    apply_btn.setBackgroundResource(R.color.gray_c4ced3);
                }
            }
        });
        Spannable string = new SpannableString("我已阅读并同意");
        // 前景色
        string.setSpan(new ForegroundColorSpan(Color.parseColor("#d84c37")), 0, string.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

//        Spannable string1 = new SpannableString("预定须知、旅游合同、特别预定提示、安全提示");
        Spannable string1 = new SpannableString("预定须知、旅游合同等");
        // 前景色
        string1.setSpan(new ForegroundColorSpan(Color.parseColor("#008B00")), 0, string1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvAllowAgreement.setText(string);
        tvAllowAgreement.append(string1);
    }

    private void getShowViewTwo(String id, String tid, final String phase_id) {

        yqid = id;

        ViseHttp.POST(NetConfig.invitationOkUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.invitationOkUrl))
                .addParam("id", tid)
                .addParam("yqid", id)
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                InvitationOkModel model = gson.fromJson(data, InvitationOkModel.class);
                                etPhoneNum.setText(model.getObj().getPhone());
                                tvNum.setText(num + "");
                                if_pay = model.getObj().getPfspendtype();
                                Log.i("789456", if_pay);
                                String title = model.getObj().getPftitle();
                                String sex = model.getObj().getPfpeoplesex();
                                String name = model.getObj().getUsername();
                                pfID = model.getObj().getId();
                                tvActiveTitle.setText(title);
                                tvName.setText(name);
                                tvSex.setText(sex);
                                String pic = model.getObj().getPic();
                                Glide.with(ApplyActivity.this).load(pic).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(ivTitle);
                                String age = model.getObj().getPfagebegin();
                                tvAge.setText(age + "岁");
                                String issingle = model.getObj().getPfmarry();
                                llQiTaYaoQiu.setVisibility(View.GONE);//其他要求暂时隐藏（邀请接口无此字段）
                                tvIssingle.setText(issingle);
                                String city = model.getObj().getPfaddress();
                                tvCity.setText("活动地点: " + city);
                                apply_ll_youhuiquan.setVisibility(View.GONE);
                                if (sex.equals("无限制")) {
                                    apply_num_ll.setVisibility(View.VISIBLE);
                                }
                                if (if_pay.equals("2")) {
                                    setApplyPaymentView(1);
                                } else if (if_pay.equals("0")) {
                                    tvPayDecs.setText("现场支付");
                                } else if (if_pay.equals("1")) {
                                    apply_num_ll.setVisibility(View.GONE);
                                    tvPayDecs.setText("邀请人支付");
                                }else if (if_pay.equals("3")){
                                    tvPayDecs.setText("免费参加");
                                }
                                //初始化选择
                                ViseHttp.POST(NetConfig.getPhase)
                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getPhase))
                                        .addParam("pfID",pfID)
                                        .request(new ACallback<String>() {

                                            @Override
                                            public void onSuccess(String data) {
                                                Log.d("132132",data);
                                                try {
                                                    JSONObject jsonObject = new JSONObject(data);
                                                    if (jsonObject.getInt("code") == 200){
                                                        Gson gson = new Gson();
                                                        ApplyChooseDateModel model = gson.fromJson(data,ApplyChooseDateModel.class);
                                                        for (int i = 0;i<model.getObj().size();i++){
                                                            if (phase_id.equals(model.getObj().get(i).getPhase_id())){
                                                                chooseDateIndex = i;
                                                            }
                                                        }
                                                        chooseDateID = model.getObj().get(chooseDateIndex).getPhase_id();
                                                        String price = model.getObj().get(chooseDateIndex).getPhase_price();
                                                        perMoney = Double.valueOf(price);
                                                        money = BigDecimalUtils.mul(perMoney+"",num+"",2);

//        String begin_time = getIntent().getStringExtra("begin_time");
                                                        tvPrice.setText("¥" + price + "元/人");
                                                        setTextMoney();
                                                        //如果是不需要支付的 不显示 金额 直接报名（邀请人支付情况）
                                                        if(!if_pay.equals("2")){
                                                            apply_btn.setVisibility(View.VISIBLE);
                                                            llPay.setVisibility(View.INVISIBLE);
                                                        }
                                                        tvTimeStart.setText("出发时间: " + model.getObj().get(chooseDateIndex).getPhase_begin_time());
                                                        tvTimeEnd.setText("结束时间: "+model.getObj().get(chooseDateIndex).getPhase_over_time());
                                                        applyChooseDateModels = model.getObj();

                                                        LinearLayoutManager manager = new LinearLayoutManager(ApplyActivity.this){
                                                            @Override
                                                            public boolean canScrollHorizontally() {
                                                                return true;
                                                            }
                                                        };
                                                        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                                        rv_choose_date.setLayoutManager(manager);
                                                        applyChooseDateModels.get(chooseDateIndex).setSelected(true);
                                                        applyHuoDongChooseDateAdapter = new ApplyHuoDongChooseDateAdapter(applyChooseDateModels);
                                                        //不允许选择
                                                        applyHuoDongChooseDateAdapter.setChooseDatePostionListen(new ApplyHuoDongChooseDateAdapter.ChooseDatePostionListener() {
                                                            @Override
                                                            public void choosePOstion(int postion) {
//                                                                chooseDateIndex = postion;
//                                                                for (int i =0;i<applyChooseDateModels.size();i++){
//                                                                    applyChooseDateModels.get(i).setSelected(false);
//                                                                }
//                                                                applyChooseDateModels.get(postion).setSelected(true);
//                                                                applyHuoDongChooseDateAdapter.notifyDataSetChanged();
//                                                                ApplyChooseDateModel.ObjBean phaseBean = applyChooseDateModels.get(postion);
//                                                                chooseDateID = phaseBean.getPhase_id();
//                                                                String price = phaseBean.getPhase_price();
//                                                                perMoney = Double.valueOf(price);
//                                                                money = BigDecimalUtils.mul(perMoney+"",num+"",2);
//
////        String begin_time = getIntent().getStringExtra("begin_time");
//
//                                                                tvPrice.setText("¥" + price + "元/人");
//                                                                setTextMoney();
//                                                                tvTimeStart.setText("出发时间: " + phaseBean.getPhase_begin_time());
//                                                                tvTimeEnd.setText("结束时间: "+phaseBean.getPhase_over_time());
                                                            }
                                                        });
                                                        rv_choose_date.setAdapter(applyHuoDongChooseDateAdapter);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            @Override
                                            public void onFail(int errCode, String errMsg) {
                                                Log.d("132132",errCode+"//"+errMsg);
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

    @OnClick({R.id.activity_apply_rl_back, R.id.apply_btn, R.id.iv_jian, R.id.iv_jia, R.id.online_pay, R.id.apply_ll_is_noname,R.id.ll_allow_agreement,R.id.apply_ll_youhuiquan})
//,R.id.apply_sex_tv
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.activity_apply_rl_back:
                finish();
                break;
            case R.id.apply_btn:
                apply();
                break;
            case R.id.iv_jian:
                if (num > 1) {
                    num = num - 1;
                    tvNum.setText(num + "");
                    money = BigDecimalUtils.sub(money+"", perMoney+"",2);
                    setTextMoney();
                }
                break;
            case R.id.iv_jia:
                num = num + 1;
                tvNum.setText(num + "");
                money = BigDecimalUtils.add(money+"" , perMoney+"",2);
                setTextMoney();
                break;
            case R.id.online_pay:
                apply();
                break;
            case R.id.apply_ll_is_noname:
                //是否匿名
                final String[] items1 = {"是", "否"};

                AlertDialog.Builder singleChoiceDialog1 =
                        new AlertDialog.Builder(ApplyActivity.this);
                singleChoiceDialog1.setTitle("请选择是否匿名");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog1.setSingleChoiceItems(items1, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isNonameChoice = items1[which];
                            }
                        });
                singleChoiceDialog1.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (TextUtils.isEmpty(isNonameChoice)) {
                                    tvIsNoname.setText("是");
                                } else {
                                    tvIsNoname.setText(isNonameChoice);
                                    isNonameChoice = "";
                                }
                            }
                        });
                singleChoiceDialog1.show();
                break;
            case R.id.ll_allow_agreement:
                Intent intent = new Intent();
                intent.setClass(ApplyActivity.this, ApplyActivityAgreementWebActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.apply_ll_youhuiquan:
                Intent intent0 = new Intent();
                intent0.setClass(ApplyActivity.this,YouHuiQuanWebActivity.class);
                String url = NetConfig.BaseUrl+"action/ac_coupon/canUseCoupon?pfID="+pfID+"&userID="+spImp.getUID();
                Log.d("urlurlurl",url);
                intent0.putExtra("url",url);
                startActivityForResult(intent0,2);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1){//同意协议
            cb_allow.setChecked(true);
            isAlowAgreement = true;
            tvOnlinePay.setBackgroundResource(R.color.red_d84c37);
            apply_btn.setBackgroundResource(R.color.red_d84c37);
        }else if (requestCode == 2 && resultCode == 1){//选择优惠券
            useYouHuiQUan(data.getStringExtra("youhui_id"),data.getStringExtra("youhui_price"));
        }
    }

    private void useYouHuiQUan(String youhuiquan_id,String youhuiquan_price) {
        youhui_money = Double.parseDouble(youhuiquan_price);
        this.youhuiquan_id = youhuiquan_id;
        tv_text_youhuiquan.setText("已选择优惠券 优惠：¥"+ youhui_money);
        setTextMoney();
    }

    public void setApplyPaymentView(int state) {
        if (state == 1) {
            apply_pay_ll.setVisibility(View.GONE);
            View v = LayoutInflater.from(this).inflate(R.layout.include_payment, null);
            ScreenAdapterTools.getInstance().loadView(v);
            RelativeLayout wachat_pay = (RelativeLayout) v.findViewById(R.id.wechat_pay);
            RelativeLayout alipay_alipay = (RelativeLayout) v.findViewById(R.id.alipay_pay);
            final ImageView wechatIv = (ImageView) v.findViewById(R.id.wechatIv);
            final ImageView alipayIv = (ImageView) v.findViewById(R.id.alipayIv);
            wachat_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payState = 0;
                    wechatIv.setImageResource(R.mipmap.apply_true);
                    alipayIv.setImageResource(R.mipmap.apply_false);
                }
            });
            alipay_alipay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payState = 1;
                    wechatIv.setImageResource(R.mipmap.apply_false);
                    alipayIv.setImageResource(R.mipmap.apply_true);
                }
            });
            apply_vessel_ll.addView(v);
            apply_btn.setVisibility(View.INVISIBLE);
            apply_ll_youhuiquan.setVisibility(View.VISIBLE);
            llPay.setVisibility(View.VISIBLE);
        }
    }

    public void getShowView() {

        String tel = getIntent().getStringExtra("tel");
        etPhoneNum.setText(tel);
        tvNum.setText(num + "");
        if_pay = getIntent().getStringExtra("if_pay");
        Log.i("789456", if_pay);
        String title = getIntent().getStringExtra("title");
        String sex = getIntent().getStringExtra("sex");
        String name = getIntent().getStringExtra("name");
        pfID = getIntent().getStringExtra("pfID");
        tvActiveTitle.setText(title);
        tvName.setText(name);
        tvSex.setText(sex);
        String pic = getIntent().getStringExtra("pic");
        Glide.with(ApplyActivity.this).load(pic).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(ivTitle);
        String age = getIntent().getStringExtra("age");
        tvAge.setText(age + "岁");
        if (getIntent().getStringExtra("Pfexplain") == null ||getIntent().getStringExtra("Pfexplain").equals("") ){
            llQiTaYaoQiu.setVisibility(View.GONE);
        }else {
            llQiTaYaoQiu.setVisibility(View.VISIBLE);
            tvQiTaYaoQiu.setText(getIntent().getStringExtra("Pfexplain"));
        }
        String issingle = getIntent().getStringExtra("issingle");
        tvIssingle.setText(issingle);
        String city = getIntent().getStringExtra("city");
        tvCity.setText("活动地点: " + city);

        if (sex.equals("无限制")) {
            apply_num_ll.setVisibility(View.VISIBLE);
        }

        if (if_pay.equals("2")) {
            setApplyPaymentView(1);
        } else if (if_pay.equals("0")) {
            tvPayDecs.setText("现场支付");
        } else if (if_pay.equals("1")) {
            tvPayDecs.setText("邀请人支付");
        }else if (if_pay.equals("3")){
            tvPayDecs.setText("免费参加");
        }
        chooseDateIndex = getIntent().getIntExtra("choose_date_intex",0);
        ViseHttp.POST(NetConfig.getPhase)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getPhase))
                .addParam("pfID",pfID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("132132",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                ApplyChooseDateModel model = gson.fromJson(data,ApplyChooseDateModel.class);
                                chooseDateID = model.getObj().get(chooseDateIndex).getPhase_id();
                                String price = model.getObj().get(chooseDateIndex).getPhase_price();
                                perMoney = Double.valueOf(price);
                                money = BigDecimalUtils.mul(perMoney+"",num+"",2);
//        String begin_time = getIntent().getStringExtra("begin_time");
                                tvPrice.setText("¥" + price + "元/人");
                                setTextMoney();
                                tvTimeStart.setText("出发时间: " + model.getObj().get(chooseDateIndex).getPhase_begin_time());
                                tvTimeEnd.setText("结束时间: "+model.getObj().get(chooseDateIndex).getPhase_over_time());
                                applyChooseDateModels = model.getObj();

                                LinearLayoutManager manager = new LinearLayoutManager(ApplyActivity.this){
                                    @Override
                                    public boolean canScrollHorizontally() {
                                        return true;
                                    }
                                };
                                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                rv_choose_date.setLayoutManager(manager);
                                applyChooseDateModels.get(chooseDateIndex).setSelected(true);
                                applyHuoDongChooseDateAdapter = new ApplyHuoDongChooseDateAdapter(applyChooseDateModels);
                                applyHuoDongChooseDateAdapter.setChooseDatePostionListen(new ApplyHuoDongChooseDateAdapter.ChooseDatePostionListener() {
                                    @Override
                                    public void choosePOstion(int postion) {
                                        chooseDateIndex = postion;
                                        for (int i =0;i<applyChooseDateModels.size();i++){
                                            applyChooseDateModels.get(i).setSelected(false);
                                        }
                                        applyChooseDateModels.get(postion).setSelected(true);
                                        applyHuoDongChooseDateAdapter.notifyDataSetChanged();
                                        ApplyChooseDateModel.ObjBean phaseBean = applyChooseDateModels.get(postion);
                                        chooseDateID = phaseBean.getPhase_id();
                                        String price = phaseBean.getPhase_price();
                                        perMoney = Double.valueOf(price);
                                        money = BigDecimalUtils.mul(perMoney+"",num+"",2);
//        String begin_time = getIntent().getStringExtra("begin_time");
                                        tvPrice.setText("¥" + price + "元/人");
                                        setTextMoney();
                                        tvTimeStart.setText("出发时间: " + phaseBean.getPhase_begin_time());
                                        tvTimeEnd.setText("结束时间: "+phaseBean.getPhase_over_time());
                                    }
                                });
                                rv_choose_date.setAdapter(applyHuoDongChooseDateAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Log.d("132132",errCode+"//"+errMsg);
                    }
                });
    }

    public void apply() {
        if (!isAlowAgreement){
            toToast(ApplyActivity.this,"请阅读旅游合同等内容");
            return;
        }
        String user_id = spImp.getUID();
        if (user_id.equals("0")) {
            startActivity(new Intent(ApplyActivity.this, LoginActivity.class));
        } else if (!StringUtils.isPhoneNumberValid(etPhoneNum.getText().toString())) {
            toToast(ApplyActivity.this, "请输入正确的手机号");
        } else {
            String peopleNum = "1";
            if (!TextUtils.isEmpty(tvNum.getText().toString())) {
                peopleNum = tvNum.getText().toString();
                Log.e("222222", peopleNum);
            }
            ViseHttp.POST(NetConfig.applyActivityUrl)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.applyActivityUrl))
                    .addParam("noname", tvIsNoname.getText().toString().equals("是") ? "1" : "0")
                    .addParam("user_id", user_id)
                    .addParam("num", peopleNum)
                    .addParam("pfid", pfID)
                    .addParam("phase_id",chooseDateID )
                    .addParam("phone", etPhoneNum.getText().toString())
                    .addParam("need_paytype", payState + "")
                    .addParam("id", yqid)
                    .addParam("myCouponID",youhuiquan_id)
                    .request(new ACallback<String>() {
                        @Override
                        public void onFail(int errCode, String errMsg) {
                            Log.e("222", errCode+""+errMsg);
                        }

                        @Override
                        public void onSuccess(String data) {
                            Log.e("222", data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Paymodel paymodel = new Gson().fromJson(data, Paymodel.class);
                                    toToast(ApplyActivity.this, "微信支付");
                                    wxPay(paymodel.getObj());
                                }else if(jsonObject.getInt("code") == 300){
                                    toToast(ApplyActivity.this, "支付宝支付");
                                    Log.e("123123", jsonObject.optString("obj"));
                                    aliPay(jsonObject.optString("obj"));
                                } else if (jsonObject.getInt("code") == 201) {
                                    toToast(ApplyActivity.this, "报名成功");
                                    finish();
                                } else if (jsonObject.getInt("code") == 400) {
                                    toToast(ApplyActivity.this, jsonObject.getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

        }
    }

    public void wxPay(Paymodel.ObjBean model) {
        api.registerApp(UMConfig.WECHAT_APPID);
        PayReq req = new PayReq();
        req.appId = model.getAppId();
        req.partnerId = model.getPartnerId();
        req.prepayId = model.getPrepayId();
        req.nonceStr = model.getNonceStr();
        req.timeStamp = model.getTimestamp() + "";
        req.packageValue = model.getPackageX();
        req.sign = model.getSign();
        req.extData = "app data";
        api.sendReq(req);
        finish();
    }

    public void aliPay(String info) {
        final String orderInfo = info;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ApplyActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    if(result.get("resultStatus").equals("9000")){
                        Toast.makeText(ApplyActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
            }
        }

    };
    private void  setTextMoney(){
        String text_money = BigDecimalUtils.sub(money,youhui_money+"",2);
        if (BigDecimalUtils.compare(text_money,"0.00")){
            apply_btn.setVisibility(View.INVISIBLE);
            llPay.setVisibility(View.VISIBLE);
            tvAllPrice.setText("¥" + text_money);
        }else {
            apply_btn.setVisibility(View.VISIBLE);
            llPay.setVisibility(View.INVISIBLE);
        }
    }

}
