package com.yiwo.friendscometogether.newadapter;

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
import com.yiwo.friendscometogether.newmodel.NewPersonMainMode_part1;
import com.yiwo.friendscometogether.webpages.ShopGoodsDetailsWebLocalActivity;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class PersonMainZuiXinShangPinAdapter extends RecyclerView.Adapter<PersonMainZuiXinShangPinAdapter.ViewHolder>{
    private Context context;
    private List<NewPersonMainMode_part1.ObjBean.GoodsBean> data;
    private AddClickListenner listenner;
//    private SpImp spImp;
    public PersonMainZuiXinShangPinAdapter(List<NewPersonMainMode_part1.ObjBean.GoodsBean> data, AddClickListenner listenner){
        this.data = data;
        this.listenner = listenner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
//        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personmain_shangpin_item, parent, false);
        PersonMainZuiXinShangPinAdapter.ViewHolder holder = new PersonMainZuiXinShangPinAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getGoodsName());
        holder.tv_now_price.setText(data.get(position).getPreferential());
        holder.tv_old_price.setText("¥"+data.get(position).getPrice());
        Glide.with(context).load(data.get(position).getGoodsImg()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopGoodsDetailsWebLocalActivity.open(context,data.get(position).getGid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tvName,tv_now_price,tv_old_price;
        LinearLayout ll ;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_goods_name);
            tv_now_price = itemView.findViewById(R.id.tv_now_price);
            tv_old_price = itemView.findViewById(R.id.tv_old_price);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
        }
    }
    public interface AddClickListenner{
        void addListen(int i, ImageView ivGoods);
    }
}
