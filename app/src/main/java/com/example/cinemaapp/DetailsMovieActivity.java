package com.example.cinemaapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailsMovieActivity extends AppCompatActivity {

    private TextView tvTitle, tvSummary, tvDirector, tvReleaseDate;
    private ImageView ivMoviePoster;
    private RecyclerView recyclerViewGenres, recyclerViewCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        // Ánh xạ các view
        tvTitle = findViewById(R.id.titleTxt);
        tvSummary = findViewById(R.id.summaryTxt);
        tvDirector = findViewById(R.id.directorTxt);
        tvReleaseDate = findViewById(R.id.movieTime);
        ivMoviePoster = findViewById(R.id.moviePic);

        // RecyclerView cho genres và cast
        recyclerViewGenres = findViewById(R.id.genreView);
        recyclerViewCast = findViewById(R.id.castListView);

        // Lấy dữ liệu từ Intent
        String id = getIntent().getStringExtra("movieId");
        String name = getIntent().getStringExtra("movieName");
        String description = getIntent().getStringExtra("movieDescription");
        String director = getIntent().getStringExtra("movieDirector");
        String actors = getIntent().getStringExtra("movieActors");
        String genres = getIntent().getStringExtra("movieGenres");
        String releaseDate = getIntent().getStringExtra("movieReleaseDate");
        String poster = getIntent().getStringExtra("moviePoster");

        if (name != null) {
            // Hiển thị thông tin phim lên màn hình
            tvTitle.setText(name);
            tvSummary.setText(description);
            tvDirector.setText(director);
            tvReleaseDate.setText(releaseDate);

            // Chuyển đổi hình ảnh từ assets và hiển thị
            Bitmap bitmap = Utils.convertToBitmapFromAssets(this, poster);
            if (bitmap != null) {
                ivMoviePoster.setImageBitmap(bitmap);
            } else {
                ivMoviePoster.setImageResource(R.drawable.avatar_image);
            }

            // Hiển thị thể loại (genre)
            ArrayList<String> genresList = new ArrayList<>();
            for (String genre : genres.split(",")) {
                genresList.add(genre.trim());
            }
            GenreAdapter genreAdapter = new GenreAdapter(this, genresList);
            recyclerViewGenres.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewGenres.setAdapter(genreAdapter);

            // Hiển thị dàn diễn viên (cast)
            ArrayList<String> actorsList = new ArrayList<>();
            for (String actor : actors.split(",")) {
                actorsList.add(actor.trim());
            }
            CastAdapter castAdapter = new CastAdapter(this, actorsList);
            recyclerViewCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewCast.setAdapter(castAdapter);
        } else {
            Toast.makeText(this, "Dữ liệu phim không hợp lệ", Toast.LENGTH_SHORT).show();
        }

        // Ánh xạ ImageView và thiết lập sự kiện click cho nút back
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());

        // Ánh xạ nút "Đặt Vé" và thêm sự kiện click để chuyển màn hình
        AppCompatButton buyTicketBtn = findViewById(R.id.buyTicketBtn);
        buyTicketBtn.setOnClickListener(v -> {
            // Chuyển sang màn hình SeatListActivity
            Intent intent = new Intent(DetailsMovieActivity.this, SeatListActivity.class);
            // Truyền dữ liệu phim vào Intent với key nhất quán
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("description", description);
            intent.putExtra("director", director);
            intent.putExtra("actors", actors);
            intent.putExtra("genres", genres);
            intent.putExtra("releaseDate", releaseDate);
            intent.putExtra("poster", poster);
            intent.putExtra("trailer", ""); // Thêm trailer nếu cần, hiện tại không có trong dữ liệu
            intent.putExtra("category", ""); // Thêm category nếu cần, hiện tại không có trong dữ liệu

            startActivity(intent);
        });
    }
}