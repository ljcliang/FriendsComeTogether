package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.DuiZhangZhuanShuModel;
import com.yiwo.friendscometogether.newmodel.LiShuGongSiSearchModel;

import java.util.List;

/**
 * Created by Administrator on 2019/1/11.
 */

public class LiShuGongSiChooseAdapter extends RecyclerView.Adapter<LiShuGongSiChooseAdapter.ViewHolder> {

    private Context context;
    private List<DuiZhangZhuanShuModel.ObjBean.ShopNameBean> data;

    public LiShuGongSiChooseAdapter(List<DuiZhangZhuanShuModel.ObjBean.ShopNameBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_gongsi_choose, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_text.setText(data.get(position).getShopname());
        holder.iv_choosed.setImageResource(data.get(position).getCheckIn().equals("1")? R.mipmap.red_choose_chceked : R.mipmap.red_choose_none);
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setCheckIn(data.get(position).getCheckIn().equals("1") ? "0":"1");
                holder.iv_choosed.setImageResource(data.get(position).getCheckIn().equals("1")? R.mipmap.red_choose_chceked : R.mipmap.red_choose_none);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_text;
        private ImageView iv_choosed;
        private RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_gongsi_name);
            iv_choosed = itemView.findViewById(R.id.iv_choosed);
            rl = itemView.findViewById(R.id.rl);
        }
    }

}
