package com.scala.exp.android.sdk;

import com.scala.exp.android.sdk.model.ContentNode;
import com.scala.exp.android.sdk.model.Device;
import com.scala.exp.android.sdk.model.Experience;
import com.scala.exp.android.sdk.model.SearchResults;
import com.scala.exp.android.sdk.model.Token;
import com.scala.exp.android.sdk.model.Data;
import com.scala.exp.android.sdk.model.Location;
import com.scala.exp.android.sdk.model.Thing;

import java.util.Map;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * End Point EXP service
 * Created by Cesar Oyarzun on 10/27/15.
 */
public interface IExpEndpoint {

        @POST("/api/auth/login")
        Observable<Token> login(@Body Map<String,String> options);

        @GET("/api/things/{uuid}")
        Observable<Thing> getThing(@Path("uuid") String uuid);

        @GET("/api/things")
        Observable<SearchResults<Thing>> findThings(@QueryMap Map<String,String> options);

        @GET("/api/devices/{uuid}")
        Observable<Device> getDevice(@Path("uuid") String uuid);

        @GET("/api/devices")
        Observable<SearchResults<Device>> findDevices(@QueryMap Map<String,String> options);

        @GET("/api/experiences/{uuid}")
        Observable<Experience> getExperience(@Path("uuid") String uuid);

        @GET("/api/experiences")
        Observable<SearchResults<Experience>> findExperiences(@QueryMap Map<String,String> options);

        @GET("/api/locations/{uuid}")
        Observable<Location> getLocation(@Path("uuid") String uuid);

        @GET("/api/locations")
        Observable<SearchResults<Location>> findLocations(@QueryMap Map<String,String> options);

        @GET("/api/content/{uuid}/children")
        Observable<ContentNode> getContentNode(@Path("uuid") String uuid);

        @GET("/api/content")
        Observable<SearchResults<ContentNode>> findContentNodes(@QueryMap Map<String,String> options);


        @GET("/api/data/{group}/{key}")
        Observable<Data> getData(@Path("group") String group,@Path("key") String key);

        @GET("/api/data/{group}/{key}")
        Observable<SearchResults<Data>> findData(@QueryMap Map<String,String> options);

}
