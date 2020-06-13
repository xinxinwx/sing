package com.example.resume.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resume.R;
import com.example.resume.adapter.SingListAdapter;
import com.example.resume.adapter.SinginAdapter;
import com.example.resume.mvp.mode.SingListMode;
import com.example.resume.mvp.presenter.SingListPresenter;
import com.example.resume.mvp.view.SingListView;
import com.example.resume.myview.TitleBarView;
import com.lbb.mvplibrary.base.MvpFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class ListFragment extends MvpFragment<SingListPresenter> implements
        View.OnClickListener,
        SingListView {

    @BindView(R.id.tbv_titlebar)
    TitleBarView tbvTitlebar;
    @BindView(R.id.subtract)
    ImageView subtract;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.rcmonth)
    RecyclerView rcmonth;
    @BindView(R.id.sing_list)
    RecyclerView singList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Unbinder unbinder;
    List<String> list = new ArrayList<>();
    private SinginAdapter singinAdapter;
    private StatusLayoutManager statusLayoutManager;
    private int id;
    private int page=1;
    private String month;
    private SingListAdapter singListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Activity mActivity = getActivity();
        View inflate = View.inflate(mActivity, R.layout.fragment_list, null);
        unbinder = ButterKnife.bind(this, inflate);
        initdata();
        statusLayoutManager = setLayout(refreshLayout);
        return inflate;
    }

    private void initdata() {
        tbvTitlebar.hideleft();
        tbvTitlebar.setMainTitle("考勤");
        tbvTitlebar.setMainTitleColor(Color.WHITE);

        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        year.setText(y + "");

        add.setOnClickListener(this);
        subtract.setOnClickListener(this);

        for (int i = 1; i < 13; i++) {
            list.add(i + "");
        }
        singinAdapter = new SinginAdapter(list, getActivity());
        rcmonth.setAdapter(singinAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcmonth.setLayoutManager(linearLayoutManager);
        singinAdapter.setmPosition(m);

        singinAdapter.setGetListener(position -> {
//                把点击的下标回传给适配器 确定下标
            singinAdapter.setmPosition(position);
            singinAdapter.notifyDataSetChanged();
           // toastShow(list.get(position));
            month=list.get(position);
            if (Integer.parseInt(month)<10){
                month="0"+month;
            }

            mvpPresenter.SingList(id+"",year.getText().toString()+"-"+month,page+"");
        });

        singListAdapter = new SingListAdapter(getActivity());
        singList.setAdapter(singListAdapter);
        singList.setLayoutManager(new LinearLayoutManager(getActivity()));

        addListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        MMKV kv = MMKV.defaultMMKV();
        id = kv.decodeInt("id");
        int mothe=(Calendar.getInstance().get(Calendar.MONTH )+1);
        if (mothe<=9){
            month="0"+mothe;
        }
        mvpPresenter.SingList(id+"",Calendar.getInstance().get(Calendar.YEAR)+"-"+month,page+"");
        statusLayoutManager.showLoadingLayout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                int addyear = Integer.parseInt(year.getText().toString());
                addyear++;
                year.setText(addyear + "");
                mvpPresenter.SingList(id+"",addyear+"-"+month,page+"");
                break;
            case R.id.subtract:
                int subtractyear = Integer.parseInt(year.getText().toString());
                subtractyear--;
                year.setText(subtractyear + "");
                mvpPresenter.SingList(id+"",subtractyear+"-"+month,page+"");
                break;
            default:
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            MMKV kv = MMKV.defaultMMKV();
            id = kv.decodeInt("id");
            int mothe=(Calendar.getInstance().get(Calendar.MONTH )+1);
            if (mothe<=9){
                month="0"+mothe;
            }
            mvpPresenter.SingList(id+"",Calendar.getInstance().get(Calendar.YEAR)+"-"+month,page+"");
            statusLayoutManager.showLoadingLayout();
        }
    }

    @Override
    public void getDataSuccess(SingListMode singListMode) {
        statusLayoutManager.showSuccessLayout();
        List<SingListMode.DataBean.RecordsBean> records = singListMode.getData().getRecords();
        if (records.size() > 0) {
            if (page == 1) {
                singListAdapter.setList(records);
            } else {
                singListAdapter.getList().addAll(records);
                singListAdapter.notifyDataSetChanged();
            }
        } else {
            if (page == 1 && records.size() == 0) {
                statusLayoutManager.showEmptyLayout();
            } else {
                page--;
                toastShow("没有更多数据了");

            }
        }
    }

    @Override
    public void getDataFail(String msg) {
        statusLayoutManager.showSuccessLayout();
    }

    @Override
    protected void initretry() {

    }

    @Override
    protected SingListPresenter createPresenter() {
        return new SingListPresenter(this);
    }

    /**
     * 刷新加载
     */
    private void addListener() {

        refreshLayout.setOnRefreshListener(refreshlayout -> {
            page = 1;
            mvpPresenter.SingList(id+"",year.getText().toString()+"-"+month,page+"");
            refreshlayout.finishRefresh();
        });


        //加载更多
        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            page++;
            mvpPresenter.SingList(id+"",year.getText().toString()+"-"+month,page+"");
            refreshlayout.finishLoadmore();
        });

    }
}
