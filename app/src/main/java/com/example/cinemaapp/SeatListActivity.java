package com.example.cinemaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SeatListActivity extends AppCompatActivity {

    private RecyclerView dateRecylerView, timeRecylerView, seatRecylerView, cinemaRecylerView;
    private DateAdapter dateAdapter;
    private TimeAdapter timeAdapter;
    private SeatAdapter seatAdapter;
    private CinemaAdapter cinemaAdapter;

    private List<Seat> selectedSeats = new ArrayList<>();
    private TextView totalPriceTextView, numberSelectedTextView;

    private static final int SEAT_PRICE = 90000;

    // Biến để lưu thông tin phim
    private String id, name, description, director, actors, genres, poster, trailer, releaseDate, category;

    // Biến để lưu thông tin đã chọn
    private String selectedCinema = null;
    private String selectedDate = null;
    private String selectedTime = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_list);

        // Nhận thông tin phim từ DetailsMovieActivity
        Intent intentFromMovie = getIntent();
        id = intentFromMovie.getStringExtra("id");
        name = intentFromMovie.getStringExtra("name");
        description = intentFromMovie.getStringExtra("description");
        director = intentFromMovie.getStringExtra("director");
        actors = intentFromMovie.getStringExtra("actors");
        genres = intentFromMovie.getStringExtra("genres");
        poster = intentFromMovie.getStringExtra("poster");
        trailer = intentFromMovie.getStringExtra("trailer");
        releaseDate = intentFromMovie.getStringExtra("releaseDate");
        category = intentFromMovie.getStringExtra("category");

        // Khởi tạo các RecyclerView
        dateRecylerView = findViewById(R.id.dateRecylerView);
        timeRecylerView = findViewById(R.id.TimeRecylerView);
        seatRecylerView = findViewById(R.id.seatRecylerView);
        cinemaRecylerView = findViewById(R.id.cinemasRecyclerView);

        totalPriceTextView = findViewById(R.id.textView7);
        numberSelectedTextView = findViewById(R.id.numberSelectedTxt);

        dateRecylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        timeRecylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        seatRecylerView.setLayoutManager(new GridLayoutManager(this, 9));
        cinemaRecylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Khởi tạo các adapter
        List<DateItem> dates = generateDatesWithMonth();
        List<String> times = generateTimes();
        dateAdapter = new DateAdapter(dates, dateItem -> {
            selectedDate = dateItem.getWeekday() + " " + dateItem.getDate();
            Log.d("SeatListActivity", "Selected Date: " + selectedDate);
        });
        timeAdapter = new TimeAdapter(times, time -> {
            selectedTime = time;
            Log.d("SeatListActivity", "Selected Time: " + selectedTime);
        });

        dateRecylerView.setAdapter(dateAdapter);
        timeRecylerView.setAdapter(timeAdapter);

        List<Seat> seats = generateSeats();
        seatAdapter = new SeatAdapter(seats, new SeatAdapter.OnSeatSelectedListener() {
            @Override
            public void onSeatSelected(Seat seat) {
                selectSeat(seat);
            }

            @Override
            public void onSeatDeselected(Seat seat) {
                deselectSeat(seat);
            }
        });
        seatRecylerView.setAdapter(seatAdapter);

        List<Cinema> cinemas = generateCinemas();
        cinemaAdapter = new CinemaAdapter(cinemas, cinema -> {
            selectedCinema = cinema.getName();
            TextView cinemaNameTextView = findViewById(R.id.textViewCinemaName);
            cinemaNameTextView.setText("Chọn Rạp: " + selectedCinema);
            Log.d("SeatListActivity", "Selected Cinema: " + selectedCinema);
        },this);
        cinemaRecylerView.setAdapter(cinemaAdapter);

        // Nút quay lại
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());

        // Nút tiếp tục (chuyển sang SnacksOrderActivity)
        findViewById(R.id.continueBtn).setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một ghế!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedCinema == null) {
                Toast.makeText(this, "Vui lòng chọn rạp chiếu phim!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedDate == null) {
                Toast.makeText(this, "Vui lòng chọn ngày chiếu!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedTime == null) {
                Toast.makeText(this, "Vui lòng chọn giờ chiếu!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Chuyển sang SnacksOrderActivity
            Intent intent = new Intent(SeatListActivity.this, SnacksOrderActivity.class);

            // Truyền danh sách ghế đã chọn
            ArrayList<String> selectedSeatsList = new ArrayList<>();
            for (Seat seat : selectedSeats) {
                selectedSeatsList.add(seat.getSeatNumber());
            }
            intent.putStringArrayListExtra("selectedSeats", selectedSeatsList);

            // Truyền số lượng ghế
            int numberOfSeats = selectedSeats.size();
            intent.putExtra("numberOfSeats", numberOfSeats);

            // Truyền tổng giá vé
            int totalPrice = selectedSeats.size() * SEAT_PRICE;
            intent.putExtra("totalPrice", totalPrice);

            // Truyền thông tin phim
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("description", description);
            intent.putExtra("director", director);
            intent.putExtra("actors", actors);
            intent.putExtra("genres", genres);
            intent.putExtra("poster", poster);
            intent.putExtra("trailer", trailer);
            intent.putExtra("releaseDate", releaseDate);
            intent.putExtra("category", category);

            // Truyền thông tin rạp, ngày, giờ
            intent.putExtra("selectedCinema", selectedCinema);
            intent.putExtra("selectedDate", selectedDate);
            intent.putExtra("selectedTime", selectedTime);

            startActivity(intent);
            finish();
        });
    }

    // Các phương thức khác (generateDatesWithMonth, generateTimes, generateSeats, generateCinemas, selectSeat, deselectSeat, updateUI)
    private List<DateItem> generateDatesWithMonth() {
        List<DateItem> dates = new ArrayList<>();
        String[] weekdays = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("d");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

        for (String weekday : weekdays) {
            String day = dayFormat.format(calendar.getTime());
            String month = monthFormat.format(calendar.getTime());
            dates.add(new DateItem(weekday, day + "/" + month));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dates;
    }

    private List<String> generateTimes() {
        List<String> times = new ArrayList<>();
        times.add("9:00 AM");
        times.add("11:00 AM");
        times.add("1:00 PM");
        times.add("3:00 PM");
        times.add("5:00 PM");
        times.add("7:00 PM");
        times.add("9:00 PM");
        return times;
    }

    private List<Seat> generateSeats() {
        List<Seat> seats = new ArrayList<>();
        char rowChar = 'A';
        int[] seatsInRow = {5, 7, 7, 7, 7, 7, 7, 5};

        for (int row = 0; row < seatsInRow.length; row++) {
            int numSeatsInRow = seatsInRow[row];
            int startCol = (9 - numSeatsInRow) / 2 + 1;

            for (int col = startCol; col < startCol + numSeatsInRow; col++) {
                String seatNumber = rowChar + String.format("%02d", col);
                boolean isUnavailable = (Math.random() < 0.1);
                seats.add(new Seat(seatNumber, isUnavailable));
            }
            rowChar++;
        }
        return seats;
    }

    private List<Cinema> generateCinemas() {
        List<Cinema> cinemas = new ArrayList<>();
        cinemas.add(new Cinema("CGV Nguyễn Thị Minh Khai, Nguyễn Thị Minh Khai, Q.1"));
        cinemas.add(new Cinema("Lotte Cinema Diamond, Diamond Plaza, Q.1"));
        cinemas.add(new Cinema("BHD Star Cinema, BHD Star, Q.3"));
        cinemas.add(new Cinema("Galaxy Nguyễn Du, Nguyễn Du, Q.1"));
        cinemas.add(new Cinema("CineStar Hồ Tùng Mậu, Hồ Tùng Mậu, Q.1"));
        return cinemas;
    }

    private void selectSeat(Seat seat) {
        if (selectedSeats.size() < 7 && !seat.isUnavailable()) {
            selectedSeats.add(seat);
            updateUI();
        }
    }

    private void deselectSeat(Seat seat) {
        selectedSeats.remove(seat);
        updateUI();
    }

    private void updateUI() {
        StringBuilder selectedSeatNumbers = new StringBuilder();
        for (Seat seat : selectedSeats) {
            if (selectedSeatNumbers.length() > 0) {
                selectedSeatNumbers.append(", ");
            }
            selectedSeatNumbers.append(seat.getSeatNumber());
        }
        numberSelectedTextView.setText(selectedSeatNumbers.toString() + " đã chọn");

        int totalPrice = selectedSeats.size() * SEAT_PRICE;
        totalPriceTextView.setText(String.format("%,d VNĐ", totalPrice));
    }
}