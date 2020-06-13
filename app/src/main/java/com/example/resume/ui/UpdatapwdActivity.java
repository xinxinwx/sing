package com.example.resume.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.resume.R;
import com.example.resume.mvp.mode.UpdatapwdMode;
import com.example.resume.mvp.presenter.UpdatapwdPresenter;
import com.example.resume.mvp.view.UpdatapwdView;
import com.example.resume.myview.TitleBarView;
import com.lbb.mvplibrary.base.MvpActivity;
import com.lbb.mvplibrary.utils.LinLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class UpdatapwdActivity extends MvpActivity<UpdatapwdPresenter> implements
        TitleBarView.BtnClickListener,
        UpdatapwdView {

    @BindView(R.id.titleview)
    TitleBarView titleview;
    @BindView(R.id.et_updatapwd_phone)
    EditText etUpdatapwdPhone;
    @BindView(R.id.et_updatapwd_change)
    EditText etUpdatapwdChange;
    @BindView(R.id.bt_updatapwd)
    Button btUpdatapwd;
    @BindView(R.id.ly)
    LinearLayout ly;
    private StatusLayoutManager statusLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatapwd);
        ButterKnife.bind(this);

        titleview.setMainTitle("修改密码");
        titleview.setMainTitleColor(Color.WHITE);
        titleview.setTitleBarListener(this);

        btUpdatapwd.setOnClickListener(this);

        statusLayoutManager = setLayout(ly);


    }

    @Override
    protected UpdatapwdPresenter createPresenter() {
        return new UpdatapwdPresenter(this);
    }

    @Override
    protected void initretry() {

    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    public void getDataSuccess(UpdatapwdMode updatapwdMode) {
        statusLayoutManager.showSuccessLayout();
        if (updatapwdMode.getCode().equals("200")){
            toastShow(updatapwdMode.getMessage());
            finish();
        }else {
            toastShow(updatapwdMode.getMessage());
        }
    }

    @Override
    public void getDataFail(String msg) {
        statusLayoutManager.showSuccessLayout();
        LinLog.loge(msg);
    }

    @Override
    public void toast(String msg) {
        toastShow(msg);
    }

    @Override
    public void showloading() {
        statusLayoutManager.showLoadingLayout();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_updatapwd:

                String tel = etUpdatapwdPhone.getText().toString().trim();
                String pwd = etUpdatapwdChange.getText().toString().trim();
                mvpPresenter.Updatapwd(tel, pwd);
                break;
            default:
                break;
        }
    }
}
