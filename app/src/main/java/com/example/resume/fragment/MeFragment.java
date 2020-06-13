package com.example.resume.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.resume.R;
import com.example.resume.myview.TitleBarView;
import com.example.resume.ui.AttendanceViewMap;
import com.example.resume.ui.FaceRegisterActivity;
import com.example.resume.ui.FeedbackActivity;
import com.example.resume.ui.LoginActivity;
import com.lbb.mvplibrary.app.AppManager;
import com.lbb.mvplibrary.base.BaseFragment;
import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.titleview)
    TitleBarView titleview;
    @BindView(R.id.me_tv)
    TextView meTv;
    @BindView(R.id.faceregister)
    RelativeLayout faceregister;
    @BindView(R.id.map)
    RelativeLayout map;
    @BindView(R.id.yijian)
    RelativeLayout yijian;
    @BindView(R.id.outlogin)
    Button outlogin;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Activity mActivity = getActivity();
        View inflate = View.inflate(mActivity, R.layout.fragment_me, null);
        unbinder = ButterKnife.bind(this, inflate);
        initdata();
        return inflate;
    }

    private void initdata() {
        titleview.hideleft();
        titleview.setMainTitle("我的");
        titleview.setMainTitleColor(Color.WHITE);

        faceregister.setOnClickListener(this);
        map.setOnClickListener(this);
        yijian.setOnClickListener(this);
        outlogin.setOnClickListener(this);

        MMKV kv = MMKV.defaultMMKV();
        String name = kv.decodeString("name");
        meTv.setText(name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.faceregister:
                startActivity(FaceRegisterActivity.class);
                break;
            case R.id.map:
                startActivity(AttendanceViewMap.class);
                break;
            case R.id.yijian:
                startActivity(FeedbackActivity.class);
                break;
            case R.id.outlogin:
                AppManager.getInstance().killAllActivity();
                startActivity(LoginActivity.class);
                break;

            default:
                break;
        }
    }
}
