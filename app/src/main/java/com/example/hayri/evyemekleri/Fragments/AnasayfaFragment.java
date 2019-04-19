package com.example.hayri.evyemekleri.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.hayri.evyemekleri.Adapters.KatFoodsAdapter;
import com.example.hayri.evyemekleri.Adapters.YemekAdapter;
import com.example.hayri.evyemekleri.Api;
import com.example.hayri.evyemekleri.ApiClient;
import com.example.hayri.evyemekleri.Models.FoodsItem;
import com.example.hayri.evyemekleri.Models.YemekList;
import com.example.hayri.evyemekleri.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnasayfaFragment extends Fragment {
    private View view;
    RecyclerView rvYemekler;
    List<FoodsItem> foodList;
    KatFoodsAdapter yemekAdapter;
    Button anaYemek,corbalar,salata,hamur,pilav,tatlılar;
    public AnasayfaFragment() {

        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          view=inflater.inflate(R.layout.fragment_anasayfa, container, false);
        Button anaYemek = (Button)view.findViewById(R.id.anaYemek);
        Button corbalar = (Button)view.findViewById(R.id.corbalar);
        Button salata = (Button)view.findViewById(R.id.salata);
        Button hamur = (Button)view.findViewById(R.id.hamur);
        Button pilav = (Button)view.findViewById(R.id.pilav);
        Button tatlılar = (Button)view.findViewById(R.id.tatlılar);
        anaYemek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AllFoodList(2);
            }
        });
        corbalar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AllFoodList(3);
            }
        });
        salata.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AllFoodList(4);
            }
        });
        hamur.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AllFoodList(5);
            }
        });
        pilav.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AllFoodList(6);
            }
        });
        tatlılar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AllFoodList(7);
            }
        });
        rvYemekler=view.findViewById(R.id.rvKatYemekler);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        rvYemekler.setLayoutManager(layoutManager);
        foodList=new ArrayList<>();

        AllFoodList(2);
        return view;
    }

    public void AllFoodList(int kat_id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        Api api = retrofit.create(Api.class);
        Call<YemekList> call = api.getAllFoods(kat_id);
        call.enqueue(new Callback<YemekList>() {
            @Override
            public void onResponse(Call<YemekList> call, Response<YemekList> response) {
                foodList = response.body().getFoods();
                yemekAdapter=new KatFoodsAdapter(foodList,getContext());
                rvYemekler.setAdapter(yemekAdapter);
                Toast.makeText(getContext(),"Sistemde kayıtlı"+foodList.size()+"yemek var",Toast.LENGTH_LONG).show();

                Log.i("Yemekler",response.body().getFoods().toString());
            }
            @Override
            public void onFailure(Call<YemekList> call, Throwable t) {
                 Toast.makeText(getContext(), "Bu kategoride ürün bulunmamakta", Toast.LENGTH_SHORT).show();
                Log.d("error-------------->",t.getLocalizedMessage());
            }
        });
    }
}
