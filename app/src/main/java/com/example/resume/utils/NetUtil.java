package com.example.resume.utils;


import com.example.resume.retrofit.ApiStores;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetUtil {

    private volatile static NetUtil netUtil=null;
    private NetUtil(){

    }
    public static NetUtil getmInstance(){
        if (netUtil==null){
            synchronized (NetUtil.class){
                if (netUtil==null){
                    netUtil=new NetUtil();
                }
            }
        }
        return netUtil;
    }

    //ok拦截日志
    OkHttpClient httpClient = new OkHttpClient.Builder().
            addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();


    public ApiStores getnetjson(String uri) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(uri)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)//添加拦截器时用
               .build();

        ApiStores apiStores = retrofit.create(ApiStores.class);
        return apiStores;

    }


}
