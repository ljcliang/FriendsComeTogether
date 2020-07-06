package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yiwo.friendscometogether.R;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class PersonMainZuiXinShangPinAdapter extends RecyclerView.Adapter<PersonMainZuiXinShangPinAdapter.ViewHolder>{
    private Context context;
    private List<String> data;
    private AddClickListenner listenner;
//    private SpImp spImp;
    public PersonMainZuiXinShangPinAdapter(List<String> data, AddClickListenner listenner){
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
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);

        }
    }
    public interface AddClickListenner{
        void addListen(int i, ImageView ivGoods);
    }
}
