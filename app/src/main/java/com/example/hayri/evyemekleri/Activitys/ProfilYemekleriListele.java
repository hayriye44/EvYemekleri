package com.example.hayri.evyemekleri.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.hayri.evyemekleri.Adapters.YemekAdapter;
import com.example.hayri.evyemekleri.Api;
import com.example.hayri.evyemekleri.ApiClient;
import com.example.hayri.evyemekleri.Models.CitysItem;
import com.example.hayri.evyemekleri.Models.FoodsItem;
import com.example.hayri.evyemekleri.Models.YemekList;
import com.example.hayri.evyemekleri.R;
import com.example.hayri.evyemekleri.SharedPref;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfilYemekleriListele extends AppCompatActivity {
    Window window;
    RecyclerView rvYemekler;
    List<FoodsItem> foodList;
    List<CitysItem>cityList;
    YemekAdapter yemekAdapter;
    TextView baslik;
    boolean goster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_yemekleri_listele);
        //statusbar rengini değişme
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));
        }
        rvYemekler=findViewById(R.id.rvYemekler);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),1);
        rvYemekler.setLayoutManager(layoutManager);
        foodList=new ArrayList<>();
        cityList=new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        int kul_id = extras.getInt("kul_id");
        int girisyapankul_id;
        girisyapankul_id=SharedPref.getInstance(this).LoggedInUserId();
        baslik=findViewById(R.id.tvBaslik);
        if(kul_id== girisyapankul_id)
        {
            goster=true;
        }
        else{goster=false;baslik.setText("Satıcının Tüm Yemekleri");}
        Log.i("kulıdİletisimBilgisi",""+kul_id);
        yemekListele(kul_id);
    }



    public void yemekListele(int kul_id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        Api api = retrofit.create(Api.class);
        Call<YemekList> call = api.getFoods(kul_id);
        call.enqueue(new Callback<YemekList>() {
            @Override
            public void onResponse(Call<YemekList> call, Response<YemekList> response) {
                foodList = response.body().getFoods();
                yemekAdapter=new YemekAdapter(foodList,goster,getApplicationContext());
                rvYemekler.setAdapter(yemekAdapter);
                Log.i("Yemekler",response.body().getFoods().toString());
            }
            @Override
            public void onFailure(Call<YemekList> call, Throwable t) {
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error-------------->",t.getLocalizedMessage());
            }
        });
    }
}
