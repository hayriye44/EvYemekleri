package com.example.hayri.evyemekleri.Activitys;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.hayri.evyemekleri.Api;
import com.example.hayri.evyemekleri.ApiClient;
import com.example.hayri.evyemekleri.Models.Iletisim;
import com.example.hayri.evyemekleri.R;
import com.example.hayri.evyemekleri.SharedPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class İletisimBilgisiGoster extends AppCompatActivity {
    TextView ili,adresi,teli;
    Window window;
    Button iletisim_güncelle;
    boolean goster;
    TextView baslik;
   public String il,adres,tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim_bilgisi_goster);
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));

        }
        Bundle extras = getIntent().getExtras();
        final int kul_id = extras.getInt("kul_id");
        Log.i("kulıdİletisimBilgisi",""+kul_id);
        iletisim_güncelle=(Button)findViewById(R.id.iletisim_güncelle_btn);
        int girisyapankul_id;
        girisyapankul_id=SharedPref.getInstance(this).LoggedInUserId();
        baslik=findViewById(R.id.tvBaslik);
        if(kul_id== girisyapankul_id)
        {
            iletisim_güncelle.setVisibility(View.VISIBLE);
        }
        else{iletisim_güncelle.setVisibility(View.INVISIBLE);baslik.setText("Satıcının İletişim Bilgileri");}

        iletisim_güncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), İletisimBilgisiGuncelleActivity.class);
                i.putExtra("kul_id",kul_id);
                startActivity(i);
            }
        });
        iletisimBilgisiGoster(kul_id);
    }
    public void iletisimBilgisiGoster(final int kul_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        Api api = retrofit.create(Api.class);
        Call<Iletisim> call = api.getIletisimBilgisi(kul_id);
        call.enqueue(new Callback<Iletisim>() {
            @Override
            public void onResponse(Call<Iletisim> call, Response<Iletisim> response) {
                Log.i("kul",""+kul_id);
                 adres=response.body().getAdresAciklama().toString();
                 tel=response.body().getTel().toString();
                 il=response.body().getIlid().toString();
                ili=findViewById(R.id.tvİl);
                adresi=findViewById(R.id.tvAdres);
                teli=findViewById(R.id.telNo);
                ili.setText(il);
                adresi.setText(adres);
                teli.setText(tel);
                 Log.i("Kulbilgisistringler",""+adres+tel+il);
                Log.i("Kulbilgisi",response.body().getKulid().toString()+""+response.body().getAdresAciklama().toString()+""+response.body().getTel().toString()+response.body().getIlid().toString());
            }
            @Override
            public void onFailure(Call<Iletisim> call, Throwable t) {
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error-------------->",t.getLocalizedMessage());
            }
        });
    }
}
