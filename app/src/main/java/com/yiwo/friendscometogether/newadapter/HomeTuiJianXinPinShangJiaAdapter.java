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
import com.yiwo.friendscometogether.newmodel.HomeTuiJianModel;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeTuiJianXinPinShangJiaAdapter extends RecyclerView.Adapter<HomeTuiJianXinPinShangJiaAdapter.ViewHolder>{
    private Context context;
    private List<HomeTuiJianModel.ObjBean.GoodsBean> data;
    private AddClickListenner listenner;
//    private SpImp spImp;
    public HomeTuiJianXinPinShangJiaAdapter(List<HomeTuiJianModel.ObjBean.GoodsBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
//        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tuijian_xinpinshangjia_item, parent, false);
        HomeTuiJianXinPinShangJiaAdapter.ViewHolder holder = new HomeTuiJianXinPinShangJiaAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getGoodsImg()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv);
        holder.tv_name.setText(data.get(position).getGoodsName());
        holder.tv_now_price.setText(data.get(position).getPrice());
        holder.tv_old_price.setText(data.get(position).getPrice());
        switch (data.get(position).getStar()){
            case "0" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing);
                holder.iv_star4.setImageResource(R.mipmap.pingxing);
                holder.iv_star5.setImageResource(R.mipmap.pingxing);
                break;
            case "1" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star3.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star4.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star5.setImageResource(R.mipmap.pingxing_gray);
                break;
            case "2" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star4.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star5.setImageResource(R.mipmap.pingxing_gray);
                break;
            case "3" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing);
                holder.iv_star4.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star5.setImageResource(R.mipmap.pingxing_gray);
                break;
            case "4" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing);
                holder.iv_star4.setImageResource(R.mipmap.pingxing);
                holder.iv_star5.setImageResource(R.mipmap.pingxing_gray);
                break;
            case "5" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing);
                holder.iv_star4.setImageResource(R.mipmap.pingxing);
                holder.iv_star5.setImageResource(R.mipmap.pingxing);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        ImageView iv_star1,iv_star2,iv_star3,iv_star4,iv_star5;
        TextView tv_name,tv_now_price,tv_old_price;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_now_price = itemView.findViewById(R.id.tv_now_price);
            tv_old_price = itemView.findViewById(R.id.tv_old_price);
            iv_star1 = itemView.findViewById(R.id.iv_star1);
            iv_star2 = itemView.findViewById(R.id.iv_star2);
            iv_star3 = itemView.findViewById(R.id.iv_star3);
            iv_star4 = itemView.findViewById(R.id.iv_star4);
            iv_star5 = itemView.findViewById(R.id.iv_star5);
        }
    }
    public interface AddClickListenner{
        void addListen(int i, ImageView ivGoods);
    }
}
