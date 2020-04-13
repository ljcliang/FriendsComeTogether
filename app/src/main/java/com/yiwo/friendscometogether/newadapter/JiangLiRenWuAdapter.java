package com.yiwo.friendscometogether.newadapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.DuiZhangZhuanShuModel;
import com.yiwo.friendscometogether.newpage.CreateFriendRememberActivityChoosePicOrVideos;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.EnterLiveActivity;

import java.util.List;

/**
 * Created by Administrator on 2019/1/11.
 */

public class JiangLiRenWuAdapter extends RecyclerView.Adapter<JiangLiRenWuAdapter.ViewHolder> {

    private Activity context;
    private List<DuiZhangZhuanShuModel.ObjBean.MissionBean> data;
    private SpImp spImp;
    private SharePf sharePf;
    public JiangLiRenWuAdapter(List<DuiZhangZhuanShuModel.ObjBean.MissionBean> data,SharePf sharePf) {
        this.data = data;
        this.sharePf = sharePf;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = (Activity) parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_jianglirenwu, parent, false);
        ViewHolder holder = new ViewHolder(view);
        spImp = new SpImp(context);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_text.setText(data.get(position).getName());//任务名称
        holder.tv_jindu.setText(data.get(position).getValname());
        holder.btn_quwancheng.setText(data.get(position).getButton());
        holder.btn_quwancheng.setBackgroundResource(data.get(position).getStatus().equals("0") ? R.drawable.bg_d84c37_30px : R.drawable.bg_d8d8d8_30px);
        if (data.get(position).getStatus().equals("0")){
            holder.btn_quwancheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    switch (data.get(position).getType()){
                        case "0":
                            holder.btn_quwancheng.setVisibility(View.GONE);
                            break;
                        case "1":
                            holder.btn_quwancheng.setVisibility(View.VISIBLE);
                            intent.setClass(context, CreateFriendRememberActivityChoosePicOrVideos.class);
                            context.startActivity(intent);
                            break;
                        case "2":
                            intent.setClass(context, CreateFriendRememberActivityChoosePicOrVideos.class);
                            intent.putExtra(CreateFriendRememberActivityChoosePicOrVideos.ONLY_ADD_VIDEO,true);
                            context.startActivity(intent);
                            holder.btn_quwancheng.setVisibility(View.VISIBLE);
                            break;
                        case "3":
                            holder.btn_quwancheng.setVisibility(View.VISIBLE);
                            EnterLiveActivity.start(context);
                            break;
                        case "4":
                            holder.btn_quwancheng.setVisibility(View.VISIBLE);
                            sharePf.share();
                            break;

                    }
                }
            });
        }else {

        }
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
    public interface SharePf{
        void share();
    }
}
