package com.example.resume.myview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.resume.R;


/**
 * Created by Hankkin on 15/10/23.
 */
public class TitleBarView extends RelativeLayout implements View.OnClickListener{

    private ImageView btnLeft;     //左侧按钮
    private TextView btnRight;    //右侧按钮
    private TextView tvTitle;   //标题文本
    private BtnClickListener listener;
    private RelativeLayout rl;
    private View top_title_bg;
    private ImageView iv_title_share;

    public TitleBarView(Context context) {
        super(context);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化组件
     * @param context
     */
    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_title,this);
        btnLeft = findViewById(R.id.btn_titlebar_left);
        rl = findViewById(R.id.rl);
        iv_title_share = findViewById(R.id.iv_title_share);
        top_title_bg = findViewById(R.id.top_title_bg);
        btnRight =  findViewById(R.id.btn_titlebar_right);
        tvTitle =  findViewById(R.id.tv_titlebar_name);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        iv_title_share.setOnClickListener(this);
    }

    public void setTitleBarListener(BtnClickListener listener){
        this.listener = listener;
    }

    /**
     * 按钮点击接口
     */
    public interface BtnClickListener{
        void leftClick();
        void rightClick();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_titlebar_left:
                if (listener!=null) {
                    listener.leftClick();
                }
                break;
            case R.id.btn_titlebar_right:
                listener.rightClick();
                break;

            default:
                break;
        }
    }


     public void hideleft(){
         btnLeft.setVisibility(GONE);
     }

    //设置背景颜色
    public void setbackColor(int color){
        rl.setBackgroundColor(getResources().getColor(color));
    }


    //设置背景颜色
    public void setviewbackColor(int color){
        top_title_bg.setBackgroundColor(getResources().getColor(color));
    }


    //设置中间title的内容
    public void setMainTitle(String text) {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(text);
    }

    //设置title中间文字颜色
    public void setMainTitleColor(int color) {
        tvTitle.setTextColor(color);
    }


    //设置title右边文字颜色
    public void setRightTitleColor(int color) {
        btnRight.setTextColor(color);
    }


    //设置title右边文字
    public void setRightTitleText(String text) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText(text);
    }

    public void setRightimg(Drawable  rightimg){
        iv_title_share.setImageDrawable(rightimg);
    }

}
