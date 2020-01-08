package com.abach.iptv.api;

import com.abach.iptv.model.Channels;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChannelApi {

    @GET("allchannels")
    Call<List<Channels>>getChannels();

    @GET("newschannels")
    Call<List<Channels>>getnews();

    @GET("entertainmentchannels")
    Call<List<Channels>>getentertainment();

    @GET("kidschannels")
    Call<List<Channels>>getkids();

    @GET("livechannels")
    Call<List<Channels>>getlive();

    @GET("sportschannels")
    Call<List<Channels>>getsports();

    @GET("search/{name}")
    Call<List<Channels>>search(@Path("name") String name);
}
