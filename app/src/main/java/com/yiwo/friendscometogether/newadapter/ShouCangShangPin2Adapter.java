package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.api.NimUIKit;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.GuanZhuHuoDongModel;
import com.yiwo.friendscometogether.newmodel.ShouCangShangPinModel;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebLocalActivity;
import com.yiwo.friendscometogether.webpages.ShopGoodsDetailsWebLocalActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class ShouCangShangPin2Adapter extends RecyclerView.Adapter<ShouCangShangPin2Adapter.ViewHolder> {

    private Context context;
    private List<ShouCangShangPinModel.ObjBean> data;
    private SpImp spImp;
    private CancelGuanZhuListion listion;

    public ShouCangShangPin2Adapter(List<ShouCangShangPinModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_huodong_guanzhu, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//
        Glide.with(context).load(data.get(position).getGoodsImg()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv_image_huodong);
        holder.tv_title.setText(data.get(position).getGoodsName());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().placeholder(R.mipmap.my_head).error(R.mipmap.my_head)).into(holder.iv_icon_owner);
        holder.tv_name_owner.setText(data.get(position).getUsername());
        holder.iv_icon_huodong_state.setImageResource(R.mipmap.round_price);
        holder.tv_timeinfo.setText(""+data.get(position).getPrice());
        holder.ll_huodong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopGoodsDetailsWebLocalActivity.open(context,data.get(position).getGoodsID());
            }
        });

        holder.btn_cancel.setFocusable(false);
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listion.cancleGuanzhu(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    private void team(String teamId) {
        String account = spImp.getYXID();
        NimUIKit.setAccount(account);
        NimUIKit.startTeamSession(context, teamId);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView btn_gochatroom;
        private TextView btn_cancel;
        private TextView tv_title;
        private ImageView iv_icon_owner;
        private TextView tv_name_owner;
        private TextView tv_timeinfo;
        private ImageView iv_icon_huodong_state;
        private ImageView iv_image_huodong;
        private LinearLayout ll_huodong;
        public ViewHolder(View itemView) {
            super(itemView);
            btn_gochatroom = itemView.findViewById(R.id.btn_gochatroom);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            tv_title = itemView.findViewById(R.id.tv_huodong_title);
            iv_icon_owner = itemView.findViewById(R.id.iv_icon_owner);
            tv_name_owner = itemView.findViewById(R.id.tv_name_owner);
            tv_timeinfo = itemView.findViewById(R.id.tv_timeinfo);
            iv_icon_huodong_state = itemView.findViewById(R.id.iv_icon_huodong_state);
            iv_image_huodong = itemView.findViewById(R.id.iv_image_huodong);
            ll_huodong = itemView.findViewById(R.id.ll_huodong);

        }
    }
    public void setCancelGuanZHuLis(CancelGuanZhuListion listion){
        this.listion = listion;
    }
    public interface CancelGuanZhuListion{
        void cancleGuanzhu(int posion);
    }

}
