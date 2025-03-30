package com.example.cinemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Button btnHome = findViewById(R.id.btn_home);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng activity hiện tại
        });
    }
}
