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
import com.example.resume.mvp.mode.FeedbackMode;
import com.example.resume.mvp.presenter.FeedbackPresenter;
import com.example.resume.mvp.view.FeedbackView;
import com.example.resume.myview.TitleBarView;
import com.lbb.mvplibrary.base.MvpActivity;
import com.tencent.mmkv.MMKV;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class FeedbackActivity extends MvpActivity<FeedbackPresenter> implements TitleBarView.BtnClickListener
        , FeedbackView {

    @BindView(R.id.titleview)
    TitleBarView titleview;
    @BindView(R.id.feedback_content)
    EditText feedbackContent;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.ly)
    LinearLayout ly;
    private StatusLayoutManager statusLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        titleview.setMainTitle("意见反馈");
        titleview.setMainTitleColor(Color.WHITE);
        titleview.setTitleBarListener(this);
        btSend.setOnClickListener(this);

        statusLayoutManager = setLayout(ly);
    }

    @Override
    protected FeedbackPresenter createPresenter() {
        return new FeedbackPresenter(this);
    }

    @Override
    protected void initretry() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:

                String content = feedbackContent.getText().toString().trim();
                if (content.isEmpty()) {
                    toastShow("请输入您宝贵的意见");
                    return;
                }
                statusLayoutManager.showLoadingLayout();
                MMKV kv = MMKV.defaultMMKV();
                int id = kv.decodeInt("id");
                mvpPresenter.Feedback(id + "", content);
                break;
        }
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    public void getDataSuccess(FeedbackMode feedbackMode) {
        statusLayoutManager.showSuccessLayout();
        if (feedbackMode.getCode().equals("200")){
            toastShow(feedbackMode.getMessage());
            finish();
        }else {
            toastShow(feedbackMode.getMessage());
        }
    }

    @Override
    public void getDataFail(String msg) {
        statusLayoutManager.showSuccessLayout();
    }
}
