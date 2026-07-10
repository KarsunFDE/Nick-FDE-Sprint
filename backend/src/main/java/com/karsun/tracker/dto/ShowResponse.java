package com.karsun.tracker.dto;

import com.karsun.tracker.entity.Genre;

import java.time.Instant;
import java.util.List;

public class ShowResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final Instant createdAt;
    private final List<Genre> genres;
    private final List<SeasonResponse> seasons;

    public ShowResponse(Long id, String title, String description, Instant createdAt, List<Genre> genres, List<SeasonResponse> seasons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.genres = genres;
        this.seasons = seasons;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<SeasonResponse> getSeasons() {
        return seasons;
    }
}
