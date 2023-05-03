package com.example.shinigami;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;


class StartupPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hap);

        // Delay for 10 seconds (10000 milliseconds)
        int delayMillis = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartupPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, delayMillis);
    }
}
