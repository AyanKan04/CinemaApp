package com.example.cinemaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class CinemaDataQuery {

    private CinemaDBHelper dbHelper;

    public CinemaDataQuery(Context context) {
        dbHelper = new CinemaDBHelper(context);
    }

    // --- Quản lý bảng Movies (Phim) ---
    public static long insert(Context context, Movie movie) {
        CinemaDBHelper dbHelper = new CinemaDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_MOVIE_NAME, movie.getName());
        values.put(Utils.COLUMN_MOVIE_DESCRIPTION, movie.getDescription());
        values.put(Utils.COLUMN_MOVIE_DIRECTOR, movie.getDirector());
        values.put(Utils.COLUMN_MOVIE_ACTORS, movie.getActors());
        values.put(Utils.COLUMN_MOVIE_GENRES, movie.getGenres());
        values.put(Utils.COLUMN_MOVIE_POSTER, movie.getPoster());
        values.put(Utils.COLUMN_MOVIE_TRAILER, movie.getTrailer());
        values.put(Utils.COLUMN_MOVIE_RELEASEDATE, movie.getReleaseDate());
        values.put(Utils.COLUMN_MOVIE_CATEGORY, movie.getCategory());

        long result = db.insert(Utils.TABLE_MOVIE, null, values);
        db.close();
        return result;
    }

    public static ArrayList<Movie> getAll(Context context) {
        ArrayList<Movie> movieList = new ArrayList<>();
        CinemaDBHelper dbHelper = new CinemaDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utils.TABLE_MOVIE, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String director = cursor.getString(3);
                String actors = cursor.getString(4);
                String genres = cursor.getString(5);
                String poster = cursor.getString(6);
                String trailer = cursor.getString(7);
                String releaseDate = cursor.getString(8);
                String category = cursor.getString(9);

                movieList.add(new Movie(String.valueOf(id), name, description, director, actors, genres, poster, trailer, releaseDate, category));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movieList;
    }

    public static boolean delete(Context context, int id) {
        CinemaDBHelper dbHelper = new CinemaDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int rs = db.delete(Utils.TABLE_MOVIE, Utils.COLUMN_MOVIE_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rs > 0;
    }

    public static int update(Context context, Movie movie) {
        CinemaDBHelper dbHelper = new CinemaDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utils.COLUMN_MOVIE_NAME, movie.getName());
        values.put(Utils.COLUMN_MOVIE_DESCRIPTION, movie.getDescription());
        values.put(Utils.COLUMN_MOVIE_DIRECTOR, movie.getDirector());
        values.put(Utils.COLUMN_MOVIE_ACTORS, movie.getActors());
        values.put(Utils.COLUMN_MOVIE_GENRES, movie.getGenres());
        values.put(Utils.COLUMN_MOVIE_POSTER, movie.getPoster());
        values.put(Utils.COLUMN_MOVIE_TRAILER, movie.getTrailer());
        values.put(Utils.COLUMN_MOVIE_RELEASEDATE, movie.getReleaseDate());
        values.put(Utils.COLUMN_MOVIE_CATEGORY, movie.getCategory());

        int rs = db.update(Utils.TABLE_MOVIE, values, Utils.COLUMN_MOVIE_ID + "=?", new String[]{String.valueOf(movie.getId())});
        db.close();
        return rs;
    }

    // --- Quản lý bảng Cart (Giỏ hàng) ---
    public long addCartItem(CartItem cartItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CinemaDBHelper.COLUMN_MOVIE_NAME, cartItem.getMovieName());
        values.put(CinemaDBHelper.COLUMN_DESCRIPTION, cartItem.getDescription());
        values.put(CinemaDBHelper.COLUMN_DIRECTOR, cartItem.getDirector());
        values.put(CinemaDBHelper.COLUMN_ACTORS, cartItem.getActors());
        values.put(CinemaDBHelper.COLUMN_GENRES, cartItem.getGenres());
        values.put(CinemaDBHelper.COLUMN_RELEASE_DATE, cartItem.getReleaseDate());
        values.put(CinemaDBHelper.COLUMN_CATEGORY, cartItem.getCategory());
        values.put(CinemaDBHelper.COLUMN_SELECTED_SEATS, String.join(",", cartItem.getSelectedSeats()));
        values.put(CinemaDBHelper.COLUMN_NUMBER_OF_SEATS, cartItem.getNumberOfSeats());
        values.put(CinemaDBHelper.COLUMN_QUANTITY_COMBO, cartItem.getQuantityCombo());
        values.put(CinemaDBHelper.COLUMN_QUANTITY_DRINK, cartItem.getQuantityDrink());
        values.put(CinemaDBHelper.COLUMN_QUANTITY_POPCORN, cartItem.getQuantityPopcorn());
        values.put(CinemaDBHelper.COLUMN_TOTAL_PRICE, cartItem.getTotalPrice());
        values.put(CinemaDBHelper.COLUMN_MOVIE_POSTER, cartItem.getMoviePoster());

        long id = db.insert(CinemaDBHelper.TABLE_CART, null, values);
        Log.d("CinemaDataQuery", "Added CartItem with ID: " + id); // Log ID của mục vừa thêm
        db.close();
        return id;
    }

    public List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CinemaDBHelper.TABLE_CART, null);

        if (cursor.moveToFirst()) {
            do {
                String movieName = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_MOVIE_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_DESCRIPTION));
                String director = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_DIRECTOR));
                String actors = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_ACTORS));
                String genres = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_GENRES));
                String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_RELEASE_DATE));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_CATEGORY));
                String selectedSeatsStr = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_SELECTED_SEATS));
                ArrayList<String> selectedSeats = new ArrayList<>();
                if (selectedSeatsStr != null && !selectedSeatsStr.isEmpty()) {
                    String[] seatsArray = selectedSeatsStr.split(",");
                    for (String seat : seatsArray) {
                        selectedSeats.add(seat.trim());
                    }
                }
                int numberOfSeats = cursor.getInt(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_NUMBER_OF_SEATS));
                int quantityCombo = cursor.getInt(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_QUANTITY_COMBO));
                int quantityDrink = cursor.getInt(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_QUANTITY_DRINK));
                int quantityPopcorn = cursor.getInt(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_QUANTITY_POPCORN));
                int totalPrice = cursor.getInt(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_TOTAL_PRICE));
                String moviePoster = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_MOVIE_POSTER));

                CartItem cartItem = new CartItem(movieName, description, director, actors, genres, releaseDate,
                        category, selectedSeats, numberOfSeats, quantityCombo, quantityDrink, quantityPopcorn,
                        totalPrice, moviePoster);
                cartItems.add(cartItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartItems;
    }

    // Xóa item khỏi giỏ hàng
    public void removeCartItem(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(CinemaDBHelper.TABLE_CART, CinemaDBHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }


    // Xóa toàn bộ giỏ hàng
    public void clearCart() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(CinemaDBHelper.TABLE_CART, null, null);
        db.close();
    }


    // --- Quản lý bảng Cinemas (Rạp chiếu phim) ---
    public long addCinema(Cinema cinema) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CinemaDBHelper.COLUMN_CINEMA_NAME, cinema.getName());
        long id = db.insert(CinemaDBHelper.TABLE_CINEMAS, null, values);
        db.close();
        return id;
    }

    public List<Cinema> getCinemas() {
        List<Cinema> cinemas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CinemaDBHelper.TABLE_CINEMAS, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_CINEMA_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CinemaDBHelper.COLUMN_CINEMA_NAME));
                cinemas.add(new Cinema(id, name)); // Dùng constructor mới
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cinemas;
    }
}