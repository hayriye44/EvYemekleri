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

import com.example.hayri.evyemekleri.Models.FoodsItem;
import com.example.hayri.evyemekleri.R;
import com.example.hayri.evyemekleri.Activitys.YemekDetay;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KatFoodsAdapter extends RecyclerView.Adapter<KatFoodsAdapter.ViewHolder> {

    List<FoodsItem> list;
    Context context;


    public KatFoodsAdapter(List<FoodsItem> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Listview için oluşturduğumuz layoutu tanımlama
        View view=LayoutInflater.from(context).inflate(R.layout.katfoodlistitem,viewGroup,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final int getir=i;
        //tanımlama atama işlemi
        int katno=Integer.valueOf(list.get(i).getKatId());
        final String katAdi;
        switch (katno) {
            case 2 :
                katAdi="Anayemekler";
                break;
            case 3 :
                katAdi="Çorbalar";
                break;
            case 4 :
                katAdi="Salata ve Turşular";
                break;
            case 5 :
                katAdi="Hamur İşi";
                break;

            case 6 :
                katAdi="Pilavlar";
                break;
            case 7 :
                katAdi="Tatlılar";
                break;
            default :
                katAdi="Kategori Adı bulunamadı";
                break;
        }
        viewHolder.tvYemekAdi.setText(list.get(i).getYemekAdi().toString());
        viewHolder.tvYemekFiyati.setText(list.get(i).getYemekFiyat().toString());

        Picasso.get().load(list.get(i).getYemekresim()).into(viewHolder.ivYemekResmi);
        viewHolder.btnDetay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,YemekDetay.class);
                intent.putExtra("yemek_id",list.get(getir).getYemekId());
                intent.putExtra("resim",list.get(getir).getYemekresim());
                intent.putExtra("Kategori", katAdi);
                intent.putExtra("YemekAdi",list.get(getir).getYemekAdi().toString());
                intent.putExtra("YemekMiktari",list.get(getir).getMiktar().toString());
                intent.putExtra("YemekFiyati",list.get(getir).getYemekFiyat().toString());
                intent.putExtra("YemekPuani",list.get(getir).getYorumPuani().toString());
                intent.putExtra("KulAdi",list.get(getir).getKulid().toString());
                context.startActivity(intent);
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
        TextView tvKategori,tvYemekAdi,tvYemekMiktari,tvYemekFiyati,tvYemekPuani;
        Button btnDetay;
        public ViewHolder(View itemView) {
            //itemview ile listviewin her elemanı için layout ile oluşturduğumuz viewin tanımlanması işlemi gerçekleşecek
            super(itemView);
            ivYemekResmi=(ImageView) itemView.findViewById(R.id.ivYemekResmi);
           // tvKategori=(TextView) itemView.findViewById(R.id.tvKategori);
            tvYemekAdi=(TextView) itemView.findViewById(R.id.tvYemekAdi);
           // tvYemekMiktari=(TextView) itemView.findViewById(R.id.tvYemekMiktari);
            tvYemekFiyati=(TextView) itemView.findViewById(R.id.tvYemekFiyati);
           // tvYemekPuani=(TextView) itemView.findViewById(R.id.tvYemekPuani);
            btnDetay=(Button) itemView.findViewById(R.id.btnDetay);
        }

        //Layout oluşturucaz ıtem tasarımı  için
    }
}
