package com.example.cinemaapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder> {
    private List<Cinema> cinemaList;
    private OnItemClickListener listener;
    private int selectedPosition = -1; // Để lưu vị trí đã chọn
    private Context context; // Context để sử dụng tài nguyên

    // Constructor
    public CinemaAdapter(List<Cinema> cinemas, OnItemClickListener listener, Context context) {
        this.cinemaList = cinemas;
        this.listener = listener;
        this.context = context;
    }

    // Định nghĩa interface OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(Cinema cinema);
    }

    @Override
    public CinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout của item Cinema
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema, parent, false);
        return new CinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CinemaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (cinemaList == null || position >= cinemaList.size()) return;

        Cinema cinema = cinemaList.get(position);
        holder.bind(cinema);

        // Kiểm tra nếu vị trí này được chọn hay chưa
        if (position == selectedPosition) {
            // Khi được chọn, thay đổi màu nền
            holder.itemView.setBackgroundResource(R.drawable.item_select_bg);
            holder.cinemaNameTextView.setTextColor(context.getResources().getColor(R.color.black)); // Đổi màu chữ thành đen
        } else {
            // Khi không được chọn, giữ nền mặc định
            holder.itemView.setBackgroundResource(R.drawable.item_day_bg);
            holder.cinemaNameTextView.setTextColor(context.getResources().getColor(R.color.white)); // Màu chữ trắng
        }

        // Gắn sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            // Kiểm tra vị trí được chọn có thay đổi không
            if (selectedPosition != position) {
                int prevPosition = selectedPosition;
                selectedPosition = position;

                // Cập nhật lại hai item thay đổi (tối ưu hơn notifyDataSetChanged)
                notifyItemChanged(prevPosition);
                notifyItemChanged(selectedPosition);
            }

            // Gọi listener khi item được click
            if (listener != null) {
                listener.onItemClick(cinema);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (cinemaList != null) ? cinemaList.size() : 0;
    }

    // Phương thức cập nhật danh sách mới (nếu muốn dùng)
    public void updateCinemaList(List<Cinema> newCinemaList) {
        this.cinemaList = newCinemaList;
        notifyDataSetChanged(); // Hoặc dùng DiffUtil để tối ưu
    }

    public class CinemaViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các view trong item cinema
        TextView cinemaNameTextView;

        public CinemaViewHolder(View itemView) {
            super(itemView);
            cinemaNameTextView = itemView.findViewById(R.id.textViewCinema); // Ví dụ, ID của TextView
        }

        public void bind(Cinema cinema) {
            // Gắn dữ liệu vào các view
            cinemaNameTextView.setText(cinema.getName());
        }
    }
}
