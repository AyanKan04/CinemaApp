package com.example.cinemaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieCallback {

    RecyclerView recyclerViewTopMovie, recyclerViewUpMovie, searchRecyclerView;
    ArrayList<Movie> topMovies, upcomingMovies, filteredMovies;
    MovieAdapter topMoviesAdapter, upcomingMoviesAdapter, searchAdapter;
    ProgressBar progressBarTopMovie, progressBarUpMovie, progressBarSlider;
    ViewPager2 viewPager2;
    ArrayList<String> bannerList;
    BottomNavigationView bottomNavigationView;
    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTopMovie = findViewById(R.id.recyclerViewTopMovie);
        recyclerViewUpMovie = findViewById(R.id.recyclerViewUpMovie);
        searchRecyclerView = findViewById(R.id.recyclerViewSearch);
        progressBarTopMovie = findViewById(R.id.progressBarTopMovie);
        progressBarUpMovie = findViewById(R.id.progressBarUpMovie);
        progressBarSlider = findViewById(R.id.progressBarSlider);
        viewPager2 = findViewById(R.id.viewPager22);
        searchEditText = findViewById(R.id.textSearch);

        progressBarSlider.setVisibility(View.VISIBLE);
        progressBarTopMovie.setVisibility(View.VISIBLE);
        progressBarUpMovie.setVisibility(View.VISIBLE);


        loadData();

        new Handler().postDelayed(() -> {
            progressBarTopMovie.setVisibility(View.GONE);
            progressBarUpMovie.setVisibility(View.GONE);
            progressBarSlider.setVisibility(View.GONE);

            // Cài đặt slider cho banner
            BannerAdapter bannerAdapter = new BannerAdapter(MainActivity.this, bannerList);
            viewPager2.setAdapter(bannerAdapter);

            // Áp dụng hiệu ứng chuyển trang cho ViewPager2
            viewPager2.setPageTransformer(new ZoomOutPageTransformer());
            // Bắt đầu tự động chuyển đổi banner sau một khoảng thời gian
            startAutoSlideBanner(viewPager2);

            topMoviesAdapter = new MovieAdapter(MainActivity.this, topMovies, MainActivity.this);
            recyclerViewTopMovie.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTopMovie.setAdapter(topMoviesAdapter);

            upcomingMoviesAdapter = new MovieAdapter(MainActivity.this, upcomingMovies, MainActivity.this);
            recyclerViewUpMovie.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewUpMovie.setAdapter(upcomingMoviesAdapter);
        }, 2000);

        ImageView imageView = findViewById(R.id.imgAvatar);
        imageView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AccountActivity.class)));

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                return true;
            } else if (item.getItemId() == R.id.favorites) {
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                return true;
            } else if (item.getItemId() == R.id.cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            } else if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
                return true;
            } else {
                return false;
            }
        });

        // Xử lý tìm kiếm
        filteredMovies = new ArrayList<>();
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new MovieAdapter(this, filteredMovies, this);
        searchRecyclerView.setAdapter(searchAdapter);
        searchRecyclerView.setVisibility(View.GONE);

        searchEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    // Hàm tự động chuyển đổi các banner trong ViewPager
    private void startAutoSlideBanner(ViewPager2 viewPager) {
        final int DELAY = 5000; // Thời gian chờ (5 giây) cho mỗi lần chuyển trang
        final int MAX_COUNT = bannerList.size(); // Số lượng banner

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int currentPage = 0;
            boolean isForward = true; // Dùng để xác định chiều di chuyển của banner

            @Override
            public void run() {
                // Chuyển banner theo chiều tiến hoặc lùi
                if (isForward) {
                    if (currentPage >= MAX_COUNT - 1) {
                        isForward = false; // Chuyển chiều khi đến banner cuối cùng
                    } else {
                        currentPage++;
                    }
                } else {
                    if (currentPage <= 0) {
                        isForward = true; // Chuyển chiều khi đến banner đầu tiên
                    } else {
                        currentPage--;
                    }
                }

                // Chuyển trang mượt mà
                viewPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, DELAY); // Lặp lại sau mỗi 5 giây
            }
        };

        handler.postDelayed(runnable, DELAY); // Bắt đầu tự động chuyển đổi banner
    }
    private void filterMovies(String keyword) {
        filteredMovies.clear();
        boolean hasTopMovies = false;
        boolean hasUpcomingMovies = false;

        // Chuyển keyword về dạng chữ thường để tìm kiếm không phân biệt hoa/thường
        String lowerCaseKeyword = keyword.toLowerCase();

        if (!lowerCaseKeyword.isEmpty()) {
            // Tìm kiếm trong danh sách phim mới nhất (topMovies)
            for (Movie movie : topMovies) {
                // So sánh tên phim với từ khóa (không phân biệt hoa thường)
                if (movie.getName().toLowerCase().contains(lowerCaseKeyword)) {
                    if (!hasTopMovies) {
                        // Thêm tiêu đề "Phim Mới Nhất" nếu chưa có
                        filteredMovies.add(new Movie("-1", "Phim Mới Nhất", "", "", "", "", "", "", "", ""));
                        hasTopMovies = true;
                    }
                    filteredMovies.add(movie);
                }
            }

            // Tìm kiếm trong danh sách phim sắp chiếu (upcomingMovies)
            for (Movie movie : upcomingMovies) {
                // So sánh tên phim với từ khóa (không phân biệt hoa thường)
                if (movie.getName().toLowerCase().contains(lowerCaseKeyword)) {
                    if (!hasUpcomingMovies) {
                        // Thêm tiêu đề "Phim Sắp Chiếu" nếu chưa có
                        filteredMovies.add(new Movie("-2", "Phim Sắp Chiếu", "", "", "", "", "", "", "", ""));
                        hasUpcomingMovies = true;
                    }
                    filteredMovies.add(movie);
                }
            }
        }

        // Nếu không có phim nào phù hợp với từ khóa, ẩn RecyclerView tìm kiếm
        if (filteredMovies.isEmpty()) {
            searchRecyclerView.setVisibility(View.GONE);
        } else {
            searchRecyclerView.setVisibility(View.VISIBLE);
        }

        // Cập nhật lại adapter để hiển thị dữ liệu mới
        searchAdapter.notifyDataSetChanged();
    }

    void loadData() {
        topMovies = new ArrayList<>();
        upcomingMovies = new ArrayList<>();

        bannerList = new ArrayList<>();
        bannerList.add("wide.jpg");
        bannerList.add("wide1.jpg");
        bannerList.add("wide3.jpg");
        bannerList.add("wide4.jpg");

        // Dữ liệu phim topMovies
        topMovies.add(new Movie("1", "Atlas",
                "Sau khi một thế lực hủy diệt toàn bộ nền văn minh, Atlas, một người lính ưu tú, phải chiến đấu chống lại một đội quân khủng khiếp để bảo vệ nhân loại. Trong một cuộc hành trình xuyên qua các vùng đất hoang tàn, anh phải đối mặt với những lựa chọn đầy thử thách để cứu thế giới khỏi sự diệt vong hoàn toàn.",
                "Brad Peyton",
                "Tom Hiddleston, Jennifer Lawrence, Idris Elba, Robert Pattinson",
                "Hành động, Khoa học viễn tưởng, Phiêu lưu",
                "atlas.jpg",
                "https://youtube.com/link_to_trailer1",
                "2024-03-18", "topMovies"));

        topMovies.add(new Movie("2", "Bad Boys",
                "Mike Lowrey và Marcus Burnett, hai cảnh sát của thành phố Miami, lại cùng nhau tham gia vào một cuộc điều tra nguy hiểm. Lần này, họ đối mặt với một thế lực tội phạm mới, và trong quá trình chiến đấu với bọn tội phạm, họ phải đối mặt với những thử thách đẩy tình bạn của họ đến giới hạn cực độ.",
                "Michael Bay",
                "Will Smith, Martin Lawrence, Gabrielle Union, Jordi Mollà",
                "Hành động, Hài, Tội phạm",
                "bad_boys.jpg",
                "https://youtube.com/link_to_trailer2",
                "1995-04-07", "topMovies"));

        topMovies.add(new Movie("3", "Fly Me To The Moon",
                "Câu chuyện kể về ba chú chuột nhỏ sống ở Trái Đất và mơ ước bay vào không gian. Khi họ phát hiện ra rằng một tàu vũ trụ chuẩn bị thực hiện một chuyến bay lên Mặt Trăng, họ quyết định tham gia vào chuyến đi này để thực hiện giấc mơ lớn nhất của đời mình. Hành trình của họ không chỉ là một cuộc phiêu lưu thú vị mà còn là một bài học về sự can đảm và tình bạn.",
                "Ben Stassen",
                "Kelly Ripa, Tim Curry, Christopher Lloyd",
                "Hoạt hình, Phiêu lưu, Gia đình",
                "fly_me_to_the_moon.jpg",
                "https://youtube.com/link_to_trailer3",
                "2008-08-15", "topMovies"));

        topMovies.add(new Movie("4", "Dune Part Two",
                "Sau những sự kiện trong phần một, Paul Atreides tiếp tục hành trình của mình trên hành tinh Arrakis. Anh phải đối mặt với nhiều thử thách mới trong việc trở thành lãnh đạo của các bộ lạc Fremen và chiến đấu với các thế lực mạnh mẽ để bảo vệ hành tinh cát và dân cư của nó.",
                "Denis Villeneuve",
                "Timothée Chalamet, Zendaya, Oscar Isaac, Javier Bardem",
                "Khoa học viễn tưởng, Phiêu lưu, Chính kịch",
                "dune_part_two.jpg",
                "https://youtube.com/link_to_trailer_dune2",
                "2024-10-20", "topMovies"));

        topMovies.add(new Movie("5", "Godzilla x Kong",
                "Cuộc chiến giữa Godzilla và Kong đã không dừng lại. Hai sinh vật huyền thoại này sẽ tiếp tục đối đầu với nhau khi một thế lực mới, thậm chí còn nguy hiểm hơn, xuất hiện. Liệu nhân loại có thể sống sót qua cuộc chiến tàn khốc này?",
                "Adam Wingard",
                "Alexander Skarsgård, Millie Bobby Brown, Rebecca Hall, Kyle Chandler",
                "Hành động, Khoa học viễn tưởng, Phiêu lưu",
                "godzilla_x_kong.jpg",
                "https://youtube.com/link_to_trailer_godzillavskong",
                "2021-03-24", "topMovies"));

        topMovies.add(new Movie("6", "Immaculate",
                "Một nữ tu sĩ sống tại một tu viện hẻo lánh bắt đầu nghi ngờ về sự trong sạch của mình khi một loạt sự kiện kỳ lạ xảy ra tại tu viện. Cô phải đối mặt với những bí mật bị che giấu và đấu tranh với niềm tin tôn giáo khi những ám ảnh trong quá khứ bủa vây.",
                "John Smith",
                "Emma Watson, Tom Hiddleston, Jessica Chastain, Idris Elba",
                "Kinh dị, Chính kịch, Tâm lý",
                "immaculate.jpg",
                "https://youtube.com/link_to_trailer_immaculate",
                "2023-10-10", "topMovies"));

// Dữ liệu phim upcomingMovies
        upcomingMovies.add(new Movie("7", "Bad Boys Ride Or Die",
                "Cặp đôi cảnh sát Mike và Marcus trở lại trong cuộc chiến với một tổ chức tội phạm quốc tế. Tình bạn giữa họ sẽ bị thử thách khi họ phải đối mặt với những kẻ thù mạnh mẽ và nguy hiểm hơn bao giờ hết.",
                "Michael Bay",
                "Will Smith, Martin Lawrence, Vanessa Hudgens, Jacob Lattimore",
                "Hành động, Hài, Tội phạm",
                "bad_boys_ride_or_die.jpg",
                "https://youtube.com/link_to_trailer_badboysrideordie",
                "2024-06-15", "upcomingMovies"));

        upcomingMovies.add(new Movie("8", "Madmax",
                "Max, người anh hùng cô độc trong thế giới hậu tận thế, tiếp tục hành trình đấu tranh để tìm kiếm sự công lý trong một thế giới đầy rẫy những kẻ độc tài và bạo lực. Cùng Furiosa, họ chống lại những thế lực xấu xa nhằm tạo ra một tương lai tươi sáng hơn.",
                "George Miller",
                "Tom Hardy, Charlize Theron, Nicholas Hoult, Hugh Keays-Byrne",
                "Hành động, Khoa học viễn tưởng, Phiêu lưu",
                "madmax.jpg",
                "https://youtube.com/link_to_trailer_madmax",
                "2024-11-05", "upcomingMovies"));

        upcomingMovies.add(new Movie("9", "Ordinary Angels",
                "Một người phụ nữ bình thường từ một thị trấn nhỏ bất ngờ trở thành người hùng khi cô tìm mọi cách để cứu sống con gái của mình, bất chấp mọi thử thách và khó khăn. Một câu chuyện cảm động về sự hy sinh và tình yêu vô điều kiện.",
                "Sharon Maguire",
                "Reese Witherspoon, Julia Roberts, Nicole Kidman, Kevin Spacey",
                "Chính kịch, Gia đình",
                "ordinary_angels.jpg",
                "https://youtube.com/link_to_trailer_ordinaryangels",
                "2024-07-20", "upcomingMovies"));

        upcomingMovies.add(new Movie("10", "The Fall Guy",
                "Một diễn viên đóng thế nổi tiếng phải đối mặt với một vụ án giết người mà anh ta vô tình bị cuốn vào. Anh phải dùng mọi mánh khóe để tìm ra sự thật trong thế giới Hollywood đầy rẫy sự lừa dối và tham vọng.",
                "David Leitch",
                "Ryan Gosling, Margot Robbie, Dwayne Johnson, Helen Mirren",
                "Hành động, Chính kịch, Tội phạm",
                "the_fall_guy.jpg",
                "https://youtube.com/link_to_trailer_thefallguy",
                "2024-08-30", "upcomingMovies"));

        upcomingMovies.add(new Movie("11", "Kungfu Panda 4",
                "Panda Po quay trở lại với những cuộc phiêu lưu hài hước và cảm động. Trong phần này, Po sẽ đối mặt với một kẻ thù mạnh mẽ mới và học hỏi thêm về di sản của mình trong hành trình trở thành chiến binh Kung Fu thực thụ.",
                "Jennifer Yuh Nelson",
                "Jack Black, Angelina Jolie, Seth Rogen, Dustin Hoffman",
                "Hoạt hình, Hành động, Phiêu lưu",
                "kungfupanda4.jpg",
                "https://youtube.com/link_to_trailer_kungfupanda4",
                "2024-12-12", "upcomingMovies"));

        upcomingMovies.add(new Movie("12", "No Way Up",
                "Khi một nhóm phi hành gia bị mắc kẹt trong không gian và phải đối mặt với sự sụp đổ của trạm không gian của họ, họ buộc phải đối mặt với những thử thách sinh tử. Cuộc chiến giành sự sống không chỉ diễn ra trong không gian mà còn là cuộc chiến với chính nội tâm của họ.",
                "Renny Harlin",
                "Tom Hanks, Sandra Bullock, George Clooney",
                "Khoa học viễn tưởng, Hành động, Chính kịch",
                "no_way_up.jpg",
                "https://youtube.com/link_to_trailer_nowayup",
                "2024-09-25", "upcomingMovies"));

    }


    @Override
    public void onItemClick(Movie movie) {
        if (!movie.getId().equals("-1") && !movie.getId().equals("-2")) {
            Intent intent = new Intent(this, DetailsMovieActivity.class);
            intent.putExtra("movieId", movie.getId());
            intent.putExtra("movieName", movie.getName());
            intent.putExtra("movieDescription", movie.getDescription());
            intent.putExtra("movieDirector", movie.getDirector());
            intent.putExtra("movieActors", movie.getActors());
            intent.putExtra("movieGenres", movie.getGenres());
            intent.putExtra("movieReleaseDate", movie.getReleaseDate());
            intent.putExtra("moviePoster", movie.getPoster());
            startActivity(intent);
        }
    }

    // Hiệu ứng chuyển trang zoom cho ViewPager2
    private static class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        @Override
        public void transformPage(@NonNull View page, float position) {
            int pageWidth = page.getWidth();
            page.setTranslationX(-position * pageWidth); // Dịch chuyển trang
            page.setAlpha(1 - Math.abs(position)); // Điều chỉnh độ mờ của trang

            // Áp dụng hiệu ứng zoom
            if (position < -1) {
                page.setAlpha(0); // Nếu trang ở ngoài bên trái, độ mờ là 0
            } else if (position <= 1) {
                page.setAlpha(1 - Math.abs(position)); // Điều chỉnh độ mờ tùy theo vị trí
            } else {
                page.setAlpha(0); // Nếu trang ở ngoài bên phải, độ mờ là 0
            }
        }
    }
}
