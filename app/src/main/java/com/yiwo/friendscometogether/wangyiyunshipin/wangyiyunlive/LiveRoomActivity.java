package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.OrderToPayModel;
import com.yiwo.friendscometogether.model.Paymodel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.network.UMConfig;
import com.yiwo.friendscometogether.newpage.CreateFriendRememberActivityChoosePicOrVideos;
import com.yiwo.friendscometogether.pages.ApplyActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.BaseActivity;
import com.yiwo.friendscometogether.wangyiyunshipin.NimContract;
import com.yiwo.friendscometogether.wangyiyunshipin.NimController;
import com.yiwo.friendscometogether.wangyiyunshipin.VcloudFileUtils;
import com.yiwo.friendscometogether.wangyiyunshipin.liveStreaming.CapturePreviewController;
import com.yiwo.friendscometogether.wangyiyunshipin.liveStreaming.PublishParam;
import com.yiwo.friendscometogether.wangyiyunshipin.server.DemoServerHttpClient;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.constant.GiftType;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment.AudienceFragment;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment.CaptureFragment;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment.ChatRoomMessageFragment;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment.ChatRoomMsgListPanel;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment.GiftAttachment;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment.LikeAttachment;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment.LiveBottomBar;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.fragment.LiveRoomInfoFragment;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.input.InputConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

/**
 * Created by zhukkun on 1/5/17.
 */
public class LiveRoomActivity extends BaseActivity implements NimContract.Ui{
    public static final String IS_AUDIENCE = "is_audience";

    //各大板块容器
    private ViewGroup rootView;
    private CaptureFragment captureFragment; //主播
    private AudienceFragment audienceFragment; //观众
    private FrameLayout roomInfoLayout;
    private LiveRoomInfoFragment liveRoomInfoFragment; //房间信息
    private LiveBottomBar liveBottomBar; //底部控制栏

    //聊天室相关
    private FrameLayout chatLayout;
    private ChatRoomMessageFragment chatRoomFragment;
    private NimController nimController;
    //礼物动画
    private SVGAImageView svgaImageView;
    private SVGAParser parser;
    private boolean svgaIsShow = false;
    private List<String> listSvgaQueue = new ArrayList<>();//当前动画队列

//     {"棒棒糖", "饮料", "魔法棒", "小熊","糖果雨","爱心","捧花","钻石","心雨","戒指","别墅","游艇"};
    private String[] arrSvgaAssets = new String[]{"svga_bbt.svga","svga_yinliao.svga","svga_mofabang.svga","svga_xiaoxiong.svga",
                                                "svga_tangguoyu.svga","svga_aixin.svga","svga_penghua.svga","svga_zuanshi.svga",
                                                        "svga_xinyu.svga","svga_jiezhi.svga","svga_bieshu.svga","svga_youting.svga"};//存放svga资源名称

    //人员操作相关
    private ChatRoomMember current_operate_member; //当前正在操作的人员
    private RelativeLayout rl_member_operate;
    private ImageView iv_avatar;
    private TextView tv_nick_name;
    private Button btn_kick;
    private Button btn_mute;

    //直播结束回调布局
    private LinearLayout ll_live_finish;
    private TextView tv_finish_operate;
    private TextView tv_finish_tip;
    private Button btn_finish_back;
    private CircleImageView iv_operate;
    //直播参数
    private boolean isAudience = true; //默认为观众
    private String roomId;
    private float screenHeight;
    private boolean isLiveStart; //是否已开启直播

    private int addRobTimes = 0;//添加机器人次数

    private SpImp spImp;
    private Handler handler;

    private static final int SDK_PAY_FLAG = 1;
    private IWXAPI api;
    /**
     * 静态方法 启动主播
     * @param context 上下文
     */
    public static void startLive(Context context, String roomId, PublishParam param){
        Intent intent = new Intent(context, LiveRoomActivity.class);
        intent.putExtra(IS_AUDIENCE, false);
        intent.putExtra(NimController.EXTRA_ROOM_ID, roomId);
        intent.putExtra(CapturePreviewController.EXTRA_PARAMS, param);
        context.startActivity(intent);
    }

    /**
     * 静态方法 启动观众
     * @param context 上下文
     * @param url 直播地址
     */
    public static void startAudience(Context context, String roomId, String url, boolean isSoftDecode) {
        Intent intent = new Intent();
        intent.setClass(context, LiveRoomActivity.class);
        intent.putExtra(IS_AUDIENCE, true);
        intent.putExtra(NimController.EXTRA_ROOM_ID, roomId);
        intent.putExtra(AudienceFragment.IS_LIVE, true); //观众默认为直播, 另一个种模式为点播.
        intent.putExtra(AudienceFragment.IS_SOFT_DECODE, isSoftDecode);
        intent.putExtra(AudienceFragment.EXTRA_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nimController = new NimController(this, this);
        nimController.onHandleIntent(getIntent());
        spImp = new SpImp(LiveRoomActivity.this);
        api = WXAPIFactory.createWXAPI(LiveRoomActivity.this, null);
        VcloudFileUtils.getInstance(getApplicationContext()).init();
        StatusBarUtils.setStatusBarTransparent(LiveRoomActivity.this);
        handler = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        liveBottomBar.refreshMyIntegral();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_live_room;
    }

    @Override
    protected void handleIntent(Intent intent) {
        isAudience = intent.getBooleanExtra(IS_AUDIENCE, true);
        roomId = intent.getStringExtra(NimController.EXTRA_ROOM_ID);
    }

    @Override
    protected void initView() {
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        screenHeight = ScreenUtil.getDisplayHeight();

        loadFragment(isAudience);

        rootView = findView(R.id.layout_live_root);
        chatLayout = findView(R.id.layout_chat_room);
        roomInfoLayout = findView(R.id.layout_room_info);
        svgaImageView = findView(R.id.svga_view);
        initSvga();
        //add bottom controller Layout
        initBottomBar();
        initMemberOperate();
        initFinishLiveLayout();
        if(isAudience){
            //观众 直接显示聊天列表与底部控制栏
            onStartLivingFinished();
        }
    }

    /**
     * 根据是否为观众,加载不同的Fragment
     * @param isAudience 是否为观众
     */
    private void loadFragment(boolean isAudience) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(isAudience){
            audienceFragment = new AudienceFragment();
            transaction.replace(R.id.layout_main_content, audienceFragment);
        }else{
            captureFragment = new CaptureFragment();
            transaction.replace(R.id.layout_main_content, captureFragment);
        }

        liveRoomInfoFragment = LiveRoomInfoFragment.getInstance(isAudience);
        transaction.replace(R.id.layout_room_info, liveRoomInfoFragment);
        transaction.commit();
    }

    /**
     * 成功登入聊天室的回调
     * @param roomId
     */
    @Override
    public void onEnterChatRoomSuc(final String roomId) {
        chatRoomFragment = (ChatRoomMessageFragment) getSupportFragmentManager().findFragmentById(R.id.chat_room_fragment);
        if (chatRoomFragment != null) {
            initChatRoomFragment();
            addRob(roomId);
        } else {
            // 如果Fragment还未Create完成，延迟初始化
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onEnterChatRoomSuc(roomId);
                }
            }, 50);
        }
    }

    private void addRob(final String roomId) {
        Integer time;
        if (addRobTimes == 0){
            time = 120000;
        }else if (addRobTimes == 1){
            time = 300000;
        }else if (addRobTimes == 2){
            time = 300000;
        }else {
            return;
        }
        if (!isAudience){
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViseHttp.POST(NetConfig.addRobot)
                            .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.addRobot))
                            .addParam("roomid",roomId)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss//获取当前时间
                                    Date date = new Date(System.currentTimeMillis());
                                    Log.d("addrobort",data+"\n"+simpleDateFormat.format(date)+"\n"+addRobTimes);
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.getInt("code") == 200){
                                            addRobTimes++;
                                            addRob(roomId);
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
            },time);
        }
    }

    /**
     * 初始化聊天室Fragment
     */
    private void initChatRoomFragment() {
        chatRoomFragment.init(roomId);
        chatRoomFragment.setMsgExtraDelegate(new ChatRoomMsgListPanel.ExtraDelegate() {

            @Override
            public void onReceivedCustomAttachment(ChatRoomMessage msg) {
                if(msg.getAttachment() instanceof LikeAttachment) {
                    liveBottomBar.addHeart();
                }else if (msg.getAttachment() instanceof GiftAttachment){
                    // 收到礼物消息
                    GiftType type = ((GiftAttachment) msg.getAttachment()).getGiftType();
                    if (!isAudience){
                        liveBottomBar.updateGiftList(type,true);
                        liveRoomInfoFragment.refreshZhiBoTongBi();
                    }
                    liveBottomBar.showGiftAnimation(msg);

                    addGift2ListQueue(type);
                }else if (msg.getAttachment() instanceof ChatRoomNotificationAttachment){
                    liveRoomInfoFragment.onReceivedNotification((ChatRoomNotificationAttachment)msg.getAttachment());
                }
            }

            @Override
            public void onMessageClick(IMMessage imMessage) {
                if(imMessage.getMsgType() == MsgTypeEnum.text){
                    onMemberOperate(getCurrentClickMember(imMessage.getFromAccount()));
                }
            }
        });
    }

    /**
     * 初始化直播结束布局
     */
    private void initFinishLiveLayout() {
        ll_live_finish = findView(R.id.ll_live_finish);
        tv_finish_operate = findView(R.id.tv_operate_name);
        tv_finish_tip = findView(R.id.tv_finish_tip);
        btn_finish_back = findView(R.id.btn_finish_back);
        btn_finish_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_operate = findView(R.id.iv_operate);
        ll_live_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //空方法,拦截点击事件
            }
        });

    }

    /**
     * 初始化人员操作布局
     */
    private void initMemberOperate() {
        rl_member_operate = findView(R.id.rl_member_operate);
        iv_avatar = findView(R.id.iv_avatar);
        tv_nick_name = findView(R.id.tv_nick_name);
        btn_kick = findView(R.id.btn_kick);
        btn_mute = findView(R.id.btn_mute);

        rl_member_operate.setVisibility(View.GONE);
        rl_member_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_member_operate.setVisibility(View.GONE);
                liveBottomBar.setVisibility(View.VISIBLE);
            }
        });

        btn_kick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(LiveRoomActivity.this);
                builder.setTitle(null);
                builder.setMessage("确认将此人踢出房间?");
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                nimController.kickMember(current_operate_member);
                                dismissMemberOperateLayout();
                            }
                        });
                builder.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });

        btn_mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(LiveRoomActivity.this);
                builder.setTitle(null);
                builder.setMessage("确认将此人在该直播间"+ (current_operate_member.isMuted()? "解禁?":" 禁言?"));
                builder.setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                nimController.muteMember(current_operate_member);
                                dismissMemberOperateLayout();
                            }
                        });
                builder.setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
    }

    /**
     * 初始化底部控制栏布局
     */
    private void initBottomBar() {
        liveBottomBar = new LiveBottomBar(this, isAudience, getIntent().getStringExtra(NimController.EXTRA_ROOM_ID));
        liveBottomBar.setSendGiftListen(new LiveBottomBar.SendGiftListen() {
            @Override
            public void send(GiftType type) {
                addGift2ListQueue(type);
                liveBottomBar.refreshMyIntegral();
            }
        });
        liveBottomBar.setGoPay(new LiveBottomBar.GoPay() {
            @Override
            public void pay(int type, String priceId) {
                Log.d("adasdasdas",priceId);
                ViseHttp.POST(NetConfig.buyIntegral)
                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.buyIntegral))
                        .addParam("paytype",type+"")
                        .addParam("uid",spImp.getUID())
                        .addParam("id",priceId)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Paymodel paymodel = new Gson().fromJson(data, Paymodel.class);
                                        showToast( "微信支付");
                                        wxPay(paymodel.getObj());
                                    }else if(jsonObject.getInt("code") == 300){
                                        showToast( "支付宝支付");
                                        Log.e("123123", jsonObject.optString("obj"));
                                        aliPay(jsonObject.optString("obj"));
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
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rootView.addView(liveBottomBar, layoutParams);

        if(isAudience) {
            audienceFragment.attachBottomBarToFragment(liveBottomBar);
        }else{
            captureFragment.attachBottomBarToFragment(liveBottomBar);
        }

        liveBottomBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {

        //未开始直播,则直接退出
        if (!isAudience && !isLiveStart) {
            super.onBackPressed();
            return;
        }

        showConfirmDialog(null, isAudience?"确定退出直播间？":"确定结束直播?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                normalFinishLive();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

    }

    /**
     * 正常结束直播
     */
    public void normalFinishLive() {
        //主播发送离开房间请求
        if(!isAudience) {
//            DemoServerHttpClient.getInstance().anchorLeave(roomId, new DemoServerHttpClient.DemoServerHttpCallback<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    //正常离开时,服务端会发送解散消息通知,此时根据解散时发送的被踢消息离开房间.
//
//                }
//
//                @Override
//                public void onFailed(int code, String errorMsg) {
//                }
//            });
            Log.d("sdasdadsa",spImp.getYXID()+"///roomId"+roomId);
            ViseHttp.POST(NetConfig.offRoom)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.offRoom))
                    .addParam("wy_accid",spImp.getYXID())
                    .addParam("room_id",roomId)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200){
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nimController.onDestroy();
        nimController.logoutChatRoom();
        if(captureFragment!=null) {
            captureFragment.destroyController();
        }
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
        DialogMaker.dismissProgressDialog();
    }

    /**
     * 根据账号获取聊天室成员对象
     * @param fromAccount
     * @return
     */
    private ChatRoomMember getCurrentClickMember(String fromAccount) {
        return liveRoomInfoFragment.getMember(fromAccount);
    }

    /**
     * 刷新房间信息
     * @param roomInfo
     */
    @Override
    public void refreshRoomInfo(ChatRoomInfo roomInfo) {
        liveRoomInfoFragment.refreshRoomInfo(roomInfo);
        List<String> accounts = new ArrayList<>();
        accounts.add(roomInfo.getCreator());
        NIMClient.getService(ChatRoomService.class).fetchRoomMembersByIds(roomInfo.getRoomId(), accounts).setCallback(new RequestCallback<List<ChatRoomMember>>() {
            @Override
            public void onSuccess(List<ChatRoomMember> chatRoomMembers) {
                if (chatRoomMembers.size()>0){
                    tv_finish_operate.setText(chatRoomMembers.get(0).getNick());
                    Glide.with(LiveRoomActivity.this).load(chatRoomMembers.get(0).getAvatar()).into(iv_operate);
                }
            }

            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

    /**
     * 刷新人员列表
     * @param result
     */
    @Override
    public void refreshRoomMember(List<ChatRoomMember> result) {
        if(result == null) return;
        liveRoomInfoFragment.updateMember(result);
    }

    /**
     * 聊天室结束回调
     * @param reason  结束原因
     */
    @Override
    public void onChatRoomFinished(String reason) {
        DialogMaker.dismissProgressDialog();
        ll_live_finish.setVisibility(View.VISIBLE);
        tv_finish_tip.setText(reason);
        liveBottomBar.setVisibility(View.GONE);
        if(isAudience && audienceFragment!=null){
            audienceFragment.stopWatching();
        }
    }

    /**
     * 点击人员时的回调
     * @param member
     */
    public void onMemberOperate(ChatRoomMember member) {
        //主播显示人员操作面板
        if(member!=null && !isAudience){
            current_operate_member = member;

            liveBottomBar.setVisibility(View.GONE);
            rl_member_operate.setVisibility(View.VISIBLE);
            Glide.with(LiveRoomActivity.this).load(member.getAvatar()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(iv_avatar);
            tv_nick_name.setText(member.getNick());
            if(member.isMuted()){
                btn_mute.setText("解禁");
            }else{
                btn_mute.setText("禁言");
            }
            if(isAudience){
                btn_mute.setEnabled(false);
                btn_kick.setEnabled(false);
            }
        }
    }

    /**
     * 隐藏人员操作布局
     */
    @Override
    public void dismissMemberOperateLayout() {
        rl_member_operate.setVisibility(View.GONE);
        liveBottomBar.setVisibility(View.VISIBLE);
    }

    /**
     * 直播开始完成的回调
     */
    public void onStartLivingFinished() {
        isLiveStart = true;
        chatLayout.setVisibility(View.VISIBLE);
        liveBottomBar.setVisibility(View.VISIBLE);
        roomInfoLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 直播断开时的回调
     */
    public void onLiveDisconnect(){
        isLiveStart = false;
        chatLayout.setVisibility(View.GONE);
        liveBottomBar.setVisibility(View.GONE);
        roomInfoLayout.setVisibility(View.GONE);
    }

    /**
     * 显示聊天输入布局 展开键盘
     */
    public void showInputPanel(){
        startInputActivity();
    }

    /**
     * ***************************** 部分机型键盘弹出会造成布局挤压的解决方案 ***********************************
     */
    private InputConfig inputConfig = new InputConfig(false, false, false);
    private String cacheInputString = "";

    private void startInputActivity() {
        InputActivity.startActivityForResult(this, cacheInputString,
                inputConfig, new InputActivity.InputActivityProxy() {
                    @Override
                    public void onSendMessage(String text) {
                        chatRoomFragment.onTextMessageSendButtonPressed(text);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == InputActivity.REQ_CODE) {
            // 设置EditText显示的内容
            cacheInputString = data.getStringExtra(InputActivity.EXTRA_TEXT);
        }
    }

    @Override
    public void showTextToast(String text) {
        showToast(text);
    }
    private void initSvga(){
        parser = new SVGAParser(LiveRoomActivity.this);
        svgaImageView.setLoops(1);
        svgaImageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
                svgaIsShow = false;
//                svgaImageView.setVisibility(View.GONE);
                Log.d("SVGALIST:","当前list中 含有"+listSvgaQueue.size()+"个！！");
                if (listSvgaQueue.size()>0){
                    listSvgaQueue.remove(0);
                }
                if (listSvgaQueue.size()>0){
                    startSVGA(listSvgaQueue.get(0));
                }
            }

            @Override
            public void onRepeat() {

            }

            @Override
            public void onStep(int i, double v) {

            }
        });
    }
    private void startSVGA(String s) {
        svgaIsShow = true;
//        svgaImageView.setVisibility(View.VISIBLE);
        parser.parse(s, new SVGAParser.ParseCompletion() {

            @Override
            public void onError() {

            }

            @Override
            public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
//                dynamicEntity.setClickArea("img_10");
                SVGADrawable drawable = new SVGADrawable(svgaVideoEntity, dynamicEntity);
                svgaImageView.setImageDrawable(drawable);
                svgaImageView.startAnimation();
            }
        });
    }
    private void addGift2ListQueue(GiftType type){
        Log.d("SVGALIST:","当前list中 含有"+listSvgaQueue.size()+"个！！"+type);
//        {"棒棒糖", "饮料", "魔法棒", "小熊","糖果雨","爱心","捧花","钻石","心雨","戒指","别墅","游艇"};
        switch (type){
            case BANGBANGTANG:
                listSvgaQueue.add(arrSvgaAssets[0]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case YINLIAO:
                listSvgaQueue.add(arrSvgaAssets[1]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case MOFABANG:
                listSvgaQueue.add(arrSvgaAssets[2]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case XIAOXIONG:
                listSvgaQueue.add(arrSvgaAssets[3]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case TANGGUOYU:
                listSvgaQueue.add(arrSvgaAssets[4]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case AIXIN:
                listSvgaQueue.add(arrSvgaAssets[5]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case PENGHUA:
                listSvgaQueue.add(arrSvgaAssets[6]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case ZUANSHI:
                listSvgaQueue.add(arrSvgaAssets[7]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case XINYU:
                listSvgaQueue.add(arrSvgaAssets[8]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            //        {"棒棒糖", "饮料", "魔法棒", "小熊","糖果雨","爱心","捧花","钻石","心雨","戒指","别墅","游艇"};
            case JIEZHI:
                listSvgaQueue.add(arrSvgaAssets[9]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case BIESHU:
                listSvgaQueue.add(arrSvgaAssets[10]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
            case YOUTING:
                listSvgaQueue.add(arrSvgaAssets[11]);
                if (!svgaIsShow){
                    startSVGA(listSvgaQueue.get(0));
                }
                break;
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
        req.extData = "tongbi_chongzhi";
        api.sendReq(req);
        liveBottomBar.refreshMyIntegral();
    }

    public void aliPay(String info) {
        final String orderInfo = info;   // 订单信息

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(LiveRoomActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandlerPay.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandlerPay = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Map<String, String> result = (Map<String, String>) msg.obj;

                    for (Map.Entry<String, String> entry : result.entrySet()) {
                        Log.d("dadaadad",entry.getKey() + ":" + entry.getValue());
                    }

                    if(result.get("resultStatus").equals("9000")){
                        Toast.makeText(LiveRoomActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        if (isAudience){
                            liveBottomBar.refreshMyIntegral();
                        }
//                        getActivity().finish();
                    }
                    break;
            }
        }

    };
}
