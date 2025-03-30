package com.example.cinemaapp;

public class DateItem {
    private String weekday; // T2, T3, T4, ...
    private String date; // Ngày/Tháng, ví dụ: 10/03

    public DateItem(String weekday, String date) {
        this.weekday = weekday;
        this.date = date;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
