package com.example.hayri.evyemekleri;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.example.hayri.evyemekleri.Fragments.AnasayfaFragment;
import com.example.hayri.evyemekleri.Fragments.FovorilerFragment;
import com.example.hayri.evyemekleri.Fragments.ProfilFragment;


public class MainActivity extends AppCompatActivity {

Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //statusbar rengini değişme
        if(Build.VERSION.SDK_INT>=21)
        {
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));

        }

        //check if user is logged in
        if (!SharedPref.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        //Bottom menüde mebülere tıklanınca ortadaki alanı değiştime  ama menünün altta kalması
        BottomNavigationView navigationView=findViewById(R.id.bottom_nav);

        final AnasayfaFragment anasayfaFragment=new AnasayfaFragment();
        final FovorilerFragment favorilerFragment=new FovorilerFragment();
        final ProfilFragment profilFragment =new ProfilFragment();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.anasayfa)
                {
                    setFragment(anasayfaFragment);
                    return  true;
                }
                else if(id==R.id.favoriler)
                {
                    setFragment(favorilerFragment);
                    return true;
                }
                else if(id==R.id.profil)
                {
                    setFragment(profilFragment);
                    return  true;
                }
                return false;
            }
        });
        navigationView.setSelectedItemId(R.id.anasayfa);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_il:
                startActivity(new Intent(this,İlSecim.class));

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }
}
