package com.yiwo.friendscometogether.pages;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.IntercalationAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.model.UserIntercalationPicModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.SolveEditTextScrollClash;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

//作者自己续写页面
public class CreateIntercalationActivity extends BaseActivity {

    @BindView(R.id.activity_create_intercalation_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_create_intercalation_rv)
    RecyclerView recyclerView;
    @BindView(R.id.activity_create_intercalation_et_title)
    EditText etTitle;
    @BindView(R.id.activity_create_intercalation_et_content)
    EditText etContent;
    @BindView(R.id.activity_create_intercalation_tv_text_num)
    TextView tvContentNum;
    @BindView(R.id.ll_bottom)
    LinearLayout llbtnsJiXu_FaBu;//从创建友记入口进来 type 为 0 未发布状态
    @BindView(R.id.ll_bottom2)
    LinearLayout llBaoCun ; //从编辑友记入口进来 草稿 状态是 2 ，已发布状态是 1
    private IntercalationAdapter adapter;
    private List<UserIntercalationPicModel> mList;

    private static final int REQUEST_CODE = 0x00000011;

    private SpImp spImp;
    private String uid = "";
    private String id = "";

    private List<File> files = new ArrayList<>();

    private Dialog dialog;

    private PopupWindow popupWindow;

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_intercalation);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());

        ButterKnife.bind(this);
        spImp = new SpImp(CreateIntercalationActivity.this);

        initData();

    }

    private void initData() {

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");//草稿 状态是 2 ，已发布状态是 1 ,从创建友记页面进入状态是0（也是未发布状态
        Log.d("sssssaaaaa ",""+type);
        if(type.equals("0")){
            llBaoCun.setVisibility(View.GONE);
            llbtnsJiXu_FaBu.setVisibility(View.VISIBLE);
            Log.d("sssssaaaaa  ","创建友记进入");
        }
        else {
            llBaoCun.setVisibility(View.VISIBLE);
            llbtnsJiXu_FaBu.setVisibility(View.GONE);
            Log.d("sssssaaaaa  ","编辑友记进入");
        }

        uid = spImp.getUID();
        mList = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(CreateIntercalationActivity.this, 3);
        recyclerView.setLayoutManager(manager);
        adapter = new IntercalationAdapter(mList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new IntercalationAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - mList.size()) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(CreateIntercalationActivity.this, REQUEST_CODE); // 打开相册
            }
        }, new IntercalationAdapter.OnDeleteImgListener() {
            @Override
            public void onDeleteImg(int i) {
                mList.remove(i);
                adapter.notifyDataSetChanged();
            }
        }, new IntercalationAdapter.OnAddDescribeListener() {
            @Override
            public void onAddDescribe(int i, String s) {
                mList.get(i).setDescribe(s);
                adapter.notifyDataSetChanged();
            }
        });

        etContent.addTextChangedListener(textContentWatcher);
        etContent.setOnTouchListener(new SolveEditTextScrollClash(etContent));
    }

    TextWatcher textContentWatcher = new TextWatcher() {

        private CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            tvContentNum.setText(temp.length() + "/2000");
            if (temp.length() >= 2000) {
                toToast(CreateIntercalationActivity.this, "您输入的字数已经超过了限制");
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            List<String> pic = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            for (int i = 0; i < pic.size(); i++) {
                Log.i("333", pic.get(i));
                mList.add(new UserIntercalationPicModel(pic.get(i), ""));
            }
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.activity_create_intercalation_rl_back,R.id.btn_baocun,R.id.btn_jixuchaungzuo,R.id.btn_lijifabu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_create_intercalation_rl_back:
                onBackPressed();
                break;
            case R.id.btn_lijifabu:
                complete(0,false);//发布
                break;
            case R.id.btn_jixuchaungzuo:
                complete(1,true);//存草稿 并且再次打开创建续写页面进行创作
                break;
            case R.id.btn_baocun:
                if (type.equals("1")){
                    complete(0,false);//发布
                }else {
                    complete(1,false);//存草稿
                }
//                if (mList.size()==0){
//                    toToast(CreateIntercalationActivity.this,"请添加图片");
//                }else {
//                    if (type.equals("1")){
//                        complete(0,false);//发布
//                    }else {
//                        complete(1,false);//存草稿
//                    }
//                }
                break;
        }
    }
    /**
     * 发布
     * type 0 发布，1存草稿
     * openagain 发布并且再次打开创作页面
     */
    private void complete(final int stuas,boolean openagain) {
        if (mList.size()==0&&etTitle.getText().toString().equals("")&&etContent.getText().toString().equals("")){
            toToast(this,"内容不能为空");
            return;
        }
        dialog = WeiboDialogUtils.createLoadingDialog(CreateIntercalationActivity.this, "请等待...");
        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
                final Map<String, File> map = new LinkedHashMap<>();
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    list.add(mList.get(i).getPic());
                }
                if (list.size()>0){
                    Luban.with(CreateIntercalationActivity.this)
                            .load(list)
                            .ignoreBy(100)
                            .filter(new CompressionPredicate() {
                                @Override
                                public boolean apply(String path) {
                                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                                }
                            })
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    // TODO 压缩成功后调用，返回压缩后的图片文件
                                    files.add(file);
                                    Log.e("222", list.size() + "..." + files.size());
                                    if (files.size() == list.size()) {
                                        for (int i = 0; i < files.size(); i++) {
                                            map.put("images[" + i + "]", files.get(i));
                                        }
                                        Log.e("222", map.size() + "");
                                        e.onNext(map);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                    WeiboDialogUtils.closeDialog(dialog);
                                }
                            }).launch();
                }else {
                    e.onNext(map);
                }
            }
        });
        Observer<Map<String, File>> observer = new Observer<Map<String, File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, File> value) {

                String describe = "";
                for (int i = 0; i < mList.size(); i++) {
                    describe = describe + mList.get(i).getDescribe() + "|";
                }
                Log.e("222", describe);

                ViseHttp.UPLOAD(NetConfig.userRenewTheArticle)
                        .addHeader("Content-Type", "multipart/form-data")
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userRenewTheArticle))
                        .addParam("title", etTitle.getText().toString())
                        .addParam("content", etContent.getText().toString())
                        .addParam("id", id)
                        .addParam("uid", uid)
                        .addParam("type", stuas + "")
                        .addParam("describe", describe)
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    Log.e("222", data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        toToast(CreateIntercalationActivity.this, "创建成功");
                                        WeiboDialogUtils.closeDialog(dialog);
                                        if (openagain){
                                            Intent intent = new Intent();
                                            intent.putExtra("id", id);
                                            intent.putExtra("type", type);//传0为当前友记为未发布状态
                                            intent.setClass(CreateIntercalationActivity.this, CreateIntercalationActivity.class);
                                            startActivity(intent);
                                        }
                                        onBackPressed();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    WeiboDialogUtils.closeDialog(dialog);
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                WeiboDialogUtils.closeDialog(dialog);
            }

            @Override
            public void onComplete() {
                WeiboDialogUtils.closeDialog(dialog);
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CreateIntercalationActivity.this.finish();
    }
}
