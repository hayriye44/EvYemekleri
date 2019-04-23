package com.example.hayri.evyemekleri;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hayri.evyemekleri.Models.Iletisim;
import com.example.hayri.evyemekleri.Models.IletisimBilgiEkle;
import com.example.hayri.evyemekleri.Models.IletisimBilgiGuncelle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class İletisimBilgisiGuncelleActivity extends AppCompatActivity {
    public String il,adres,tel;
    public  int il_id;
    EditText ili,adresi,teli;
    Window window;
    Button iletisim_güncelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim_bilgisi_guncelle);
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));

        }
        Bundle extras = getIntent().getExtras();
        final int kul_id = extras.getInt("kul_id");
        iletisimBilgisiGoster(kul_id);
        Log.i("kulıd",""+kul_id);
        iletisim_güncelle=findViewById(R.id.iletisim_güncelle_btn);
        iletisim_güncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ili=findViewById(R.id.tvİl);
                adresi=findViewById(R.id.tvAdres);
                teli=findViewById(R.id.telNo);
                il_id=Integer.valueOf(ili.getText().toString());
                adres=adresi.getText().toString();
                tel=teli.getText().toString();
                iletisimBilgisiGüncelle(kul_id,il_id,adres,tel);
            }
        });

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

    private void iletisimBilgisiGüncelle(int kul_id, int il_id,String adres_aciklama,String tel) {
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<IletisimBilgiGuncelle> iletisimBilgiGuncelleCall = api.iletisimBilgisiGüncelle(kul_id,il_id,adres_aciklama,tel);
        iletisimBilgiGuncelleCall.enqueue(new Callback<IletisimBilgiGuncelle>() {
            @Override
            public void onResponse(Call<IletisimBilgiGuncelle> call, Response<IletisimBilgiGuncelle> response) {
                Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<IletisimBilgiGuncelle> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
