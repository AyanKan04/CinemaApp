package com.example.cinemaapp;

public class Seat {

    private String seatNumber;
    private boolean isSelected;
    private boolean isUnavailable;

    public Seat(String seatNumber, boolean isUnavailable) {
        this.seatNumber = seatNumber;
        this.isUnavailable = isUnavailable;
        this.isSelected = false; // Mặc định không chọn
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isUnavailable() {
        return isUnavailable;
    }
}

