package com.yiwo.friendscometogether.newadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeTuiJian_ReMenDuiZhang_new_Adapter extends RecyclerView.Adapter<HomeTuiJian_ReMenDuiZhang_new_Adapter.ViewHolder>{
    private Context context;
    private List<HomeTuiJianModel.ObjBean.CaptainBeanX> data;
    private SpImp spImp;
    public HomeTuiJian_ReMenDuiZhang_new_Adapter(List<HomeTuiJianModel.ObjBean.CaptainBeanX> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tuijian_remenduizhang1, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        HomeTuiJian_ReMenDuiZhang_new_Adapter.ViewHolder holder = new HomeTuiJian_ReMenDuiZhang_new_Adapter.ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Intent intent = new Intent();
        holder.tv_name.setText(data.get(position).getUsername());

        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_icon);
        holder.iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        if (data.get(position).getGz().equals("1")){
            holder.tv_guanzhu.setBackgroundResource(R.drawable.bg_d84c37_30px);
            holder.tv_guanzhu.setTextColor(Color.WHITE);
            holder.tv_guanzhu.setText("已关注");
        }else {
            holder.tv_guanzhu.setBackgroundResource(R.drawable.bg_d8d8d8_border_30px);
            holder.tv_guanzhu.setTextColor(Color.parseColor("#999999"));
            holder.tv_guanzhu.setText("+关注");
        }
        holder.tv_guanzhu.setOnClickListener(new View.OnClickListener() {
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
                                                holder.tv_guanzhu.setBackgroundResource(R.drawable.bg_d84c37_30px);
                                                holder.tv_guanzhu.setTextColor(Color.WHITE);
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
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_icon;
        TextView tv_name,tv_guanzhu;
        LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_guanzhu = itemView.findViewById(R.id.tv_guanzhu);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
