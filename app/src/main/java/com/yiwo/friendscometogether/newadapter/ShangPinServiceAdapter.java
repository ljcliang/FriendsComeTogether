package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.ShangPinServiceModel;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

public class ShangPinServiceAdapter extends RecyclerView.Adapter<ShangPinServiceAdapter.ViewHolder> {
    private Context context;
    private List<ShangPinServiceModel.ObjBean> data;
    private SpImp spImp;
    private boolean canChoose ;
    public ShangPinServiceAdapter(List<ShangPinServiceModel.ObjBean> data,boolean canChoose){
        this.data = data;
        this.canChoose = canChoose;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_shangpin_service, parent, false);
        ShangPinServiceAdapter.ViewHolder holder = new ShangPinServiceAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvInfo.setText(data.get(position).getInfo());
        holder.tvName.setText(data.get(position).getName());
        holder.ivChecked.setImageResource(data.get(position).isChecked() ? R.mipmap.checkbox_red_true:R.mipmap.checkbox_red_false);
        if (canChoose){
            holder.rlAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).isChecked()){
                        data.get(position).setChecked(false);
                        notifyDataSetChanged();
                    }else {
                        int chekedNum = 0;
                        for (ShangPinServiceModel.ObjBean bean : data){
                            if (bean.isChecked()){
                                chekedNum++;
                            }
                        }
                        if (chekedNum>2){
                            Toast.makeText(context,"最多选择三个服务",Toast.LENGTH_SHORT).show();
                        }else {
                            data.get(position).setChecked(true);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvInfo;
        ImageView ivChecked;
        RelativeLayout rlAll;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvInfo = itemView.findViewById(R.id.tv_info);
            ivChecked = itemView.findViewById(R.id.iv_checked);
            rlAll = itemView.findViewById(R.id.rl_all);
        }
    }
    public interface OnItemsChoosedListenner{
        void onChoosed(List<ShangPinServiceModel.ObjBean> chooseServices);
    }
}
