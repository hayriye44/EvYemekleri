package com.example.hayri.evyemekleri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hayri.evyemekleri.Models.Iletisim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class İletisimBilgisiGoster extends AppCompatActivity {
    TextView ili,adresi,teli;
   public String il,adres,tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim_bilgisi_goster);
        Bundle extras = getIntent().getExtras();
        int kul_id = extras.getInt("kul_id");
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
