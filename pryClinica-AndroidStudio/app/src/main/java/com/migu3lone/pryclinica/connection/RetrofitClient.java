package com.migu3lone.pryclinica.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    //private static final String baseUrl = "http://192.168.20.22/pryClinica/";
    private static final String baseUrl = "http://10.0.2.2/pryClinica/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static ApiServiceLogin getApiService() {
        return getClient().create(ApiServiceLogin.class);
    }
}