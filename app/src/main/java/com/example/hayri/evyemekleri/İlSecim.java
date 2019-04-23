package com.example.hayri.evyemekleri;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.hayri.evyemekleri.Adapters.CityAdapter;
import com.example.hayri.evyemekleri.Models.CitysItem;
import com.example.hayri.evyemekleri.Models.SehirList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class İlSecim extends AppCompatActivity {
    private RecyclerView citylistrecyclerview;
    private CityAdapter citysAdapter;
    private List<CitysItem> cityList;
   // public int kul_id;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_il_secim);
        //statusbar rengini değişme
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));

        }
        //tanimla();
        //getCitys();


       /* kul_id=SharedPref.getInstance(this).LoggedInUserId();
        Intent intent=new Intent(getApplicationContext(),İletisimBilgisiGuncelleActivity.class);
        intent.putExtra("kul_id",kul_id);
        getApplicationContext().startActivity(intent);*/

    }
    public void tanimla(){
        cityList=new ArrayList<>();
        citylistrecyclerview=findViewById(R.id.citylistrecylerview);
        RecyclerView.LayoutManager mng=new GridLayoutManager(this,3);
        citylistrecyclerview.setLayoutManager(mng);
    }

    public void getCitys()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        Api api = retrofit.create(Api.class);
        Call<SehirList> call = api.getCitys();
        call.enqueue(new Callback<SehirList>() {
            @Override
            public void onResponse(Call<SehirList> call, Response<SehirList> response) {
                cityList = response.body().getCitys();
                citysAdapter=new CityAdapter(cityList,İlSecim.this);
                citylistrecyclerview.setAdapter(citysAdapter);
                Log.i("Listem",response.body().getCitys().toString());
            }
            @Override
            public void onFailure(Call<SehirList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error-------------->",t.getLocalizedMessage());
            }
        });
    }
}
