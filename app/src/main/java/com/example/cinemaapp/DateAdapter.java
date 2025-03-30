package com.example.cinemaapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private List<DateItem> dates;
    private int selectedPosition = -1; // Để lưu vị trí đã chọn
    private OnItemClickListener listener; // Listener để thông báo ngày được chọn

    // Interface để thông báo sự kiện click
    public interface OnItemClickListener {
        void onItemClick(DateItem dateItem);
    }

    // Constructor cập nhật để nhận listener
    public DateAdapter(List<DateItem> dates, OnItemClickListener listener) {
        this.dates = dates;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        DateItem dateItem = dates.get(position);
        holder.weekdayTextView.setText(dateItem.getWeekday());
        holder.dateTextView.setText(dateItem.getDate());

        // Sử dụng holder.getAdapterPosition() thay vì position
        int currentPosition = holder.getAdapterPosition();

        // Kiểm tra nếu vị trí này được chọn hay chưa
        if (currentPosition != selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.item_day_bg);
            holder.weekdayTextView.setTextColor(Color.WHITE); // Màu chữ trắng
            holder.dateTextView.setTextColor(Color.WHITE); // Màu chữ trắng
        } else {
            holder.itemView.setBackgroundResource(R.drawable.item_select_bg); // Đổi nền thành trắng
            holder.weekdayTextView.setTextColor(Color.BLACK); // Đổi màu chữ thành đen
            holder.dateTextView.setTextColor(Color.BLACK); // Đổi màu chữ thành đen
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = currentPosition; // Cập nhật vị trí được chọn
            notifyDataSetChanged(); // Cập nhật lại RecyclerView để thay đổi nền và màu chữ
            if (listener != null) {
                listener.onItemClick(dateItem); // Thông báo ngày được chọn cho SeatListActivity
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView weekdayTextView;
        TextView dateTextView;

        public DateViewHolder(View itemView) {
            super(itemView);
            weekdayTextView = itemView.findViewById(R.id.dayTxt);
            dateTextView = itemView.findViewById(R.id.datMonthTxt);
        }
    }
}