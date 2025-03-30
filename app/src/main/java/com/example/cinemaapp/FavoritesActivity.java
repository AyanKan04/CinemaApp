package com.example.cinemaapp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FavoritesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites); // Tạo layout cho màn hình yêu thích
        // Ánh xạ ImageView và thiết lập sự kiện click cho nút back
        ImageView backBtn = findViewById(R.id.backBtn);  // Ánh xạ button
        backBtn.setOnClickListener(v -> onBackPressed());
    }
}