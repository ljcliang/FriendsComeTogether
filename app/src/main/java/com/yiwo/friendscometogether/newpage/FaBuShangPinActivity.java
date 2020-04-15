package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.NewUserIntercalationPicModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.FabuShangpinIntercalationPicsAdapter;
import com.yiwo.friendscometogether.newadapter.NewCreateFriendRemberIntercalationAdapter;
import com.yiwo.friendscometogether.newadapter.ShangPinServiceAdapter;
import com.yiwo.friendscometogether.newadapter.fabushangpinadapter.FaBuShangPinJiaGeAdapter;
import com.yiwo.friendscometogether.newmodel.FabuShangPinUpLoadModel;
import com.yiwo.friendscometogether.newmodel.ShangPinServiceModel;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

public class FaBuShangPinActivity extends TakePhotoActivity {


    @BindView(R.id.rv_choose_pics)
    RecyclerView rvImages;
    //价格
    @BindView(R.id.rl_btn_add_price)
    RelativeLayout rlBtnAddPrice;
    @BindView(R.id.rv_price)
    RecyclerView rvPrice;
    private FaBuShangPinJiaGeAdapter adapterPrice;
    private List<FabuShangPinUpLoadModel.SpecBean> listPrices = new ArrayList<>();

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

    private List<NewUserIntercalationPicModel> mList;
    private FabuShangpinIntercalationPicsAdapter adapter;
    private static final int REQUEST_CODE1 = 0x00000012;
    private SpImp spImp;
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
        FabuShangPinUpLoadModel.SpecBean bean = new FabuShangPinUpLoadModel.SpecBean();
        listPrices.add(bean);
        adapterPrice = new FaBuShangPinJiaGeAdapter(listPrices, new FaBuShangPinJiaGeAdapter.DeleteItemListenner() {
            @Override
            public void deleteItem(int pos) {
                if (listPrices.size()>1){
                    listPrices.remove(pos);
                    adapterPrice.notifyDataSetChanged();
                }
                if (listPrices.size()>2){
                    rlBtnAddPrice.setVisibility(View.GONE);
                }else {
                    rlBtnAddPrice.setVisibility(View.VISIBLE);
                }
            }
        });
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
    private void initPopServiceList(){
        viewPop = LayoutInflater.from(FaBuShangPinActivity.this).inflate(R.layout.popupwindow_rv_choose_item,null);
        rlAddNewService = viewPop.findViewById(R.id.rl_add_new_service);
        rlAddNewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    @OnClick({R.id.rl_back,R.id.rl_btn_add_price,R.id.rl_btn_add_service})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.rl_btn_add_price:
                    FabuShangPinUpLoadModel.SpecBean bean = new FabuShangPinUpLoadModel.SpecBean();
                    listPrices.add(bean);
                    adapterPrice.notifyDataSetChanged();
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
    }
}
