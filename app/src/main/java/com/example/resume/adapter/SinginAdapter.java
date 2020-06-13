package com.example.resume.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SinginAdapter extends RecyclerView.Adapter<SinginAdapter.SinginHodler> {

    private List<String> list;
    private Context context;
    private HashMap<Integer, Boolean> map;


    public SinginAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public SinginHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item, null);
        return new SinginHodler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SinginHodler holder, final int position) {
        holder.tv.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                一定要刷新适配器 当条目发生改变这是必须的
                getListener.onClick(position);
                notifyDataSetChanged();

            }
        });
//        如果下标和传回来的下标相等 那么确定是点击的条目 把背景设置一下颜色
        if (position == getmPosition()){
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.month_bg));
            holder.tv.setTextColor(Color.WHITE);
        }
         else{
//            否则的话就全白色初始化背景
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tv.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SinginHodler extends RecyclerView.ViewHolder {
        TextView tv;
        public SinginHodler(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_item);

        }
    }


    public interface GetListener {

        void onClick(int position);
    }

    private GetListener getListener;

    public void setGetListener(GetListener getListener) {
        this.getListener = getListener;
    }
    private  int mPosition;

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

}
