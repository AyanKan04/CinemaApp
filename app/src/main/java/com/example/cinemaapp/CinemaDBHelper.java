package com.example.cinemaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CinemaDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CinemaDB.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng Movies (từ Utils)
    private static final String CREATE_TABLE_MOVIE = "CREATE TABLE " + Utils.TABLE_MOVIE + " (" +
            Utils.COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Utils.COLUMN_MOVIE_NAME + " TEXT, " +
            Utils.COLUMN_MOVIE_DESCRIPTION + " TEXT, " +
            Utils.COLUMN_MOVIE_DIRECTOR + " TEXT, " +
            Utils.COLUMN_MOVIE_ACTORS + " TEXT, " +
            Utils.COLUMN_MOVIE_GENRES + " TEXT, " +
            Utils.COLUMN_MOVIE_POSTER + " TEXT, " +
            Utils.COLUMN_MOVIE_TRAILER + " TEXT, " +
            Utils.COLUMN_MOVIE_RELEASEDATE + " TEXT, " +
            Utils.COLUMN_MOVIE_CATEGORY + " TEXT)";

    // Bảng Cart
    public static final String TABLE_CART = "cart";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MOVIE_NAME = "movie_name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DIRECTOR = "director";
    public static final String COLUMN_ACTORS = "actors";
    public static final String COLUMN_GENRES = "genres";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_SELECTED_SEATS = "selected_seats";
    public static final String COLUMN_NUMBER_OF_SEATS = "number_of_seats";
    public static final String COLUMN_QUANTITY_COMBO = "quantity_combo";
    public static final String COLUMN_QUANTITY_DRINK = "quantity_drink";
    public static final String COLUMN_QUANTITY_POPCORN = "quantity_popcorn";
    public static final String COLUMN_TOTAL_PRICE = "total_price";
    public static final String COLUMN_MOVIE_POSTER = "movie_poster";

    private static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MOVIE_NAME + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_DIRECTOR + " TEXT, " +
            COLUMN_ACTORS + " TEXT, " +
            COLUMN_GENRES + " TEXT, " +
            COLUMN_RELEASE_DATE + " TEXT, " +
            COLUMN_CATEGORY + " TEXT, " +
            COLUMN_SELECTED_SEATS + " TEXT, " +
            COLUMN_NUMBER_OF_SEATS + " INTEGER, " +
            COLUMN_QUANTITY_COMBO + " INTEGER, " +
            COLUMN_QUANTITY_DRINK + " INTEGER, " +
            COLUMN_QUANTITY_POPCORN + " INTEGER, " +
            COLUMN_TOTAL_PRICE + " INTEGER, " +
            COLUMN_MOVIE_POSTER + " TEXT)";

    // Bảng Cinemas
    public static final String TABLE_CINEMAS = "cinemas";
    public static final String COLUMN_CINEMA_ID = "cinema_id";
    public static final String COLUMN_CINEMA_NAME = "cinema_name";

    private static final String CREATE_TABLE_CINEMAS = "CREATE TABLE " + TABLE_CINEMAS + " (" +
            COLUMN_CINEMA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CINEMA_NAME + " TEXT)";

    public CinemaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIE);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_CINEMAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CINEMAS);
        onCreate(db);
    }
    public void deleteCartItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("CartTable", "id = ?", new String[]{String.valueOf(id)});
        db.close();

    }



}