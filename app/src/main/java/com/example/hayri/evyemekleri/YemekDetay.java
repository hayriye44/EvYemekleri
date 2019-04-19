package com.example.hayri.evyemekleri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hayri.evyemekleri.Models.KulAdi;
import com.example.hayri.evyemekleri.Models.Model;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YemekDetay extends AppCompatActivity {
    private TextView tvKategori,tvYemekAdi,tvYemekMiktari,tvYemekFiyati,tvYemekPuani,tvSatıcıAd;
    private Button btnsiparis,btnsatici_profil,btn_favori;
    ImageView yemekresim;
    String user;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yemek_detay);
        tanimla();
        Bundle extras = getIntent().getExtras();
        String resim = extras.getString("resim");
        String kategori=extras.getString("Kategori");
        String yemekadi=extras.getString("YemekAdi");
        String yemekmikar=extras.getString("YemekMiktari");
        String yemekfiyati=extras.getString("YemekFiyati");
        String yemekpuani=extras.getString("YemekPuani");
        userId=Integer.valueOf(extras.getString("KulAdi"));
        kulAdiGetir(userId);
        tvKategori.setText(kategori);
        tvYemekAdi.setText(yemekadi);
        tvYemekMiktari.setText(yemekmikar);
        tvYemekFiyati.setText(yemekfiyati);
        tvYemekPuani.setText(yemekpuani);

        Picasso.get().load(resim).into(yemekresim);
    }
    public void tanimla(){
        yemekresim=(ImageView)findViewById(R.id.yemekResim);
        tvKategori=(TextView)findViewById(R.id.tvKategori);
        tvYemekAdi=(TextView)findViewById(R.id.tvYemekAdi);
        tvYemekMiktari=(TextView)findViewById(R.id.tvYemekMiktari);
        tvYemekFiyati=(TextView)findViewById(R.id.tvYemekFiyati);
        tvYemekPuani=(TextView)findViewById(R.id.tvYemekPuani);
        tvSatıcıAd=(TextView)findViewById(R.id.tvSatıcıAd);
    }


    private void kulAdiGetir(int id) {

        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<KulAdi> ad = api.kulAdi(id);

        ad.enqueue(new Callback<KulAdi>() {
            @Override
            public void onResponse(Call<KulAdi> call, Response<KulAdi> response) {

                if(response.body().getIsSuccess() == 1){
                    //get username
                    user= response.body().getUsername();
                    tvSatıcıAd.setText(user);
                }else{
                    Toast.makeText(YemekDetay.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<KulAdi> call, Throwable t) {
                Toast.makeText(YemekDetay.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

}
