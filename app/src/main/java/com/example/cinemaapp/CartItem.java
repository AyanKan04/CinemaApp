package com.example.cinemaapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class CartItem implements Serializable {
    private String id; // ID duy nhất để định danh CartItem
    private String movieName;
    private String description;
    private String director;
    private String actors;
    private String genres;
    private String releaseDate;
    private String category;
    private ArrayList<String> selectedSeats;
    private int numberOfSeats;
    private int quantityCombo;
    private int quantityDrink;
    private int quantityPopcorn;
    private int totalPrice;
    private String moviePoster; // Đường dẫn poster phim

    // Constructor
    public CartItem(String movieName, String description, String director, String actors, String genres,
                    String releaseDate, String category, ArrayList<String> selectedSeats, int numberOfSeats,
                    int quantityCombo, int quantityDrink, int quantityPopcorn, int totalPrice, String moviePoster) {
        this.id = UUID.randomUUID().toString(); // Tạo ID duy nhất
        this.movieName = movieName;
        this.description = description;
        this.director = director;
        this.actors = actors;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.category = category;
        this.selectedSeats = selectedSeats;
        this.numberOfSeats = numberOfSeats;
        this.quantityCombo = quantityCombo;
        this.quantityDrink = quantityDrink;
        this.quantityPopcorn = quantityPopcorn;
        this.totalPrice = totalPrice;
        this.moviePoster = moviePoster;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getGenres() {
        return genres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getSelectedSeats() {
        return selectedSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public int getQuantityCombo() {
        return quantityCombo;
    }

    public int getQuantityDrink() {
        return quantityDrink;
    }

    public int getQuantityPopcorn() {
        return quantityPopcorn;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    // Setters (nếu cần cập nhật dữ liệu sau khi tạo)
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSelectedSeats(ArrayList<String> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setQuantityCombo(int quantityCombo) {
        this.quantityCombo = quantityCombo;
    }

    public void setQuantityDrink(int quantityDrink) {
        this.quantityDrink = quantityDrink;
    }

    public void setQuantityPopcorn(int quantityPopcorn) {
        this.quantityPopcorn = quantityPopcorn;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id='" + id + '\'' +
                ", movieName='" + movieName + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
