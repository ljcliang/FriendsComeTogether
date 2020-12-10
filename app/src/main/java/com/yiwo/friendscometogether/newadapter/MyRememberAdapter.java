package com.yiwo.friendscometogether.newadapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.yiwo.friendscometogether.model.UserRememberModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.EditorFriendRememberActivity;
import com.yiwo.friendscometogether.pages.ModifyFriendRememberActivity;
import com.yiwo.friendscometogether.pages.TeamIntercalationActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebLocalActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebLocalActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.yiwo.friendscometogether.utils.TokenUtils.getToken;

/**
 * Created by Administrator on 2018/12/18.
 */

public class MyRememberAdapter extends RecyclerView.Adapter<MyRememberAdapter.ViewHolder> {

    private Context context;
    private List<UserRememberModel.ObjBean> data;
    private OnDeleteListener listener;
    public MyRememberAdapter(List<UserRememberModel.ObjBean> data,OnDeleteListener listener) {
        this.listener = listener;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_remember, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getFmtitle());
        holder.tvLookNum.setText(data.get(position).getFmlook()+"");
        Glide.with(context).load(data.get(position).getFmpic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.iv);
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, DetailsOfFriendsWebLocalActivity.class);
                intent.putExtra("fmid", data.get(position).getFmID());
                context.startActivity(intent);
            }
        });

        if (data.get(position).getType().equals("0")){
            holder.tvFaBuStatus.setText("已发布");
            holder.tvFaBuStatus.setTextColor(Color.parseColor("#666666"));
            holder.tvFaBuStatus.setBackgroundResource(R.drawable.bg_66666_border_3dp);
        }else {
            holder.tvFaBuStatus.setText("未发布");
            holder.tvFaBuStatus.setTextColor(Color.parseColor("#d84c37"));
            holder.tvFaBuStatus.setBackgroundResource(R.drawable.bg_d84c37_border_3dp);
        }
        holder.tvFaBuStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(data.get(position).getType().equals("0") ? "确定修改为未发布状态？":"确定发布？");
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ViseHttp.POST(NetConfig.releaseDraftUrl)
                                        .addParam("app_key", getToken(NetConfig.BaseUrl+NetConfig.releaseDraftUrl))
                                        .addParam("id", data.get(position).getFmID())
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String data1) {
                                                Log.d("asaasas",data1);
                                                try {
                                                    JSONObject jsonObject = new JSONObject(data1);
                                                    if(jsonObject.getInt("code") == 200){
                                                        Toast.makeText(context, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                                        if (data.get(position).getType().equals("0")){
                                                            data.get(position).setType("1");
                                                        }else {
                                                            data.get(position).setType("0");
                                                        }
                                                        notifyDataSetChanged();
                                                    }else {
                                                        Intent intent = new Intent();
                                                        Toast.makeText(context, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                                        intent.setClass(context, ModifyFriendRememberActivity.class);
                                                        intent.putExtra("id", data.get(position).getFmID());
                                                        context.startActivity(intent);
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
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        holder.tvEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", data.get(position).getFmID());
                if (data.get(position).getType().equals("0")){
                    intent.putExtra("draft", "1");//已发布状态
                }else {
                    intent.putExtra("draft", "2");//未发布状态
                }
                intent.setClass(context, EditorFriendRememberActivity.class);
                context.startActivity(intent);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onDelete(position);
                }else {
                    Log.d("isnull","listenner");
                }

            }
        });

        holder.tvTeamChawen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", data.get(position).getFmID());
                intent.setClass(context, TeamIntercalationActivity.class);
                context.startActivity(intent);
            }
        });
        if (data.get(position).getInNum().equals("0")){
            holder.viewXuXie.setVisibility(View.INVISIBLE);
            holder.tvCanYuXieZuo.setText("尚无团友参与写作");
        }else if (data.get(position).getInNum().equals("-1")){
            holder.viewXuXie.setVisibility(View.INVISIBLE);
            holder.tvCanYuXieZuo.setText("未设置团友共同写作");
        }else {
            holder.viewXuXie.setVisibility(View.VISIBLE);
            holder.tvCanYuXieZuo.setText("团友共有"+data.get(position).getInNum()+"人参与写作");
        }
        holder.tvFaBuShiJian.setText("发表时间： "+data.get(position).getFmtime());
        holder.tvGuanLianHuoDong.setText("相关关联： "+data.get(position).getPftitle());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvLookNum;
        private ImageView iv;
        private LinearLayout ll;
        private TextView tvEditor;
        private TextView tvDelete;
        private TextView tvTeamChawen;
        private TextView tvFaBuShiJian;
        private TextView tvGuanLianHuoDong;
        private TextView tvCanYuXieZuo;
        private TextView tvFaBuStatus;
        private RelativeLayout rlCanYuXieZuo;
        private View viewZhuPian,viewXuXie;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvLookNum = itemView.findViewById(R.id.tv_look_num);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
            tvEditor = itemView.findViewById(R.id.tv_editor);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvFaBuShiJian = itemView.findViewById(R.id.tv_fabu_time);
            tvGuanLianHuoDong = itemView.findViewById(R.id.tv_xiangguanhuodong);
            tvCanYuXieZuo = itemView.findViewById(R.id.tv_canyuxiezuo_num);
            tvTeamChawen = itemView.findViewById(R.id.tv_team_chawen);
            rlCanYuXieZuo = itemView.findViewById(R.id.rl_canyuxiezuo);
            viewZhuPian = itemView.findViewById(R.id.view_zhupian);
            viewXuXie = itemView.findViewById(R.id.view_xuxie);
            tvFaBuStatus = itemView.findViewById(R.id.tv_staus);
        }
    }

    public interface OnDeleteListener{
        void onDelete(int i);
    }

}
