package com.example.resume.retrofit;


import com.example.resume.mvp.facemode.Adduser;
import com.example.resume.mvp.facemode.Faceuser;
import com.example.resume.mvp.mode.AdduserMode;
import com.example.resume.mvp.mode.CheckinMode;
import com.example.resume.mvp.mode.Detect;
import com.example.resume.mvp.mode.DetectMode;
import com.example.resume.mvp.mode.FaceMode;
import com.example.resume.mvp.mode.FeedbackMode;
import com.example.resume.mvp.mode.GetToken;
import com.example.resume.mvp.mode.LoginMode;
import com.example.resume.mvp.mode.RegisterMode;
import com.example.resume.mvp.mode.SingListMode;
import com.example.resume.mvp.mode.SinginMode;
import com.example.resume.mvp.mode.SiteMode;
import com.example.resume.mvp.mode.UpdatapwdMode;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiStores {
    //baseUrl
    String API_SERVER_URL = "http://xinxinapi.icu:9090/";

     //获取token
     @POST(ApiPart.getkoen)
     Observable<GetToken> GetToken();

    //人脸注册
    @POST(ApiPart.adduser)
    Observable<AdduserMode> Adduser(@Query("access_token") String token, @Body Adduser adduser);

    //人脸检测
    @POST(ApiPart.detect)
    Observable<DetectMode> Detect(@Query("access_token") String token, @Body List<Detect> detect);

    //人脸对比
    @POST(ApiPart.face)
    Observable<FaceMode> Face(@Query("access_token") String token, @Body Faceuser faceuser);

    //登录
    @POST(ApiPart.login)
    @FormUrlEncoded
    Observable<LoginMode> Login(@FieldMap Map<String, String> map);

    //注册
    @POST(ApiPart.register)
    @FormUrlEncoded
    Observable<RegisterMode> Register(@FieldMap Map<String, String> map);

    //修改密码
    @POST(ApiPart.updatapwd)
    @FormUrlEncoded
    Observable<UpdatapwdMode> Updatapwd(@FieldMap Map<String, String> map);

    //意见反馈
    @POST(ApiPart.opinion)
    @FormUrlEncoded
    Observable<FeedbackMode> Opinion(@FieldMap Map<String, String> map);

    //获取位置
    @POST(ApiPart.site)
    @FormUrlEncoded
    Observable<SiteMode> Site(@FieldMap Map<String, String> map);

    //获取考勤列表
    @POST(ApiPart.checkinlist)
    @FormUrlEncoded
    Observable<SingListMode> Checkinlist(@FieldMap Map<String, String> map);

    //获取当天考勤数据
    @POST(ApiPart.singin)
    @FormUrlEncoded
    Observable<SinginMode> Singin(@FieldMap Map<String, String> map);

    //考勤打卡
    @POST(ApiPart.checkin)
    @FormUrlEncoded
    Observable<CheckinMode> Checkin(@FieldMap Map<String, String> map);

}
