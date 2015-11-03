package com.scala.expandroidsdk;

/**
 * Created by Cesar Oyarzun on 10/27/15.
 */

import com.google.gson.GsonBuilder;
import com.scala.expandroidsdk.adapters.ContentNodeJsonAdapter;
import com.scala.expandroidsdk.adapters.DeviceJsonAdapter;
import com.scala.expandroidsdk.adapters.ExpObservable;
import com.scala.expandroidsdk.adapters.LocationJsonAdapter;
import com.scala.expandroidsdk.adapters.ThingJsonAdapter;
import com.scala.expandroidsdk.model.ContentNode;
import com.scala.expandroidsdk.model.Device;
import com.scala.expandroidsdk.model.Location;
import com.scala.expandroidsdk.model.Thing;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Observable;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


public final class ExpService {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";


    public static ExpObservable init(String host)  {

            AppSingleton.getInstance().setHost(host);
            // Define the interceptor, add authentication headers
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request();
                    if(AppSingleton.getInstance().getToken()!=null){
                        newRequest.newBuilder().addHeader(AUTHORIZATION, BEARER + AppSingleton.getInstance().getToken()).build();
                        return chain.proceed(newRequest);
                    }
                    return chain.proceed(newRequest);
                }
            };

            // Add the interceptor to OkHttpClient
            OkHttpClient client = new OkHttpClient();
            client.interceptors().add(interceptor);


            //GSON builder adapter for model
            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(Device.class, new DeviceJsonAdapter());
            gson.registerTypeAdapter(Thing.class, new ThingJsonAdapter());
            gson.registerTypeAdapter(Location.class, new LocationJsonAdapter());
            gson.registerTypeAdapter(ContentNode.class, new ContentNodeJsonAdapter());
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson.create());

            // Create  REST adapter  API.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(host)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(gsonConverterFactory)
                    .client(client)
                    .build();

            // Create an instance of our RestCall API interface.
            IExpEndpoint expApi = retrofit.create(IExpEndpoint.class);
            AppSingleton.getInstance().setEndPoint(expApi);

            return new ExpObservable(rx.Observable.just(true));
    }

//    public static ExpObservable init(String host)  {
//
//        AppSingleton.getInstance().setHost(host);
////        // Define the interceptor, add authentication headers
////        Interceptor interceptor = new Interceptor() {
////            @Override
////            public Response intercept(Chain chain) throws IOException {
////                Request newRequest = chain.request().newBuilder().addHeader(AUTHORIZATION, BEARER +tokenExp).build();
////                return chain.proceed(newRequest);
////            }
////        };
////
////        // Add the interceptor to OkHttpClient
////        OkHttpClient client = new OkHttpClient();
////        client.interceptors().add(interceptor);
//
//
//        //GSON builder adapter for model
//        GsonBuilder gson = new GsonBuilder();
//        gson.registerTypeAdapter(Device.class, new DeviceJsonAdapter());
//        gson.registerTypeAdapter(Thing.class, new ThingJsonAdapter());
//        gson.registerTypeAdapter(Location.class, new LocationJsonAdapter());
//        gson.registerTypeAdapter(ContentNode.class, new ContentNodeJsonAdapter());
//        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(gson.create());
//
//        // Create  REST adapter  API.
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(host)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(gsonConverterFactory)
////                .client(client)
//                .build();
//
//
//        // Create an instance of our RestCall API interface.
//        IExpEndpoint expApi = retrofit.create(IExpEndpoint.class);
//        AppSingleton.getInstance().setEndPoint(expApi);
//
//        return new ExpObservable(rx.Observable.just(true));
//    }
//
//
    
    public boolean setInterceptor(final String tokenExp){
        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader(AUTHORIZATION, BEARER +tokenExp).build();
                return chain.proceed(newRequest);
            }
        };

        // Add the interceptor to OkHttpClient
        OkHttpClient client = new OkHttpClient();
        return client.interceptors().add(interceptor);

    }

}

