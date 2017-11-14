package com.louisk.wgstore.api;

import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface CampusApi {

    @POST("v1/Authenticate/login")
    Observable<String> getLongin();

    @GET("v1/Navigation/GetIndexMenu/2")
    Observable<String> getHome();

}
