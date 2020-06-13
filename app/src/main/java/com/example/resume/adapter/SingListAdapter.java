package com.example.resume.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.R;
import com.example.resume.mvp.mode.SingListMode;

import java.util.List;

public class SingListAdapter extends RecyclerView.Adapter<SingListAdapter.SingListhodler> {

    private Context context;
    private List<SingListMode.DataBean.RecordsBean> list;

    public List<SingListMode.DataBean.RecordsBean> getList() {
        return list;
    }

    public void setList(List<SingListMode.DataBean.RecordsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public SingListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SingListhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_singlist, parent,false);
        return new SingListhodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingListhodler holder, int position) {
       holder.sing_date.setText(list.get(position).getCockdate());
        String morningtime = list.get(position).getMorningtime();
        if (morningtime.isEmpty()){
            holder.sing_stime.setText("未打卡");
        }else {
            holder.sing_stime.setText(morningtime.substring(10,morningtime.length()));
        }
        String night = list.get(position).getNight();
        if (night.isEmpty()){
            holder.sing_xtime.setText("未打卡");
        }else {
            holder.sing_xtime.setText(night.substring(10,night.length()));
        }

    }

    @Override
    public int getItemCount() {
        return list ==null ?0 :list.size();
    }

    class SingListhodler extends RecyclerView.ViewHolder{
        public TextView sing_date;
        public TextView sing_stime;
        public TextView sing_xtime;
        public SingListhodler(@NonNull View itemView) {
            super(itemView);
            sing_date=itemView.findViewById(R.id.sing_date);
            sing_stime=itemView.findViewById(R.id.sing_stime);
            sing_xtime=itemView.findViewById(R.id.sing_xtime);
        }
    }
}
