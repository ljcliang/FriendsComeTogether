package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.DuiZhangZhuanShuModel;
import com.yiwo.friendscometogether.newmodel.ZAndScModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity2;

import java.util.List;

/**
 * Created by Administrator on 2019/1/11.
 */

public class JiangLiRenWuAdapter extends RecyclerView.Adapter<JiangLiRenWuAdapter.ViewHolder> {

    private Context context;
    private List<DuiZhangZhuanShuModel.ObjBean.MissionBean> data;

    public JiangLiRenWuAdapter(List<DuiZhangZhuanShuModel.ObjBean.MissionBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_jianglirenwu, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_text.setText(data.get(position).getName());//任务名称
        holder.tv_jindu.setText(data.get(position).getValname());
        switch (data.get(position).getType()){
            case "0":
                holder.btn_quwancheng.setVisibility(View.GONE);
                break;
            case "1":
                holder.btn_quwancheng.setVisibility(View.VISIBLE);
                break;
            case "2":
                holder.btn_quwancheng.setVisibility(View.VISIBLE);
                break;
            case "3":
                holder.btn_quwancheng.setVisibility(View.VISIBLE);
                break;
            case "4":
                holder.btn_quwancheng.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_text,tv_jindu,btn_quwancheng;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_text);
            tv_jindu = itemView.findViewById(R.id.tv_jindu);
            btn_quwancheng = itemView.findViewById(R.id.btn_quwancheng);
        }
    }

}
