package com.example.cinemaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private Context context;
    private boolean isInCartView; // Biến xác định trạng thái hiện tại


    public CartAdapter(List<CartItem> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
        this.isInCartView = isInCartView; // Gán giá trị

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);


        // Hiển thị poster phim
        String moviePoster = cartItem.getMoviePoster();
        if (moviePoster != null && !moviePoster.isEmpty()) {
            Bitmap bitmap = Utils.convertToBitmapFromAssets(holder.itemView.getContext(), moviePoster);
            if (bitmap != null) {
                holder.ivMoviePoster.setImageBitmap(bitmap);
            } else {
                holder.ivMoviePoster.setImageResource(android.R.drawable.ic_menu_gallery); // Hình ảnh mặc định
            }
        } else {
            holder.ivMoviePoster.setImageResource(android.R.drawable.ic_menu_gallery); // Hình ảnh mặc định
        }

        // Hiển thị thông tin phim
        holder.tvMovieName.setText("Phim: " + (cartItem.getMovieName() != null ? cartItem.getMovieName() : "N/A"));

        // Hiển thị ghế đã chọn
        StringBuilder seatsText = new StringBuilder();
        if (cartItem.getSelectedSeats() != null && !cartItem.getSelectedSeats().isEmpty()) {
            for (int i = 0; i < cartItem.getSelectedSeats().size(); i++) {
                seatsText.append(cartItem.getSelectedSeats().get(i));
                if (i < cartItem.getSelectedSeats().size() - 1) {
                    seatsText.append(", ");
                }
            }
        } else {
            seatsText.append("N/A");
        }
        holder.tvSelectedSeats.setText("Ghế đã chọn: " + seatsText);

        // Hiển thị thông tin snack
        StringBuilder snacksText = new StringBuilder("Snack đã chọn: ");
        boolean hasSnacks = false;
        if (cartItem.getQuantityCombo() > 0) {
            snacksText.append(cartItem.getQuantityCombo()).append(" Combo");
            hasSnacks = true;
        }
        if (cartItem.getQuantityDrink() > 0) {
            if (hasSnacks) snacksText.append(", ");
            snacksText.append(cartItem.getQuantityDrink()).append(" Drink");
            hasSnacks = true;
        }
        if (cartItem.getQuantityPopcorn() > 0) {
            if (hasSnacks) snacksText.append(", ");
            snacksText.append(cartItem.getQuantityPopcorn()).append(" Popcorn");
            hasSnacks = true;
        }
        if (!hasSnacks) {
            snacksText.append("Không có");
        }
        holder.tvSelectedSnacks.setText(snacksText);

        // Hiển thị tổng giá
        holder.tvTotalPrice.setText("Tổng giá: " + String.format("%,d VNĐ", cartItem.getTotalPrice()));

        // Xử lý sự kiện bấm nút "Xóa"
        holder.btnDelete.setOnClickListener(v -> {
            cartItems.remove(position); // Xóa mục khỏi danh sách
            notifyItemRemoved(position); // Cập nhật giao diện
            notifyItemRangeChanged(position, cartItems.size()); // Cập nhật các vị trí còn lại
        });

        // Xử lý sự kiện bấm vào mục để mở PaymentActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), PaymentActivity.class);
            intent.putExtra("name", cartItem.getMovieName());
            intent.putExtra("description", cartItem.getDescription());
            intent.putExtra("director", cartItem.getDirector());
            intent.putExtra("actors", cartItem.getActors());
            intent.putExtra("genres", cartItem.getGenres());
            intent.putExtra("releaseDate", cartItem.getReleaseDate());
            intent.putExtra("category", cartItem.getCategory());
            intent.putExtra("selectedSeats", cartItem.getSelectedSeats());
            intent.putExtra("numberOfSeats", cartItem.getNumberOfSeats());
            intent.putExtra("quantityCombo", cartItem.getQuantityCombo());
            intent.putExtra("quantityDrink", cartItem.getQuantityDrink());
            intent.putExtra("quantityPopcorn", cartItem.getQuantityPopcorn());
            intent.putExtra("totalPrice", cartItem.getTotalPrice());
            intent.putExtra("poster", cartItem.getMoviePoster());
            intent.putExtra("position", position); // Thêm vị trí của mục trong danh sách

            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMoviePoster;
        TextView tvMovieName, tvSelectedSeats, tvSelectedSnacks, tvTotalPrice;
        ImageButton btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMoviePoster = itemView.findViewById(R.id.iv_cart_movie_poster);
            tvMovieName = itemView.findViewById(R.id.tv_cart_movie_name);
            tvSelectedSeats = itemView.findViewById(R.id.tv_cart_selected_seats);
            tvSelectedSnacks = itemView.findViewById(R.id.tv_cart_selected_snacks);
            tvTotalPrice = itemView.findViewById(R.id.tv_cart_total_price);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
