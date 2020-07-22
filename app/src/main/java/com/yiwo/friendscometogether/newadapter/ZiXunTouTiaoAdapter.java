package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.NewHomeTuiJian;

import java.util.List;

public class ZiXunTouTiaoAdapter extends RecyclerView.Adapter<ZiXunTouTiaoAdapter.ViewHolder> {
    private Context context;
    private List<NewHomeTuiJian.ObjBean.ZxBean> data ;
    public ZiXunTouTiaoAdapter(List<NewHomeTuiJian.ObjBean.ZxBean> list){
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
        holder.tv_title.setText(data.get(position).getFmtitle());
        if (data.get(position).getPic().size()>0){
            Glide.with(context).load(data.get(position).getPic().get(0)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv0);
        }
        if (data.get(position).getPic().size()>1){
            Glide.with(context).load(data.get(position).getPic().get(1)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv1);
        }
        if (data.get(position).getPic().size()>2){
            Glide.with(context).load(data.get(position).getPic().get(2)).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv2);
        }
        holder.tv_look_num.setText(data.get(position).getFmlook());
        holder.tv_pinglun_num.setText(data.get(position).getFmcomment());
    }

    @Override
    public int getItemCount() {
        return data.size()>0 ? data.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_look_num,tv_pinglun_num;
        ImageView iv0,iv1,iv2;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title =  itemView.findViewById(R.id.tv_title);
            tv_look_num =  itemView.findViewById(R.id.tv_look_num);
            tv_pinglun_num =  itemView.findViewById(R.id.tv_pinglun_num);
            iv0 =  itemView.findViewById(R.id.iv0);
            iv1 =  itemView.findViewById(R.id.iv1);
            iv2 =  itemView.findViewById(R.id.iv2);
        }
    }
}
