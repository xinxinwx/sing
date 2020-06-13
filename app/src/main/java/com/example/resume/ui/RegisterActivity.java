package com.example.resume.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.example.resume.R;
import com.example.resume.mvp.mode.RegisterMode;
import com.example.resume.mvp.presenter.RegisterPresenter;
import com.example.resume.mvp.view.RegisterView;
import com.example.resume.myview.TitleBarView;
import com.lbb.mvplibrary.base.MvpActivity;
import com.lbb.mvplibrary.utils.LinLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class RegisterActivity extends MvpActivity<RegisterPresenter> implements TitleBarView.BtnClickListener,
        RegisterView {

    @BindView(R.id.titleview)
    TitleBarView titleview;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_change)
    EditText etRegisterChange;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.ly)
    LinearLayout ly;
    private StatusLayoutManager statusLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        titleview.setMainTitle("注册");
        titleview.setMainTitleColor(Color.WHITE);
        titleview.setTitleBarListener(this);

        btRegister.setOnClickListener(this);

        statusLayoutManager = setLayout(ly);
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register:

                String tel = etRegisterPhone.getText().toString().trim();
                String pwd = etRegisterChange.getText().toString().trim();
                String name = etRegisterName.getText().toString().trim();
                mvpPresenter.register(tel, pwd, name);
                break;
            default:
                break;
        }
    }

    @Override
    public void getDataSuccess(RegisterMode registerMode) {
        statusLayoutManager.showSuccessLayout();
        String code = registerMode.getCode();
        if (code.equals("200")) {
            toastShow(registerMode.getMessage());
            finish();
        } else {
            toastShow(registerMode.getMessage());
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
}
