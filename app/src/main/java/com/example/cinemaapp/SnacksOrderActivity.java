package com.example.cinemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SnacksOrderActivity extends AppCompatActivity {

    private EditText edtCombo, edtDrink, edtPopcorn;
    private TextView tvTotalPrice;

    // Biến để lưu thông tin từ SeatListActivity
    private String id, name, description, director, actors, genres, poster, trailer, releaseDate, category;
    private ArrayList<String> selectedSeatsList;
    private int numberOfSeats, ticketPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks_order);

        // Nhận thông tin từ SeatListActivity hoặc PaymentActivity
        Intent intentFromSeatList = getIntent();
        id = intentFromSeatList.getStringExtra("id");
        name = intentFromSeatList.getStringExtra("name");
        description = intentFromSeatList.getStringExtra("description");
        director = intentFromSeatList.getStringExtra("director");
        actors = intentFromSeatList.getStringExtra("actors");
        genres = intentFromSeatList.getStringExtra("genres");
        poster = intentFromSeatList.getStringExtra("poster");
        trailer = intentFromSeatList.getStringExtra("trailer");
        releaseDate = intentFromSeatList.getStringExtra("releaseDate");
        category = intentFromSeatList.getStringExtra("category");
        selectedSeatsList = intentFromSeatList.getStringArrayListExtra("selectedSeats");
        numberOfSeats = intentFromSeatList.getIntExtra("numberOfSeats", 0);
        ticketPrice = intentFromSeatList.getIntExtra("totalPrice", 0);

        // Thêm log để kiểm tra dữ liệu nhận được
        Log.d("SnacksOrderActivity", "Movie Name: " + name);
        Log.d("SnacksOrderActivity", "Poster: " + poster);
        Log.d("SnacksOrderActivity", "Selected Seats: " + (selectedSeatsList != null ? selectedSeatsList.toString() : "null"));

        // Xử lý sự kiện khi nhấn nút quay lại
        ImageView backBtn = findViewById(R.id.backBtn);
        if (backBtn != null) {
            backBtn.setOnClickListener(v -> {
                Intent intent = new Intent(SnacksOrderActivity.this, SeatListActivity.class);
                // Truyền tất cả thông tin về SeatListActivity
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("director", director);
                intent.putExtra("actors", actors);
                intent.putExtra("genres", genres);
                intent.putExtra("poster", poster); // Truyền poster
                intent.putExtra("trailer", trailer);
                intent.putExtra("releaseDate", releaseDate);
                intent.putExtra("category", category);
                intent.putStringArrayListExtra("selectedSeats", selectedSeatsList);
                intent.putExtra("numberOfSeats", numberOfSeats);
                intent.putExtra("totalPrice", ticketPrice);
                startActivity(intent);
                finish();
            });
        }

        // Ánh xạ ID
        edtCombo = findViewById(R.id.edt_combo);
        edtDrink = findViewById(R.id.edt_drink);
        edtPopcorn = findViewById(R.id.edt_popcorn);
        tvTotalPrice = findViewById(R.id.tv_total_price);

        // Thêm sự kiện lắng nghe cho ô nhập số lượng
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTotalPrice();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Gán sự kiện cho các ô nhập số lượng
        edtCombo.addTextChangedListener(textWatcher);
        edtDrink.addTextChangedListener(textWatcher);
        edtPopcorn.addTextChangedListener(textWatcher);

        // Thêm sự kiện cho nút "Tiếp tục"
        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tính tổng giá đồ ăn
                int priceCombo = 100000;
                int priceDrink = 35000;
                int pricePopcorn = 50000;

                int quantityCombo = 0, quantityDrink = 0, quantityPopcorn = 0;
                try {
                    String comboText = edtCombo.getText().toString();
                    String drinkText = edtDrink.getText().toString();
                    String popcornText = edtPopcorn.getText().toString();

                    quantityCombo = comboText.isEmpty() ? 0 : Integer.parseInt(comboText);
                    quantityDrink = drinkText.isEmpty() ? 0 : Integer.parseInt(drinkText);
                    quantityPopcorn = popcornText.isEmpty() ? 0 : Integer.parseInt(popcornText);
                } catch (NumberFormatException e) {
                    Log.e("SnacksOrderActivity", "Error parsing snack quantities: " + e.getMessage());
                }

                int snacksPrice = (quantityCombo * priceCombo) + (quantityDrink * priceDrink) + (quantityPopcorn * pricePopcorn);
                int finalTotalPrice = ticketPrice + snacksPrice;

                // Chuyển hướng sang PaymentActivity
                Intent intent = new Intent(SnacksOrderActivity.this, PaymentActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("director", director);
                intent.putExtra("actors", actors);
                intent.putExtra("genres", genres);
                intent.putExtra("poster", poster);
                intent.putExtra("trailer", trailer);
                intent.putExtra("releaseDate", releaseDate);
                intent.putExtra("category", category);
                intent.putStringArrayListExtra("selectedSeats", selectedSeatsList);
                intent.putExtra("numberOfSeats", numberOfSeats);
                intent.putExtra("totalPrice", finalTotalPrice);
                intent.putExtra("quantityCombo", quantityCombo);
                intent.putExtra("quantityDrink", quantityDrink);
                intent.putExtra("quantityPopcorn", quantityPopcorn);
                startActivity(intent);
                finish();
            }
        });

        // Cập nhật tổng giá ban đầu (chỉ có giá vé)
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        int priceCombo = 100000;
        int priceDrink = 35000;
        int pricePopcorn = 50000;

        try {
            String comboText = edtCombo.getText().toString();
            String drinkText = edtDrink.getText().toString();
            String popcornText = edtPopcorn.getText().toString();

            int quantityCombo = comboText.isEmpty() ? 0 : Integer.parseInt(comboText);
            int quantityDrink = drinkText.isEmpty() ? 0 : Integer.parseInt(drinkText);
            int quantityPopcorn = popcornText.isEmpty() ? 0 : Integer.parseInt(popcornText);

            int snacksPrice = (quantityCombo * priceCombo) + (quantityDrink * priceDrink) + (quantityPopcorn * pricePopcorn);
            int finalTotalPrice = ticketPrice + snacksPrice;
            tvTotalPrice.setText("Tổng: " + finalTotalPrice + " VND");
        } catch (NumberFormatException e) {
            tvTotalPrice.setText("Tổng: " + ticketPrice + " VND");
        }
    }
}