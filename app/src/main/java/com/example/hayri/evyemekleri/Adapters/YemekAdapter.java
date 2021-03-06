package com.example.hayri.evyemekleri.Adapters;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class YemekAdapter extends  RecyclerView.Adapter<YemekAdapter.ViewHolder>{
        List<FoodsItem> list;
        Context context;
        boolean goster;

public YemekAdapter(List<FoodsItem> list,boolean goster2, Context context) {
        this.list = list;
        this.context = context;
        this. goster=goster2;
        }
    @NonNull
    @Override
    public YemekAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Listview için oluşturduğumuz layoutu tanımlama
        View view=LayoutInflater.from(context).inflate(R.layout.foodlistitemlayout,viewGroup,false);
        return new YemekAdapter.ViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull YemekAdapter.ViewHolder viewHolder, int i) {
        //tanımlama atama işlemi
        int katno=Integer.valueOf(list.get(i).getKatId());
        String katAdi;
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
        viewHolder.tvKategori.setText(katAdi);
        viewHolder.tvYemekAdi.setText(list.get(i).getYemekAdi().toString());
        viewHolder.tvYemekMiktari.setText(list.get(i).getMiktar().toString());
        viewHolder.tvYemekFiyati.setText(list.get(i).getYemekFiyat().toString());
        viewHolder.tvYemekPuani.setText(list.get(i).getYorumPuani().toString());
        if(goster==true)
        { viewHolder.btnYemekSil.setVisibility(View.VISIBLE);}
        else {viewHolder.btnYemekSil.setVisibility(View.INVISIBLE);}
        Picasso.get().load(list.get(i).getYemekresim()).into(viewHolder.ivYemekResmi);

    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        Button btnYemekSil;
        ImageView ivYemekResmi;
        TextView tvKategori,tvYemekAdi,tvYemekMiktari,tvYemekFiyati,tvYemekPuani;


        //itemView ile listviewin her ıtemi için layout ile oluşturduğumuz view tanımlanması işlemi gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            btnYemekSil=(Button)itemView.findViewById(R.id.foodDelete);
            ivYemekResmi=(ImageView) itemView.findViewById(R.id.ivYemekResmi);
            tvKategori=(TextView) itemView.findViewById(R.id.tvKategori);
            tvYemekAdi=(TextView) itemView.findViewById(R.id.tvYemekAdi);
            tvYemekMiktari=(TextView) itemView.findViewById(R.id.tvYemekMiktari);
            tvYemekFiyati=(TextView) itemView.findViewById(R.id.tvYemekFiyati);
            tvYemekPuani=(TextView) itemView.findViewById(R.id.tvYemekPuani);

        }
    }


}
