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
import com.yiwo.friendscometogether.newmodel.ShouCangShangPinModel;
import com.yiwo.friendscometogether.webpages.ShopGoodsDetailsWebLocalActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/12/19.
 */

public class ShouCangShangPinAdapter extends RecyclerView.Adapter<ShouCangShangPinAdapter.ViewHolder> {

    private Context context;
    private List<ShouCangShangPinModel.ObjBean> data;
    private OnCancelListener listener;

    public void setListener(OnCancelListener listener) {
        this.listener = listener;
    }

    public ShouCangShangPinAdapter(List<ShouCangShangPinModel.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_shoucang_shangpin, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getGoodsName());
        holder.tv_name.setText(data.get(position).getUsername());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_head);
//        holder.tv_level.setText("Lv."+data.get(position).getUsergrade());
//        if (data.get(position).getIfcaptain().equals("1")){
//            holder.iv_level.setVisibility(View.VISIBLE);
//            switch (data.get(position).getLevelName()){
//                case "0":
//                    holder.iv_level.setImageResource(R.mipmap.level_qingtong);
//                    break;
//                case "1":
//                    holder.iv_level.setImageResource(R.mipmap.level_baiyin);
//                    break;
//                case "2":
//                    holder.iv_level.setImageResource(R.mipmap.level_huangjin);
//                    break;
//                case "3":
//                    holder.iv_level.setImageResource(R.mipmap.level_bojin);
//                    break;
//                case "4":
//                    holder.iv_level.setImageResource(R.mipmap.level_zuanshi);
//                    break;
//                case "5":
//                    holder.iv_level.setImageResource(R.mipmap.level_huangguan);
//                    break;
//            }
//        }else {
//            holder.iv_level.setVisibility(View.GONE);
//        }
        holder.tv_fabu_time.setText("规格："+data.get(position).getSpec());
        holder.tv_xiangguanhuodong.setText("价格："+data.get(position).getPrice());

        Glide.with(context).load(data.get(position).getGoodsImg()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopGoodsDetailsWebLocalActivity.open(context,data.get(position).getGoodsID());
            }
        });
        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle,tv_name,tv_fabu_time,tv_xiangguanhuodong;
        private ImageView iv,iv_head;
        private LinearLayout ll;
        private TextView tvCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_fabu_time = itemView.findViewById(R.id.tv_fabu_time);
            tv_xiangguanhuodong = itemView.findViewById(R.id.tv_xiangguanhuodong);
            iv_head = itemView.findViewById(R.id.iv_head);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
            tvCancel = itemView.findViewById(R.id.tv_cancel);
        }
    }

    public interface OnCancelListener{
        void onCancel(int i);
    }

}
