package com.example.cinemaapp;

public class Movie {
    private String id;
    private String name;
    private String description;
    private String director;
    private String actors;
    private String genres;
    private String poster;
    private String trailer;
    private String releaseDate;
    private String category;  // Thêm trường category để phân biệt banner, topMovies, upcomingMovies

    public Movie(String id, String name, String description, String director, String actors, String genres,
                 String poster, String trailer, String releaseDate, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.director = director;
        this.actors = actors;
        this.genres = genres;
        this.poster = poster;
        this.trailer = trailer;
        this.releaseDate = releaseDate;
        this.category = category;
    }

    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
