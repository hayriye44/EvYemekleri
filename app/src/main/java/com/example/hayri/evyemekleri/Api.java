package com.example.hayri.evyemekleri;

import com.example.hayri.evyemekleri.Models.Model;
import com.example.hayri.evyemekleri.Models.SehirList;
import com.example.hayri.evyemekleri.Models.YemekEkle;
import com.example.hayri.evyemekleri.Models.YemekList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Kamere on 9/1/2018.
 */

public interface Api {


    @POST("register.php")
    @FormUrlEncoded
    Call<Model> register(@Field("username") String username, @Field("email") String email, @Field("password") String password);

    @POST("login2.php")
    @FormUrlEncoded
    Call<Model> login(@Field("username") String username, @Field("password") String password);

    @GET("citys.php")
    Call<SehirList> getCitys();

    @POST("yemekler.php")
    @FormUrlEncoded
    Call<YemekList> getFoods(@Field("kul_id") int kul_id);

    @POST("yemekEkle.php")
    @FormUrlEncoded
    Call<YemekEkle> yemekEkle(@Field("kat_id") int kat_id, @Field("kul_id") int kul_id, @Field("yemek_adi") String yemek_adi, @Field("yemek_fiyat") double yemek_fiyat, @Field("yorum_puani") int yorum_puani,@Field("sehir_id") int sehir_id, @Field("yemekresim") String yemekresim, @Field("miktar") String miktar);


}

