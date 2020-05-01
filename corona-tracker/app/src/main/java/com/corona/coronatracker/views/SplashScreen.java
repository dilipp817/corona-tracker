package com.corona.coronatracker.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.corona.coronatracker.R;

import static com.corona.coronatracker.views.MainActivity.COUNTRY;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startCounter();
    }

    private void startCounter() {
        new Handler().postDelayed(() -> {
            startActivity(MainActivity.getInstance(this, COUNTRY, null));
//            finish();

        }, 3*1000); // wait for 5 seconds
    }
}
