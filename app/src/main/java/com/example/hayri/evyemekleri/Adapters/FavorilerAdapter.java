package com.example.hayri.evyemekleri.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hayri.evyemekleri.Activitys.MainActivity;
import com.example.hayri.evyemekleri.Api;
import com.example.hayri.evyemekleri.ApiClient;
import com.example.hayri.evyemekleri.Fragments.FovorilerFragment;
import com.example.hayri.evyemekleri.Models.FavoriIslemler;
import com.example.hayri.evyemekleri.Models.Favoriler;
import com.example.hayri.evyemekleri.Models.FavoriyemeklerItem;
import com.example.hayri.evyemekleri.Models.FoodsItem;
import com.example.hayri.evyemekleri.R;
import com.example.hayri.evyemekleri.Activitys.YemekDetay;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavorilerAdapter extends RecyclerView.Adapter<FavorilerAdapter.ViewHolder> {

    List<FavoriyemeklerItem> list;
    Context context;
    String uye_id;
    String yemek_id;

    public FavorilerAdapter(List<FavoriyemeklerItem> list, Context context,String uye_id) {
        this.list = list;
        this.context = context;
        this.uye_id=uye_id;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Listview için oluşturduğumuz layoutu tanımlama
        View view=LayoutInflater.from(context).inflate(R.layout.favorileritemlayout,viewGroup,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final int getir=i;
        //tanımlama atama işlemi
        viewHolder.tvYemekAdi.setText(list.get(i).getYemekadi().toString());
        viewHolder.tvYemekFiyati.setText(list.get(i).getYemekfiyat().toString());
        viewHolder.tvYemekMiktari.setText(list.get(i).getMiktar().toString());
        Picasso.get().load(list.get(i).getYemekresim()).into(viewHolder.ivYemekResmi);
        yemek_id=list.get(i).getYemekid();
        viewHolder.favoridencikar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriIslemler(uye_id,yemek_id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //view elemanları tanımı için
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivYemekResmi;
        TextView tvYemekAdi,tvYemekMiktari,tvYemekFiyati;
        Button favoridencikar;
        public ViewHolder(View itemView) {
            //itemview ile listviewin her elemanı için layout ile oluşturduğumuz viewin tanımlanması işlemi gerçekleşecek
            super(itemView);
            ivYemekResmi=(ImageView) itemView.findViewById(R.id.ivYemekResmi);
            // tvKategori=(TextView) itemView.findViewById(R.id.tvKategori);
            tvYemekAdi=(TextView) itemView.findViewById(R.id.tvYemekAdi);
            tvYemekMiktari=(TextView) itemView.findViewById(R.id.tvYemekMiktari);
            tvYemekFiyati=(TextView) itemView.findViewById(R.id.tvYemekFiyati);
            favoridencikar=(Button)itemView.findViewById(R.id.favoridencikar);
        }

        //Layout oluşturucaz ıtem tasarımı  için
    }


    private void favoriIslemler(final String uye_id, final String yemek_id) {
        Api api = ApiClient.getClient().create(Api.class);
        Call<FavoriIslemler> ad = api.favoriIslemler(uye_id,yemek_id);

        ad.enqueue(new Callback<FavoriIslemler>() {
            @Override
            public void onResponse(Call<FavoriIslemler> call, Response<FavoriIslemler> response) {

                if(response.body().getIsSuccess()==0)
                {
                    Toast.makeText(context,"Yemek favorilere eklendi",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context,"Yemek favorilerden çıkarıldı",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<FavoriIslemler> call, Throwable t) {
              // Toast.makeText(this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }
}
