package com.lbb.mvplibrary.utils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
       final Charset UTF8 = Charset.forName("UTF-8");

        Connection connection = chain.connection();
        Request request = chain.request();
        Response response = chain.proceed(request);

        RequestBody requestBody = request.body();
        String requestStartMessage = "--> "
                + request.method()
                + ' ' + request.url()
                + (connection != null ? " " + connection.protocol() : "");
               LinLog.OklLog("请求"+requestStartMessage);

        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);

        Charset charset = UTF8;
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        LinLog.OklLog("提交的参数-->"+buffer.readString(charset));

        LinLog.OklLog("结果-->"+response.code()
                + (response.message().isEmpty() ? "" : ' ' + response.message())
                + ' ' + response.request().url());

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer1 = source.buffer();
        if (contentLength != 0) {
            LinLog.OklLog("返回的数据-->"+buffer1.clone().readString(charset));
        }

        return response;

    }
}
