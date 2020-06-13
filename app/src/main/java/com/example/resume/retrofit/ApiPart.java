package com.example.resume.retrofit;

/**
 * 接口
 */
public interface ApiPart {

    //
    //String Index = "data.do?channelId=0";

    //获取token
    String getkoen="oauth/2.0/token?grant_type=client_credentials&client_id=cNlWHNpjr5Ae5YaHyg1KyGck&client_secret=dWqGZLTxoF9wzjcf1DLfss6GVY6tkpBv";

    //添加人员
    String adduser="rest/2.0/face/v3/faceset/user/add";

    //人脸检测
    String detect="rest/2.0/face/v3/faceverify";

    //人脸对比
    String face="rest/2.0/face/v3/multi-search";

    //登录
    String login="xinxin/user/login";

    //注册
    String register="xinxin/user/register";

    //修改密码
    String updatapwd="xinxin/user/updatapwd";

    //意见反馈
    String opinion="xinxin/user/opinion";

    //获取位置
    String site="xinxin/user/getsite";

    //获取考勤列表
    String checkinlist="xinxin/checkingin/checkinlist";

    //获取当天考勤数据
    String singin="xinxin/checkingin/singin";

    //考勤打卡
    String checkin="xinxin/checkingin/checkin";


}
