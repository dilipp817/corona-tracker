package com.corona.coronatracker.repository.webservice.service;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFactory {

    private static final long CONNECTION_TIMEOUT = 120;
    private static final long READ_TIMEOUT = 120;

    public static ApiService getApiService(Context context) {
        return generateApiService(context);
    }

    private static ApiService generateApiService(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19india.org")
                .client(provideOkhttpClient(context))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        return retrofit.create(ApiService.class);
    }

    private static OkHttpClient provideOkhttpClient(Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) // make time out values into constants
                .addInterceptor(chain -> chain.proceed(chain.request()))
                .addInterceptor(chain ->  chain.proceed(chain.request()))
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Response response = chain.proceed(originalRequest);
                    String responseBodyString = response.peekBody(Long.MAX_VALUE).string();
                    if (!response.isSuccessful()) {
                        responseBodyString = response.message();
                    }
                    return response.newBuilder().message(responseBodyString).build();
                });

        if (BuildConfig.DEBUG)
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        return builder.build();
    }


}