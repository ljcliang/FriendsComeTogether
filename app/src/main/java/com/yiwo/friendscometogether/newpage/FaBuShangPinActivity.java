package com.yiwo.friendscometogether.newpage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.jph.takephoto.app.TakePhotoActivity;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.NewUserIntercalationPicModel;
import com.yiwo.friendscometogether.newadapter.FabuShangpinIntercalationPicsAdapter;
import com.yiwo.friendscometogether.newadapter.NewCreateFriendRemberIntercalationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaBuShangPinActivity extends TakePhotoActivity {


    @BindView(R.id.rv_choose_pics)
    RecyclerView rvImages;
    @BindView(R.id.rv_price)
    RecyclerView rvPrice;
    private List<NewUserIntercalationPicModel> mList;
    private FabuShangpinIntercalationPicsAdapter adapter;
    private static final int REQUEST_CODE1 = 0x00000012;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_bu_shang_pin);
        ButterKnife.bind(this);
        initUpData();
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
