package com.example.foregroundservicedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartService(View view) {
        Intent intent = new Intent(this, ForegroundPlayerService.class);
        intent.putExtra("MUSIC", R.raw.music);
        ContextCompat.startForegroundService(this, intent);

        Toast.makeText(this, "Playing", Toast.LENGTH_LONG).show();
    }

    public void onStopService(View view) {
        Intent intent = new Intent(this, ForegroundPlayerService.class);

        stopService(intent);
    }
}