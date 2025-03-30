package com.example.cinemaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.widget.Toast;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {
    private ImageView moviePic, backBtn;
    private TextView tvMovieName, tvDescription, tvDirector, tvActors, tvGenres, tvReleaseDate, tvCategory,
            tvSelectedSeats, tvNumberOfSeats, tvTotalPrice, tvSelectedSnacks;
    private Button btnPay, btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Ánh xạ các view
        moviePic = findViewById(R.id.moviePic);
        backBtn = findViewById(R.id.backBtn);
        tvMovieName = findViewById(R.id.tv_movie_name);
        tvDescription = findViewById(R.id.tv_description);
        tvDirector = findViewById(R.id.tv_director);
        tvActors = findViewById(R.id.tv_actors);
        tvGenres = findViewById(R.id.tv_genres);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvCategory = findViewById(R.id.tv_category);
        tvSelectedSeats = findViewById(R.id.tv_selected_seats);
        tvNumberOfSeats = findViewById(R.id.tv_number_of_seats);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        tvSelectedSnacks = findViewById(R.id.tv_selected_snacks);
        btnPay = findViewById(R.id.btn_pay);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String moviePoster = intent.getStringExtra("poster");
        String movieName = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String director = intent.getStringExtra("director");
        String actors = intent.getStringExtra("actors");
        String genres = intent.getStringExtra("genres");
        String releaseDate = intent.getStringExtra("releaseDate");
        String category = intent.getStringExtra("category");
        ArrayList<String> selectedSeats = intent.getStringArrayListExtra("selectedSeats");
        int numberOfSeats = intent.getIntExtra("numberOfSeats", 0);
        int totalPrice = intent.getIntExtra("totalPrice", 0);
        int position = intent.getIntExtra("position", -1); // Lấy position từ Intent

        // Nhận thông tin snack từ Intent
        int quantityCombo = intent.getIntExtra("quantityCombo", 0);
        int quantityDrink = intent.getIntExtra("quantityDrink", 0);
        int quantityPopcorn = intent.getIntExtra("quantityPopcorn", 0);

        // Thêm log để kiểm tra dữ liệu snack
        Log.d("PaymentActivity", "Quantity Combo: " + quantityCombo);
        Log.d("PaymentActivity", "Quantity Drink: " + quantityDrink);
        Log.d("PaymentActivity", "Quantity Popcorn: " + quantityPopcorn);

        // Debug giá trị poster
        Log.d("PaymentActivity", "Received poster: " + moviePoster);

        // Hiển thị ảnh phim từ assets
        if (moviePoster != null && !moviePoster.isEmpty()) {
            Bitmap bitmap = Utils.convertToBitmapFromAssets(this, moviePoster);
            if (bitmap != null) {
                moviePic.setImageBitmap(bitmap);
            } else {
                Log.w("PaymentActivity", "Failed to load bitmap from assets: " + moviePoster);
            }
        } else {
            Log.w("PaymentActivity", "Poster is null or empty");
        }

        // Hiển thị thông tin snack
        StringBuilder snacksText = new StringBuilder("Snack đã chọn: ");
        boolean hasSnacks = false;
        if (quantityCombo > 0) {
            snacksText.append(quantityCombo).append(" Combo");
            hasSnacks = true;
        }
        if (quantityDrink > 0) {
            if (hasSnacks) snacksText.append(", ");
            snacksText.append(quantityDrink).append(" Drink");
            hasSnacks = true;
        }
        if (quantityPopcorn > 0) {
            if (hasSnacks) snacksText.append(", ");
            snacksText.append(quantityPopcorn).append(" Popcorn");
            hasSnacks = true;
        }
        if (!hasSnacks) {
            snacksText.append("Không có");
        }
        if (tvSelectedSnacks != null) {
            tvSelectedSnacks.setText(snacksText.toString());
        } else {
            Log.e("PaymentActivity", "tvSelectedSnacks is null");
        }

        // Hiển thị thông tin phim và thanh toán
        tvMovieName.setText("Phim: " + (movieName != null ? movieName : "N/A"));
        tvDescription.setText("Mô tả: " + (description != null ? description : "N/A"));
        tvDirector.setText("Đạo diễn: " + (director != null ? director : "N/A"));
        tvActors.setText("Diễn viên: " + (actors != null ? actors : "N/A"));
        tvGenres.setText("Thể loại: " + (genres != null ? genres : "N/A"));
        tvReleaseDate.setText("Ngày phát hành: " + (releaseDate != null ? releaseDate : "N/A"));
        tvCategory.setText("Danh mục: " + (category != null ? category : "N/A"));
        tvSelectedSeats.setText("Ghế đã chọn: " + (selectedSeats != null && !selectedSeats.isEmpty() ? String.join(", ", selectedSeats) : "N/A"));
        tvNumberOfSeats.setText("Số lượng ghế: " + numberOfSeats);
        tvTotalPrice.setText("Tổng giá: " + String.format("%,d VNĐ", totalPrice));

        // Gắn sự kiện cho nút quay lại
        if (backBtn != null) {
            backBtn.setOnClickListener(v -> {
                Log.d("PaymentActivity", "Back button clicked");
                Intent backIntent = new Intent(PaymentActivity.this, SnacksOrderActivity.class);
                backIntent.putExtra("id", intent.getStringExtra("id"));
                backIntent.putExtra("name", movieName);
                backIntent.putExtra("description", description);
                backIntent.putExtra("director", director);
                backIntent.putExtra("actors", actors);
                backIntent.putExtra("genres", genres);
                backIntent.putExtra("poster", moviePoster);
                backIntent.putExtra("trailer", intent.getStringExtra("trailer"));
                backIntent.putExtra("releaseDate", releaseDate);
                backIntent.putExtra("category", category);
                backIntent.putStringArrayListExtra("selectedSeats", selectedSeats);
                backIntent.putExtra("numberOfSeats", numberOfSeats);
                backIntent.putExtra("totalPrice", totalPrice);
                backIntent.putExtra("quantityCombo", quantityCombo);
                backIntent.putExtra("quantityDrink", quantityDrink);
                backIntent.putExtra("quantityPopcorn", quantityPopcorn);
                startActivity(backIntent);
                finish();
            });
        }

        // Sự kiện nút "Thanh toán"
        btnPay.setOnClickListener(v -> {
            Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();

            // Chuyển sang SuccessActivity
            Intent successIntent = new Intent(PaymentActivity.this, SuccessActivity.class);
            startActivity(successIntent);


            // Trả kết quả về CartActivity
            Intent resultIntent = new Intent(); // Sử dụng tên biến khác để tránh xung đột
            resultIntent.putExtra("position", position); // Gửi lại vị trí của mục
            setResult(RESULT_OK, resultIntent);
            finish();
        });


        // Sự kiện nút "Thêm vào Giỏ hàng"
        btnAddToCart.setOnClickListener(v -> {
            // Tạo đối tượng CartItem
            CartItem cartItem = new CartItem(
                    movieName,
                    description,
                    director,
                    actors,
                    genres,
                    releaseDate,
                    category,
                    selectedSeats,
                    numberOfSeats,
                    quantityCombo,
                    quantityDrink,
                    quantityPopcorn,
                    totalPrice,
                    moviePoster
            );

            // Gọi trực tiếp CinemaDataQuery
            CinemaDataQuery dataQuery = new CinemaDataQuery(this);
            dataQuery.addCartItem(cartItem);
            Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
        });

    }
}