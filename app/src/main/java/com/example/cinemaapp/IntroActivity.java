package com.example.cinemaapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.WindowInsetsController;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Thiết lập giao diện Edge-to-Edge
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsetsCompat.Type.statusBars()); // Ẩn thanh trạng thái
                controller.hide(WindowInsetsCompat.Type.navigationBars()); // Ẩn thanh điều hướng
            }
        }

        // Lắng nghe sự kiện click vào nút btnStart
        findViewById(R.id.btnStart).setOnClickListener(v -> {
            // Chuyển sang MainActivity khi nút btnStart được nhấn
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Đảm bảo không quay lại IntroActivity khi nhấn nút "Back"
        });

        // Áp dụng padding cho hệ thống thanh (system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
