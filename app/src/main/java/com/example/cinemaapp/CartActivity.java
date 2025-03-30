package com.example.cinemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.content.Context;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems; // Không còn static
    private Button btnCheckout;
    private ImageView backBtn;
    private CinemaDataQuery dataQuery; // Khai báo biến instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Ánh xạ view
        backBtn = findViewById(R.id.backBtn); // Thêm ánh xạ cho nút quay lại
        rvCartItems = findViewById(R.id.rv_cart_items);
        btnCheckout = findViewById(R.id.btn_checkout);

        // Khởi tạo CinemaDataQuery
        dataQuery = new CinemaDataQuery(this);
        cartItems = dataQuery.getCartItems(); // Lấy danh sách từ DB
        Log.d("CartActivity", "Loaded " + cartItems.size() + " items into cart");

        // Thiết lập RecyclerView
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartItems, this);

        rvCartItems.setAdapter(cartAdapter);

        // Sự kiện nút quay lại
        backBtn.setOnClickListener(v -> {
            // Kết thúc activity và quay lại màn hình trước đó
            finish();
        });
        // Xử lý sự kiện khi nhấn nút Thanh toán
        btnCheckout.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                checkoutCart();
            } else {
                Toast.makeText(this, "Giỏ hàng rỗng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại danh sách khi quay lại CartActivity
        cartItems = dataQuery.getCartItems();
        cartAdapter = new CartAdapter(cartItems, this);
        rvCartItems.setAdapter(cartAdapter);

        Log.d("CartActivity", "Refreshed with " + cartItems.size() + " items");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            long id = data.getLongExtra("cartItemId", -1); // Nhận ID thay vì position
            if (id != -1) {
                dataQuery.removeCartItem(id); // Xóa từ DB
                cartItems = dataQuery.getCartItems(); // Cập nhật danh sách
                cartAdapter = new CartAdapter(cartItems, this);
                rvCartItems.setAdapter(cartAdapter);

                Log.d("CartActivity", "Removed item with ID: " + id);
            }
        }
    }

    // Phương thức thanh toán và xóa các đơn hàng đã thanh toán
    private void checkoutCart() {
        Log.d("CartActivity", "Cart size before clear: " + cartItems.size());
        dataQuery.clearCart(); // Xóa tất cả từ DB
        cartItems.clear(); // Xóa danh sách trong bộ nhớ
        cartAdapter.notifyDataSetChanged();
        Log.d("CartActivity", "Cart size after clear: " + cartItems.size());
        Toast.makeText(this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
    }

    // Phương thức tĩnh để thêm CartItem vào cơ sở dữ liệu
    public static void addCartItem(Context context, CartItem cartItem) {
        if (cartItem != null) {
            CinemaDataQuery dataQuery = new CinemaDataQuery(context);
            long id = dataQuery.addCartItem(cartItem); // Thêm vào DB
            Log.d("CartActivity", "Added item to DB with ID: " + id);
        }
    }
}