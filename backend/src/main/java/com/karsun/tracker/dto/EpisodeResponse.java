package com.karsun.tracker.dto;

import java.time.Instant;

public class EpisodeResponse {

    private final Long id;
    private final Integer episodeNumber;
    private final String title;
    private final String status;
    private final Instant watchedAt;

    public EpisodeResponse(Long id, Integer episodeNumber, String title, String status, Instant watchedAt) {
        this.id = id;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.status = status;
        this.watchedAt = watchedAt;
    }

    public Long getId() {
        return id;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public Instant getWatchedAt() {
        return watchedAt;
    }
}
