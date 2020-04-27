package com.yiwo.friendscometogether.pages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.ArticleCommentAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.emoji.EmotionMainFragment;
import com.yiwo.friendscometogether.model.ArticleCommentListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.tongban_emoticon.TbEmoticonFragment;
import com.yiwo.friendscometogether.utils.SoftKeyBoardListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleCommentActivity extends BaseActivity {

    @BindView(R.id.activity_article_comment_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_article_comment_rv)
    RecyclerView recyclerView;

    private ArticleCommentAdapter adapter;
    private List<ArticleCommentListModel.ObjBean> mList;

    private String fmID = "";

    private SpImp spImp;
    private String uid = "";

    private boolean isComment = true;
    private Dialog dialog;

    /**
     * popupwindow相关
     */
    private Button btn_submit;
    private ImageView btn_back;
    private PopupWindow popupWindowhf;

    private String userid = "";
    private String fcid = "";

//    private EmotionMainFragment emotionMainFragment;
    private TbEmoticonFragment emotionMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_comment);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(ArticleCommentActivity.this);

        //注册软键盘的监听
        SoftKeyBoardListener.setListener(ArticleCommentActivity.this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
//                        Toast.makeText(TieziXqActivity.this,
//                                "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void keyBoardHide(int height) {
//                        Toast.makeText(TieziXqActivity.this,
//                                "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
                        if (popupWindowhf != null) {
                            popupWindowhf.dismiss();
                        }
                    }
                });

        initData();
        initEmotionMainFragment();

    }

    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment(){
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        emotionMainFragment = TbEmoticonFragment.newInstance(TbEmoticonFragment.class,new Bundle());
        emotionMainFragment.setCommitListenner(new TbEmoticonFragment.OnCommitListenner() {
            @Override
            public void onCommitListen(String string) {
                if (TextUtils.isEmpty(string)) {
                    toToast(ArticleCommentActivity.this, "请输入评论内容");
                } else {
                    toComment(string);
                }
            }
        });
        transaction.replace(R.id.fl_emotionview_main,emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    private void initData() {

        uid = spImp.getUID();
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                emotionMainFragment.hideEmotionKeyboard();
                emotionMainFragment.goneKeyboard();
                return false;
            }
        });
        Intent intent = getIntent();
        fmID = intent.getStringExtra("id");

        LinearLayoutManager manager = new LinearLayoutManager(ArticleCommentActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ViseHttp.POST(NetConfig.articleCommentListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCommentListUrl))
                .addParam("fmID", fmID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                ArticleCommentListModel model = gson.fromJson(data, ArticleCommentListModel.class);
                                mList = model.getObj();
                                adapter = new ArticleCommentAdapter(mList);
                                recyclerView.setAdapter(adapter);
                                adapter.setOnReplyListener(new ArticleCommentAdapter.OnReplyListener() {
                                    @Override
                                    public void onReply(int position, String id) {
//                                        showPopupCommnet(1, id, mList.get(position).getFcID());
                                        userid = id;
                                        fcid = mList.get(position).getFcID();
                                        isComment = false;
                                        emotionMainFragment.showKeyBoard();
                                    }
                                });
                                adapter.setDeleteListener(new ArticleCommentAdapter.OnDeleteListener() {
                                    @Override
                                    public void onDelete(final String id, String c) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ArticleCommentActivity.this);
                                        builder.setMessage("是否删除“"+c+"”")
                                                .setNegativeButton("是", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        deletePinglun(id);
                                                    }
                                                }).setPositiveButton("否", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                            }
                                        }).show();
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

    private void deletePinglun(String id) {
        ViseHttp.POST(NetConfig.managerComments)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.managerComments))
                .addParam("type", "0")
                .addParam("delID",id)
                .addParam("userID",spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                toToast(ArticleCommentActivity.this,"删除成功");
                                initData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(ArticleCommentActivity.this,"删除失败："+errCode+"/"+errMsg);
                    }
                });

    }

    @OnClick({R.id.activity_article_comment_rl_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_article_comment_rl_back:
                onBackPressed();
                break;
        }
    }

    /**
     * 提交评论
     */
    private void toComment(String commentStr) {
        if(isComment){
            dialog = WeiboDialogUtils.createLoadingDialog(ArticleCommentActivity.this,"");
            ViseHttp.POST(NetConfig.articleCommentUrl)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCommentUrl))
                    .addParam("id", fmID)
                    .addParam("fctitle", commentStr)
                    .addParam("uid", uid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                Log.e("222", data);
                                JSONObject jsonObject = new JSONObject(data);
                                WeiboDialogUtils.closeDialog(dialog);
                                if (jsonObject.getInt("code") == 200) {
                                    toToast(ArticleCommentActivity.this, "评论成功");
//                                    emotionMainFragment.hideEmotionKeyboard();
                                    emotionMainFragment.goneKeyboard();
                                    emotionMainFragment.clearEdt();
                                    reload();
//                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(dialog);
                            toToast(ArticleCommentActivity.this, "评论失败："+errCode+"//"+errMsg);
                        }
                    });
        }else {
            dialog = WeiboDialogUtils.createLoadingDialog(ArticleCommentActivity.this,"");
            ViseHttp.POST(NetConfig.replyCommentUrl)
                    .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.replyCommentUrl))
                    .addParam("commentid", userid)
                    .addParam("first_fcID", userid)
                    .addParam("ArticleId", fmID)
                    .addParam("fctitle", commentStr)
                    .addParam("uid", uid)
                    .addParam("oneID", fcid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                Log.e("222", data);
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.getInt("code") == 200){
                                    WeiboDialogUtils.closeDialog(dialog);
                                    toToast(ArticleCommentActivity.this, "回复成功");
//                                    emotionMainFragment.hideEmotionKeyboard();
                                    emotionMainFragment.goneKeyboard();
                                    emotionMainFragment.clearEdt();
                                    reload();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            isComment = true;
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(dialog);
                            toToast(ArticleCommentActivity.this, "评论失败："+errCode+"//"+errMsg);
                            isComment = true;
                        }
                    });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ArticleCommentActivity.this.finish();
    }
    private void reload (){
        ViseHttp.POST(NetConfig.articleCommentListUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.articleCommentListUrl))
                .addParam("fmID", fmID)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            Log.e("222", data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                ArticleCommentListModel model = gson.fromJson(data, ArticleCommentListModel.class);
                                mList.clear();
                                mList.addAll(model.getObj());
                                adapter.notifyDataSetChanged();
                                Log.e("222", "刷新页面");
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
