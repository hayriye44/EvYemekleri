package com.example.hayri.evyemekleri.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hayri.evyemekleri.Models.CitysItem;
import com.example.hayri.evyemekleri.R;

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
    viewHolder.btnCity.setText(list.get(i).getSehirAdi().toString());

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
