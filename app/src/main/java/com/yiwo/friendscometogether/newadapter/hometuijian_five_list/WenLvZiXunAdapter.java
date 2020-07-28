package com.yiwo.friendscometogether.newadapter.hometuijian_five_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.HomePageSkipListModel;
import com.yiwo.friendscometogether.newmodel.NewHomeTuiJian;

import java.util.List;

public class WenLvZiXunAdapter extends RecyclerView.Adapter<WenLvZiXunAdapter.ViewHolder> {
    private Context context;
    private List<HomePageSkipListModel.ObjBean.InfoListBean> data ;
    public WenLvZiXunAdapter(List<HomePageSkipListModel.ObjBean.InfoListBean> list){
        this.data = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_hometuijian_zixuntoutiao,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ll_1.setVisibility(View.GONE);
        holder.ll_2.setVisibility(View.GONE);
        holder.ll_3.setVisibility(View.GONE);
        switch (position % 4){
            case 0:
                holder.ll_1.setVisibility(View.VISIBLE);
                holder.tv_title.setText(data.get(position).getFmtitle());
                if (data.get(position).getPicList().size()>0){
                    Glide.with(context).load(data.get(position).getPicList().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv0);
                }
                if (data.get(position).getPicList().size()>1){
                    Glide.with(context).load(data.get(position).getPicList().get(1)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv1);
                }
                if (data.get(position).getPicList().size()>2){
                    Glide.with(context).load(data.get(position).getPicList().get(2)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv2);
                }
                break;
            case 1:
                holder.ll_1.setVisibility(View.VISIBLE);
                holder.tv_title.setText(data.get(position).getFmtitle());
                if (data.get(position).getPicList().size()>0){
                    Glide.with(context).load(data.get(position).getPicList().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv0);
                }
                if (data.get(position).getPicList().size()>1){
                    Glide.with(context).load(data.get(position).getPicList().get(1)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv1);
                }
                if (data.get(position).getPicList().size()>2){
                    Glide.with(context).load(data.get(position).getPicList().get(2)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv2);
                }
                break;
            case 2:
                holder.ll_2.setVisibility(View.VISIBLE);
                holder.tv_title2.setText(data.get(position).getFmtitle());
                if (data.get(position).getPicList().size()>0){
                    Glide.with(context).load(data.get(position).getPicList().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv2_0);
                }
                break;
            case 3:
                holder.ll_3.setVisibility(View.VISIBLE);
                holder.tv_title3.setText(data.get(position).getFmtitle());
                if (data.get(position).getPicList().size()>0){
                    Glide.with(context).load(data.get(position).getPicList().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv3_0);
                }
                break;
        }
        holder.tv_look_num.setText(data.get(position).getFmlook());
        holder.tv_pinglun_num.setText(data.get(position).getCNum());
    }

    @Override
    public int getItemCount() {
        return data.size()>0 ? data.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_title2,tv_title3,tv_look_num,tv_pinglun_num;
        ImageView iv0,iv1,iv2,iv2_0,iv3_0;
        LinearLayout ll_1,ll_2,ll_3;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title =  itemView.findViewById(R.id.tv_title);
            tv_look_num =  itemView.findViewById(R.id.tv_look_num);
            tv_pinglun_num =  itemView.findViewById(R.id.tv_pinglun_num);
            iv0 =  itemView.findViewById(R.id.iv0);
            iv1 =  itemView.findViewById(R.id.iv1);
            iv2 =  itemView.findViewById(R.id.iv2);

            tv_title2 =  itemView.findViewById(R.id.tv_title2);
            tv_title3 =  itemView.findViewById(R.id.tv_title3);
            iv2_0 =  itemView.findViewById(R.id.iv2_0);
            iv3_0 =  itemView.findViewById(R.id.iv3_0);
            ll_1 =  itemView.findViewById(R.id.ll_1);
            ll_2 =  itemView.findViewById(R.id.ll_2);
            ll_3 =  itemView.findViewById(R.id.ll_3);
        }
    }
}
