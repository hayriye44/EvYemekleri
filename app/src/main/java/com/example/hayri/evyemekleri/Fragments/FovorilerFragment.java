package com.example.hayri.evyemekleri.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hayri.evyemekleri.Activitys.YemekDetay;
import com.example.hayri.evyemekleri.Adapters.FavorilerAdapter;
import com.example.hayri.evyemekleri.Adapters.YemekAdapter;
import com.example.hayri.evyemekleri.Api;
import com.example.hayri.evyemekleri.ApiClient;
import com.example.hayri.evyemekleri.Models.CitysItem;
import com.example.hayri.evyemekleri.Models.FavoriIslemler;
import com.example.hayri.evyemekleri.Models.Favoriler;
import com.example.hayri.evyemekleri.Models.FavorilerList;
import com.example.hayri.evyemekleri.Models.FavoriyemeklerItem;
import com.example.hayri.evyemekleri.Models.FoodsItem;
import com.example.hayri.evyemekleri.Models.YemekList;
import com.example.hayri.evyemekleri.R;
import com.example.hayri.evyemekleri.SharedPref;

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
public class FovorilerFragment extends Fragment {
    RecyclerView rvYemekler;
    List<FavoriyemeklerItem> foodList;
    FavorilerAdapter yemekAdapter;
    private View myFragment;

    public FovorilerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_fovoriler, container, false);
        rvYemekler=myFragment.findViewById(R.id.rvYemekler);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(myFragment.getContext(),1);
        rvYemekler.setLayoutManager(layoutManager);
        foodList=new ArrayList<>();

        String girisyapankul_id;
        girisyapankul_id=String.valueOf(SharedPref.getInstance(getContext()).LoggedInUserId());
        FavoriYemeklerListele(girisyapankul_id);
        return myFragment;
    }
    public void FavoriYemeklerListele(final String uye_id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        Api api = retrofit.create(Api.class);
        Call<FavorilerList> call = api.getfavorilerList(uye_id);
        call.enqueue(new Callback<FavorilerList>() {
            @Override
            public void onResponse(Call<FavorilerList> call, Response<FavorilerList> response) {
                foodList = response.body().getFavoriyemekler();
                yemekAdapter=new FavorilerAdapter(foodList,getContext(),uye_id);
                rvYemekler.setAdapter(yemekAdapter);
                //Log.i("Yemekler",response.body().getFoods().toString());
            }
            @Override
            public void onFailure(Call<FavorilerList> call, Throwable t) {
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error-------------->",t.getLocalizedMessage());
            }
        });
    }

}
