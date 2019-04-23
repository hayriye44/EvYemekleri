package com.example.hayri.evyemekleri.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hayri.evyemekleri.Fragments.AnasayfaFragment;
import com.example.hayri.evyemekleri.MainActivity;
import com.example.hayri.evyemekleri.Models.CitysItem;
import com.example.hayri.evyemekleri.R;
import com.example.hayri.evyemekleri.YemekDetay;
import com.example.hayri.evyemekleri.İletisimBilgisiGuncelleActivity;

import java.util.List;

public class CityAdapter extends  RecyclerView.Adapter<CityAdapter.ViewHolder>{
    List<CitysItem> list;
    Context context;

    public CityAdapter(List<CitysItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Listview için oluşturduğumuz layoutu tanımlama
        View view=LayoutInflater.from(context).inflate(R.layout.citylistitemlayout,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    //tanımlama atama işlemi
        final int getir=i;
    viewHolder.btnCity.setText(list.get(i).getSehirAdi().toString());
    viewHolder.btnCity.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("il_id",list.get(getir).getId());
           /*Intent intent=new Intent(context,İletisimBilgisiGuncelleActivity.class);
            intent.putExtra("secilen_il_id",list.get(getir).getId());
            context.startActivity(intent);*/
        }
    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        Button btnCity;
        //itemView ile listviewin her ıtemi için layout ile oluşturduğumuz view tanımlanması işlemi gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            btnCity=(Button)itemView.findViewById(R.id.cityname);
        }
    }
}
