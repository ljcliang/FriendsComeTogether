package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.utils.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeTuiJian_DuiZhangPuZi_Adapter extends RecyclerView.Adapter<HomeTuiJian_DuiZhangPuZi_Adapter.ViewHolder>{
    private Context context;
    private List<HomeTuiJianModel.ObjBean.GoodsBean> data;
    private SpImp spImp;
    public HomeTuiJian_DuiZhangPuZi_Adapter(List<HomeTuiJianModel.ObjBean.GoodsBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_duizhangpuzi, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        HomeTuiJian_DuiZhangPuZi_Adapter.ViewHolder holder = new HomeTuiJian_DuiZhangPuZi_Adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Intent intent = new Intent();
        holder.tv_name.setText(data.get(position).getUsername());
        holder.tv_level.setText("Lv."+data.get(position).getUsergrade());
        holder.tv_goods.setText(data.get(position).getGoodsName());
        holder.tv_price.setText(data.get(position).getPrice());
        holder.tv_pinglun_num.setText("共有"+data.get(position).getComNum()+"评论");
        Glide.with(context).load(data.get(position).getGoodsImg()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv_big);
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_head);
        holder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        if (data.get(position).getGz().equals("1")){
            holder.iv_guanzhu.setImageResource(R.mipmap.hert_0324_r);
        }else {
            holder.iv_guanzhu.setImageResource(R.mipmap.hert_0324_g);
        }
        holder.rl_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"asdasdas",Toast.LENGTH_SHORT).show();
            }
        });
        holder.rl_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    if(data.get(position).getGz().equals("0")){
                        ViseHttp.POST(NetConfig.userFocusUrl)
                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                                .addParam("uid", spImp.getUID())
                                .addParam("likeId", data.get(position).getUserID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            if (jsonObject.getInt("code") == 200) {
                                                data.get(position).setGz("1");
                                                notifyDataSetChanged();
                                                holder.iv_guanzhu.setImageResource(R.mipmap.hert_0324_r);
                                                Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                                            }else if(jsonObject.getInt("code") == 400){

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {

                                    }
                                });
                    }
                }else {
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        switch (data.get(position).getLevelName()){
            case "0":
                holder.iv_level.setImageResource(R.mipmap.level_qingtong);
                break;
            case "1":
                holder.iv_level.setImageResource(R.mipmap.level_baiyin);
                break;
            case "2":
                holder.iv_level.setImageResource(R.mipmap.level_huangjin);
                break;
            case "3":
                holder.iv_level.setImageResource(R.mipmap.level_bojin);
                break;
            case "4":
                holder.iv_level.setImageResource(R.mipmap.level_zuanshi);
                break;
            case "5":
                holder.iv_level.setImageResource(R.mipmap.level_huangguan);
                break;
        }
        switch (data.get(position).getStar()){
            case "0" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star2.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star3.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star4.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star5.setImageResource(R.mipmap.pingxing_gray);
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
        ImageView iv_big,iv_head,iv_level,iv_guanzhu,iv_star1,iv_star2,iv_star3,iv_star4,iv_star5;
        TextView tv_name,tv_level,tv_goods,tv_price,tv_pinglun_num;
        RelativeLayout rl_guanzhu,rl_all;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_big = itemView.findViewById(R.id.iv_big);
            iv_head = itemView.findViewById(R.id.iv_head);
            iv_level = itemView.findViewById(R.id.iv_level);
            iv_guanzhu = itemView.findViewById(R.id.iv_guanzhu);
            iv_star1 = itemView.findViewById(R.id.iv_star1);
            iv_star2 = itemView.findViewById(R.id.iv_star2);
            iv_star3 = itemView.findViewById(R.id.iv_star3);
            iv_star4 = itemView.findViewById(R.id.iv_star4);
            iv_star5 = itemView.findViewById(R.id.iv_star5);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_level = itemView.findViewById(R.id.tv_level);
            tv_goods = itemView.findViewById(R.id.tv_goods);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_pinglun_num = itemView.findViewById(R.id.tv_pinglun_num);
            rl_guanzhu = itemView.findViewById(R.id.rl_guanzhu);
            rl_all = itemView.findViewById(R.id.rl_all);
        }
    }
}
