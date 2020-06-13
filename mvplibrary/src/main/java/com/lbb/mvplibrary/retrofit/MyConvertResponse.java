package com.lbb.mvplibrary.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MyConvertResponse<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final Type type;

    public MyConvertResponse(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        // 解析code
        JsonPrimitive jsonPrimitive = jsonObject.getAsJsonPrimitive("code");
        int code = 0;
        if (jsonPrimitive != null) {
            code = jsonPrimitive.getAsInt();
        }
        T t = null;
        try {
            // 通过反射获取泛型的实例对象
            Class<T> clazz = (Class<T>) type;
            t = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (code == 0){

        }else {
            return gson.fromJson(json, type);
        }
        return null;
    }
}
