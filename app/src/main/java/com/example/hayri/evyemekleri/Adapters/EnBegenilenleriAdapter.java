package com.example.hayri.evyemekleri.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hayri.evyemekleri.Models.BegenilenyemeklerItem;
import com.example.hayri.evyemekleri.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EnBegenilenleriAdapter  extends RecyclerView.Adapter<EnBegenilenleriAdapter.ViewHolder> {
    List<BegenilenyemeklerItem> list;
    Context context;
    public EnBegenilenleriAdapter(List<BegenilenyemeklerItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EnBegenilenleriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.yuksekpuanliyemeklist,viewGroup,false);
        return new EnBegenilenleriAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnBegenilenleriAdapter.ViewHolder viewHolder, int i) {
        final int getir=i;
        //tanımlama atama işlemi
        viewHolder.tvÜrünAdi.setText(list.get(i).getYemekadi().toString());
        Picasso.get().load(list.get(i).getYemekresim()).into(viewHolder.ivÜrünResmi);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    //view elemanları tanımı için
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivÜrünResmi;
        TextView tvÜrünAdi;
        public ViewHolder(View itemView) {
            //itemview ile listviewin her elemanı için layout ile oluşturduğumuz viewin tanımlanması işlemi gerçekleşecek
            super(itemView);
            ivÜrünResmi =(ImageView) itemView.findViewById(R.id.ivÜrünResmi);
            tvÜrünAdi =(TextView) itemView.findViewById(R.id.tvÜrünAdi);
        }

        //Layout oluşturucaz ıtem tasarımı  için
    }




}
