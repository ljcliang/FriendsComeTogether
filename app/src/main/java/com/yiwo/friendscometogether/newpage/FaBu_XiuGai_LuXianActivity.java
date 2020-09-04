package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.TitleMessageOkDialog;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.FaBuLuXianLabelAdapter;
import com.yiwo.friendscometogether.newadapter.FaBuLuXianQiShuAdapter;
import com.yiwo.friendscometogether.newadapter.LabelAdapter;
import com.yiwo.friendscometogether.newadapter.ShangPinLabelAdapter;
import com.yiwo.friendscometogether.newmodel.FaBuLuXianQiShuModel;
import com.yiwo.friendscometogether.newmodel.Fabu_Xiugai_LuXian_model;
import com.yiwo.friendscometogether.pages.MyInformationActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.SolveEditTextScrollClash;
import com.yiwo.friendscometogether.utils.StringUtils;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.widget.CustomDatePicker_ljc;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

public class FaBu_XiuGai_LuXianActivity extends TakePhotoActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_first_image)
    ImageView ivFirstImage;
    @BindView(R.id.rl_choose_first_image)
    RelativeLayout rlChooseFirstImage;
    @BindView(R.id.edt_input_title)
    EditText edtInputTitle;
    @BindView(R.id.edt_input_content)
    EditText edtInputContent;
    @BindView(R.id.iv_tishi_guanjianci)
    ImageView ivTishiGuanjianci;
    @BindView(R.id.edt_input_guanjianci)
    EditText edtInputGuanjianci;
    @BindView(R.id.edt_input_jihedidian)
    EditText edtInputJihedidian;
    @BindView(R.id.edt_input_xingchengdidian)
    EditText edtInputXingchengdidian;
    @BindView(R.id.rv_phase)
    RecyclerView rvPhase;
    @BindView(R.id.edt_input_goumaixuzhi)
    EditText edtInputGoumaixuzhi;
    @BindView(R.id.edt_input_feiyongbaohan)
    EditText edtInputFeiyongbaohan;
    @BindView(R.id.edt_input_feiyongbuhan)
    EditText edtInputFeiyongbuhan;
    @BindView(R.id.edt_input_wenxintishi)
    EditText edtInputWenxintishi;
    @BindView(R.id.rv_label)
    RecyclerView rvLabel;
    @BindView(R.id.btn_jixuchaungzuo)
    TextView btnJixuchaungzuo;
    @BindView(R.id.btn_lijifabu)
    TextView btnLijifabu;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    public final static String PF_ID = "pf_id";
    public final static String DATE_PICKER_TYPE_KEY = "DATE_PICKER_TYPE_KEY";//0为开始时间，1为结束时间
    public final static String DATE_PICKER_POSITION__KEY = "DATE_PICKER_POSITION__KEY";//需要更改期数开始、结束时间的索引
    @BindView(R.id.edt_input_person_num_min)
    EditText edtInputPersonNumMin;
    @BindView(R.id.edt_input_person_num_max)
    EditText edtInputPersonNumMax;
    @BindView(R.id.edt_input_person_age_min)
    EditText edtInputPersonAgeMin;
    @BindView(R.id.edt_input_person_age_max)
    EditText edtInputPersonAgeMax;
    @BindView(R.id.tv_input_person_sex)
    TextView tvInputPersonSex;
    @BindView(R.id.rl_input_sex)
    RelativeLayout rlInputSex;
    @BindView(R.id.tv_input_person_single)
    TextView tvInputPersonSingle;
    @BindView(R.id.rl_input_single)
    RelativeLayout rlInputSingle;
    @BindView(R.id.edt_input_order_ask)
    EditText edtInputOrderAsk;
    private List<Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean> listQiShu = new ArrayList<>();
    private FaBuLuXianQiShuAdapter faBuLuXianQiShuAdapter;
    private FaBuLuXianQiShuAdapter.EventListenner fabuqishuListenner;
    private List<UserLabelModel.ObjBean> listLabel = new ArrayList<>();//标签
    private FaBuLuXianLabelAdapter faBuLuXianLabelAdapter;
    private List<String> listChoosedLabel;
    private String strChoosedLabels = "";
    private static final int REQUEST_CODE = 0x00000011;
    private String firstImageUrl = "";

    private CustomDatePicker_ljc customDatePicker1;
    private String now;

    private String chooseSex = "0"; // 0不限 1男 2女
    private String chooseSingle = "0"; // 单身要求 0不限  1单身
    private SpImp spImp;
    private boolean isFabu = false;
    private String pfId = "0";
    private Fabu_Xiugai_LuXian_model.ObjBean xiuGaiBean ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu__xiu_gai__lu_xian);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initEdit();
        initRv();
        initLabelData();
        initDatePicker();
        if (getIntent().getStringExtra(PF_ID) == null || getIntent().getStringExtra(PF_ID).equals("")) {
            tvTitle.setText("发布路线");
//            btnLijifabu.setText("发布");
            isFabu = true;
        } else {
            tvTitle.setText("修改路线");
//            btnLijifabu.setText("修改");
            pfId = getIntent().getStringExtra(PF_ID);
            isFabu = false;
        }
    }

    private void initLabelData() {
        listChoosedLabel = new ArrayList<>();
        ViseHttp.POST(NetConfig.userLabel)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userLabel))
                .addParam("type", "0")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                UserLabelModel userLabelModel = gson.fromJson(data, UserLabelModel.class);
                                listLabel.clear();
                                listLabel.addAll(userLabelModel.getObj());
                                faBuLuXianLabelAdapter.notifyDataSetChanged();
                                if (!isFabu){
                                    initData();
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

    private void initRv() {
        //期数
        if (listQiShu.size() == 0) {
            listQiShu.add(new Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean());
        }
        fabuqishuListenner = new FaBuLuXianQiShuAdapter.EventListenner() {
            @Override
            public void deleteItem(int pos) {
                listQiShu.remove(pos);
                faBuLuXianQiShuAdapter.notifyDataSetChanged();
            }

            @Override
            public void changeStartTime(int pos) {
                Bundle bundle = new Bundle();
                bundle.putInt(DATE_PICKER_TYPE_KEY, 0);
                bundle.putInt(DATE_PICKER_POSITION__KEY, pos);
                customDatePicker1.show(now, bundle);
            }

            @Override
            public void changeEndTime(int pos) {
                Bundle bundle = new Bundle();
                bundle.putInt(DATE_PICKER_TYPE_KEY, 1);
                bundle.putInt(DATE_PICKER_POSITION__KEY, pos);
                customDatePicker1.show(now, bundle);
            }

            @Override
            public void changeApplyTime(int pos) {
                Bundle bundle = new Bundle();
                bundle.putInt(DATE_PICKER_TYPE_KEY, 2);
                bundle.putInt(DATE_PICKER_POSITION__KEY, pos);
                customDatePicker1.show(now, bundle);
            }

            @Override
            public void changeWoShiDaiDui(int pos) {
                listQiShu.get(pos).setIfCaptain(listQiShu.get(pos).getIfCaptain().equals("1") ? "0":"1");
                faBuLuXianQiShuAdapter.notifyDataSetChanged();
            }

            @Override
            public void changePrice(int pos, String string) {
                listQiShu.get(pos).setPhase_price(string);
                Log.d("ssaassddda",pos+"//、、"+string);
//                faBuLuXianQiShuAdapter.notifyDataSetChanged();
            }

            @Override
            public void changeFenXiaoTiCheng(int pos, String string) {
                Log.d("ssaassddda",pos+"//"+string);
                listQiShu.get(pos).setUserBonus(string);
//                faBuLuXianQiShuAdapter.notifyDataSetChanged();
            }
        };
        faBuLuXianQiShuAdapter = new FaBuLuXianQiShuAdapter(listQiShu,fabuqishuListenner);
        LinearLayoutManager managerPh = new LinearLayoutManager(FaBu_XiuGai_LuXianActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        managerPh.setOrientation(LinearLayoutManager.VERTICAL);
        rvPhase.setLayoutManager(managerPh);
        rvPhase.setAdapter(faBuLuXianQiShuAdapter);
        //标签
        //标签rv---------------------------------------------------------------------------------
        faBuLuXianLabelAdapter = new FaBuLuXianLabelAdapter(listLabel, new FaBuLuXianLabelAdapter.OnItemChoosed() {
            @Override
            public void onChoosed(int pos) {
                int choosed_num = 0;
                for (int i = 0 ;i<listLabel.size();i++){
                    if (listLabel.get(i).isChoose()){
                        choosed_num ++;
                    }
                }
                if (choosed_num>=2&&!listLabel.get(pos).isChoose()){
                    return;
                }else {
                    listLabel.get(pos).setChoose(!listLabel.get(pos).isChoose());
                    faBuLuXianLabelAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onLongClick(int pos) {
//                Intent intent = new Intent();
//                intent.setClass(FaBu_XiuGaiShangPinActivity.this,ShangPinLabelEditActivity.class);
//                intent.putExtra(ShangPinLabelEditActivity.EDIT_LABEL_BEAN,listShangPinLabel.get(pos));
//                startActivityForResult(intent,REQUEST_CODE_EDIT_LABEL);
            }
        });
        GridLayoutManager managerLabel = new GridLayoutManager(FaBu_XiuGai_LuXianActivity.this,4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        managerLabel.setOrientation(LinearLayoutManager.VERTICAL);
        rvLabel.setLayoutManager(managerLabel);
        rvLabel.setAdapter(faBuLuXianLabelAdapter);
    }

    private void initData() {
        ViseHttp.POST(NetConfig.getActivityEditInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.shareMyShop))
                .addParam("uid",spImp.getUID())
                .addParam("pfID",pfId)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("dtadtatd",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                Fabu_Xiugai_LuXian_model model = gson.fromJson(data,Fabu_Xiugai_LuXian_model.class);
                                xiuGaiBean = model.getObj();
                                Glide.with(FaBu_XiuGai_LuXianActivity.this).load(xiuGaiBean.getPfpic()).into(ivFirstImage);
                                edtInputTitle.setText(xiuGaiBean.getPftitle());
                                edtInputContent.setText(xiuGaiBean.getActivity_ts());
                                edtInputXingchengdidian.setText(xiuGaiBean.getPfaddress());
                                edtInputJihedidian.setText(xiuGaiBean.getGo_address());
                                edtInputGuanjianci.setText(xiuGaiBean.getKeyWord());
                                edtInputPersonAgeMin.setText(xiuGaiBean.getAge_begin());
                                edtInputPersonAgeMax.setText(xiuGaiBean.getAge_end());
                                edtInputPersonNumMin.setText(xiuGaiBean.getMin_num());
                                edtInputPersonNumMax.setText(xiuGaiBean.getMax_num());
                                edtInputFeiyongbaohan.setText(xiuGaiBean.getFeiYongBaoHan());
                                edtInputFeiyongbuhan.setText(xiuGaiBean.getFeiYongBuHan());
                                edtInputOrderAsk.setText(xiuGaiBean.getPfexplain());
                                edtInputWenxintishi.setText(xiuGaiBean.getWenXinTiShi());
                                edtInputGoumaixuzhi.setText(xiuGaiBean.getBuyNeedKnow());
                                chooseSex = xiuGaiBean.getSex();
                                switch (chooseSex){
                                    case "1":
                                        tvInputPersonSex.setText("男");
                                        break;
                                    case "2":
                                        tvInputPersonSex.setText("女");
                                        break;
                                    default:
                                        tvInputPersonSex.setText("不限");
                                        break;
                                }
                                chooseSingle = xiuGaiBean.getSingle();
                                switch (chooseSingle){
                                    case "0":
                                        tvInputPersonSingle.setText("不限");
                                        break;
                                    case "1":
                                        tvInputPersonSingle.setText("单身");
                                        break;
                                }
                                listQiShu.clear();
                                listQiShu.addAll(xiuGaiBean.getPhaseInfos());
                                faBuLuXianQiShuAdapter.notifyDataSetChanged();
                                strChoosedLabels = xiuGaiBean.getActivityLabel();
                                for (String s :strChoosedLabels.split(",")){
                                    for (int i = 0 ;i<listLabel.size();i++){
                                        if (listLabel.get(i).getLID().equals(s)){
                                            listLabel.get(i).setChoose(true);
                                            faBuLuXianLabelAdapter.notifyDataSetChanged();
                                        }
                                    }
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

    private void initEdit() {
        edtInputContent.setOnTouchListener(new SolveEditTextScrollClash(edtInputContent));
        edtInputFeiyongbaohan.setOnTouchListener(new SolveEditTextScrollClash(edtInputFeiyongbaohan));
        edtInputFeiyongbuhan.setOnTouchListener(new SolveEditTextScrollClash(edtInputFeiyongbuhan));
        edtInputGoumaixuzhi.setOnTouchListener(new SolveEditTextScrollClash(edtInputGoumaixuzhi));
        edtInputWenxintishi.setOnTouchListener(new SolveEditTextScrollClash(edtInputWenxintishi));
        edtInputTitle.setOnTouchListener(new SolveEditTextScrollClash(edtInputTitle));

    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());

        customDatePicker1 = new CustomDatePicker_ljc(this, new CustomDatePicker_ljc.ResultHandler() {
            @Override
            public void handle(String time, Bundle bundle) { // 回调接口，获得选中的时间
                Log.d("sssaaaasss", time + "\n" + bundle);

                if (bundle != null) {
                    int position = bundle.getInt(DATE_PICKER_POSITION__KEY, -1);
                    if (position < listQiShu.size()) {
                        if (bundle.getInt(DATE_PICKER_TYPE_KEY, -1) == 0) {//开始时间
                            listQiShu.get(position).setBegin_time(time.substring(0, 10));
                            faBuLuXianQiShuAdapter.notifyDataSetChanged();
                        } else if (bundle.getInt(DATE_PICKER_TYPE_KEY, -1) == 1) {//结束时间
                            listQiShu.get(position).setEnd_time(time.substring(0, 10));
                            faBuLuXianQiShuAdapter.notifyDataSetChanged();
                        } else if (bundle.getInt(DATE_PICKER_TYPE_KEY, -1) == 2) {//报名截止时间
                            listQiShu.get(position).setSign_up_over_time(time.substring(0, 10));
                            faBuLuXianQiShuAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }, "1900-01-01 00:00", "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动
    }

    public static void open(Context context, String pfId) {
        Intent intent = new Intent();
        intent.putExtra(PF_ID, pfId);
        intent.setClass(context, FaBu_XiuGai_LuXianActivity.class);
        context.startActivity(intent);
    }

    public static void open(Context context) {
        open(context, "");
    }

    @OnClick({R.id.rl_back, R.id.rl_right,R.id.rl_choose_first_image, R.id.iv_tishi_guanjianci, R.id.rl_btn_add_price, R.id.btn_jixuchaungzuo, R.id.btn_lijifabu,R.id.rl_input_sex, R.id.rl_input_single})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rl_btn_add_price:
                listQiShu.add(new Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean());
                faBuLuXianQiShuAdapter = new FaBuLuXianQiShuAdapter(listQiShu,fabuqishuListenner);
                rvPhase.setAdapter(faBuLuXianQiShuAdapter);
                break;
            case R.id.rl_choose_first_image:
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
//                        .setCrop(true)
                        .setSingle(true)  //设置是否单选
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(FaBu_XiuGai_LuXianActivity.this, REQUEST_CODE); // 打开相册
                break;
            case R.id.iv_tishi_guanjianci:
                TitleMessageOkDialog titleMessageOkDialog1 = new TitleMessageOkDialog(FaBu_XiuGai_LuXianActivity.this, "",
                        "用于搜索查找时的“搜索内容”，请用“ | ”分割，如：云南|大理|东方瑞士...", "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog1.show();
                break;
            case R.id.btn_jixuchaungzuo:
                next();
                break;
            case R.id.btn_lijifabu:
            case R.id.rl_right:
                if (checkInfo()) {
                    Log.d("sssaaaasss", qiShuData2JSONString(listQiShu));
                    if (isFabu){
                        fabu();
                    }else {
                        xiugai();
                    }
                }
                ;
                break;
            case R.id.rl_input_sex:
                final String[] items = {"不限","男", "女"};
                final int[] choosedSexIndex = {0};
                AlertDialog.Builder singleChoiceDialog =
                        new AlertDialog.Builder(FaBu_XiuGai_LuXianActivity.this);
                singleChoiceDialog.setTitle("请选择性别要求");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog.setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                choosedSexIndex[0] = which;
                            }
                        });
                singleChoiceDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (choosedSexIndex[0]){
                                    case 0:
                                        chooseSex = "0";
                                        tvInputPersonSex.setText("不限");
                                        break;
                                    case 1:
                                        chooseSex = "1";
                                        tvInputPersonSex.setText("男");
                                        break;
                                    case 2:
                                        chooseSex = "2";
                                        tvInputPersonSex.setText("女");
                                        break;
                                }
                            }
                        });
                singleChoiceDialog.show();
                break;
            case R.id.rl_input_single:
                final String[] items1 = {"不限", "是"};
                final int[] choosedSingleIndex = {0};
                AlertDialog.Builder singleChoiceDialog1 =
                        new AlertDialog.Builder(FaBu_XiuGai_LuXianActivity.this);
                singleChoiceDialog1.setTitle("请选择单身要求");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog1.setSingleChoiceItems(items1, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("sasdasdas",which+"");
                                choosedSingleIndex[0] = which;
                            }
                        });
                singleChoiceDialog1.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (choosedSingleIndex[0]){
                                    case 0:
                                        chooseSingle = "0";
                                        tvInputPersonSingle.setText("不限");
                                        break;
                                    case 1:
                                        chooseSingle = "1";
                                        tvInputPersonSingle.setText("单身");
                                        break;
                                }
                            }
                        });
                singleChoiceDialog1.show();
                break;
        }
    }

    private void xiugai() {
        if (TextUtils.isEmpty(firstImageUrl)){//没有修改首图
            ViseHttp.UPLOAD(NetConfig.activityUpdateInfo)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activityUpdateInfo))
                    .addParam("uid", spImp.getUID())
                    .addParam("pfID",pfId)
                    .addParam("pftitle", edtInputTitle.getText().toString())
                    .addParam("pfaddress", edtInputXingchengdidian.getText().toString())
                    .addParam("go_address", edtInputJihedidian.getText().toString())
                    .addParam("keyWord", edtInputGuanjianci.getText().toString())
                    .addParam("activity_ts", edtInputContent.getText().toString())
                    .addParam("sex",chooseSex)
                    .addParam("min_num",edtInputPersonNumMin.getText().toString())
                    .addParam("max_num",edtInputPersonNumMax.getText().toString())
                    .addParam("single",chooseSingle)
                    .addParam("age_begin",edtInputPersonAgeMin.getText().toString())
                    .addParam("age_end",edtInputPersonAgeMax.getText().toString())
                    .addParam("pfexplain",edtInputOrderAsk.getText().toString())
                    .addParam("buyNeedKnow", edtInputGoumaixuzhi.getText().toString())
                    .addParam("feiYongBaoHan", edtInputFeiyongbaohan.getText().toString())
                    .addParam("feiYongBuHan", edtInputFeiyongbuhan.getText().toString())
                    .addParam("wenXinTiShi", edtInputWenxintishi.getText().toString())//
                    .addParam("activityLabel",strChoosedLabels)
                    .addParam("phaseInfos",qiShuData2JSONString(listQiShu))
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                Log.e("222", data);
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(FaBu_XiuGai_LuXianActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            Log.e("222", errCode+"::"+errMsg);
                        }
                    });
        }else {
            Observable<File> observable = Observable.create(new ObservableOnSubscribe<File>() {
                @Override
                public void subscribe(ObservableEmitter<File> e) throws Exception {
                    File file = new File(firstImageUrl);
                    e.onNext(file);
                }
            });
            Observer<File> observer = new Observer<File>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(File value) {

                    ViseHttp.UPLOAD(NetConfig.activityUpdateInfo)
                            .addHeader("Content-Type", "multipart/form-data")
                            .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.activityUpdateInfo))
                            .addParam("uid", spImp.getUID())
                            .addParam("pfID",pfId)
                            .addParam("pftitle", edtInputTitle.getText().toString())
                            .addParam("pfaddress", edtInputXingchengdidian.getText().toString())
                            .addParam("go_address", edtInputJihedidian.getText().toString())
                            .addParam("keyWord", edtInputGuanjianci.getText().toString())
                            .addParam("activity_ts", edtInputContent.getText().toString())
                            .addParam("sex",chooseSex)
                            .addParam("min_num",edtInputPersonNumMin.getText().toString())
                            .addParam("max_num",edtInputPersonNumMax.getText().toString())
                            .addParam("single",chooseSingle)
                            .addParam("age_begin",edtInputPersonAgeMin.getText().toString())
                            .addParam("age_end",edtInputPersonAgeMax.getText().toString())
                            .addParam("pfexplain",edtInputOrderAsk.getText().toString())
                            .addParam("buyNeedKnow", edtInputGoumaixuzhi.getText().toString())
                            .addParam("feiYongBaoHan", edtInputFeiyongbaohan.getText().toString())
                            .addParam("feiYongBuHan", edtInputFeiyongbuhan.getText().toString())
                            .addParam("wenXinTiShi", edtInputWenxintishi.getText().toString())//
                            .addParam("activityLabel",strChoosedLabels)
                            .addParam("phaseInfos",qiShuData2JSONString(listQiShu))
                            .addFile("file_img", value)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        Log.e("222", data);
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200) {
                                            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }else {
                                            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            observable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }

    }

    private boolean checkInfo() {
        if (isFabu){
            if (TextUtils.isEmpty(firstImageUrl)) {
                Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请选择首图！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (TextUtils.isEmpty(edtInputTitle.getText().toString())) {
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请填写标题！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtInputContent.getText().toString())) {
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请填写简介！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtInputGuanjianci.getText().toString())) {
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请填写关键词！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtInputJihedidian.getText().toString())) {
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请填写集合地点！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtInputXingchengdidian.getText().toString())) {
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请填写行程地点！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtInputGuanjianci.getText().toString())) {
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请填写关键词！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (listQiShu.size() > 0) {
            for (int i = 0;i<listQiShu.size();i++) {
                if (!checkQiShu(listQiShu.get(i))) {
                    return false;
                }
            }
        } else {
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "至少有一期！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!TextUtils.isEmpty(edtInputPersonNumMin.getText().toString())&&!TextUtils.isEmpty(edtInputPersonNumMax.getText().toString())){
            if (Integer.parseInt(edtInputPersonNumMin.getText().toString())>Integer.parseInt(edtInputPersonNumMax.getText().toString())){
                Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请填写正确的人数要求！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else if (TextUtils.isEmpty(edtInputPersonNumMin.getText().toString())||TextUtils.isEmpty(edtInputPersonNumMax.getText().toString())){
            edtInputPersonNumMin.setText("");
            edtInputPersonNumMax.setText("");
        }
        if (!TextUtils.isEmpty(edtInputPersonAgeMin.getText().toString())&&!TextUtils.isEmpty(edtInputPersonAgeMax.getText().toString())){
            if (Integer.parseInt(edtInputPersonAgeMin.getText().toString())>Integer.parseInt(edtInputPersonAgeMax.getText().toString())){
                Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请填写正确的年龄要求！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else if (TextUtils.isEmpty(edtInputPersonAgeMin.getText().toString())||TextUtils.isEmpty(edtInputPersonAgeMax.getText().toString())){
            edtInputPersonAgeMin.setText("");
            edtInputPersonAgeMax.setText("");
        }
        for (UserLabelModel.ObjBean bean : listLabel){
            if (bean.isChoose()){
                listChoosedLabel.add(bean.getLID());
            }
        }
        strChoosedLabels = "";
        if (listChoosedLabel.size()>0){
            for (int i = 0;i< listChoosedLabel.size();i++){
                strChoosedLabels = strChoosedLabels + ","  + listChoosedLabel.get(i);
            }
            if (strChoosedLabels.length()>1 && strChoosedLabels.charAt(0) == ','){
                strChoosedLabels = strChoosedLabels.substring(1);
            }
            Log.d("ssssss",strChoosedLabels);
        }else {
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请选择标签！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检测每一期输入的数据是否完整
     *
     * @param faBuLuXianQiShuModel
     * @return 完整返回 T 否则返回F
     */
    private boolean checkQiShu(Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean faBuLuXianQiShuModel) {
        if (TextUtils.isEmpty(faBuLuXianQiShuModel.getPhase_price()) ||
                TextUtils.isEmpty(faBuLuXianQiShuModel.getUserBonus()) ||
                TextUtils.isEmpty(faBuLuXianQiShuModel.getBegin_time()) ||
                TextUtils.isEmpty(faBuLuXianQiShuModel.getEnd_time()) ||
                TextUtils.isEmpty(faBuLuXianQiShuModel.getIfCaptain())) {
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "请填写完善详情内容！", Toast.LENGTH_SHORT).show();
            return false;
        }
        double price = Double.parseDouble(faBuLuXianQiShuModel.getPhase_price());
        double ticheng = Double.parseDouble(faBuLuXianQiShuModel.getUserBonus());
        if (price<1){
            faBuLuXianQiShuModel.setUserBonus("0");
            faBuLuXianQiShuAdapter.notifyDataSetChanged();
        }else {
            if ((price * 0.01)>ticheng){
                TitleMessageOkDialog titleMessageOkDialog1 = new TitleMessageOkDialog(FaBu_XiuGai_LuXianActivity.this, "",
                        "最小分销提成为行程售价的1%" , "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog1.show();
                return false;
            }
        }
        if (!(StringUtils.getTimeCompareSize(now,faBuLuXianQiShuModel.getBegin_time())==3)){
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "发团开始时间不能早于明天", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ((StringUtils.getTimeCompareSize(faBuLuXianQiShuModel.getBegin_time(),faBuLuXianQiShuModel.getEnd_time())==1)){
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "发团开始时间不能早于结束时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!(StringUtils.getTimeCompareSize(now,faBuLuXianQiShuModel.getSign_up_over_time())==3)){
            Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "报名截止时间不能早于明天", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String qiShuData2JSONString(List listQiShu) {
        Gson gson = new Gson();
        return gson.toJson(listQiShu);
    }
    private void fabu(){
        fabu(false);
    }
    private void fabu(boolean jixu) {
        Observable<File> observable = Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                File file = new File(firstImageUrl);
                e.onNext(file);
            }
        });
        Observer<File> observer = new Observer<File>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(File value) {

                ViseHttp.UPLOAD(NetConfig.add_activity)
                        .addHeader("Content-Type", "multipart/form-data")
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.add_activity))
                        .addParam("uid", spImp.getUID())
                        .addParam("pftitle", edtInputTitle.getText().toString())
                        .addParam("pfaddress", edtInputXingchengdidian.getText().toString())
                        .addParam("go_address", edtInputJihedidian.getText().toString())
                        .addParam("keyWord", edtInputGuanjianci.getText().toString())
                        .addParam("activity_ts", edtInputContent.getText().toString())
                        .addParam("sex",chooseSex)
                        .addParam("min_num",edtInputPersonNumMin.getText().toString())
                        .addParam("max_num",edtInputPersonNumMax.getText().toString())
                        .addParam("single",chooseSingle)
                        .addParam("age_begin",edtInputPersonAgeMin.getText().toString())
                        .addParam("age_end",edtInputPersonAgeMax.getText().toString())
                        .addParam("pfexplain",edtInputOrderAsk.getText().toString())
                        .addParam("buyNeedKnow", edtInputGoumaixuzhi.getText().toString())
                        .addParam("feiYongBaoHan", edtInputFeiyongbaohan.getText().toString())
                        .addParam("feiYongBuHan", edtInputFeiyongbuhan.getText().toString())
                        .addParam("wenXinTiShi", edtInputWenxintishi.getText().toString())//
                        .addParam("activityLabel",strChoosedLabels)
                        .addParam("phaseInfos",qiShuData2JSONString(listQiShu))
                        .addFile("file_img", value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    Log.e("222", data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Toast.makeText(FaBu_XiuGai_LuXianActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                                        if (jixu){

                                        }
                                        finish();

                                    }else {
                                        Toast.makeText(FaBu_XiuGai_LuXianActivity.this, jsonObject.getInt("message"), Toast.LENGTH_SHORT).show();
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

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void next() {
        fabu(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            final List<String> list = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            firstImageUrl = list.get(0);
            Glide.with(FaBu_XiuGai_LuXianActivity.this).load(list.get(0)).into(ivFirstImage);
        }
    }
}
