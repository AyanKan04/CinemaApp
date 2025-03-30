package com.example.cinemaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {

    private List<String> times;
    private int selectedPosition = -1; // Để lưu vị trí đã chọn
    private OnItemClickListener listener; // Listener để thông báo giờ được chọn

    // Interface để thông báo sự kiện click
    public interface OnItemClickListener {
        void onItemClick(String time);
    }

    // Constructor cập nhật để nhận listener
    public TimeAdapter(List<String> times, OnItemClickListener listener) {
        this.times = times;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeViewHolder holder, int position) {
        String time = times.get(position);
        holder.timeTextView.setText(time);

        // Sử dụng holder.getAdapterPosition() thay vì position
        int currentPosition = holder.getAdapterPosition();

        // Kiểm tra nếu vị trí này được chọn hay chưa
        if (currentPosition == selectedPosition) {
            // Khi được chọn, dùng background của item_select_bg
            holder.itemView.setBackgroundResource(R.drawable.item_select_bg);
            holder.timeTextView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.black)); // Đổi màu chữ thành đen
        } else {
            // Khi không được chọn, giữ background từ XML (item_day_bg)
            holder.itemView.setBackgroundResource(R.drawable.item_day_bg);
            holder.timeTextView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white)); // Màu chữ trắng
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = currentPosition; // Cập nhật vị trí được chọn
            notifyDataSetChanged(); // Cập nhật lại RecyclerView để thay đổi nền và màu chữ
            if (listener != null) {
                listener.onItemClick(time); // Thông báo giờ được chọn cho SeatListActivity
            }
        });
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView timeTextView;

        public TimeViewHolder(View itemView) {
            super(itemView);
            timeTextView = itemView.findViewById(R.id.textViewTime);
        }
    }
}