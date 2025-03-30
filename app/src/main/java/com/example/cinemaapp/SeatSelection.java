package com.example.cinemaapp;

import java.util.ArrayList;
import java.util.List;

public class SeatSelection {
    private static final int SEAT_PRICE = 90000; // Giá mỗi ghế
    private List<Seat> selectedSeats;  // Danh sách ghế đã chọn

    public SeatSelection() {
        selectedSeats = new ArrayList<>();
    }

    // Thêm ghế vào danh sách ghế đã chọn
    public void selectSeat(Seat seat) {
        if (seat != null && !seat.isUnavailable() && !selectedSeats.contains(seat)) {
            selectedSeats.add(seat);
        }
    }

    // Loại bỏ ghế khỏi danh sách đã chọn
    public void deselectSeat(Seat seat) {
        if (seat != null && selectedSeats.contains(seat)) {
            selectedSeats.remove(seat);
        }
    }

    // Tính tổng tiền đã chọn
    public int getTotalPrice() {
        return selectedSeats.size() * SEAT_PRICE;
    }

    // Lấy danh sách tên ghế đã chọn
    public String getSelectedSeatNames() {
        List<String> seatNames = new ArrayList<>();
        for (Seat seat : selectedSeats) {
            seatNames.add(seat.getSeatNumber());
        }
        return String.join(",", seatNames); // Dùng String.join để nối các tên ghế
    }

    // Lấy số lượng ghế đã chọn
    public int getSelectedSeatsCount() {
        return selectedSeats.size();
    }
}
