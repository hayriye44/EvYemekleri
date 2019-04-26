package com.example.hayri.evyemekleri.Activitys;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.hayri.evyemekleri.Adapters.EnBegenilenleriAdapter;
import com.example.hayri.evyemekleri.Api;
import com.example.hayri.evyemekleri.ApiClient;
import com.example.hayri.evyemekleri.Models.BegenilenYemeklerList;
import com.example.hayri.evyemekleri.Models.BegenilenyemeklerItem;
import com.example.hayri.evyemekleri.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KulIdProfil extends AppCompatActivity {
    Window window;
    List<BegenilenyemeklerItem>enBegenilenYemekler;
    EnBegenilenleriAdapter begenilenlerAdapter;
    RecyclerView rvYüksekPuanlilar;
    TextView username;
    Button kulyemeklergetir_btn,iletisimbilgisigetir_btn;
    int kul_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kul_id_profil);
        //statusbar rengini değişme
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));
        }
        tanimla();
        Bundle extras = getIntent().getExtras();
        kul_id = extras.getInt("kul_id");
        String kulAdi= extras.getString("kulAdi");
        username.setText(kulAdi);
        IndirimdekilerGetir(kul_id);
        kulyemeklergetir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ProfilYemekleriListele.class);
                intent.putExtra("kul_id",kul_id);
                startActivity(intent);
            }
        });
        iletisimbilgisigetir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),İletisimBilgisiGoster.class);
                intent.putExtra( "kul_id",kul_id);
                startActivity(intent);
            }
        });
    }
    public void tanimla(){
        rvYüksekPuanlilar=findViewById(R.id.rvBegenilenYemekleri);
        //RecyclerView.LayoutManager layoutManager2=new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvYüksekPuanlilar.setLayoutManager(layoutManager2);
        username = findViewById(R.id.username);
        kulyemeklergetir_btn=findViewById(R.id.kulyemeklerigetir_btn);
        iletisimbilgisigetir_btn=findViewById(R.id.iletisimbilgisigetir_btn);
    }

    public void IndirimdekilerGetir(int kul_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        Api api = retrofit.create(Api.class);
        Call<BegenilenYemeklerList> call = api.getBegenilenYemeklerGetir(kul_id);
        call.enqueue(new Callback<BegenilenYemeklerList>() {
            @Override
            public void onResponse(Call<BegenilenYemeklerList> call, Response<BegenilenYemeklerList> response) {
                enBegenilenYemekler=response.body().getBegenilenyemekler();
                Log.i("indirimdekiler","Adı"+response.body().getBegenilenyemekler());
                begenilenlerAdapter=new EnBegenilenleriAdapter(enBegenilenYemekler,getApplicationContext());
                rvYüksekPuanlilar.setAdapter(begenilenlerAdapter);
            }
            @Override
            public void onFailure(Call<BegenilenYemeklerList> call, Throwable t) {
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error-------------->",t.getLocalizedMessage());
            }
        });
    }
}
