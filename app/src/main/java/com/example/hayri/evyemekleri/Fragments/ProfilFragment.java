package com.example.hayri.evyemekleri.Fragments;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Vibrator;
        import android.provider.MediaStore;
        import android.support.annotation.NonNull;
        import android.support.v4.app.Fragment;
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
        import com.example.hayri.evyemekleri.Api;
        import com.example.hayri.evyemekleri.ApiClient;
        import com.example.hayri.evyemekleri.Models.Iletisim;
        import com.example.hayri.evyemekleri.Models.IletisimBilgiEkle;
        import com.example.hayri.evyemekleri.Models.IletisimBilgiGuncelle;
        import com.example.hayri.evyemekleri.Models.ProfilFoto;
        import com.example.hayri.evyemekleri.Models.YemekEkle;
        import com.example.hayri.evyemekleri.Activitys.ProfilYemekleriListele;
        import com.example.hayri.evyemekleri.R;
        import com.example.hayri.evyemekleri.SharedPref;
        import com.example.hayri.evyemekleri.Activitys.İletisimBilgisiGoster;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.UploadTask;
        import com.squareup.picasso.Picasso;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;

        import de.hdodenhof.circleimageview.CircleImageView;
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
    Button btnLogout,iletisimBilgisiEkleIv,iletisimbilgisigetir_btn,profilyemeklerigetir_btn;
    ImageView yemekEkleIv;
    ImageView yemekResmi;
    private CityAdapter citysAdapter;

    Bitmap bitmap,bitmap2;
    int kat_ıd;
    int kul_id;
    private View myFragment;
    String imageString;
    Vibrator v;
    ImageView profilephoto;
    //change to your register url
    final String yemekEkleUrl = "http://ysiparis.tk/yemekEkle.php";
    private String[] kategoriler={"Anayemekler","Çorbalar","Salata ve Turşular","Hamur İşleri","Pilavlar","Tatlılar"};
    private  String[] iller=new String[81];
    //Spinner'ları ve Adapter'lerini tanımlıyoruz
    private Spinner spinnerkategoriler,spinneriller;
    private ArrayAdapter<String> dataAdapterKategoriler;

    EditText yemekFiyati,yemekAdi,yemekMiktari,adres,tel,il;

    StorageReference storageReference;
    FirebaseStorage firebaseStorage;


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
        iletisimBilgisiEkleIv=myFragment.findViewById(R.id.add_iletisim_button);
        iletisimbilgisigetir_btn=myFragment.findViewById(R.id.iletisimbilgisigetir_btn);
        //getting logged in user name
        String loggedUsename = SharedPref.getInstance(getActivity()).LoggedInUser();
        kul_id=SharedPref.getInstance(getActivity()).LoggedInUserId();


        bitmap=null;imageString="";
        profilephoto=(ImageView)myFragment.findViewById(R.id.profile_image);
        profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeriAcProfile();
            }
        });


       // profilFotoGetir(kul_id);
        username.setText("Username : "+loggedUsename);
        //logging out
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                SharedPref.getInstance(getContext()).logout();
            }
        });
        yemekEkleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addYemekAlert();
            }
        });
        iletisimBilgisiEkleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addİletisimAlert();
            }
        });
        Log.i("kulıdProfil",""+kul_id);
        profilyemeklerigetir_btn=myFragment.findViewById(R.id.kulyemeklerigetir_btn);
        profilyemeklerigetir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ProfilYemekleriListele.class);
                intent.putExtra("kul_id",kul_id);
                startActivity(intent);
            }
        });
        Log.i("kul", "onCreateView: "+kul_id);
        iletisimbilgisigetir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),İletisimBilgisiGoster.class);
                intent.putExtra( "kul_id",kul_id);
                startActivity(intent);
            }
        });
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        kullanicifotoGetir();

        return myFragment;
    }

    public void kullanicifotoGetir()
    { StorageReference ref=storageReference.child("kullaniciresimleri").child(String.valueOf(kul_id)+".jpg");

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilephoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void addYemekAlert()
    {
        LayoutInflater layoutInflater=this.getLayoutInflater();
        final View view=layoutInflater.inflate(R.layout.yemekekle_alert,null);
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
        final Button yemekEkleButton=(Button)view.findViewById(R.id.btnYemekEkle);
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

                 //addYemek(kat_ıd,kul_id,yemekName,yemekFiy,7,44,imageToString(),miktar);
                 iletisimBilgisiGoster(kul_id,kat_ıd,yemekName,yemekFiy,7,imageToString(),miktar);
                 yemekAdi.setText("");
                 yemekMiktari.setText("");
                 yemekFiyati.setText("");
                 yemekResmi.setVisibility(View.INVISIBLE);
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

    public void addİletisimAlert()
    {
        LayoutInflater layoutInflater=this.getLayoutInflater();
        final View view=layoutInflater.inflate(R.layout.iletisimekle_alert,null);

        adres=(EditText)view.findViewById(R.id.etAdres);
        tel=(EditText)view.findViewById(R.id.etTel);
        il=(EditText)view.findViewById(R.id.etSehir);
        final Button iletisimEkleButton=(Button)view.findViewById(R.id.btnİletisimBilgisiEkle);
        iletisimEkleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !adres.getText().toString().equals("")&&!tel.getText().toString().equals(""))
                {
                    String adresName= adres.getText().toString();
                    String telNo=tel.getText().toString();
                    String ilAdi=il.getText().toString();
                    addİletisim(kul_id,ilAdi,adresName,telNo);
                    tel.setText("");
                    adres.setText("");
                    il.setText("");
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
                Toast.makeText(getContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void iletisimBilgisiGoster(final int kul_id, final int kat_id, final String yemekName, final double yemekFiy, final int yorum_puani, final String yemekresim, final String miktar){

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
                int il_id=Integer.valueOf(response.body().getIlid());
                addYemek(kat_id,kul_id,yemekName,yemekFiy,yorum_puani,il_id,yemekresim,miktar);
            }
            @Override
            public void onFailure(Call<Iletisim> call, Throwable t) {
                // Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error-------------->",t.getLocalizedMessage());

            }
        });

    }

    private void addİletisim(int kul_id, String sehirAdi,String adres_aciklama,String tel) {
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<IletisimBilgiEkle> iletisimBilgiEkleCall = api.iletisimBilgiEkle(kul_id,sehirAdi,adres_aciklama,tel);
        iletisimBilgiEkleCall.enqueue(new Callback<IletisimBilgiEkle>() {
            @Override
            public void onResponse(Call<IletisimBilgiEkle> call, Response<IletisimBilgiEkle> response) {
                Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<IletisimBilgiEkle> call, Throwable t) {
                 Toast.makeText(getContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    void galeriAc()
    {Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,777);
    }
    void galeriAcProfile()
    {Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //Activity içinde seçilen resmi gösterme
    startActivityForResult(intent,5);
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
        //seçtiğimiz resme ulaşmak için
        if(requestCode==5 && resultCode==Activity.RESULT_OK)
        {
            //filepath
            Uri filepath=data.getData();
            StorageReference ref=storageReference.child("kullaniciresimleri").child(String.valueOf(kul_id)+".jpg");
           ref.putFile(filepath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(),"Resim başarıyla güncellendi",Toast.LENGTH_LONG).show();
                        kullanicifotoGetir();

                    }
                    else {
                        Toast.makeText(getContext(),"Resim güncellenemedi",Toast.LENGTH_LONG).show();
                    }
               }
           });

            /* try {
                bitmap=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                profilephoto.setImageBitmap(bitmap);
                profilephoto.setVisibility(View.VISIBLE);
                //profilephotoGüncelle(kul_id,imageToString());
            } catch (IOException e) {
                e.printStackTrace();
            }*/
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


}
