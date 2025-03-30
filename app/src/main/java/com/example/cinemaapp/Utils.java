package com.example.cinemaapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
public class Utils {

    public static final String DATABASE_NAME = "CinemaDB";

    // Tên bảng và các cột trong cơ sở dữ liệu
    //Movie
    public static final String TABLE_MOVIE = "Movie";
    public static final String COLUMN_MOVIE_ID = "id";
    public static final String COLUMN_MOVIE_NAME = "name";
    public static final String COLUMN_MOVIE_DESCRIPTION = "description";
    public static final String COLUMN_MOVIE_DIRECTOR = "director";
    public static final String COLUMN_MOVIE_ACTORS = "actors";
    public static final String COLUMN_MOVIE_GENRES = "genres";
    public static final String COLUMN_MOVIE_POSTER = "poster";
    public static final String COLUMN_MOVIE_TRAILER = "trailer";
    public static final String COLUMN_MOVIE_RELEASEDATE = "releaseDate";
    public static final String COLUMN_MOVIE_CATEGORY = "category";

    //Banner
    public static final String TABLE_BANNER = "Banner";
    public static final String COLUMN_BANNER_ID = "id";
    public static final String COLUMN_BANNER_NAME = "name";

    //TopMovies
    public static final String TABLE_TOP_MOVIES = "TopMovies";
    public static final String COLUMN_TOP_MOVIES_ID = "id";
    public static final String COLUMN_TOP_MOVIES_NAME = "name";

    //UpcomingMovies
    public static final String TABLE_UPCOMING_MOVIES = "UpcomingMovies";
    public static final String COLUMN_UPCOMING_MOVIES_ID = "id";
    public static final String COLUMN_UPCOMING_MOVIES_NAME = "name";

    //User
    public static final String TABLE_USER = "User";
    //Admin
    public static final String TABLE_ADMIN = "Admin";
    //History
    public static final String TABLE_HISTORY = "History";
    //Cart
    public static final String TABLE_CART = "Cart";
    //Order
    public static final String TABLE_ORDER = "Order";
    //Ticket
    public static final String TABLE_TICKET = "Ticket";
    //Catory
    public static final String TABLE_CATEGORY = "Category";


    // Phương thức chuyển đổi tên ảnh thành Bitmap từ thư mục assets/images
    public static Bitmap convertToBitmapFromAssets(Context context, String imageName) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;

        try {
            // Mở file ảnh trong thư mục assets/images/
            inputStream = assetManager.open("images/" + imageName);
            // Giải mã ảnh từ InputStream thành Bitmap
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace(); // In ra lỗi nếu không tìm thấy ảnh
        } finally {
            // Đảm bảo đóng InputStream để giải phóng tài nguyên
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Nếu có lỗi, trả về null
        return null;
    }
}
