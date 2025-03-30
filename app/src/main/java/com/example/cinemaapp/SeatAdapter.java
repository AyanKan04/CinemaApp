package com.example.cinemaapp;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private List<Seat> seats;
    private OnSeatSelectedListener seatSelectedListener;

    // Interface để xử lý sự kiện chọn ghế
    public interface OnSeatSelectedListener {
        void onSeatSelected(Seat seat);
        void onSeatDeselected(Seat seat);
    }

    // Constructor có thêm tham số listener
    public SeatAdapter(List<Seat> seats, OnSeatSelectedListener seatSelectedListener) {
        this.seats = seats;
        this.seatSelectedListener = seatSelectedListener;
    }

    @Override
    public SeatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflating layout seat_item.xml cho mỗi item ghế
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seat_item, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SeatViewHolder holder, int position) {
        Seat seat = seats.get(position);
        holder.seatTextView.setText(seat.getSeatNumber());

        // Đặt nền cho ghế tùy theo trạng thái
        if (seat.isSelected()) {
            holder.seatTextView.setBackgroundResource(R.drawable.ic_seat_selected); // Ghế đã chọn
        } else if (seat.isUnavailable()) {
            holder.seatTextView.setBackgroundResource(R.drawable.ic_seat_unavailable); // Ghế đã đặt
        } else {
            holder.seatTextView.setBackgroundResource(R.drawable.ic_seat_available); // Ghế có sẵn
        }

        // Đặt màu chữ là trắng cho tất cả ghế
        holder.seatTextView.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.white));

        // Canh giữa chữ trong TextView
        holder.seatTextView.setGravity(Gravity.CENTER);  // Canh giữa theo chiều ngang và dọc

        // Sự kiện click chọn ghế
        holder.itemView.setOnClickListener(v -> {
            // Kiểm tra số ghế đã chọn và không cho phép chọn thêm nếu đã đạt 7 ghế
            int selectedSeatCount = getSelectedSeatCount();

            if (!seat.isUnavailable()) { // Không cho phép chọn ghế đã đặt
                if (seat.isSelected()) {
                    // Nếu ghế đã được chọn, cho phép bỏ chọn
                    seat.setSelected(false);
                    notifyItemChanged(position); // Chỉ cập nhật item tại vị trí này
                    seatSelectedListener.onSeatDeselected(seat);
                } else if (selectedSeatCount < 7) {
                    // Nếu ghế chưa được chọn và chưa đạt tối đa, cho phép chọn
                    seat.setSelected(true);
                    notifyItemChanged(position); // Cập nhật lại trạng thái ghế đã chọn
                    seatSelectedListener.onSeatSelected(seat);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seats.size();
    }

    // Hàm tính toán số ghế đã chọn
    private int getSelectedSeatCount() {
        int count = 0;
        for (Seat seat : seats) {
            if (seat.isSelected()) {
                count++;
            }
        }
        return count;
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        TextView seatTextView;

        public SeatViewHolder(View itemView) {
            super(itemView);
            seatTextView = itemView.findViewById(R.id.seat);
        }
    }
}
