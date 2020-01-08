package com.abach.iptv.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.abach.iptv.R;

import java.sql.Time;

public class Launcher extends AppCompatActivity {

    private Long splashtime=3000L;
    private Handler myhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        myhandler=new Handler();
        myhandler.postDelayed(new Runnable () {
            public void run() {
                gohome();
            }
        }, 3000);
    }

    private void gohome()
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
