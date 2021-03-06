package com.example.hayri.evyemekleri;

import com.example.hayri.evyemekleri.Models.BegenilenYemeklerList;
import com.example.hayri.evyemekleri.Models.FavoriIslemler;
import com.example.hayri.evyemekleri.Models.Favoriler;
import com.example.hayri.evyemekleri.Models.FavorilerList;
import com.example.hayri.evyemekleri.Models.Iletisim;
import com.example.hayri.evyemekleri.Models.IletisimBilgiEkle;
import com.example.hayri.evyemekleri.Models.IletisimBilgiGuncelle;
import com.example.hayri.evyemekleri.Models.KulAdi;
import com.example.hayri.evyemekleri.Models.Model;
import com.example.hayri.evyemekleri.Models.ProfilFoto;
import com.example.hayri.evyemekleri.Models.SehirList;
import com.example.hayri.evyemekleri.Models.YemekEkle;
import com.example.hayri.evyemekleri.Models.YemekList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

    @POST("kulAdi.php")
    @FormUrlEncoded
    Call<KulAdi> kulAdi(@Field("id") int id);

    @GET("citys.php")
    Call<SehirList> getCitys();

    @POST("yemekler.php")
    @FormUrlEncoded
    Call<YemekList> getFoods(@Field("kul_id") int kul_id);

    @POST("iletisimbilgisi.php")
    @FormUrlEncoded
    Call<Iletisim> getIletisimBilgisi(@Field("kul_id") int kul_id);

    @POST("tümyemekler.php")
    @FormUrlEncoded
    Call<YemekList> getAllFoods(@Field("kat_id") int kat_id,@Field("sehir_id") int sehir_id);

    @POST("yemekEkle.php")
    @FormUrlEncoded
    Call<YemekEkle> yemekEkle(@Field("kat_id") int kat_id, @Field("kul_id") int kul_id, @Field("yemek_adi") String yemek_adi, @Field("yemek_fiyat") double yemek_fiyat, @Field("yorum_puani") int yorum_puani,@Field("sehir_id") int sehir_id, @Field("yemekresim") String yemekresim, @Field("miktar") String miktar);

    @POST("iletisimBilgiEkle.php")
    @FormUrlEncoded
    Call<IletisimBilgiEkle> iletisimBilgiEkle( @Field("kul_id") int kul_id, @Field("sehirAdi") String sehirAdi, @Field("adres_aciklama") String adres_aciklama, @Field("tel") String tel);

    @POST("iletisimBilgisiGüncelle.php")
    @FormUrlEncoded
    Call<IletisimBilgiGuncelle> iletisimBilgisiGüncelle(@Field("kul_id") int kul_id, @Field("il_id") int il_id, @Field("adres_aciklama") String adres_aciklama, @Field("tel") String tel);

    //aynı tür cevaplar döndüğü için burdada İletisimBilgisiGüncelle modelini kullandım
  /*  @POST("profilfotoGüncelle.php")
    @FormUrlEncoded
    Call<IletisimBilgiGuncelle> profilfotoGüncelle(@Field("kul_id") int kul_id,@Field("userfoto") String userfoto);
*/
    @POST("YüksekPuanliYemek.php")
    @FormUrlEncoded
    Call<BegenilenYemeklerList> getBegenilenYemeklerGetir(@Field("kul_id") int kul_id);

    @POST("favoriler.php")
    @FormUrlEncoded
    Call<Favoriler> favoriKontrol(@Field("uye_id") String uye_id, @Field("yemek_id") String yemek_id);


    @POST("favoriIslemler.php")
    @FormUrlEncoded
    Call<FavoriIslemler> favoriIslemler(@Field("uye_id") String uye_id, @Field("yemek_id") String yemek_id);

    @POST("favorilerListesi.php")
    @FormUrlEncoded
    Call<FavorilerList> getfavorilerList(@Field("uye_id") String uye_id);

/*
    @POST("ProfilFotoGetir.php")
    @FormUrlEncoded
    Call<ProfilFoto> getProfilFoto(@Field("kul_id") int kul_id);
*/
}

