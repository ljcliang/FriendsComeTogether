package com.yiwo.friendscometogether.newpage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

public class ApplyForCaptainActivity extends BaseActivity {

    @BindView(R.id.iv)
    ImageView imageView;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_person_num)
    EditText edtPersonNum;
    @BindView(R.id.edt_phone_num)
    EditText edtPhoneNum;
    @BindView(R.id.edt_shop_name)
    EditText edtShopName;
    @BindView(R.id.tv_yingyezhizhao)
    TextView tvYingyezhizhao;
    @BindView(R.id.rl_upload_img)
    RelativeLayout rlUploadImg;
    @BindView(R.id.ll_shenqing_show)
    LinearLayout llShenqingShow;
    @BindView(R.id.tv_show_text)
    TextView tvShowText;
    @BindView(R.id.rl_show_text)
    RelativeLayout rlShowText;
    @BindView(R.id.btn_finish)
    Button btnFinish;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.btn_upload_again)
    Button btnUploadAgain;
    private SpImp spImp;
    private Dialog dialog;
    private String imageUrl = "";
    private static final int REQUEST_CODE = 0x00000011;
    private String status = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_captain);
//        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initData();
    }

    private void initData() {
        ViseHttp.POST(NetConfig.getUserAuthInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getUserAuthInfo))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("obj");
                                status = jsonObject1.getString("status");
                                switch (status) {
                                    case "1"://已是队长
                                        llShenqingShow.setVisibility(View.GONE);
                                        rlShowText.setVisibility(View.VISIBLE);
                                        tvShowText.setText("您已认证成功！");
                                        btnFinish.setVisibility(View.VISIBLE);
                                        btnUpload.setVisibility(View.GONE);
                                        btnUploadAgain.setVisibility(View.GONE);
                                        break;
                                    case "2"://审核中
                                        llShenqingShow.setVisibility(View.GONE);
                                        rlShowText.setVisibility(View.VISIBLE);
                                        tvShowText.setText("审核中...");
                                        btnFinish.setVisibility(View.VISIBLE);
                                        btnUpload.setVisibility(View.GONE);
                                        btnUploadAgain.setVisibility(View.GONE);
                                        break;
                                    case "3"://没有通过审核
                                        llShenqingShow.setVisibility(View.GONE);
                                        rlShowText.setVisibility(View.VISIBLE);
                                        tvShowText.setText("您未通过认证");
                                        btnFinish.setVisibility(View.GONE);
                                        btnUpload.setVisibility(View.GONE);
                                        btnUploadAgain.setVisibility(View.VISIBLE);
                                        edtName.setText(jsonObject1.getString("name"));
                                        edtPersonNum.setText(jsonObject1.getString("usercodenum"));
                                        break;
                                    case "4"://未提交过队长认证 已实名认证
                                        llShenqingShow.setVisibility(View.VISIBLE);
                                        rlShowText.setVisibility(View.GONE);
                                        btnFinish.setVisibility(View.GONE);
                                        btnUpload.setVisibility(View.VISIBLE);
                                        btnUploadAgain.setVisibility(View.GONE);
                                        break;
                                    case "5"://未提交过队长认证 也未实名认证
                                        llShenqingShow.setVisibility(View.VISIBLE);
                                        rlShowText.setVisibility(View.GONE);
                                        btnFinish.setVisibility(View.GONE);
                                        btnUpload.setVisibility(View.VISIBLE);
                                        btnUploadAgain.setVisibility(View.GONE);
                                        edtName.setText(jsonObject1.getString("name"));
                                        edtPersonNum.setText(jsonObject1.getString("usercodenum"));
                                        break;

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

    @OnClick({R.id.iv, R.id.rl_back,R.id.btn_finish, R.id.btn_upload, R.id.btn_upload_again})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
            case R.id.btn_finish:
                onBackPressed();
                break;
            case R.id.iv:
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(ApplyForCaptainActivity.this, REQUEST_CODE); // 打开相册
                break;
            case R.id.btn_upload:
                if (imageUrl.equals("")) {
                    toToast(ApplyForCaptainActivity.this,"请选择相关证件照片");
                    break;
                }
                if (edtName.getText().toString().equals("")) {
                    toToast(ApplyForCaptainActivity.this,"请输入真实姓名");
                    break;
                }
                if (edtPersonNum.getText().toString().equals("")) {
                    toToast(ApplyForCaptainActivity.this,"请输入身份证号");
                    break;
                }
                if (edtPhoneNum.getText().toString().equals("")) {
                    toToast(ApplyForCaptainActivity.this,"请输入联系电话");
                    break;
                }
                if (edtShopName.getText().toString().equals("")) {
                    toToast(ApplyForCaptainActivity.this,"请输入所属旅行社");
                    break;
                }
//                upLoad();
                break;
            case R.id.btn_upload_again:
                llShenqingShow.setVisibility(View.VISIBLE);
                rlShowText.setVisibility(View.GONE);
                btnFinish.setVisibility(View.GONE);
                btnUpload.setVisibility(View.VISIBLE);
                btnUploadAgain.setVisibility(View.GONE);
                break;
        }

    }

    private void upLoad() {
        Observable<List<File>> observable = Observable.create(new ObservableOnSubscribe<List<File>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<File>> e) throws Exception {
                dialog = WeiboDialogUtils.createLoadingDialog(ApplyForCaptainActivity.this, "请等待...");
                List<String> list = new ArrayList<>();
                list.add(imageUrl);
                final List<File> files = new ArrayList<>();
                Luban.with(ApplyForCaptainActivity.this)
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
                                if (files.size() == 1) {
                                    e.onNext(files);
                                } else {
                                    WeiboDialogUtils.closeDialog(dialog);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                WeiboDialogUtils.closeDialog(dialog);
                                toToast(ApplyForCaptainActivity.this, "上传失败，请重试");

                            }
                        }).launch();
            }
        });
        Observer<List<File>> observer = new Observer<List<File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<File> value) {
                ViseHttp.UPLOAD(NetConfig.pleaseCaptain)
                        .addHeader("Content-Type", "multipart/form-data")
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.pleaseCaptain))
                        .addParam("uid", spImp.getUID())
                        .addFile("captainImg", value.get(0))
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("22222", data + "::::");
                                Log.e("22222::", data + "");
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    if (!TextUtils.isEmpty(data)) {
                                        jsonObject = new JSONObject(data);
                                    }
                                    if (jsonObject.getInt("code") == 200) {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ApplyForCaptainActivity.this);
                                        builder.setMessage("已提交审核")
                                                .setNegativeButton("好的", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        onBackPressed();
                                                    }
                                                }).show();
                                    }
                                    if (jsonObject.getInt("code") == 400) {
                                        toToast(ApplyForCaptainActivity.this, jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                WeiboDialogUtils.closeDialog(dialog);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.e("22222", errMsg + errCode);
                                WeiboDialogUtils.closeDialog(dialog);
                                toToast(ApplyForCaptainActivity.this, "提交失败：" + errMsg);
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                WeiboDialogUtils.closeDialog(dialog);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            imageUrl = scList.get(0);
            Glide.with(ApplyForCaptainActivity.this).load(imageUrl).into(imageView);

        }
    }
}
