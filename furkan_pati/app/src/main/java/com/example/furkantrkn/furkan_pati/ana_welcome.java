package com.example.furkantrkn.furkan_pati;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ana_welcome extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_welcome);

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *
     * activity transitions
     *
     */


    public void Tiklendi(View v ) {
        if (v.getId() == R.id.patiliyo_com) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

       else if (v.getId() == R.id.patiliyo_shop_kategori)
        {
            Intent intent = new Intent(getApplicationContext(),shop_patiliyo.class);

            startActivity(intent);
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
