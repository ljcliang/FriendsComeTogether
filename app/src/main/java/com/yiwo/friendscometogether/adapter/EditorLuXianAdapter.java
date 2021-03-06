package com.yiwo.friendscometogether.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.EditorFriendRememberModel;
import com.yiwo.friendscometogether.newmodel.ActivityEditorModel;

import java.util.List;

/**
 * Created by Administrator on 2018/7/27.
 * /
 * 编辑友记 续写适配器
 */

public class EditorLuXianAdapter extends RecyclerView.Adapter<EditorLuXianAdapter.ViewHolder> {

    private Context context;
    private List<ActivityEditorModel.ObjBean.RenewListBean> data;
    private Activity activity;
    private OndeleteListenner ondeleteListenner;
    private OnEditListenner onEditListenner;
    public EditorLuXianAdapter(List<ActivityEditorModel.ObjBean.RenewListBean> data, Activity activity, OndeleteListenner ondeleteListenner, OnEditListenner onEditListenner) {
        this.data = data;
        this.ondeleteListenner = ondeleteListenner;
        this.onEditListenner = onEditListenner;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_editor_lu_xian, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getPfpurl()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
        holder.tvTitle.setText(data.get(position).getPfptitle());
        holder.tvContent.setText(data.get(position).getPfpcontent());
        holder.tvFaBuShijian.setText(""+data.get(position).getPfptime());
        holder.btnShanChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ondeleteListenner.delete(position);
            }
        });
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditListenner.edit(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private RelativeLayout rl;
        private TextView tvContent;
        private TextView tvFaBuShijian;
        private TextView btnBianJi,btnShanChu;
        private ImageView iv;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.activity_editor_friend_remember_rv_tv);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvFaBuShijian = itemView.findViewById(R.id.tv_fabu_time);
            tvTitle = itemView.findViewById(R.id.activity_editor_friend_remember_rv_tv);
            btnBianJi = itemView.findViewById(R.id.btn_bianji);
            btnShanChu = itemView.findViewById(R.id.btn_shanchu);
            rl = itemView.findViewById(R.id.activity_editor_friend_remember_rv_rl);
            iv = itemView.findViewById(R.id.iv);
        }
    }
    public interface OndeleteListenner{
        void delete(int pos);
    }
    public interface OnEditListenner{
        void edit(int pos);
    }
}
