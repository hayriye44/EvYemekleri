package com.example.hayri.evyemekleri.Activitys;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hayri.evyemekleri.Api;
import com.example.hayri.evyemekleri.ApiClient;
import com.example.hayri.evyemekleri.Models.FavoriIslemler;
import com.example.hayri.evyemekleri.Models.Favoriler;
import com.example.hayri.evyemekleri.Models.KulAdi;
import com.example.hayri.evyemekleri.R;
import com.example.hayri.evyemekleri.SharedPref;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YemekDetay extends AppCompatActivity {
    Window window;
    private TextView tvKategori,tvYemekAdi,tvYemekMiktari,tvYemekFiyati,tvYemekPuani,tvSatıcıAd;
    private Button btnsiparis,btnsatici_profil,btn_favori;
    ImageView yemekresim;
    String user;
    int userId;
    String  giriyapanuye_id;
    String yemek_ıd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yemek_detay);
        //statusbar rengini değişme
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));
        }
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

        giriyapanuye_id=String.valueOf(SharedPref.getInstance(this).LoggedInUserId());
        yemek_ıd=extras.getString("yemek_id");
        favoriKontrol(giriyapanuye_id,yemek_ıd);
        btn_favori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriIslemler(giriyapanuye_id,yemek_ıd);
            }
        });



        btnsatici_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), KulIdProfil.class);
                i.putExtra("kul_id",userId);
                String saticiAd= tvSatıcıAd.getText().toString();
                i.putExtra("kulAdi",saticiAd);
                startActivity(i);
            }
        });
    }
    public void tanimla(){
        yemekresim=(ImageView)findViewById(R.id.yemekResim);
        tvKategori=(TextView)findViewById(R.id.tvKategori);
        tvYemekAdi=(TextView)findViewById(R.id.tvYemekAdi);
        tvYemekMiktari=(TextView)findViewById(R.id.tvYemekMiktari);
        tvYemekFiyati=(TextView)findViewById(R.id.tvYemekFiyati);
        tvYemekPuani=(TextView)findViewById(R.id.tvYemekPuani);
        tvSatıcıAd=(TextView)findViewById(R.id.tvSatıcıAd);
        btnsatici_profil=(Button)findViewById(R.id.btnsatici_profil);
        btn_favori=(Button)findViewById(R.id.btn_favori);
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
    private void favoriKontrol(String uye_id,String yemek_id) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<Favoriler> ad = api.favoriKontrol(uye_id,yemek_id);

        ad.enqueue(new Callback<Favoriler>() {
            @Override
            public void onResponse(Call<Favoriler> call, Response<Favoriler> response) {

                    if(response.body().getIsSuccess()==0)
                    {
                        btn_favori.setText(response.body().getMessage());
                    }
                    else
                    {
                        btn_favori.setText(response.body().getMessage());

                    }
            }

            @Override
            public void onFailure(Call<Favoriler> call, Throwable t) {
                Toast.makeText(YemekDetay.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void favoriIslemler(final String uye_id, final String yemek_id) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<FavoriIslemler> ad = api.favoriIslemler(uye_id,yemek_id);

        ad.enqueue(new Callback<FavoriIslemler>() {
            @Override
            public void onResponse(Call<FavoriIslemler> call, Response<FavoriIslemler> response) {

                if(response.body().getIsSuccess()==0)
                {
                    Toast.makeText(YemekDetay.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    favoriKontrol(uye_id,yemek_id);
                }
                else
                {
                    Toast.makeText(YemekDetay.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    favoriKontrol(uye_id,yemek_id);
                }
            }

            @Override
            public void onFailure(Call<FavoriIslemler> call, Throwable t) {
                Toast.makeText(YemekDetay.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
}
