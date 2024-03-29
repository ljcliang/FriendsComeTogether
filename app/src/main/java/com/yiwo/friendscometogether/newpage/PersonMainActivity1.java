package com.yiwo.friendscometogether.newpage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.DuiZhangShowDialog;
import com.yiwo.friendscometogether.custom.DuiZhangShowLVAllDialog;
import com.yiwo.friendscometogether.custom.DuiZhangShowLVDialog;
import com.yiwo.friendscometogether.custom.FriendDescribeDialog;
import com.yiwo.friendscometogether.custom.HuoZanDialog;
import com.yiwo.friendscometogether.custom.NotOnLiveDialog;
import com.yiwo.friendscometogether.model.KVMode;
import com.yiwo.friendscometogether.model.PersonMain_YouJu_model;
import com.yiwo.friendscometogether.model.PersonMain_Youji_model;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.PersonMainActivity_Video_Adapter;
import com.yiwo.friendscometogether.newadapter.PersonMainActivity_YouJi_Adapter;
import com.yiwo.friendscometogether.newadapter.PersonMainActivity_YouJu_Adapter;
import com.yiwo.friendscometogether.newadapter.PersonMainLabelAdapter;
import com.yiwo.friendscometogether.newadapter.PersonMainLabelModel;
import com.yiwo.friendscometogether.newadapter.PersonMainZuiXinShangPinAdapter;
import com.yiwo.friendscometogether.newadapter.PersonSameLabelAdapter;
import com.yiwo.friendscometogether.newmodel.NewPersonMainMode_part1;
import com.yiwo.friendscometogether.newmodel.PersonMainModel;
import com.yiwo.friendscometogether.newmodel.PersonMain_Videos_Model;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.MyFriendActivity;
import com.yiwo.friendscometogether.pages.MyPicturesActivity;
import com.yiwo.friendscometogether.pages.OtherPicActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonMainActivity1 extends BaseActivity {

    @BindView(R.id.recycler_view_labels)
    RecyclerView recycler_view_labels;
    @BindView(R.id.rl_back)
    RelativeLayout rl_back;

    @BindView(R.id.iv_images)
    ImageView iv_images;

    // 他人主页 或 我的主页 有区别的控件；
    @BindView(R.id.tv_label_wode_or_tade)
    TextView tv_label_wode_or_tade;
    @BindView(R.id.tv_title_wode_or_tade)
    TextView tv_title_wode_or_tade;

    @BindView(R.id.tv_more_label)
    TextView tv_more_label;
    //头像右侧控件
    @BindView(R.id.rl_algin_right_tade)
    RelativeLayout rl_algin_right_tade;
    @BindView(R.id.rl_algin_right_wode)
    RelativeLayout rl_algin_right_wode;

    @BindView(R.id.iv_person_icon)
    ImageView iv_person_icon;
    @BindView(R.id.iv_person_sex)
    ImageView iv_person_sex;
    @BindView(R.id.tv_person_name)
    TextView tv_person_name;
    @BindView(R.id.tv_person_age)
    TextView tv_person_age;
    @BindView(R.id.tv_person_code_ok)
    TextView tv_person_code_ok;
    @BindView(R.id.tv_person_marry)
    TextView tv_person_marry;
    @BindView(R.id.tv_level)
    TextView tv_level;
    @BindView(R.id.rl_level)
    RelativeLayout rl_level;

    @BindView(R.id.tv_person_address)
    TextView tv_person_address;
    @BindView(R.id.tv_person_sign_text)
    TextView tv_person_sign_text;
    @BindView(R.id.tv_guanzhu_num)
    TextView tv_guanzhu_num;
    @BindView(R.id.tv_huozan_num)
    TextView tv_huozan_num;
    @BindView(R.id.tv_fans_num)
    TextView tv_fans_num;
    @BindView(R.id.iv_addfriend)
    ImageView iv_addfriend;
    @BindView(R.id.iv_image_guanzhu)
    ImageView iv_image_guanzhu;
    @BindView(R.id.iv_heart)
    ImageView iv_image_heart;
//    @BindView(R.id.iv_kefu)
//    ImageView iv_kefu;
    @BindView(R.id.rl_label_text)
    RelativeLayout rl_label_text;
//    @BindView(R.id.rl_tab_1)
//    RelativeLayout rl_tab_1;
    @BindView(R.id.rl_tab_2)
    RelativeLayout rl_tab_2;
    @BindView(R.id.rl_tab_3)
    RelativeLayout rl_tab_3;
//    @BindView(R.id.bottom_line_1)
//    View bottom_line_1;
    @BindView(R.id.bottom_line_2)
    View bottom_line_2;
    @BindView(R.id.bottom_line_3)
    View bottom_line_3;
    @BindView(R.id.bottom_line_4)
    View bottom_line_4;

//    @BindView(R.id.rv_pic)
//    RecyclerView rv_pic;
    @BindView(R.id.rv_youji)
    RecyclerView rv_youji;
    @BindView(R.id.rv_youju)
    RecyclerView rv_youju;
    @BindView(R.id.rv_videos)
    RecyclerView rv_videos;

//    @BindView(R.id.rl_pics)
//    RelativeLayout rl_pics;
    @BindView(R.id.rl_youji)
    RelativeLayout rl_youji;
    @BindView(R.id.rl_youju)
    RelativeLayout rl_youju;
    @BindView(R.id.rl_vides)
    RelativeLayout rl_videos;

    @BindView(R.id.person_main_refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.rv_zuixin_goods)
    RecyclerView rv_zuixin_goods;
    @BindView(R.id.ll_goods)
    LinearLayout ll_goods;
    @BindView(R.id.iv_level)
    ImageView iv_level;
    //商品
    private List<NewPersonMainMode_part1.ObjBean.GoodsBean> list_goods = new ArrayList<>();
    private PersonMainZuiXinShangPinAdapter shangPinAdapter;
    private String ta = "他";
    private SpImp spImp;
    private int type_tade_or_wode = 0;//0 为他的 1 为我的
    private String person_id;// 进入activity传入的id 可能为云信ID
    private String otherUserId;//储存 接口返回的UID
    private String  status; //status   =0 时传 用户ID    =1 时传网易ID
    private NewPersonMainMode_part1 model;

    private List<PersonMainLabelModel> list_label = new ArrayList<>();
    private int isFollow = -1;//是否关注 0为未关注 1为已关注

    //recyclerView
    private int show_tab = 2;//1为照片 2为友记 3为友聚 4为视频
    private int page1 = 1;
    private int page2 = 1;
    private int page3 = 1;
    private int page4 = 1;
//    //照片 //// 去掉照片卡页
//    private PersonMainActivity_Pics_Adapter picsAdapter;
//    private List<PersonMain_Pics_model.ObjBean.PhotoBean> picsList = new ArrayList<>();
    //友记
    private PersonMainActivity_YouJi_Adapter youJiAdapter;
    private List<PersonMain_Youji_model.ObjBean.FriendBean> youJiList = new ArrayList<>();
    //y友聚
    private PersonMainActivity_YouJu_Adapter youJuAdapter;
    private List<PersonMain_YouJu_model.ObjBean.ActivityBean> youJuList = new ArrayList<>();
    //视频
    private PersonMainActivity_Video_Adapter videoAdapter;
    private List<PersonMain_Videos_Model.ObjBean> videosList = new ArrayList<>();

    private PersonMainLabelAdapter personLabelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_main_1);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(PersonMainActivity1.this);
        person_id = getIntent().getStringExtra("person_id");
        if (getIntent().getStringExtra("status") == null||getIntent().getStringExtra("status").equals("")){
            status = "0";
        }else {
            status = getIntent().getStringExtra("status");
        }
        Log.d("person_id", person_id);
        spImp = new SpImp(PersonMainActivity1.this);
        if (spImp.getUID().equals(person_id)||spImp.getYXID().equals(person_id)) {
            type_tade_or_wode = 1;
//            recycler_view_labels.setVisibility(View.GONE);
//            rl_label_text.setVisibility(View.GONE);
        } else {
            type_tade_or_wode = 0;
//            recycler_view_labels.setVisibility(View.VISIBLE);
//            rl_label_text.setVisibility(View.VISIBLE);
        }
        if (getIntent().getBooleanExtra("is_by_live",false)){
            showDialogNotOnLive(getIntent().getStringExtra("next_on_live_time"));
        }
        //---------------先设置label 的 manager-------------
//        FlowLayoutManager managerFlow = new FlowLayoutManager(){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//        recycler_view_labels.setLayoutManager(managerFlow);
        LinearLayoutManager manager_labs = new LinearLayoutManager(PersonMainActivity1.this);
        manager_labs.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view_labels.setLayoutManager(manager_labs);
//        ----------------------------------------------------
        Log.d("personIDIDID",person_id);
        initData();
        if (type_tade_or_wode == 0) {
//            iv_image_heart.setVisibility(View.VISIBLE);
            RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(PersonMainActivity1.this).load(R.drawable.sayhi).apply(options).into(iv_image_heart);
            rl_algin_right_tade.setVisibility(View.VISIBLE);
            rl_algin_right_wode.setVisibility(View.GONE);
        } else if (type_tade_or_wode == 1) {
            tv_label_wode_or_tade.setText("我的标签");
            tv_title_wode_or_tade.setText("我的主页");
//            iv_image_heart.setVisibility(View.GONE);
            rl_algin_right_tade.setVisibility(View.GONE);
            rl_algin_right_wode.setVisibility(View.VISIBLE);
        }
        rl_youji.setVisibility(View.VISIBLE);
        bottom_line_2.setVisibility(View.VISIBLE);
        initRecyclerView();
        initRefresh();
    }

    private void showDialogNotOnLive(String time) {
        NotOnLiveDialog dialog = new NotOnLiveDialog(PersonMainActivity1.this,time);
        dialog.show();
    }

    private void initRecyclerView() {
        // recyclerView初始化开始
//        StaggeredGridLayoutManager mLayoutManager1 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//        rv_pic.setLayoutManager(mLayoutManager1);
//        picsAdapter = new PersonMainActivity_Pics_Adapter(picsList);
//        rv_pic.setAdapter(picsAdapter);


        StaggeredGridLayoutManager mLayoutManager2 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_youji.setLayoutManager(mLayoutManager2);
        youJiAdapter = new PersonMainActivity_YouJi_Adapter(youJiList);
        rv_youji.setAdapter(youJiAdapter);


        StaggeredGridLayoutManager mLayoutManager3 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_youju.setLayoutManager(mLayoutManager3);
        youJuAdapter = new PersonMainActivity_YouJu_Adapter(youJuList);
        rv_youju.setAdapter(youJuAdapter);

        // /设置布局管理器为2列，纵向
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_videos.setLayoutManager(mLayoutManager);
        videoAdapter = new PersonMainActivity_Video_Adapter(videosList);
        rv_videos.setAdapter(videoAdapter);

        //----------照片----------
//        ViseHttp.POST(NetConfig.homepagePartFour)
//                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartFour))
//                .addParam("uid", spImp.getUID())
//                .addParam("tid", person_id)
//                .addParam("status",status)
//                .request(new ACallback<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//                        try {
//                            Log.d("asdasfsada_zhaopain",data);
//                            JSONObject jsonObject = new JSONObject(data);
//                            if (jsonObject.getInt("code") == 200){
//                                Gson gson = new Gson();
//                                PersonMain_Pics_model model = gson.fromJson(data,PersonMain_Pics_model.class);
//                                picsList.clear();
//                                picsList.addAll(model.getObj().getPhoto());
//                                picsAdapter.notifyDataSetChanged();
//                                page1 = 2;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFail(int errCode, String errMsg) {
//
//                    }
//                });

        //----------友记----------
        ViseHttp.POST(NetConfig.homepagePartTwo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartTwo))
                .addParam("uid", spImp.getUID())
                .addParam("tid", person_id)
                .addParam("status",status)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("asdasfsada_Youji",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonMain_Youji_model model = gson.fromJson(data,PersonMain_Youji_model.class);
                                youJiList.clear();
                                youJiList.addAll(model.getObj().getFriend());
                                youJiAdapter.notifyDataSetChanged();
                                page2 = 2;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        //----------友聚----------
        ViseHttp.POST(NetConfig.homepagePartthree)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartthree))
                .addParam("uid", spImp.getUID())
                .addParam("tid", person_id)
                .addParam("status",status)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.d("asdasfsada_YouJu",data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonMain_YouJu_model model = gson.fromJson(data,PersonMain_YouJu_model.class);
                                youJuList.clear();
                                youJuList.addAll(model.getObj().getActivity());
                                youJuAdapter.notifyDataSetChanged();
                                page3 = 3;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        //视频
        ViseHttp.POST(NetConfig.homepageVideos)
                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homepageVideos))
                .addParam("status", status)
                .addParam("tid",person_id)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                PersonMain_Videos_Model model = gson.fromJson(data, PersonMain_Videos_Model.class);
                                page4 = 2;
                                videosList.clear();
                                videosList.addAll(model.getObj());
                                videoAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                    }
                });

        LinearLayoutManager manager = new LinearLayoutManager(PersonMainActivity1.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        shangPinAdapter = new PersonMainZuiXinShangPinAdapter(list_goods, new PersonMainZuiXinShangPinAdapter.AddClickListenner() {
            @Override
            public void addListen(int i, ImageView ivGoods) {
            }
        });
        rv_zuixin_goods.setLayoutManager(manager);
        rv_zuixin_goods.setAdapter(shangPinAdapter);
    }

    private void initRefresh() {
        refreshLayout.setRefreshHeader(new ClassicsHeader(PersonMainActivity1.this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(PersonMainActivity1.this));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetConfig.homepagePartOne)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartOne))
                        .addParam("uid", spImp.getUID())
                        .addParam("tid", person_id)
                        .addParam("status",status)//=0时传 用户ID    =1时传网易ID
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("222", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Gson gson = new Gson();
                                        model = gson.fromJson(data, NewPersonMainMode_part1.class);
                                        otherUserId = model.getObj().getInfo().getOtherUserId();//
                                        Glide.with(PersonMainActivity1.this).load(model.getObj().getInfo().getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(iv_person_icon);

                                        if (model.getObj().getInfo().getSex().equals("0")) {
                                            ta = "他";
                                            if (type_tade_or_wode == 0) {
                                                tv_title_wode_or_tade.setText(ta + "的主页");
//                                                tv_more_label.setText(ta+"的更多标签");
                                            }
                                            Glide.with(PersonMainActivity1.this).load(R.mipmap.nan).into(iv_person_sex);
                                        } else if (model.getObj().getInfo().getSex().equals("1")) {
                                            Glide.with(PersonMainActivity1.this).load(R.mipmap.nv).into(iv_person_sex);
                                            ta = "她";
                                            if (type_tade_or_wode == 0) {
                                                tv_title_wode_or_tade.setText(ta + "的主页");
//                                                tv_more_label.setText(ta+"的更多标签");
                                            }
                                        }
                                        tv_person_name.setText(model.getObj().getInfo().getUsername());

                                        tv_level.setText("Lv."+model.getObj().getInfo().getUsergrade());
                                        iv_level.setVisibility(View.GONE);
                                        if (model.getObj().getInfo().getCaptain().equals("1")){
                                            tv_level.setText("领队");
                                            iv_level.setVisibility(View.VISIBLE);
                                        }else if (model.getObj().getInfo().getCaptain().equals("2")){
//                                            rl_level.setBackgroundResource(R.mipmap.lv_duizhang_daili);
                                        }else {
//                                            rl_level.setBackgroundResource(R.mipmap.lv_putong);
                                        }
                                        switch (model.getObj().getInfo().getLevelName()){
                                            case "0":
                                                iv_level.setImageResource(R.mipmap.level_qingtong);
                                                break;
                                            case "1":
                                                iv_level.setImageResource(R.mipmap.level_baiyin);
                                                break;
                                            case "2":
                                                iv_level.setImageResource(R.mipmap.level_huangjin);
                                                break;
                                            case "3":
                                                iv_level.setImageResource(R.mipmap.level_bojin);
                                                break;
                                            case "4":
                                                iv_level.setImageResource(R.mipmap.level_zuanshi);
                                                break;
                                            case "5":
                                                iv_level.setImageResource(R.mipmap.level_huangguan);
                                                break;
                                        }
                                        if (model.getObj().getInfo().getUsermarry().equals("1")){
                                            tv_person_marry.setText("单身");
                                        }else if (model.getObj().getInfo().getUsermarry().equals("2")){
                                            tv_person_marry.setText("非单身");
                                        }
                                        if (model.getObj().getInfo().getUsercodeok().equals("0")){
                                            tv_person_code_ok.setVisibility(View.GONE);
                                        }else if (model.getObj().getInfo().getUsermarry().equals("1")){
                                            tv_person_code_ok.setVisibility(View.VISIBLE);
                                            tv_person_code_ok.setText("已实名");
                                        }
                                        tv_person_age.setText(model.getObj().getInfo().getAge());
                                        tv_person_address.setText("所在城市:"+model.getObj().getInfo().getAddress());
                                        tv_person_sign_text.setText(model.getObj().getInfo().getAutograph());
                                        tv_guanzhu_num.setText(model.getObj().getInfo().getUserlike());
                                        tv_huozan_num.setText(model.getObj().getInfo().getGiveCount() + "");
                                        tv_fans_num.setText(model.getObj().getInfo().getFans());
                                        if (model.getObj().getInfo().getFriends().equals("1")) {
                                            Glide.with(PersonMainActivity1.this).load(R.mipmap.other_send_msg).into(iv_addfriend);
                                        } else if (model.getObj().getInfo().getFriends().equals("0")) {
                                            Glide.with(PersonMainActivity1.this).load(R.mipmap.tarenzhuye_tianjiahaoyou).into(iv_addfriend);

                                        }
                                        if(model.getObj().getInfo().getIf_kefu().equals("1")){//如果是客服 直接可以聊天
                                            Glide.with(PersonMainActivity1.this).load(R.mipmap.other_send_msg).into(iv_addfriend);
                                        }
                                        if (model.getObj().getInfo().getFollow().equals("0")) {
                                            isFollow = 0 ;
                                            Glide.with(PersonMainActivity1.this).load(R.mipmap.tarenzhuye_heart).into(iv_image_guanzhu);
                                        } else if (model.getObj().getInfo().getFollow().equals("1")) {
                                            isFollow = 1 ;
                                            Glide.with(PersonMainActivity1.this).load(R.mipmap.tarenzhuye_heartwhite).into(iv_image_guanzhu);
                                        }
//                                        if (model.getObj().getInfo().getIf_kefu().equals("0")){
//                                            iv_kefu.setVisibility(View.GONE);
//                                        }else if(model.getObj().getInfo().getIf_kefu().equals("1")){
//                                            iv_kefu.setVisibility(View.VISIBLE);
//                                        }
                                        //------------他的所有标签----------------
                                        NewPersonMainMode_part1.ObjBean.UsertagBean usertagBean = model.getObj().getUsertag();

                                        String[] strings11 = usertagBean.getPersonality().split(",");
                                        String[] strings22 = usertagBean.getMotion().split(",");
                                        String[] strings33 = usertagBean.getMusic().split(",");
                                        String[] strings44 = usertagBean.getDelicious().split(",");
                                        String[] strings55 = usertagBean.getFilm().split(",");
                                        String[] strings66 = usertagBean.getBook().split(",");
                                        String[] strings77 = usertagBean.getTravel().split(",");
                                        List<PersonMainLabelModel> listUserlabel = new ArrayList<>();
                                        listUserlabel.addAll(addLabelToListNew(false,strings11));
                                        listUserlabel.addAll(addLabelToListNew(false,strings22));
                                        listUserlabel.addAll(addLabelToListNew(false,strings33));
                                        listUserlabel.addAll(addLabelToListNew(false,strings44));
                                        listUserlabel.addAll(addLabelToListNew(false,strings55));
                                        listUserlabel.addAll(addLabelToListNew(false,strings66));
                                        listUserlabel.addAll(addLabelToListNew(false,strings77));

                                        List<PersonMainLabelModel> listLab = list_label;
                                        for (PersonMainLabelModel personMainLabelModel : listUserlabel){
                                            boolean hasIt = false;
                                            for (int i = 0 ; i<listLab.size();i++){
                                                if (listLab.get(i).getName().equals(personMainLabelModel.getName())){
                                                    hasIt = true;
                                                    break;
                                                }
                                            }
                                            if (!hasIt) list_label.add(personMainLabelModel);
                                        }
                                        if (list_label.size() == 0){
                                            PersonMainLabelModel kvMode = new PersonMainLabelModel();
                                            kvMode.setSameMine(false);
                                            kvMode.setName("未设置 - 这家伙有点懒");
                                            list_label.add(kvMode);
                                        }
                                        personLabelAdapter.notifyDataSetChanged();
                                        //商品
                                        list_goods.clear();
                                        list_goods.addAll(model.getObj().getGoods());
                                        if (list_goods.size()>0){
                                            ll_goods.setVisibility(View.VISIBLE);
                                        }else {
                                            ll_goods.setVisibility(View.GONE);
                                        }
                                        shangPinAdapter.notifyDataSetChanged();

                                    }
                                    refreshLayout.finishRefresh(1000);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishRefresh(1000);
                            }
                        });
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                switch (show_tab)
                {
                    case 1:
                        //----------照片----------  已经去掉照片选项卡
//                        ViseHttp.POST(NetConfig.homepagePartFour)
//                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartFour))
//                                .addParam("uid", spImp.getUID())
//                                .addParam("tid", person_id)
//                                .addParam("status",status)
//                                .addParam("page",page1+"")
//                                .request(new ACallback<String>() {
//                                    @Override
//                                    public void onSuccess(String data) {
//                                        try {
//                                            Log.d("asdasfsada_zhaopain",data);
//                                            JSONObject jsonObject = new JSONObject(data);
//                                            if (jsonObject.getInt("code") == 200){
//                                                Gson gson = new Gson();
//                                                PersonMain_Pics_model model = gson.fromJson(data,PersonMain_Pics_model.class);
//                                                picsList.addAll(model.getObj().getPhoto());
//                                                picsAdapter.notifyDataSetChanged();
//                                                page1 ++;
//                                            }
//                                            refreshLayout.finishLoadMore(1000);
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFail(int errCode, String errMsg) {
//                                        refreshLayout.finishLoadMore(1000);
//
//                                    }
//                                });
                        break;
                    case 2:
                        //----------友记----------
                        ViseHttp.POST(NetConfig.homepagePartTwo)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartTwo))
                                .addParam("uid", spImp.getUID())
                                .addParam("tid", person_id)
                                .addParam("status",status)
                                .addParam("page",page2+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.d("asdasfsada_Youji",data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                PersonMain_Youji_model model = gson.fromJson(data,PersonMain_Youji_model.class);
                                                youJiList.addAll(model.getObj().getFriend());
                                                youJiAdapter.notifyDataSetChanged();
                                                page2++;

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        refreshLayout.finishLoadMore(1000);
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                });
                        break;
                    case 3:
                        //----------友聚----------
                        ViseHttp.POST(NetConfig.homepagePartthree)
                                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartthree))
                                .addParam("uid", spImp.getUID())
                                .addParam("tid", person_id)
                                .addParam("status",status)
                                .addParam("page",page3+"")
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.d("asdasfsada_YouJu",data);
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if (jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                PersonMain_YouJu_model model = gson.fromJson(data,PersonMain_YouJu_model.class);
                                                youJuList.addAll(model.getObj().getActivity());
                                                youJuAdapter.notifyDataSetChanged();
                                                page3++;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        refreshLayout.finishLoadMore(1000);
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                });
                        break;
                    case 4:
                        ViseHttp.POST(NetConfig.homepageVideos)
                                .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.homepageVideos))
                                .addParam("status", status)
                                .addParam("page",page4+"")
                                .addParam("tid",person_id)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.getInt("code") == 200){
                                                Gson gson = new Gson();
                                                PersonMain_Videos_Model model = gson.fromJson(data, PersonMain_Videos_Model.class);
                                                videosList.addAll(model.getObj());
                                                videoAdapter.notifyDataSetChanged();
                                                page4++;
                                                refreshLayout.finishLoadMore(1000);
                                            }
                                        } catch (JSONException e) {
                                            refreshLayout.finishLoadMore(1000);
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {
                                        refreshLayout.finishLoadMore(1000);
                                    }
                                });
                        break;
                }
            }
        });
    }

    private void initData() {
        // --------- 顶部数据----
        ViseHttp.POST(NetConfig.homepagePartOne)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.homepagePartOne))
                .addParam("uid", spImp.getUID())
                .addParam("tid", person_id)
                .addParam("status",status)
                .addParam("type", "0")//0 为5条  1为全部
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                model = gson.fromJson(data, NewPersonMainMode_part1.class);
                                otherUserId = model.getObj().getInfo().getOtherUserId();//
                                Glide.with(PersonMainActivity1.this).load(model.getObj().getInfo().getUserpic()).into(iv_person_icon);

                                if (model.getObj().getInfo().getSex().equals("0")) {
                                    ta = "他";
                                    if (type_tade_or_wode == 0) {
                                        tv_title_wode_or_tade.setText(ta + "的主页");
//                                        tv_more_label.setText(ta+"的更多标签");
                                    }
                                    Glide.with(PersonMainActivity1.this).load(R.mipmap.nan).into(iv_person_sex);
                                } else if (model.getObj().getInfo().getSex().equals("1")) {
                                    Glide.with(PersonMainActivity1.this).load(R.mipmap.nv).into(iv_person_sex);
                                    ta = "她";
                                    if (type_tade_or_wode == 0) {
                                        tv_title_wode_or_tade.setText(ta + "的主页");
//                                        tv_more_label.setText(ta+"的更多标签");
                                    }
                                }
                                tv_person_name.setText(model.getObj().getInfo().getUsername());
                                tv_person_age.setText(model.getObj().getInfo().getAge());
                                tv_person_address.setText("所在城市:"+model.getObj().getInfo().getAddress());
                                tv_person_sign_text.setText(model.getObj().getInfo().getAutograph());

                                if (model.getObj().getInfo().getUsermarry().equals("1")){
                                    tv_person_marry.setText("单身");
                                }else if (model.getObj().getInfo().getUsermarry().equals("2")){
                                    tv_person_marry.setText("非单身");
                                }
                                if (model.getObj().getInfo().getUsercodeok().equals("0")){
                                    tv_person_code_ok.setVisibility(View.GONE);
                                }else if (model.getObj().getInfo().getUsermarry().equals("1")){
                                    tv_person_code_ok.setVisibility(View.VISIBLE);
                                    tv_person_code_ok.setText("已实名");
                                }

                                tv_guanzhu_num.setText(model.getObj().getInfo().getUserlike());
                                tv_huozan_num.setText(model.getObj().getInfo().getGiveCount() + "");
                                tv_fans_num.setText(model.getObj().getInfo().getFans());
                                if (model.getObj().getInfo().getFriends().equals("1")) {
                                    Glide.with(PersonMainActivity1.this).load(R.mipmap.other_send_msg).into(iv_addfriend);
                                } else if (model.getObj().getInfo().getFriends().equals("0")) {
                                    Glide.with(PersonMainActivity1.this).load(R.mipmap.tarenzhuye_tianjiahaoyou).into(iv_addfriend);
                                }
                                if(model.getObj().getInfo().getIf_kefu().equals("1")){//如果是客服 直接可以聊天
                                    Glide.with(PersonMainActivity1.this).load(R.mipmap.other_send_msg).into(iv_addfriend);
                                }
                                if (model.getObj().getInfo().getFollow().equals("0")) {
                                    isFollow = 0 ;
                                    Glide.with(PersonMainActivity1.this).load(R.mipmap.tarenzhuye_heart).into(iv_image_guanzhu);
                                } else if (model.getObj().getInfo().getFollow().equals("1")) {
                                    isFollow = 1 ;
                                    Glide.with(PersonMainActivity1.this).load(R.mipmap.tarenzhuye_heartwhite).into(iv_image_guanzhu);
                                }
//                                if (model.getObj().getInfo().getIf_kefu().equals("0")){
//                                    iv_kefu.setVisibility(View.GONE);
//                                }else if(model.getObj().getInfo().getIf_kefu().equals("1")){
//                                    iv_kefu.setVisibility(View.VISIBLE);
//                                }
                                iv_level.setVisibility(View.GONE);
                                tv_level.setText("Lv."+model.getObj().getInfo().getUsergrade());
                                if (model.getObj().getInfo().getCaptain().equals("1")){
                                    tv_level.setText("领队");
                                    iv_level.setVisibility(View.VISIBLE);
                                }else if (model.getObj().getInfo().getCaptain().equals("2")){
//                                            rl_level.setBackgroundResource(R.mipmap.lv_duizhang_daili);
                                }else {
//                                            rl_level.setBackgroundResource(R.mipmap.lv_putong);
                                }
                                switch (model.getObj().getInfo().getLevelName()){
                                    case "0":
                                        iv_level.setImageResource(R.mipmap.level_qingtong);
                                        break;
                                    case "1":
                                        iv_level.setImageResource(R.mipmap.level_baiyin);
                                        break;
                                    case "2":
                                        iv_level.setImageResource(R.mipmap.level_huangjin);
                                        break;
                                    case "3":
                                        iv_level.setImageResource(R.mipmap.level_bojin);
                                        break;
                                    case "4":
                                        iv_level.setImageResource(R.mipmap.level_zuanshi);
                                        break;
                                    case "5":
                                        iv_level.setImageResource(R.mipmap.level_huangguan);
                                        break;
                                }
                                //---------共同标签-----------------------------
                                NewPersonMainMode_part1.ObjBean.UsertagBean.SameBean sameBean = model.getObj().getUsertag().getSame();

                                String[] strings1 = sameBean.getPersonality().split(",");
                                String[] strings2 = sameBean.getMotion().split(",");
                                String[] strings3 = sameBean.getMusic().split(",");
                                String[] strings4 = sameBean.getDelicious().split(",");
                                String[] strings5 = sameBean.getFilm().split(",");
                                String[] strings6 = sameBean.getBook().split(",");
                                String[] strings7 = sameBean.getTravel().split(",");
                                list_label.addAll(addLabelToListNew(true,strings1));
                                list_label.addAll(addLabelToListNew(true,strings2));
                                list_label.addAll(addLabelToListNew(true,strings3));
                                list_label.addAll(addLabelToListNew(true,strings4));
                                list_label.addAll(addLabelToListNew(true,strings5));
                                list_label.addAll(addLabelToListNew(true,strings6));
                                list_label.addAll(addLabelToListNew(true,strings7));
                                //------------他的所有标签----------------
                                NewPersonMainMode_part1.ObjBean.UsertagBean usertagBean = model.getObj().getUsertag();

                                String[] strings11 = usertagBean.getPersonality().split(",");
                                String[] strings22 = usertagBean.getMotion().split(",");
                                String[] strings33 = usertagBean.getMusic().split(",");
                                String[] strings44 = usertagBean.getDelicious().split(",");
                                String[] strings55 = usertagBean.getFilm().split(",");
                                String[] strings66 = usertagBean.getBook().split(",");
                                String[] strings77 = usertagBean.getTravel().split(",");
                                List<PersonMainLabelModel> listUserlabel = new ArrayList<>();
                                listUserlabel.addAll(addLabelToListNew(false,strings11));
                                listUserlabel.addAll(addLabelToListNew(false,strings22));
                                listUserlabel.addAll(addLabelToListNew(false,strings33));
                                listUserlabel.addAll(addLabelToListNew(false,strings44));
                                listUserlabel.addAll(addLabelToListNew(false,strings55));
                                listUserlabel.addAll(addLabelToListNew(false,strings66));
                                listUserlabel.addAll(addLabelToListNew(false,strings77));

                                List<PersonMainLabelModel> listLab = list_label;
                                for (PersonMainLabelModel personMainLabelModel : listUserlabel){
                                    boolean hasIt = false;
                                    for (int i = 0 ; i<listLab.size();i++){
                                        if (listLab.get(i).getName().equals(personMainLabelModel.getName())){
                                            hasIt = true;
                                            break;
                                        }
                                    }
                                    if (!hasIt) list_label.add(personMainLabelModel);
                                }
                                if (list_label.size() == 0){
                                    PersonMainLabelModel kvMode = new PersonMainLabelModel();
                                    kvMode.setSameMine(false);
                                    kvMode.setName("未设置 - 这家伙有点懒");
                                    list_label.add(kvMode);
                                }
                                personLabelAdapter =new PersonMainLabelAdapter(list_label);
                                recycler_view_labels.setAdapter(personLabelAdapter);

                                //商品
                                list_goods.clear();
                                list_goods.addAll(model.getObj().getGoods());
                                if (list_goods.size()>0){
                                    ll_goods.setVisibility(View.VISIBLE);
                                }else {
                                    ll_goods.setVisibility(View.GONE);
                                }
                                shangPinAdapter.notifyDataSetChanged();
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

    private List<KVMode> addLabelToList(int i ,String[] strings) {
        List<KVMode> list  =new ArrayList<>();
        for (int j = 0 ;j<strings.length;j++){
            if (TextUtils.isEmpty(strings[j])) continue;
            KVMode kvMode = new KVMode();
            kvMode.setI(i);
            kvMode.setString(strings[j]);
            list.add(kvMode);
        }
        return list;
    }
    private List<PersonMainLabelModel> addLabelToListNew(boolean sameMine , String[] strings) {
        List<PersonMainLabelModel> list  =new ArrayList<>();
        for (int j = 0 ;j<strings.length;j++){
            if (TextUtils.isEmpty(strings[j])) continue;
            PersonMainLabelModel kvMode = new PersonMainLabelModel();
            kvMode.setSameMine(sameMine);
            kvMode.setName(strings[j]);
            list.add(kvMode);
        }
        return list;
    }
    @OnClick({R.id.rl_back,R.id.rl_label_text,R.id.iv_images,
            R.id.rl_algin_right_wode, R.id.rl_add_friend, R.id.rl_guanzhu,R.id.iv_heart,R.id.ll_huozan,R.id.ll_guanzhu,R.id.ll_fans,R.id.iv_person_icon,
            R.id.rl_tab_2,R.id.rl_tab_3,R.id.rl_tab_4,R.id.rl_level,R.id.iv_level})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_images:
                intent.setClass(PersonMainActivity1.this,PersonImagesActivity.class);
                intent.putExtra("person_id",person_id);
                intent.putExtra("status",status);
                startActivity(intent);
                break;
            case R.id.rl_label_text:
                if (type_tade_or_wode == 0){
                    intent.setClass(PersonMainActivity1.this,PersonLabelActivity.class);
                    intent.putExtra("userName",model.getObj().getInfo().getUsername());
                    intent.putExtra("userSex",ta);
                    intent.putExtra("labelData",model.getObj().getUsertag());
                    startActivity(intent);
                }else {
                    intent.setClass(PersonMainActivity1.this, EditorLabelActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_person_all_pics:
                if (type_tade_or_wode == 1) {
                    intent.setClass(PersonMainActivity1.this, MyPicturesActivity.class);
                } else if (type_tade_or_wode == 0) {
                    intent.putExtra("otheruid", otherUserId);
                    intent.setClass(PersonMainActivity1.this, OtherPicActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.rl_algin_right_wode:
                intent.setClass(PersonMainActivity1.this, MyFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_add_friend:
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    intent.setClass(PersonMainActivity1.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (model.getObj().getInfo().getFriends().equals("1")||model.getObj().getInfo().getIf_kefu().equals("1")) {//如果是客服 直接可以聊天
                        liaotian(model.getObj().getInfo().getWy_accid());
                    } else{
                        FriendDescribeDialog dialog = new FriendDescribeDialog(PersonMainActivity1.this);
                        dialog.show();
                        dialog.setOnReturnListener(new FriendDescribeDialog.OnReturnListener() {
                            @Override
                            public void onReturn(String title) {
                                ViseHttp.POST(NetConfig.addFriendsUrl)
                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.addFriendsUrl))
                                        .addParam("uid", spImp.getUID())
                                        .addParam("friendId", otherUserId)
                                        .addParam("describe", title)
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(data);
                                                    if (jsonObject.getInt("code") == 200) {
                                                        toToast(PersonMainActivity1.this, "好友请求已发送");
                                                    } else {
                                                        toToast(PersonMainActivity1.this, jsonObject.getString("message"));
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
                        });
                    }
                }
                break;
            case R.id.rl_guanzhu:
                guanzhu();
                break;
            case R.id.iv_heart:
                ViseHttp.POST(NetConfig.sayHello)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.sayHello))
                        .addForm("uid", spImp.getUID())
                        .addForm("bid", otherUserId)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Log.d("22222", data);
                                        toToast(PersonMainActivity1.this, "打招呼成功！");
                                    } else {
                                        toToast(PersonMainActivity1.this, "您已经打过招呼了");
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
            case R.id.ll_huozan:
                HuoZanDialog dialog = new HuoZanDialog(PersonMainActivity1.this,tv_person_name.getText().toString(),tv_huozan_num.getText().toString());
                dialog.show();
                break;
            case R.id.ll_guanzhu:
                if (model.getObj().getInfo().getUserlike().equals("0")){
                    toToast(PersonMainActivity1.this,"他还没有关注任何人");
                }else {
                    intent.setClass(PersonMainActivity1.this,PersonGuanZhuActivity.class);
                    intent.putExtra("userID",otherUserId);
                    intent.putExtra("type",1);
                    if (type_tade_or_wode == 1){
                        intent.putExtra("userName","我");
                    }else{
                        intent.putExtra("userName",model.getObj().getInfo().getUsername());
                    }
                    startActivity(intent);
                }
                break;
            case R.id.ll_fans:
                if (model.getObj().getInfo().getFans().equals("0")){
                    toToast(PersonMainActivity1.this,"他还没有粉丝");
                }else {
                    intent.setClass(PersonMainActivity1.this,PersonGuanZhuActivity.class);
                    intent.putExtra("userID",otherUserId);
                    intent.putExtra("type",2);
                    if (type_tade_or_wode == 1){
                        intent.putExtra("userName","我");
                    }else{
                        intent.putExtra("userName",model.getObj().getInfo().getUsername());
                    }
                    startActivity(intent);
                }
                break;
            case R.id.iv_person_icon:
                    intent.setClass(PersonMainActivity1.this,PersonImpressionActivity.class);
                    intent.putExtra("person_id",otherUserId);
                    intent.putExtra("user_icon",model.getObj().getInfo().getUserpic());
                    intent.putExtra("sex",model.getObj().getInfo().getSex());
                    intent.putExtra("yx_id",model.getObj().getInfo().getWy_accid());
                    startActivity(intent);
                break;
//            case R.id.rl_tab_1:
//                show_tab = 1;
//                bottom_line_1.setVisibility(View.VISIBLE);
//                bottom_line_2.setVisibility(View.GONE);
//                bottom_line_3.setVisibility(View.GONE);
//                bottom_line_4.setVisibility(View.GONE);
//                rl_pics.setVisibility(View.VISIBLE);
//                rl_youji.setVisibility(View.GONE);
//                rl_youju.setVisibility(View.GONE);
//                rl_videos.setVisibility(View.GONE);
//                break;
            case R.id.rl_tab_2:
//                bottom_line_1.setVisibility(View.GONE);
                bottom_line_2.setVisibility(View.VISIBLE);
                bottom_line_3.setVisibility(View.GONE);
                bottom_line_4.setVisibility(View.GONE);
//                rl_pics.setVisibility(View.GONE);
                rl_youji.setVisibility(View.VISIBLE);
                rl_youju.setVisibility(View.GONE);
                rl_videos.setVisibility(View.GONE);
                show_tab = 2;
                break;
            case R.id.rl_tab_3:
//                bottom_line_1.setVisibility(View.GONE);
                bottom_line_2.setVisibility(View.GONE);
                bottom_line_3.setVisibility(View.VISIBLE);
                bottom_line_4.setVisibility(View.GONE);
//                rl_pics.setVisibility(View.GONE);
                rl_youji.setVisibility(View.GONE);
                rl_youju.setVisibility(View.VISIBLE);
                rl_videos.setVisibility(View.GONE);
                show_tab = 3;
                break;
            case R.id.rl_tab_4:
//                bottom_line_1.setVisibility(View.GONE);
                bottom_line_2.setVisibility(View.GONE);
                bottom_line_3.setVisibility(View.GONE);
                bottom_line_4.setVisibility(View.VISIBLE);
//                rl_pics.setVisibility(View.GONE);
                rl_youji.setVisibility(View.GONE);
                rl_youju.setVisibility(View.GONE);
                rl_videos.setVisibility(View.VISIBLE);
                show_tab = 4;
                break;
            case R.id.iv_level:
            case R.id.rl_level:
                showDialogLvIcon();
                break;
        }


    }

    private void guanzhu() {
        if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
            Intent intent = new Intent();
            intent.setClass(PersonMainActivity1.this, LoginActivity.class);
            startActivity(intent);
        } else {
            if (isFollow == 0){
                ViseHttp.POST(NetConfig.userFocusUrl)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                        .addParam("uid", spImp.getUID())
                        .addParam("likeId", otherUserId)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        toToast(PersonMainActivity1.this, "关注成功");
                                        Glide.with(PersonMainActivity1.this).load(R.mipmap.tarenzhuye_heartwhite).into(iv_image_guanzhu);
                                        isFollow = 1;
                                    } else {
                                        toToast(PersonMainActivity1.this, jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onFail(int errCode, String errMsg) {

                            }
                        });
            }else if (isFollow == 1){
                ViseHttp.POST(NetConfig.removeConcerns)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.removeConcerns))
                        .addParam("uid", spImp.getUID())
                        .addParam("bid", otherUserId)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        toToast(PersonMainActivity1.this, "取消关注成功");
                                        Glide.with(PersonMainActivity1.this).load(R.mipmap.tarenzhuye_heart).into(iv_image_guanzhu);
                                        isFollow = 0;
                                    } else {
                                        toToast(PersonMainActivity1.this, jsonObject.getString("message"));
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
    }

    private void showDialogLvIcon() {
        if (model.getObj().getInfo().getCaptain().equals("1")){
            DuiZhangShowLVAllDialog dialog = new DuiZhangShowLVAllDialog(this);
            dialog.show();
        }else {
            return;
        }
    }

    private void liaotian(String liaotianAccount) {
        NimUIKit.setAccount(spImp.getYXID());
        NimUIKit.startP2PSession(PersonMainActivity1.this, liaotianAccount);
    }
}
