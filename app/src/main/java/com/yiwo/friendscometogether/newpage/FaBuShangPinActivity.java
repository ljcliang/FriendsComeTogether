package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.WeiboDialogUtils;
import com.yiwo.friendscometogether.model.NewUserIntercalationPicModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.FabuShangpinIntercalationPicsAdapter;
import com.yiwo.friendscometogether.newadapter.ShangPinLabelAdapter;
import com.yiwo.friendscometogether.newadapter.ShangPinServiceAdapter;
import com.yiwo.friendscometogether.newadapter.fabushangpinadapter.FaBuShangPinJiaGeAdapter;
import com.yiwo.friendscometogether.newmodel.ShangPinUpLoadModel;
import com.yiwo.friendscometogether.newmodel.ShangPinLabelModel;
import com.yiwo.friendscometogether.newmodel.ShangPinServiceModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

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

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

public class FaBuShangPinActivity extends TakePhotoActivity {


    @BindView(R.id.rv_choose_pics)
    RecyclerView rvImages;

    @BindView(R.id.edt_shangpin_name)
    EditText edtShangPinName;
    @BindView(R.id.edt_shangpin_info)
    EditText edtShangPinInfo;
    //价格
    @BindView(R.id.rl_btn_add_price)
    RelativeLayout rlBtnAddPrice;
    @BindView(R.id.rv_price)
    RecyclerView rvPrice;
    private FaBuShangPinJiaGeAdapter adapterPrice;
    private List<ShangPinUpLoadModel.SpecBean> listPrices = new ArrayList<>();
    private FaBuShangPinJiaGeAdapter.DeleteItemListenner shangpinjiageDeleteItemLisner;

    //选择服务（多选）
    @BindView(R.id.rl_btn_add_service)
    RelativeLayout rlBtnAddService;
    private PopupWindow popupWindow;
    private View viewPop;
    private RecyclerView rvListServices;
    private List<ShangPinServiceModel.ObjBean> listShangPinServiceList = new ArrayList<>();
    private TextView tvBtnSaveChoosedService;
    private RelativeLayout rlAddNewService;
    private ShangPinServiceAdapter shangPinServiceListAdapter;
    //已选服务
    @BindView(R.id.rv_choosed_service)
    RecyclerView rvChoosedService;
    private ShangPinServiceAdapter shangPinServiceChoosedAdapter;
    private List<ShangPinServiceModel.ObjBean> listShangPinServiceChoosed = new ArrayList<>();

    //标签
    @BindView(R.id.rv_label)
    RecyclerView rvLabel;
    private ShangPinLabelAdapter shangPinLabelAdapter;
    private List<ShangPinLabelModel.ObjBean> listShangPinLabel = new ArrayList<>();

    private List<NewUserIntercalationPicModel> mList;
    private FabuShangpinIntercalationPicsAdapter adapter;
    private static final int REQUEST_CODE1 = 0x00000012;
    private static final int REQUEST_CODE_ADD_SERVICE = 2;
    private SpImp spImp;
    private Dialog dialog;
    private List<File> files = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu_shang_pin);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        initUpData();
        initRv();
        initPopServiceList();
    }

    private void initRv() {
        //价格recyclerview---------------------------------------------
        ShangPinUpLoadModel.SpecBean bean = new ShangPinUpLoadModel.SpecBean();
        listPrices.add(bean);
        shangpinjiageDeleteItemLisner = new FaBuShangPinJiaGeAdapter.DeleteItemListenner() {
            @Override
            public void deleteItem(int pos) {
                if (listPrices.size()>1){
                    listPrices.remove(pos);
                    adapterPrice = new FaBuShangPinJiaGeAdapter(listPrices,shangpinjiageDeleteItemLisner);
                    rvPrice.setAdapter(adapterPrice);
                }
                if (listPrices.size()>2){
                    rlBtnAddPrice.setVisibility(View.GONE);
                }else {
                    rlBtnAddPrice.setVisibility(View.VISIBLE);
                }
            }
        };
        adapterPrice = new FaBuShangPinJiaGeAdapter(listPrices,shangpinjiageDeleteItemLisner );
        LinearLayoutManager managerPrice = new LinearLayoutManager(FaBuShangPinActivity.this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        managerPrice.setOrientation(LinearLayoutManager.VERTICAL);
        rvPrice.setLayoutManager(managerPrice);
        rvPrice.setAdapter(adapterPrice);
        //已选服务rv-------------------------------------------------------------------------
        shangPinServiceChoosedAdapter = new ShangPinServiceAdapter(listShangPinServiceChoosed,false);
        LinearLayoutManager mangerChoosedService = new LinearLayoutManager(FaBuShangPinActivity.this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvChoosedService.setLayoutManager(mangerChoosedService);
        rvChoosedService.setAdapter(shangPinServiceChoosedAdapter);
        //标签rv---------------------------------------------------------------------------------
        shangPinLabelAdapter = new ShangPinLabelAdapter(listShangPinLabel);
        GridLayoutManager managerLabel = new GridLayoutManager(FaBuShangPinActivity.this,4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        managerLabel.setOrientation(LinearLayoutManager.VERTICAL);
        rvLabel.setLayoutManager(managerLabel);
        rvLabel.setAdapter(shangPinLabelAdapter);
        callLabelData();
    }

    /**
     * 初始化上传图片
     */
    private void initUpData() {

        mList = new ArrayList<>();
//        GridLayoutManager manager = new GridLayoutManager(CreateFriendRememberActivity1.this, 3);
//        recyclerView.setLayoutManager(manager);
        LinearLayoutManager manager = new LinearLayoutManager(FaBuShangPinActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvImages.setLayoutManager(manager);
        adapter = new FabuShangpinIntercalationPicsAdapter(mList);
        adapter.setDescribe(false);
        rvImages.setAdapter(adapter);
        adapter.setListener(new FabuShangpinIntercalationPicsAdapter.OnAddImgListener() {
            @Override
            public void onAddImg() {
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - mList.size()) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(FaBuShangPinActivity.this, REQUEST_CODE1); // 打开相册
            }
        }, new FabuShangpinIntercalationPicsAdapter.OnDeleteImgListener() {
            @Override
            public void onDeleteImg(int i) {
                mList.remove(i);
                adapter.notifyDataSetChanged();
            }
        }, new FabuShangpinIntercalationPicsAdapter.OnAddDescribeListener() {
            @Override
            public void onAddDescribe(int i, String s) {
                mList.get(i).setDescribe(s);
                adapter.notifyDataSetChanged();
            }
        }, new FabuShangpinIntercalationPicsAdapter.OnSetFirstPicListienner() {
            @Override
            public void onSetFirst(final int postion) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(FaBuShangPinActivity.this);
                builder.setMessage("设置为首图？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i<mList.size();i++){
                                    if (i == postion){
//                                       NewUserIntercalationPicModel model = mList.get(postion);
//                                       model.setFirstPic(true);
                                        mList.get(i).setFirstPic(true);
                                    }else {
                                        mList.get(i).setFirstPic(false);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }
    private void callLabelData(){
        ViseHttp.POST(NetConfig.tagList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.tagList))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson =  new Gson();
                                listShangPinLabel.clear();
                                listShangPinLabel.addAll(gson.fromJson(data,ShangPinLabelModel.class).getObj());
                                shangPinLabelAdapter.notifyDataSetChanged();
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
    private void initPopServiceList(){
        viewPop = LayoutInflater.from(FaBuShangPinActivity.this).inflate(R.layout.popupwindow_rv_choose_item,null);
        rlAddNewService = viewPop.findViewById(R.id.rl_add_new_service);
        rlAddNewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FaBuShangPinActivity.this,ShangPinServiceEditActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ADD_SERVICE);
            }
        });
        tvBtnSaveChoosedService = viewPop.findViewById(R.id.tv_btn_add);
        tvBtnSaveChoosedService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listShangPinServiceChoosed.clear();
                for (ShangPinServiceModel.ObjBean bean : listShangPinServiceList){
                    if (bean.isChecked()){
                        listShangPinServiceChoosed.add(bean);
                    }
                }
                shangPinServiceChoosedAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
        rvListServices = viewPop.findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(FaBuShangPinActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListServices.setLayoutManager(manager);
        shangPinServiceListAdapter = new ShangPinServiceAdapter(listShangPinServiceList,true);
        rvListServices.setAdapter(shangPinServiceListAdapter);
    }
    private void showPopupServicesList(){
        ViseHttp.POST(NetConfig.serveList)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.serveList))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200){
                                Gson gson =  new Gson();
                                listShangPinServiceList.clear();
                                listShangPinServiceList.addAll(gson.fromJson(data,ShangPinServiceModel.class).getObj());
                                shangPinServiceListAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        popupWindow = new PopupWindow(viewPop, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(viewPop, Gravity.BOTTOM, 0, 0);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
               getWindow().setAttributes(params);
            }
        });
    }
    @OnClick({R.id.rl_back,R.id.rl_btn_add_price,R.id.rl_btn_add_service,R.id.rl_save})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rl_save:
                save();
                break;
            case R.id.rl_btn_add_price:
                    ShangPinUpLoadModel.SpecBean bean = new ShangPinUpLoadModel.SpecBean();
                    listPrices.add(bean);
//                listPrices.add(listPrices.size()-1,bean);
                adapterPrice = new FaBuShangPinJiaGeAdapter(listPrices,shangpinjiageDeleteItemLisner);
                rvPrice.setAdapter(adapterPrice);
                if (listPrices.size()>2){
                    rlBtnAddPrice.setVisibility(View.GONE);
                }else {
                    rlBtnAddPrice.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_btn_add_service:
                showPopupServicesList();
                break;
        }
    }

    private void save() {
        ShangPinUpLoadModel model = new ShangPinUpLoadModel();
        if (edtShangPinName.getText().toString().equals("")){
            Toast.makeText(FaBuShangPinActivity.this,"请填写商品名称",Toast.LENGTH_SHORT).show();
            return;
        }else {
            model.setGoodsName(edtShangPinName.getText().toString());
        }
        model.setGoodsInfo(edtShangPinInfo.getText().toString());
        String strServicesIdChoose = "";
        for (ShangPinServiceModel.ObjBean bean : listShangPinServiceChoosed){
            if (bean.isChecked()) strServicesIdChoose = strServicesIdChoose + bean.getId();
        }
        strServicesIdChoose = strServicesIdChoose.replace(""," ").trim();
        strServicesIdChoose = strServicesIdChoose.replace(" ",",");
        String strLabelsIdChoose = "";
        for (ShangPinLabelModel.ObjBean bean : listShangPinLabel){
            if (bean.isChecked()) strLabelsIdChoose = strLabelsIdChoose + bean.getId();
        }
        strLabelsIdChoose = strLabelsIdChoose.replace(""," ").trim();
        strLabelsIdChoose = strLabelsIdChoose.replace(" ",",");
        model.setService(strServicesIdChoose);
        model.setTag(strLabelsIdChoose);
        List<ShangPinUpLoadModel.SpecBean> listUp = new ArrayList<>();
        for (ShangPinUpLoadModel.SpecBean bean : listPrices){//过滤没有填写任何信息的价格
            if (bean.getSpec().equals("") && bean.getOldPrice().equals("") && bean.getNowPrice().equals("") && bean.getAllNum().equals("")){
                continue;
            }else {
                listUp.add(bean);
            }
        }
        if (listUp.size()>0){
            for (ShangPinUpLoadModel.SpecBean bean : listUp){
                if (bean.getSpec().equals("")){
                    Toast.makeText(FaBuShangPinActivity.this,"商品规格不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (bean.getNowPrice().equals("")&&bean.getOldPrice().equals("")){
                    Toast.makeText(FaBuShangPinActivity.this,"商品价格不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }else {
            Toast.makeText(FaBuShangPinActivity.this,"请填写商品价格和规格",Toast.LENGTH_SHORT).show();
            return;
        }
        model.setSpec(listUp);
        Gson gson = new Gson();
        Log.d("JSON::::",gson.toJson(model));
//        if (true) return;
        if (mList.size()==0){
            Toast.makeText(FaBuShangPinActivity.this,"请选择商品图片",Toast.LENGTH_SHORT).show();
            return;
        }
        dialog = WeiboDialogUtils.createLoadingDialog(FaBuShangPinActivity.this, "请等待...");
        Observable<Map<String, File>> observable = Observable.create(new ObservableOnSubscribe<Map<String, File>>() {
            @Override
            public void subscribe(final ObservableEmitter<Map<String, File>> e) throws Exception {
                final Map<String, File> map = new LinkedHashMap<>();
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).getFirstPic()){
                        list.add(0,mList.get(i).getPic());
                    }else {
                        list.add(mList.get(i).getPic());
                    }
                }
                Luban.with(FaBuShangPinActivity.this)
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
                                        map.put("goods_files[" + i + "]", files.get(i));
                                    }
                                    Log.e("222", map.size() + "");
                                    e.onNext(map);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();

            }
        });
        Observer<Map<String, File>> observer = new Observer<Map<String, File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, File> value) {
                ViseHttp.UPLOAD(NetConfig.addGoods)
                        .addHeader("Content-Type","multipart/form-data")
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.addGoods))
                        .addParam("uid",spImp.getUID())
                        .addParam("goodsInfo", gson.toJson(model))
                        .addFiles(value)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("上传返回", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200) {
                                        Toast.makeText(FaBuShangPinActivity.this, jsonObject.getString("message") + "", Toast.LENGTH_SHORT).show();
                                        WeiboDialogUtils.closeDialog(dialog);
                                        finish();
                                    }else {
                                        WeiboDialogUtils.closeDialog(dialog);
                                        Toast.makeText(FaBuShangPinActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    WeiboDialogUtils.closeDialog(dialog);
                                    Toast.makeText(FaBuShangPinActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.e("上传返回", errMsg);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE1 && data != null) {
            //获取选择器返回的数据
            List<String> pic = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            for (int i = 0; i < pic.size(); i++) {
                Log.i("333", pic.get(i));
                mList.add(new NewUserIntercalationPicModel(pic.get(i), ""));
            }
            adapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_CODE_ADD_SERVICE && resultCode == ShangPinServiceEditActivity.ADD_SUCCESS_RESULT_CODE && data!=null){
            listShangPinServiceList.add((ShangPinServiceModel.ObjBean) data.getSerializableExtra(ShangPinServiceEditActivity.NEW_ADD_SERVICE_MODEL));
            shangPinServiceListAdapter.notifyDataSetChanged();
        }
    }
}
