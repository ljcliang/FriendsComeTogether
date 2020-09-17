package com.yiwo.friendscometogether.webpages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.netease.nim.uikit.api.NimUIKit;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseSonicWebActivity;
import com.yiwo.friendscometogether.custom.EditNumContentDialog_L;
import com.yiwo.friendscometogether.dbmodel.LookHistoryDbModel;
import com.yiwo.friendscometogether.dbmodel.WebInfoOfDbUntils;
import com.yiwo.friendscometogether.dbmodel.YouJiWebInfoDbModel;
import com.yiwo.friendscometogether.dbmodel.YouJuHuoDongWebInfoDbModel;
import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DaoSession;
import com.yiwo.friendscometogether.greendao.gen.LookHistoryDbModelDao;
import com.yiwo.friendscometogether.imagepreview.Consts;
import com.yiwo.friendscometogether.imagepreview.ImagePreviewActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.ActiveShareModel;
import com.yiwo.friendscometogether.model.FocusOnToFriendTogetherModel;
import com.yiwo.friendscometogether.model.IsRealNameModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.MuLuItemYouJuAdapter;
import com.yiwo.friendscometogether.newmodel.LocalWebInfoModel;
import com.yiwo.friendscometogether.newmodel.YouJuWebModel;
import com.yiwo.friendscometogether.newpage.JuBaoActivity;
import com.yiwo.friendscometogether.newpage.MoreCommentHuodongActivity;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.ApplyActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.RealNameActivity;
import com.yiwo.friendscometogether.pages.VideoActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.FileUtils;
import com.yiwo.friendscometogether.utils.ShareUtils;
import com.yiwo.friendscometogether.utils.WebUntils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DetailsOfFriendTogetherWebLocalActivity extends BaseSonicWebActivity {

    @BindView(R.id.activity_details_of_friends_together_iv_focus_on)
    ImageView focusOnIv;
    @BindView(R.id.wv)
    WebView webView;
    @BindView(R.id.rl_show_more)
    RelativeLayout rl_show_more;
    @BindView(R.id.progresss_bar)
    ProgressBar progresss_bar;
    @BindView(R.id.view_showmore)
    View view_showmore;
    private Unbinder unbinder;
    SpImp spImp;
    private String uid;
    private String pfID;
    private String phase_id;
    private String url;
    private int chooseDateIndex = 0;
    private YouJuWebModel model;
    private List<YouJuWebModel.ObjBean.TitleBean> listMuLu = new ArrayList<>();
    private PopupWindow popupWindow;
    private MuLuItemYouJuAdapter muLuItemAdapter;

    //    //数据库
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private boolean isFirst = true;
    WebInfoOfDbUntils webInfoOfDbUntils;
    LookHistoryDbModelDao lookHistoryDbModelDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_friend_together_web);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        StatusBarUtils.setStatusBarTransparent(DetailsOfFriendTogetherWebLocalActivity.this);
        Log.d("活动生命周期","onCreate");
        unbinder = ButterKnife.bind(this);
        spImp = new SpImp(DetailsOfFriendTogetherWebLocalActivity.this);
        uid = spImp.getUID();
        webInfoOfDbUntils = new WebInfoOfDbUntils(this);
        pfID = getIntent().getStringExtra("pfID");
        url = "file:///android_asset/htmlfile/demoU.html";
        setDatabase();
        lookHistoryDbModelDao = mDaoSession.getLookHistoryDbModelDao();
        initWebView(webView,url);
        initIntentSonic(url,webView);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    progresss_bar.setVisibility(View.GONE);//加载完网页进度条消失
                    if (isFirst){
                        isFirst = false;
                        if (webInfoOfDbUntils.hasThisId_HuoDong(pfID)){
                            String strr1 = webInfoOfDbUntils.queryYouJu(pfID).getWeb_info();
                            String strr2 = "";
                            String strr3 = "1";//0youji,1youju;2shangpin,5jingcailuxian;3canjiarenyuan;
                            Log.d("adsadasd：：\n",strr1+"\n"+strr2+"\n"+strr3);
                            webView.loadUrl("javascript:getTongbanDataAndroid('"+strr1+"','"+strr2+"','"+strr3+"','"+spImp.getUID()+"')");
                            /**
                             * 加载之后再次查询更新数据库
                             */
                            saveNewWebInFo();
                        }else {
                            ViseHttp.POST(NetConfig.articleInfo)
                                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleInfo))
                                    .addParam("uid", spImp.getUID())
                                    .addParam("fmID",pfID)
                                    .addParam("type","1")
                                    .request(new ACallback<String>() {
                                        @Override
                                        public void onSuccess(String data) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(data);
                                                if (jsonObject.getInt("code") == 200){
                                                    Gson gson = new Gson();
                                                    LocalWebInfoModel mode =  gson.fromJson(data,LocalWebInfoModel.class);
                                                    String strr1 = mode.getObj().getStr();
                                                    String strr2 = "";
                                                    String strr3 = "1";
                                                    strr1 = WebUntils.replaceStr(strr1);
                                                    Log.d("数据库中没有此条数据：pfIDID-",pfID+"\n"+strr1+"\n"+strr2+"\n"+strr3);
                                                    webView.loadUrl("javascript:getTongbanDataAndroid('"+strr1+"','"+strr2+"','"+strr3+"','"+spImp.getUID()+"')");
                                                    YouJuHuoDongWebInfoDbModel youJuHuoDongWebInfoDbModel = new YouJuHuoDongWebInfoDbModel();
                                                    youJuHuoDongWebInfoDbModel.setWeb_info(mode.getObj().getStr());
                                                    youJuHuoDongWebInfoDbModel.setPf_id(pfID);
                                                    webInfoOfDbUntils.insertYouJuHuoDongModel(youJuHuoDongWebInfoDbModel);
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
                    //判断是否为自己的发布的友聚，是否显示“补填参加人员”
                    updateWebStaus(spImp.getUID(),"4","-1");
//                    updateWebStaus("7","4","-1");
//                    supplement(userID)
                }else{
                    progresss_bar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progresss_bar.setProgress(newProgress);//设置进度值				}
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.addJavascriptInterface(new DetailsOfFriendTogetherWebLocalActivity.AndroidInterface(),"android");//交互
        initData();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("活动生命周期","onStart");
        uid = spImp.getUID();
        ShareUtils.closeDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("活动生命周期","onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("活动生命周期","onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("活动生命周期","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("活动生命周期","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("活动生命周期","onDestroy");
    }
    /**
     *
     * @param id id:被关注人的id（当type=4时为当前登录人的ID），
     * @param type type:1友记2友聚3商品4友聚补填人员,
     * @param staus staus:1已收藏0未收藏（当type=4时传啥都无所谓,
     *                                    当id=0时为收藏/取消收藏友记、友聚、商品，
     *                                       ≠0时为关注/取消关注此id的人）)
     */
    public void updateWebStaus(String id,String type,String staus){
        //                    supplement(id:被关注人的id（当type=4时为当前登录人的ID），
//                              type:1友记2友聚3商品4友聚补填人员,
//                              staus1已收藏0未收藏（当type=4时传啥都无所谓,、
//                                                   当id=0时为收藏/取消收藏友记、友聚、商品，
//                                                      ≠0时为关注/取消关注此id的人）)

        webView.loadUrl("javascript:supplement('"+id+"','"+type+"','"+ staus+"')");
    }
    private void saveNewWebInFo() {
        ViseHttp.POST(NetConfig.articleInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleInfo))
                .addParam("uid", spImp.getUID())
                .addParam("fmID",pfID)
                .addParam("type","1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson = new Gson();
                                LocalWebInfoModel mode =  gson.fromJson(data,LocalWebInfoModel.class);
//                                                    String strr1 = mode.getObj().getStr();
//                                                    String strr2 = "";
//                                                    String strr3 = "0";
//                                                    Log.d("adsadasd",strr1+""+strr2+""+strr3);
//                                                    webView.loadUrl("javascript:getTongbanDataAndroid('"+strr1+"','"+strr2+"','"+strr3+"')");
                                YouJuHuoDongWebInfoDbModel youJuHuoDongWebInfoDbModel = new YouJuHuoDongWebInfoDbModel();
                                youJuHuoDongWebInfoDbModel.setWeb_info(mode.getObj().getStr());
                                youJuHuoDongWebInfoDbModel.setPf_id(pfID);
                                webInfoOfDbUntils.insertYouJuHuoDongModel(youJuHuoDongWebInfoDbModel);
                                //每次获取新数据，再次更新
                                String strr1 = mode.getObj().getStr();
                                String strr2 = "";
                                String strr3 = "1";//0youji,1youju;2shangpin,5jingcailuxian;3canjiarenyuan;
                                Log.d("adsadasd：：\n",strr1+"\n"+strr2+"\n"+strr3);
                                webView.loadUrl("javascript:androidagain('"+strr1+"','"+strr2+"','"+strr3+"')");
                                updateWebStaus(spImp.getUID(),"4","-1");
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

    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "usergive-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.getActivityInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getActivityInfo))
                .addParam("id",pfID)
                .addParam("uid",uid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code")==200){
                                Log.d("asdsadasd",data);
                                Gson gson = new Gson();
                                model = gson.fromJson(data,YouJuWebModel.class);
                                if (lookHistoryDbModelDao.queryBuilder()
                                        .where(LookHistoryDbModelDao.Properties.Type.eq("0"),
                                                LookHistoryDbModelDao.Properties.Look_id.eq(pfID),
                                                LookHistoryDbModelDao.Properties.User_id.eq(spImp.getUID())).build()
                                        .list().size()>0){
                                    LookHistoryDbModel model = lookHistoryDbModelDao.queryBuilder()
                                            .where(LookHistoryDbModelDao.Properties.Type.eq("0"),
                                                    LookHistoryDbModelDao.Properties.Look_id.eq(pfID),
                                                    LookHistoryDbModelDao.Properties.User_id.eq(spImp.getUID())).build()
                                            .list().get(0);
                                    model.setLook_time(new Date().toLocaleString());
                                    lookHistoryDbModelDao.update(model);
                                }else {
                                    LookHistoryDbModel historyModel = new LookHistoryDbModel();
                                    historyModel.setLook_time(new Date().toLocaleString());
                                    historyModel.setUser_id(spImp.getUID());
                                    historyModel.setType("0");
                                    historyModel.setLook_id(pfID);
                                    historyModel.setTitle(model.getObj().getShare_info());
                                    historyModel.setPic_url(model.getObj().getShare_pic());
                                    lookHistoryDbModelDao.insert(historyModel);
                                }
                                listMuLu.clear();
                                listMuLu.addAll(model.getObj().getTitle());
                                //底部按钮
                                //收藏
                                focusOnIv.setImageResource(model.getObj().getCollect().equals("0") ? R.mipmap.heart_red_border : R.mipmap.heart_red);
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
    @OnClick({R.id.activity_details_of_friends_together_rl_back,R.id.details_applyTv,R.id.activity_details_of_friends_together_ll_share,
                    R.id.activity_details_of_friends_together_ll_focus_on,R.id.consult_leaderLl,R.id.rl_show_more})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.activity_details_of_friends_together_rl_back:
                finish();
                break;
            case R.id.details_applyTv:
                apply();
                break;
            case R.id.activity_details_of_friends_together_ll_share:
                share();
                break;
            case R.id.activity_details_of_friends_together_ll_focus_on:
                guanzhuHuoDong();
                break;
            case R.id.consult_leaderLl:
                quLiaoTian();
                break;
            case R.id.rl_show_more:
                showMore(rl_show_more);
                break;
        }
    }

    private void apply() {
        if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
            Intent intent = new Intent(DetailsOfFriendTogetherWebLocalActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            ViseHttp.POST(NetConfig.isRealNameUrl)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.isRealNameUrl))
                    .addParam("userID", spImp.getUID())
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.i("123321", data);
                            IsRealNameModel models = new Gson().fromJson(data, IsRealNameModel.class);
                            if (models.getCode() == 200) {
                                if (models.getObj().getOk().equals("2")) {
                                    Intent it = new Intent(DetailsOfFriendTogetherWebLocalActivity.this, ApplyActivity.class);
                                    it.putExtra("if_pay", model.getObj().getInfo().getPfspendtype());
                                    it.putExtra("title", model.getObj().getInfo().getPftitle());
                                    it.putExtra("pfID", model.getObj().getInfo().getPfID());
                                    it.putExtra("name", model.getObj().getInfo().getTruename());
                                    it.putExtra("sex", model.getObj().getInfo().getPeoplesex());
                                    it.putExtra("age", model.getObj().getInfo().getAge());
                                    it.putExtra("pic", model.getObj().getInfo().getPfpic());
                                    it.putExtra("issingle", model.getObj().getInfo().getMarry());
                                    it.putExtra("city", model.getObj().getInfo().getAddress());
                                    it.putExtra("tel", model.getObj().getInfo().getTel());
                                    it.putExtra("choose_date_intex",chooseDateIndex);
                                    it.putExtra("Pfexplain",model.getObj().getInfo().getOthers());
                                    startActivity(it);
                                } else if (models.getObj().getOk().equals("1")) {
                                    toToast(DetailsOfFriendTogetherWebLocalActivity.this, "请于身份审核通过后报名");
                                } else {
                                    startActivity(new Intent(DetailsOfFriendTogetherWebLocalActivity.this, RealNameActivity.class));
                                }
                            } else {
                                toToast(DetailsOfFriendTogetherWebLocalActivity.this, models.getMessage());
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }
    }

    private void share(){
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
                                new ShareAction(DetailsOfFriendTogetherWebLocalActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                                        .setShareboardclickCallback(new ShareBoardlistener() {
                                            @Override
                                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                                ShareUtils.shareWeb(DetailsOfFriendTogetherWebLocalActivity.this, shareModel.getObj().getUrl()+"&uid="+spImp.getUID(), shareModel.getObj().getTitle(),
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
    private void quLiaoTian(){
        String uid = spImp.getUID();
        if (TextUtils.isEmpty(uid) || uid.equals("0")) {
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendTogetherWebLocalActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            liaotian(model.getObj().getInfo().getWy_accid());
        }
    }
    private void guanzhuHuoDong(){
        String userID = spImp.getUID();
        if (spImp.getUID().equals("0") || TextUtils.isEmpty(spImp.getUID())) {
            Intent intent1 = new Intent(DetailsOfFriendTogetherWebLocalActivity.this, LoginActivity.class);
            startActivity(intent1);
        } else {
            ViseHttp.POST(NetConfig.focusOnToFriendTogetherUrl)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.focusOnToFriendTogetherUrl))
                    .addParam("userID", userID)
                    .addParam("pfID", pfID)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if(jsonObject.getInt("code") == 200){
                                    FocusOnToFriendTogetherModel model = new Gson().fromJson(result, FocusOnToFriendTogetherModel.class);
                                    if (model.getCode() == 200) {
                                        if (model.getObj().equals("1")) {
                                            focusOnIv.setImageResource(R.mipmap.heart_red);
                                            //更新收藏活动状态
                                            updateWebStaus("0","2","1");
                                            toToast(DetailsOfFriendTogetherWebLocalActivity.this, "收藏成功");
                                        } else {
                                            focusOnIv.setImageResource(R.mipmap.heart_red_border);
                                            //更新收藏活动状态
                                            updateWebStaus("0","2","0");
                                            toToast(DetailsOfFriendTogetherWebLocalActivity.this, "已取消收藏");
                                        }
                                    }
                                }else if(jsonObject.getInt("code") == 400) {
                                    toToast(DetailsOfFriendTogetherWebLocalActivity.this, jsonObject.getString("message"));
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
    public class AndroidInterface extends Object{
        @JavascriptInterface
        public void backgo(){
            Log.d("交互了","fanhui");
            finish();
        }
        @JavascriptInterface
        public void btnmore(){
            showMore(view_showmore);
        }
        @JavascriptInterface
        public void lookpostpicture(String json,String index){
//            [{"id":"958","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad155365614111.jpg","desc":""},
//              {"id":"959","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad1553656141238.jpg","desc":""},
//              {"id":"960","imgurl":"http:\/\/39.104.102.152\/uploads\/header\/2019\/03\/27\/7c2494c2f044a8011c6030e7ed75baad1553656141899.jpg","desc":""}]
            String strJson = new String(Base64.decode(json,Base64.DEFAULT));
            Log.d("交互了","json::"+json+"////\r\nindex::"+index);
            int position = Integer.parseInt(index);
            Log.d("交互解码了","json::"+strJson+"////\r\nindex::"+index);
            List<String> listPics = new ArrayList<>();
            List<String> listContent = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(strJson);
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listPics.add(jsonObject.getString("imgurl"));
                    listContent.add(jsonObject.getString("desc"));
                }
                Intent intent1 = new Intent();
                intent1.setClass(DetailsOfFriendTogetherWebLocalActivity.this, ImagePreviewActivity.class);
                intent1.putStringArrayListExtra("imageList", (ArrayList<String>) listPics);
                intent1.putExtra("hasImageContent",true);
                intent1.putStringArrayListExtra("imageContenList", (ArrayList<String>) listContent);
                intent1.putExtra(Consts.START_ITEM_POSITION, position);
                intent1.putExtra(Consts.START_IAMGE_POSITION, position);
                startActivity(intent1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @JavascriptInterface
        public void userinfo(String uid){
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendTogetherWebLocalActivity.this, PersonMainActivity1.class);
            intent.putExtra("person_id", uid);
            startActivity(intent);
        }
        @JavascriptInterface
        public void clickphase(String index){
            Log.d("asdasdas","选择期数：："+index);
            chooseDateIndex = Integer.parseInt(index);
        }
        @JavascriptInterface
        public void moreinfo(String pfID){//web点击更多评论
            Log.d("sadas",pfID);
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendTogetherWebLocalActivity.this, MoreCommentHuodongActivity.class);
            intent.putExtra("pfID",pfID);
            startActivity(intent);
        }
        @JavascriptInterface
        public void inshop(String url){//商家详情
            Log.d("asdasd",url);
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendTogetherWebLocalActivity.this,ShopInfoWebActivity.class);
            intent.putExtra("url",url);
            startActivity(intent);
        }
        @JavascriptInterface
        public void playpfvideo(String videoUrl){
            startVideoACtivity(videoUrl);
        }
        @JavascriptInterface
        public void jumpyouji(String fmID){
            Intent intent = new Intent();
            intent.setClass(DetailsOfFriendTogetherWebLocalActivity.this, DetailsOfFriendsWebLocalActivity.class);
            intent.putExtra("fmid", fmID);
            startActivity(intent);
        }
        @JavascriptInterface
        public void partyfav(){//关注活动
            guanzhuHuoDong();
        }
        @JavascriptInterface
        public void chatgo(){//去聊天
            quLiaoTian();
        }
        @JavascriptInterface
        public void sharego(){//分享
            share();
        }
        @JavascriptInterface
        public void saveImg(String img_url){
            Log.d("asdas",img_url);
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailsOfFriendTogetherWebLocalActivity.this);
            builder.setMessage("确定保存图片到本地？")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            download(img_url);
                        }
                    }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
        //partysend（）友聚报名
        @JavascriptInterface
        public void partysend(){
            Log.d("asdasdas","活动报名");
            apply();
        }
        //getPhaeUser（友聚id，期数id）  友聚报名人员
        @JavascriptInterface
        public void getPhaeUser(String youJuId,String qiShuId){
            Log.d("asdasdas","youji:"+youJuId+";;;"+"qishu ::"+qiShuId);
            refreshCanJiaRenYuan(youJuId,qiShuId);
        }

        /**
         *
         * @param showstr showstr参数的信息（就是日期和周几），直接显示就
         * @param hid 活动id  hid  期数id  qid
         * @param qid 活动id  hid  期数id  qid
         */
        @JavascriptInterface
        public void addjoinuser(String showstr,String hid,String qid){
            EditNumContentDialog_L dialogL = new EditNumContentDialog_L(DetailsOfFriendTogetherWebLocalActivity.this,
                    showstr, new EditNumContentDialog_L.OnReturnListener() {
                @Override
                public void onReturn(String content) {
//                    toToast(DetailsOfFriendTogetherWebLocalActivity.this,content+"---"+hid+"//"+qid);
                    addNoNameJoin(content,hid,qid);
                }
            });
            dialogL.show();
        }
    }

    private void addNoNameJoin(String num, String hid, String qid) {
        ViseHttp.POST(NetConfig.setNoNameJoin)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.setNoNameJoin))
                .addParam("pfID", hid)
                .addParam("phase_id",qid)
                .addParam("num",num)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.d("asdasdas",data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                toToast(DetailsOfFriendTogetherWebLocalActivity.this,"添加成功");
                                refreshCanJiaRenYuan(hid,qid);
//                                String str = jsonObject.getJSONObject("obj").getString("str");
//                                Log.d("asdasdas","str::"+str);
//                                webView.loadUrl("javascript:loadnewjoin('"+str+"')");
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

    private void refreshCanJiaRenYuan(String youJuId, String qiShuId) {
        ViseHttp.POST(NetConfig.getPhaeUser)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getPhaeUser))
                .addParam("pfID", youJuId)
                .addParam("phase_id",qiShuId)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.d("asdasdas",data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                String str = jsonObject.getJSONObject("obj").getString("str");
                                Log.d("asdasdas","str::"+str);
                                webView.loadUrl("javascript:loadnewjoin('"+str+"')");
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

    // 保存图片到手机
    public void download(final String url) {

        new AsyncTask<Void, Integer, File>() {

            @Override
            protected File doInBackground(Void... params) {
                File file = null;
                try {
                    FutureTarget<File> future = Glide
                            .with(DetailsOfFriendTogetherWebLocalActivity.this)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    file = future.get();

                    // 首先保存图片
                    File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
//                    File pictureFolder = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);;
                    File appDir = new File(pictureFolder ,"Beauty");
                    if (!appDir.exists()) {
                        appDir.mkdirs();
                    }
                    String fileName = "瞳伴图片_"+System.currentTimeMillis() + ".jpg";
                    File destFile = new File(appDir, fileName);
                    FileUtils.copy(file, destFile);
                    // 最后通知图库更新
                    DetailsOfFriendTogetherWebLocalActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.fromFile(new File(destFile.getPath()))));


                } catch (Exception e) {
                    Log.e("123132", e.getMessage());
                }
                return file;
            }

            @Override
            protected void onPostExecute(File file) {

                Toast.makeText(DetailsOfFriendTogetherWebLocalActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.execute();
    }
    private void showMore(final View view_p) {

        View view = LayoutInflater.from(DetailsOfFriendTogetherWebLocalActivity.this).inflate(R.layout.popupwindow_detail_of_friend_together_web_activity_show_more, null);
        final PopupWindow popupWindow;
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        LinearLayout ll_show_more = view.findViewById(R.id.ll_show_more);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ll_show_more.getLayoutParams();
        LinearLayout llMuLu = view.findViewById(R.id.ll_mulu);
//        if (listMuLu.size()>0){
//            llMuLu.setVisibility(View.GONE);
//            params.height = 200;
//        }else {
//            llMuLu.setVisibility(View.GONE);
//            params.height = 100;
//        }
//        ll_show_more.setLayoutParams(params);
        LinearLayout llShare = view.findViewById(R.id.ll_share);
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
                popupWindow.dismiss();
            }
        });
        ScreenAdapterTools.getInstance().loadView(view);//确定后dp
        llMuLu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showMuLuPopupwindow(view_p);
            }
        });
        LinearLayout llJuBao = view.findViewById(R.id.ll_jubao);
        llJuBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent();
                intent.setClass(DetailsOfFriendTogetherWebLocalActivity.this, JuBaoActivity.class);
                intent.putExtra("pfID",pfID);
                intent.putExtra("type","2");
                startActivity(intent);
            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        popupWindow.showAsDropDown(view_p,0,0);
        // 设置popWindow的显示和消失动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
            }
        });


    }
    private void showMuLuPopupwindow(View p_view) {

        View view = LayoutInflater.from(DetailsOfFriendTogetherWebLocalActivity.this).inflate(R.layout.popupwindow_web_mulu, null);
        ScreenAdapterTools.getInstance().loadView(view);
        RecyclerView recyclerView = view.findViewById(R.id.rv_mulu);
        LinearLayoutManager manager = new LinearLayoutManager(DetailsOfFriendTogetherWebLocalActivity.this);
        recyclerView.setLayoutManager(manager);
        muLuItemAdapter= new MuLuItemYouJuAdapter(listMuLu);
        muLuItemAdapter.setListener(new MuLuItemYouJuAdapter.ChooseListen() {
            @Override
            public void chooseItemId(String ID) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:jumptitle('"+ID+"')");
//                        popupWindow.dismiss();
                    }
                });
                popupWindow.dismiss();
            }
        });
        recyclerView.setAdapter(muLuItemAdapter);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.showAsDropDown(p_view,0,0);
//        popupWindow.showAsDropDown(p_view,0,0);
        popupWindow.showAtLocation(webView, Gravity.RIGHT,0,500);
//        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.popwindow_anim_left_in_out);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 1f;
//        getWindow().setAttributes(params);
        popupWindow.update();
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//            // 在dismiss中恢复透明度
//            public void onDismiss() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
//            }
//        });
    }
    private void liaotian(String liaotianAccount) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startP2PSession(DetailsOfFriendTogetherWebLocalActivity.this, liaotianAccount);
    }
    private void startVideoACtivity(String vurl){
        Intent it = new Intent(DetailsOfFriendTogetherWebLocalActivity.this, VideoActivity.class);
        it.putExtra("videoUrl", vurl);
        startActivity(it);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("活动生命周期","onActivityResult");
        UMShareAPI.get(DetailsOfFriendTogetherWebLocalActivity.this).onActivityResult(requestCode, resultCode, data);
    }
}
