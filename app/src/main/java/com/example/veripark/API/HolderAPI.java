package com.example.veripark.API;


import com.example.veripark.Model.Detail.DetailPost;
import com.example.veripark.Model.Detail.DetailResponse;
import com.example.veripark.Model.Handshake.HandshakeStartPost;
import com.example.veripark.Model.Handshake.HandshakeStartResponse;
import com.example.veripark.Model.List.ListPost;
import com.example.veripark.Model.List.ListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HolderAPI {
    @Headers("Accept: application/json")
    @POST("handshake/start")
    Call<HandshakeStartResponse> handshakeStart(@Body HandshakeStartPost handshakeStartPost);

    @Headers("Accept: application/json")
    @POST("stocks/list")
    Call<ListResponse> stocksList(@Header("X-VP-Authorization") String header, @Body ListPost listPost);

    @Headers("Accept: application/json")
    @POST("stocks/detail")
    Call<DetailResponse> stocksDetail(@Header("X-VP-Authorization") String header, @Body DetailPost detailPost);

}
