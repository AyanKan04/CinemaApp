package com.example.cinemaapp;

public class Cinema {
    private int id;       // Thêm trường id
    private String name;

    // Constructor nhận cả id và name
    public Cinema(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor chỉ nhận name (nếu cần giữ lại)
    public Cinema(String name) {
        this.id = 0; // Giá trị mặc định cho id
        this.name = name;
    }

    // Getter cho id
    public int getId() {
        return id;
    }

    // Getter cho name
    public String getName() {
        return name;
    }

    // Setter (nếu cần)
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}