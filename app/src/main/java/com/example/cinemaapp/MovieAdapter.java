package com.example.cinemaapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movieList;
    private MovieCallback movieCallback;

    public MovieAdapter(Context context, ArrayList<Movie> movieList, MovieCallback movieCallback) {
        this.context = context;
        this.movieList = movieList;
        this.movieCallback = movieCallback;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        if (movie.getId().equals("-1") || movie.getId().equals("-2")) {
            // Nếu là tiêu đề, chỉ hiển thị tên với kiểu chữ lớn hơn và căn giữa
            holder.movieName.setText(movie.getName());
            holder.movieName.setTextSize(18);
            holder.movieName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.movieImage.setVisibility(View.GONE);
        } else {
            // Hiển thị tên và ảnh phim bình thường
            holder.movieName.setText(movie.getName());
            holder.movieName.setTextSize(14);
            holder.movieName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.movieImage.setVisibility(View.VISIBLE);

            Bitmap moviePoster = Utils.convertToBitmapFromAssets(context, movie.getPoster());
            if (moviePoster != null) {
                holder.movieImage.setImageBitmap(moviePoster);
            } else {
                holder.movieImage.setImageResource(R.drawable.avatar_image);
            }
        }

        // Thiết lập sự kiện click, bỏ qua tiêu đề
        holder.itemView.setOnClickListener(v -> {
            if (!movie.getId().equals("-1") && !movie.getId().equals("-2")) {
                movieCallback.onItemClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public void updateMovieList(ArrayList<Movie> newMovieList) {
        this.movieList = newMovieList;
        notifyDataSetChanged();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView movieName;
        ImageView movieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.nameTxT);
            movieImage = itemView.findViewById(R.id.pic);
        }
    }

    // Interface để callback khi item được click
    public interface MovieCallback {
        void onItemClick(Movie movie);
    }
}
