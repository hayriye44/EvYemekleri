package com.example.hayri.evyemekleri.Fragments;
        import android.app.AlertDialog;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Vibrator;
        import android.provider.MediaStore;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.GridLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Base64;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.hayri.evyemekleri.Adapters.CityAdapter;
        import com.example.hayri.evyemekleri.Adapters.YemekAdapter;
        import com.example.hayri.evyemekleri.Api;
        import com.example.hayri.evyemekleri.ApiClient;
        import com.example.hayri.evyemekleri.Models.FoodsItem;
        import com.example.hayri.evyemekleri.Models.SehirList;
        import com.example.hayri.evyemekleri.Models.YemekEkle;
        import com.example.hayri.evyemekleri.Models.YemekList;
        import com.example.hayri.evyemekleri.R;
        import com.example.hayri.evyemekleri.SharedPref;
        import com.example.hayri.evyemekleri.İlSecim;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;
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
public class ProfilFragment extends Fragment {
    TextView username;
    Button btnLogout;
    ImageView yemekEkleIv;
    ImageView yemekResmi;
    RecyclerView rvYemekler;
    List<FoodsItem> foodList;
    YemekAdapter yemekAdapter;

    Bitmap bitmap;
    int kat_ıd;
    int kul_id;
    private View myFragment;
    String imageString;
    Vibrator v;
    //change to your register url
    final String yemekEkleUrl = "http://ysiparis.tk/yemekEkle.php";
    private String[] kategoriler={"Anayemekler","Çorbalar","Salata ve Turşular","Hamur İşleri","Pilavlar","Tatlılar"};
    //Spinner'ları ve Adapter'lerini tanımlıyoruz
    private Spinner spinnerkategoriler;
    private ArrayAdapter<String> dataAdapterKategoriler;
    EditText yemekFiyati,yemekAdi,yemekMiktari;
    public ProfilFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_profil, container, false);
        // Inflate the layout for this fragment
        username = myFragment.findViewById(R.id.username);
        btnLogout = myFragment.findViewById(R.id.btnLogout);
        yemekEkleIv=myFragment.findViewById(R.id.add_yemek_button);
        rvYemekler=myFragment.findViewById(R.id.rvYemekler);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getContext(),1);
        rvYemekler.setLayoutManager(layoutManager);
        foodList=new ArrayList<>();

        bitmap=null;
        imageString="";
        yemekEkleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addYemekAlert();
            }
        });
        //getting logged in user name
        String loggedUsename = SharedPref.getInstance(getActivity()).LoggedInUser();
        kul_id=SharedPref.getInstance(getActivity()).LoggedInUserId();
        username.setText("Username : "+loggedUsename);
        //logging out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                SharedPref.getInstance(getContext()).logout();
            }
        });

       yemekListele(3);

        return myFragment;
    }
    public void addYemekAlert()
    {
        LayoutInflater layoutInflater=this.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.yemekekle_alert,null);
        yemekAdi=(EditText)view.findViewById(R.id.etYemekAdi);
        yemekFiyati=(EditText)view.findViewById(R.id.yemekFiyat);
        yemekMiktari=(EditText)view.findViewById(R.id.yemekMiktar);
        yemekResmi=(ImageView)view.findViewById(R.id.ivYemekResim);
        spinnerkategoriler=(Spinner)view.findViewById(R.id.katSpinner);
        //Spinner'lar için adapterleri hazırlıyoruz.
        dataAdapterKategoriler = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, kategoriler);
        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterKategoriler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        spinnerkategoriler.setAdapter(dataAdapterKategoriler);
        spinnerkategoriler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Seçilen il ve ilçeyi ekranda gösteriyoruz.
                kat_ıd=(int)(spinnerkategoriler.getSelectedItemId())+2;
                Toast.makeText(getContext(), ""+kat_ıd, Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
       // ImageView yemekResim=(ImageView)view.findViewById(R.id.imgYemek);
        Button yemekEkleButton=(Button)view.findViewById(R.id.btnYemekEkle);
        Button resimSecButton=(Button)view.findViewById(R.id.btnResimSec);
        resimSecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeriAc();
            }
        });
        yemekEkleButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(!imageToString().equals("")&& !yemekAdi.getText().toString().equals("")&&!yemekFiyati.getText().toString().equals(""))
             {
                 String yemekName=yemekAdi.getText().toString();
                 String miktar=yemekMiktari.getText().toString();
                 double yemekFiy= Double.parseDouble(yemekFiyati.getText().toString());
                 addYemek(kat_ıd,kul_id,yemekName,yemekFiy,7,44,imageToString(),miktar);
                 yemekAdi.setText("");
                 yemekFiyati.setText("");
             }
             else
             {
                 Toast.makeText(getContext(),"Tüm alanların doldurulması ve resmin seçilmesi zorunludur",Toast.LENGTH_LONG).show();
             }
            }
        });
        //alertDialog
        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog=alert.create();
        alertDialog.show();
    }
    private void addYemek(int kat_id, int kul_id, String yemek_adi,double yemek_fiyat,int yorum_puani,int sehir_id,String yemekresim,String miktar) {
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<YemekEkle> yemekEkleCall = api.yemekEkle(kat_id,kul_id,yemek_adi,yemek_fiyat,yorum_puani,sehir_id,yemekresim,miktar);
        yemekEkleCall.enqueue(new Callback<YemekEkle>() {
            @Override
            public void onResponse(Call<YemekEkle> call, Response<YemekEkle> response) {
                Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<YemekEkle> call, Throwable t) {
               // Toast.makeText(ProfilFragment.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    void galeriAc()
    {Intent intent=new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(intent,777);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==777 && data!=null)
        {Uri path=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                yemekResmi.setImageBitmap(bitmap);
                yemekResmi.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public String imageToString(){
        if(bitmap!=null){
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] byt=byteArrayOutputStream.toByteArray();
            imageString=Base64.encodeToString(byt,Base64.DEFAULT);
            return imageString;
        }
        else{
        return imageString;
        }
    }
    public void yemekListele(int kul_id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();
        Api api = retrofit.create(Api.class);
        Call<YemekList> call = api.getFoods(kul_id);
        call.enqueue(new Callback<YemekList>() {
            @Override
            public void onResponse(Call<YemekList> call, Response<YemekList> response) {
                foodList = response.body().getFoods();
                yemekAdapter=new YemekAdapter(foodList,getContext());
                rvYemekler.setAdapter(yemekAdapter);
                Log.i("Yemekler",response.body().getFoods().toString());
            }
            @Override
            public void onFailure(Call<YemekList> call, Throwable t) {
               // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error-------------->",t.getLocalizedMessage());
            }
        });
    }
}
