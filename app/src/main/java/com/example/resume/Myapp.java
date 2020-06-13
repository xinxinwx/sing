package com.example.resume;

import android.app.Application;

import com.example.resume.retrofit.ApiStores;
import com.lbb.mvplibrary.retrofit.ApiManager;
import com.tencent.mmkv.MMKV;
import com.wenming.library.LogReport;
import com.wenming.library.save.imp.CrashWriter;
import com.wenming.library.upload.email.EmailReporter;

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiManager.init(ApiStores.API_SERVER_URL);
        initCrashReport();
        LogReport.getInstance().upload(this);
         MMKV.initialize(this);
    }


    private void initCrashReport() {
        LogReport.getInstance()
                .setCacheSize(30 * 1024 * 1024)//支持设置缓存大小，超出后清空
                .setLogDir(getApplicationContext(), "sdcard/" + this.getString(this.getApplicationInfo().labelRes) + "/")//定义路径为：sdcard/[app name]/
                .setWifiOnly(true)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setLogSaver(new CrashWriter(getApplicationContext()))//支持自定义保存崩溃信息的样式
                //.setEncryption(new AESEncode()) //支持日志到AES加密或者DES加密，默认不开启
                .init(getApplicationContext());
        initEmailReporter();
    }

    /**
     * 使用EMAIL发送日志
     */
    private void initEmailReporter() {
        EmailReporter email = new EmailReporter(this);
        email.setReceiver("xinxinapi@163.com");//收件人
        email.setSender("xinxinapi@163.com");//发送人邮箱
        email.setSendPassword("li15128884033.");
//        email.setSendPassword("TFQNTKDCGNIBIGYO");//邮箱的客户端授权码，注意不是邮箱密码
        email.setSMTPHost("smtp.163.com");//SMTP地址
        email.setPort("465");//SMTP 端口
        LogReport.getInstance().setUploadType(email);
    }
}
