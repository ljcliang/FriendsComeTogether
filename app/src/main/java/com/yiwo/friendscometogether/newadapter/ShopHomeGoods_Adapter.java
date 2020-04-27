package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
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
import com.yiwo.friendscometogether.newmodel.HomeYouPuModel;
import com.yiwo.friendscometogether.newmodel.ShopHomeModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class ShopHomeGoods_Adapter extends RecyclerView.Adapter<ShopHomeGoods_Adapter.ViewHolder>{
    private Context context;
    private List<ShopHomeModel.ObjBean.GoodsListBean> data;
    private SpImp spImp;
    public ShopHomeGoods_Adapter(List<ShopHomeModel.ObjBean.GoodsListBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_shop_home_goods, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        ShopHomeGoods_Adapter.ViewHolder holder = new ShopHomeGoods_Adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Intent intent = new Intent();
        holder.tv_name.setText(data.get(position).getGoodsName());
        holder.tv_price.setText(data.get(position).getPrice());
        holder.tv_pinglun.setText(data.get(position).getCommentNum());
        Glide.with(context).load(data.get(position).getGoodsImg()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv);
        holder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv_name,tv_price,tv_pinglun;
        LinearLayout ll_all;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_all = itemView.findViewById(R.id.ll_all);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            iv = itemView.findViewById(R.id.iv);
            tv_pinglun = itemView.findViewById(R.id.tv_pinglun);
        }
    }
}
